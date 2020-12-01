package codes.mydna.services.impl;

import codes.mydna.entities.GeneEntity;
import codes.mydna.exceptions.BadRequestException;
import codes.mydna.exceptions.NotFoundException;
import codes.mydna.lib.Gene;
import codes.mydna.mappers.GeneMapper;
import codes.mydna.services.GeneService;
import codes.mydna.utils.EntityList;
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

    private static Logger LOG = Logger.getLogger(GeneServiceImpl.class.getSimpleName());
    private String uuid;

    @Inject
    private EntityManager em;

    @PostConstruct
    private void pc(){
        uuid = UUID.randomUUID().toString();
        LOG.info(GeneServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be initialized");
    }

    @PreDestroy
    private void pd(){
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
        if(id == null)
            throw new BadRequestException("Id cannot be null.");

        GeneEntity entity = getGeneEntity(id);
        Gene gene = GeneMapper.fromEntity(entity);
        if(gene == null)
            throw new NotFoundException(Gene.class, id);

        return gene;
    }

    @Override
    public Gene insertGene(Gene gene) {

        if(gene == null)
            throw new BadRequestException("Gene object not provided.");
        if(gene.getName() == null || gene.getName().isEmpty())
            throw new BadRequestException("Field 'name' of Gene object is invalid.");

        GeneEntity geneEntity = GeneMapper.toEntity(gene);

        em.getTransaction().begin();
        em.persist(geneEntity);
        em.getTransaction().commit();

        return GeneMapper.fromEntity(geneEntity);
    }

    @Override
    public Gene updateGene(Gene gene, String id) {
        if(id == null)
            throw new BadRequestException("Id cannot be null.");
        if(gene == null)
            throw new BadRequestException("Gene object not provided.");

        GeneEntity old = getGeneEntity(id);
        if(old == null)
            throw new NotFoundException(Gene.class, id);

        GeneEntity entity = GeneMapper.toEntity(gene);
        entity.setId(id);

        // Prevent cascade update if sequence is null or empty
        if(entity.getSequence() == null || entity.getSequence().getValue().isEmpty())
            entity.setSequence(old.getSequence());

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        return GeneMapper.fromEntity(entity);
    }

    @Override
    public boolean removeGene(String id) {
        GeneEntity entity = getGeneEntity(id);
        if(entity == null)
            return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }

    public GeneEntity getGeneEntity(String id){
        if(id == null)
            return null;
        return em.find(GeneEntity.class, id);
    }

}
