package codes.mydna.services.impl;

import codes.mydna.entities.DnaEntity;
import codes.mydna.lib.enums.SequenceType;
import codes.mydna.exceptions.NotFoundException;
import codes.mydna.lib.Dna;
import codes.mydna.lib.Sequence;
import codes.mydna.mappers.DnaMapper;
import codes.mydna.mappers.SequenceMapper;
import codes.mydna.services.DnaService;
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
public class DnaServiceImpl implements DnaService {

    private static final Logger LOG = Logger.getLogger(DnaServiceImpl.class.getName());
    private String uuid;

    @Inject
    private EntityManager em;

    @Inject
    private SequenceService sequenceService;

    @PostConstruct
    private void pc() {
        uuid = UUID.randomUUID().toString();
        LOG.info(DnaServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be initialized");
    }

    @PreDestroy
    private void pd() {
        LOG.info(DnaServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be destroyed");
    }

    @Override
    public EntityList<Dna> getDnas(QueryParameters qp) {
        List<Dna> dnas = JPAUtils.queryEntities(em, DnaEntity.class, qp)
                .stream()
                .map(DnaMapper::fromEntityLazy)
                .collect(Collectors.toList());
        Long count = JPAUtils.queryEntitiesCount(em, DnaEntity.class);
        return new EntityList<>(dnas, count);
    }

    @Override
    public Dna getDna(String id) {
        Assert.fieldNotEmpty(id, "id");

        DnaEntity entity = getDnaEntity(id);
        Dna dna = DnaMapper.fromEntity(entity);
        if (dna == null)
            throw new NotFoundException(Dna.class, id);

        return dna;
    }

    @Override
    public Dna insertDna(Dna dna) {

        Assert.objectNotNull(dna, Dna.class);
        Assert.fieldNotEmpty(dna.getName(), "name", Dna.class);

        DnaEntity dnaEntity = DnaMapper.toEntity(dna);

        Sequence insertedSequence = sequenceService.insertSequence(dna.getSequence(), SequenceType.DNA);
        dnaEntity.setSequence(SequenceMapper.toEntity(insertedSequence));

        em.getTransaction().begin();
        em.persist(dnaEntity);
        em.getTransaction().commit();

        return DnaMapper.fromEntity(dnaEntity);
    }

    @Override
    public Dna updateDna(Dna dna, String id) {

        Assert.fieldNotNull(id, "id");
        Assert.objectNotNull(dna, Dna.class);

        DnaEntity old = getDnaEntity(id);
        if (old == null)
            throw new NotFoundException(Dna.class, id);

        DnaEntity entity = DnaMapper.toEntity(dna);
        entity.setId(id);

        Sequence updatedSequence = sequenceService.updateSequence(dna.getSequence(), SequenceType.DNA, id);
        entity.setSequence(SequenceMapper.toEntity(updatedSequence));

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        return DnaMapper.fromEntity(entity);
    }

    @Override
    public boolean removeDna(String id) {
        DnaEntity entity = getDnaEntity(id);
        if (entity == null)
            return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }

    public DnaEntity getDnaEntity(String id) {
        if (id == null)
            return null;
        return em.find(DnaEntity.class, id);
    }

}
