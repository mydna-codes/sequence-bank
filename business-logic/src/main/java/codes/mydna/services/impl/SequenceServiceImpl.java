package codes.mydna.services.impl;

import codes.mydna.auth.common.models.User;
import codes.mydna.configurations.UserLimits;
import codes.mydna.entities.SequenceEntity;
import codes.mydna.exceptions.BadRequestException;
import codes.mydna.exceptions.NotFoundException;
import codes.mydna.lib.Sequence;
import codes.mydna.lib.enums.SequenceType;
import codes.mydna.lib.util.BasePairUtil;
import codes.mydna.mappers.SequenceMapper;
import codes.mydna.services.SequenceService;
import codes.mydna.validation.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.InternalServerErrorException;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class SequenceServiceImpl implements SequenceService {

    private static final Logger LOG = Logger.getLogger(SequenceServiceImpl.class.getName());
    private String uuid;

    @Inject
    private EntityManager em;

    @Inject
    private UserLimits userLimits;

    @PostConstruct
    private void pc() {
        uuid = UUID.randomUUID().toString();
        LOG.info(SequenceServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be initialized");
    }

    @PreDestroy
    private void pd() {
        LOG.info(SequenceServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be destroyed");
    }

    @Override
    public Sequence insertSequence(Sequence sequence, SequenceType type, User user) {

        Assert.objectNotNull(sequence, Sequence.class);
        Assert.fieldNotEmpty(sequence.getValue(), "value", Sequence.class);

        validateSequenceLength(sequence.getValue(), type, user);

        sequence.setValue(sequence.getValue().toUpperCase());

        SequenceEntity sequenceEntity = SequenceMapper.toEntity(sequence);

        validateSequence(sequenceEntity, type, user);

        return SequenceMapper.fromEntity(sequenceEntity);
    }

    @Override
    public Sequence updateSequence(Sequence sequence, SequenceType type, String id, User user) {

        Assert.fieldNotNull(id, "id");

        SequenceEntity old = getSequenceEntity(id);
        if (old == null)
            throw new NotFoundException(Sequence.class, id);

        // Dynamic update
        if (sequence == null || sequence.getValue() == null || sequence.getValue().isEmpty())
            return SequenceMapper.fromEntity(old);

        sequence.setCreated(sequence.getCreated());
        sequence.setValue(sequence.getValue().toUpperCase());

        SequenceEntity entity = SequenceMapper.toEntity(sequence);
        entity.setId(id);

        validateSequence(entity, type, user);

        return SequenceMapper.fromEntity(entity);
    }

    public SequenceEntity getSequenceEntity(String id) {
        if (id == null)
            return null;
        return em.find(SequenceEntity.class, id);
    }

    private void validateSequence(SequenceEntity sequence, SequenceType type, User user) {
        validateSequenceLength(sequence.getValue(), type, user);
        validateSequenceContent(sequence, type);
    }

    private void validateSequenceLength(String sequence, SequenceType type, User user){
        int maxLength;

        if(type == SequenceType.DNA) {
            maxLength = userLimits.getMaxDnaLength(user);
        } else if (type == SequenceType.ENZYME) {
            maxLength = userLimits.getMaxEnzymeLength(user);
        } else if (type == SequenceType.GENE) {
            maxLength = userLimits.getMaxGeneLength(user);
        } else {
            throw new InternalServerErrorException("Provided SequenceType does not exist");
        }

        if (sequence.length() > maxLength) {
            throw new BadRequestException("Sequence is too long!");
        }
    }

    private void validateSequenceContent(SequenceEntity sequence, SequenceType type){
        if (!BasePairUtil.isSequenceValid(sequence.getValue(), type)) {
            throw new BadRequestException("Sequence contains invalid characters! Valid characters are: "
                    + Arrays.toString(type.getValidCharacters()));
        }
    }

}
