package codes.mydna.services.impl;

import codes.mydna.auth.common.models.User;
import codes.mydna.entities.GeneEntity;
import codes.mydna.exceptions.NotFoundException;
import codes.mydna.lib.Gene;
import codes.mydna.lib.Sequence;
import codes.mydna.lib.enums.SequenceAccessType;
import codes.mydna.lib.enums.SequenceType;
import codes.mydna.mappers.GeneMapper;
import codes.mydna.mappers.SequenceMapper;
import codes.mydna.services.GeneService;
import codes.mydna.services.SequenceService;
import codes.mydna.util.AuthorizationUtil;
import codes.mydna.utils.EntityList;
import codes.mydna.utils.QueryUtil;
import codes.mydna.validation.Assert;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    public EntityList<Gene> getGenes(QueryParameters qp, User user) {

        String userId = (user == null) ? null : user.getId();

        long limit = qp.getLimit();
        long offset = qp.getOffset();
        QueryParameters queryParameters = QueryUtil.removeLimitAndOffset(qp);

        List<Gene> genes = JPAUtils.queryEntities(em, GeneEntity.class, queryParameters)
                .stream()
                .filter(entity ->
                        entity.getOwnerId().equals(userId) || entity.getAccess().equals(SequenceAccessType.PUBLIC)
                )
                .skip(offset)
                .limit(limit)
                .map(GeneMapper::fromEntityLazy)
                .collect(Collectors.toList());

        Long count = JPAUtils.queryEntitiesCount(em, GeneEntity.class, queryParameters, (p, cb, r)
                -> (cb.and(p, cb.or(cb.equal(r.get("access"), SequenceAccessType.PUBLIC), cb.equal(r.get("ownerId"), userId)))));

        return new EntityList<>(genes, count);
    }

    @Override
    public Gene getGene(String id, User user) {

        Assert.fieldNotNull(id, "id");

        try {
            GeneEntity entity = (GeneEntity) em.createNamedQuery(GeneEntity.GET_PUBLIC_OR_BY_OWNER_ID)
                    .setParameter("id", id)
                    .setParameter("owner_id", user != null ? user.getId() : null)
                    .getSingleResult();
            return GeneMapper.fromEntity(entity);

        } catch (NoResultException e) {
            throw new NotFoundException(Gene.class, id);
        }
    }

    @Override
    public Gene insertGene(Gene gene, User user) {

        Assert.userNotNull(user);
        Assert.objectNotNull(gene, Gene.class);
        Assert.fieldNotEmpty(gene.getName(), "name", Gene.class);

        AuthorizationUtil.verifyModification(gene.getAccess(), user);

        GeneEntity geneEntity = GeneMapper.toEntity(gene);
        geneEntity.setOwnerId(user.getId());

        Sequence insertSequence = sequenceService.insertSequence(gene.getSequence(), SequenceType.GENE, user);
        geneEntity.setSequence(SequenceMapper.toEntity(insertSequence));

        em.getTransaction().begin();
        em.persist(geneEntity);
        em.getTransaction().commit();

        return GeneMapper.fromEntity(geneEntity);
    }

    @Override
    public Gene updateGene(Gene gene, String id, User user) {

        Assert.userNotNull(user);
        Assert.fieldNotNull(id, "id");
        Assert.objectNotNull(gene, Gene.class);

        GeneEntity old = getGeneEntity(id, user);
        if (old == null) throw new NotFoundException(Gene.class, id);

        // Only owner can update sequence
        AuthorizationUtil.verifyOwner(old, user);

        GeneEntity entity = GeneMapper.toEntity(gene);
        entity.setId(id);
        entity.setCreated(old.getCreated());

        Sequence updatedSequence = sequenceService.updateSequence(gene.getSequence(), SequenceType.GENE, old.getSequence().getId(), user);
        entity.setSequence(SequenceMapper.toEntity(updatedSequence));

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        return GeneMapper.fromEntity(getGeneEntity(entity.getId(), user));
    }

    @Override
    public boolean removeGene(String id, User user) {

        Assert.userNotNull(user);

        GeneEntity entity = getGeneEntity(id, user);

        if (entity == null)
            return false;

        // Only owner can remove sequence
        AuthorizationUtil.verifyOwner(entity, user);

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }


    public GeneEntity getGeneEntity(String id, User user) {
        try {
            return (GeneEntity) em.createNamedQuery(GeneEntity.GET_PUBLIC_OR_BY_OWNER_ID)
                    .setParameter("id", id)
                    .setParameter("owner_id", user.getId())
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
}
