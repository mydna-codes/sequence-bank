package codes.mydna.services.impl;

import codes.mydna.entities.EnzymeEntity;
import codes.mydna.lib.enums.SequenceType;
import codes.mydna.exceptions.BadRequestException;
import codes.mydna.exceptions.NotFoundException;
import codes.mydna.lib.Enzyme;
import codes.mydna.lib.Sequence;
import codes.mydna.mappers.EnzymeMapper;
import codes.mydna.mappers.SequenceMapper;
import codes.mydna.services.EnzymeService;
import codes.mydna.services.SequenceService;
import codes.mydna.utils.EntityList;
import codes.mydna.validation.Assert;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class EnzymeServiceImpl implements EnzymeService {

    private static final Logger LOG = Logger.getLogger(EnzymeServiceImpl.class.getName());
    private String uuid;

    @Inject
    private EntityManager em;

    @Inject
    private SequenceService sequenceService;

    @PostConstruct
    private void pc() {
        uuid = UUID.randomUUID().toString();
        LOG.info(EnzymeServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be initialized");
    }

    @PreDestroy
    private void pd() {
        LOG.info(EnzymeServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be destroyed");
    }

    @Override
    public EntityList<Enzyme> getEnzymes(QueryParameters qp) {
        List<Enzyme> enzymes = JPAUtils.queryEntities(em, EnzymeEntity.class, qp)
                .stream()
                .map(EnzymeMapper::fromEntityLazy)
                .collect(Collectors.toList());
        Long count = JPAUtils.queryEntitiesCount(em, EnzymeEntity.class);
        return new EntityList<>(enzymes, count);
    }

    @Override
    public Enzyme getEnzyme(String id) {
        Assert.fieldNotNull(id, "id");

        EnzymeEntity entity = getEnzymeEntity(id);
        Enzyme enzyme = EnzymeMapper.fromEntity(entity);
        if (enzyme == null)
            throw new NotFoundException(Enzyme.class, id);

        return enzyme;
    }

    @Override
    public Enzyme insertEnzyme(Enzyme enzyme) {

        Assert.objectNotNull(enzyme, Enzyme.class);
        Assert.fieldNotEmpty(enzyme.getName(), "name", Enzyme.class);

        EnzymeEntity enzymeEntity = EnzymeMapper.toEntity(enzyme);

        Sequence insertedSequence = sequenceService.insertSequence(enzyme.getSequence(), SequenceType.ENZYME);
        enzymeEntity.setSequence(SequenceMapper.toEntity(insertedSequence));

        // Add symmetric (mirrored) lower cut
        enzymeEntity.setLowerCut(enzymeEntity.getSequence().getValue().length() - enzymeEntity.getUpperCut());

        validateEnzymeCuts(enzymeEntity);

        em.getTransaction().begin();
        em.persist(enzymeEntity);
        em.getTransaction().commit();

        return EnzymeMapper.fromEntity(enzymeEntity);
    }

    @Override
    public Enzyme updateEnzyme(Enzyme enzyme, String id) {

        Assert.fieldNotNull(id, "id");
        Assert.objectNotNull(enzyme, Enzyme.class);

        EnzymeEntity old = getEnzymeEntity(id);
        if (old == null) throw new NotFoundException(Enzyme.class, id);

        EnzymeEntity entity = EnzymeMapper.toEntity(enzyme);
        entity.setId(id);
        entity.setCreated(old.getCreated());

        Sequence updatedSeq = sequenceService.updateSequence(enzyme.getSequence(), SequenceType.ENZYME, old.getSequence().getId());
        entity.setSequence(SequenceMapper.toEntity(updatedSeq));

        // Dynamic update "upperCut"
        entity.setUpperCut(enzyme.getUpperCut() == null
                ? old.getUpperCut()
                : enzyme.getUpperCut());

        // Add symmetric (mirrored) lower cut
        entity.setLowerCut(entity.getSequence().getValue().length() - entity.getUpperCut());

        validateEnzymeCuts(entity);

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        return EnzymeMapper.fromEntity(getEnzymeEntity(entity.getId()));
    }

    @Override
    public boolean removeEnzyme(String id) {
        EnzymeEntity entity = getEnzymeEntity(id);
        if (entity == null)
            return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }

    public EnzymeEntity getEnzymeEntity(String id) {
        if (id == null)
            return null;
        return em.find(EnzymeEntity.class, id);
    }

    // Must be called after sequence is set
    private void validateEnzymeCuts(EnzymeEntity enzyme) {

        Assert.fieldNotNull(enzyme.getUpperCut(), "upperCut", Enzyme.class);
        Assert.fieldNotNull(enzyme.getLowerCut(), "lowerCut", Enzyme.class);

        if (enzyme.getUpperCut() > enzyme.getSequence().getValue().length())
            throw new BadRequestException("Enzyme is not valid because 'upperCut' cannot be bigger than sequence length.");

        if (enzyme.getUpperCut() < 0)
            throw new BadRequestException("Enzyme is not valid because 'upperCut' cannot be less than 0.");

        if (enzyme.getLowerCut() > enzyme.getSequence().getValue().length())
            throw new BadRequestException("Enzyme is not valid because 'lowerCut' cannot be bigger than sequence length.");

        if (enzyme.getLowerCut() < 0)
            throw new BadRequestException("Enzyme is not valid because 'lowerCut' cannot be less than 0.");
    }

}
