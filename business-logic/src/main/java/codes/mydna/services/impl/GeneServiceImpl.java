package codes.mydna.services.impl;

import codes.mydna.entities.GeneEntity;
import codes.mydna.entities.enums.SequenceType;
import codes.mydna.exceptions.NotFoundException;
import codes.mydna.lib.Gene;
import codes.mydna.lib.Sequence;
import codes.mydna.mappers.GeneMapper;
import codes.mydna.mappers.SequenceMapper;
import codes.mydna.services.GeneService;
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
public class GeneServiceImpl implements GeneService {

    private static final Logger LOG = Logger.getLogger(GeneServiceImpl.class.getName());
    private String uuid;

    @Inject
    private EntityManager em;

    @Inject
    private SequenceService sequenceService;

    @PostConstruct
    private void pc() {
        uuid = UUID.randomUUID().toString();
        LOG.info(GeneServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be initialized");
    }

    @PreDestroy
    private void pd() {
        LOG.info(GeneServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be destroyed");
    }

    @Override
    public EntityList<Gene> getGenes(QueryParameters qp) {
        List<Gene> genes = JPAUtils.queryEntities(em, GeneEntity.class, qp)
                .stream()
                .map(GeneMapper::fromEntityLazy)
                .collect(Collectors.toList());
        Long count = JPAUtils.queryEntitiesCount(em, GeneEntity.class);
        return new EntityList<>(genes, count);
    }

    @Override
    public Gene getGene(String id) {

        Assert.fieldNotNull(id, "id");

        GeneEntity entity = getGeneEntity(id);
        Gene gene = GeneMapper.fromEntity(entity);
        if (gene == null)
            throw new NotFoundException(Gene.class, id);

        return gene;
    }

    @Override
    public Gene insertGene(Gene gene) {

        Assert.objectNotNull(gene, Gene.class);
        Assert.fieldNotEmpty(gene.getName(), "name", Gene.class);

        GeneEntity geneEntity = GeneMapper.toEntity(gene);

        Sequence insertSequence = sequenceService.insertSequence(gene.getSequence(), SequenceType.GENE);
        geneEntity.setSequence(SequenceMapper.toEntity(insertSequence));

        em.getTransaction().begin();
        em.persist(geneEntity);
        em.getTransaction().commit();

        return GeneMapper.fromEntity(geneEntity);
    }

    @Override
    public Gene updateGene(Gene gene, String id) {

        Assert.fieldNotNull(id, "id");
        Assert.objectNotNull(gene, Gene.class);

        GeneEntity old = getGeneEntity(id);
        if (old == null)
            throw new NotFoundException(Gene.class, id);

        GeneEntity entity = GeneMapper.toEntity(gene);
        entity.setId(id);

        Sequence updatedSequence = sequenceService.updateSequence(gene.getSequence(), SequenceType.GENE, id);
        entity.setSequence(SequenceMapper.toEntity(updatedSequence));

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        return GeneMapper.fromEntity(entity);
    }

    @Override
    public boolean removeGene(String id) {
        GeneEntity entity = getGeneEntity(id);
        if (entity == null)
            return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }

    public GeneEntity getGeneEntity(String id) {
        if (id == null)
            return null;
        return em.find(GeneEntity.class, id);
    }

}
