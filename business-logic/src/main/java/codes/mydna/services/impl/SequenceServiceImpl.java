package codes.mydna.services.impl;

import codes.mydna.entities.SequenceEntity;
import codes.mydna.entities.enums.SequenceType;
import codes.mydna.exceptions.BadRequestException;
import codes.mydna.exceptions.NotFoundException;
import codes.mydna.lib.Sequence;
import codes.mydna.mappers.SequenceMapper;
import codes.mydna.services.SequenceService;
import codes.mydna.util.BasePairUtil;
import codes.mydna.validation.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class SequenceServiceImpl implements SequenceService {

    private static final Logger LOG = Logger.getLogger(SequenceServiceImpl.class.getName());
    private String uuid;

    @Inject
    private EntityManager em;

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
    public Sequence insertSequence(Sequence sequence, SequenceType type) {

        Assert.objectNotNull(sequence, Sequence.class);
        Assert.fieldNotEmpty(sequence.getValue(), "value", Sequence.class);

        SequenceEntity sequenceEntity = SequenceMapper.toEntity(sequence);

        if (sequence.getValue().length() > type.getMaxSequenceLength())
            throw new BadRequestException("Sequence is too long!");

        validateSequence(sequenceEntity, type);

        return SequenceMapper.fromEntity(sequenceEntity);
    }

    @Override
    public Sequence updateSequence(Sequence sequence, SequenceType type, String id) {

        Assert.fieldNotNull(id, "id");

        SequenceEntity old = getSequenceEntity(id);
        if (old == null)
            throw new NotFoundException(Sequence.class, id);

        // Dynamic update
        if (sequence == null || sequence.getValue() == null || sequence.getValue().isEmpty())
            return SequenceMapper.fromEntity(old);

        SequenceEntity entity = SequenceMapper.toEntity(sequence);
        entity.setId(id);

        validateSequence(entity, type);

        return SequenceMapper.fromEntity(entity);
    }

    //    @Override
    public boolean removeSequence(String id) {
        SequenceEntity entity = getSequenceEntity(id);
        if (entity == null)
            return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }

    public SequenceEntity getSequenceEntity(String id) {
        if (id == null)
            return null;
        return em.find(SequenceEntity.class, id);
    }

    private void validateSequence(SequenceEntity sequence, SequenceType type) {

        // Length validation
        if (sequence.getValue().length() > type.getMaxSequenceLength())
            throw new BadRequestException("Sequence is too long!");

        // Content validation
        if (!BasePairUtil.isSequenceValid(sequence.getValue(), type))
            throw new BadRequestException("Sequence contains invalid characters! Valid characters are: " + Arrays.toString(type.getValidCharacters()));
    }

}
