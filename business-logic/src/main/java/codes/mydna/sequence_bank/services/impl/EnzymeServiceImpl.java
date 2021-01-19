package codes.mydna.sequence_bank.services.impl;

import codes.mydna.auth.common.models.User;
import codes.mydna.rest.exceptions.BadRequestException;
import codes.mydna.rest.exceptions.NotFoundException;
import codes.mydna.rest.utils.EntityList;
import codes.mydna.rest.utils.QueryUtil;
import codes.mydna.rest.validation.Assert;
import codes.mydna.sequence_bank.entities.EnzymeEntity;
import codes.mydna.sequence_bank.lib.Enzyme;
import codes.mydna.sequence_bank.lib.Sequence;
import codes.mydna.sequence_bank.lib.enums.SequenceAccessType;
import codes.mydna.sequence_bank.lib.enums.SequenceType;
import codes.mydna.sequence_bank.mappers.EnzymeMapper;
import codes.mydna.sequence_bank.mappers.SequenceMapper;
import codes.mydna.sequence_bank.services.EnzymeService;
import codes.mydna.sequence_bank.services.SequenceService;
import codes.mydna.sequence_bank.util.AuthorizationUtil;
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
    public EntityList<Enzyme> getEnzymes(QueryParameters qp, User user) {

        String userId = (user == null) ? null : user.getId();

        long limit = qp.getLimit();
        long offset = qp.getOffset();
        QueryParameters queryParameters = QueryUtil.removeLimitAndOffset(qp);

        List<Enzyme> enzymes = JPAUtils.queryEntities(em, EnzymeEntity.class, queryParameters)
                .stream()
                .filter(entity ->
                        entity.getOwnerId().equals(userId) || entity.getAccess().equals(SequenceAccessType.PUBLIC)
                )
                .skip(offset)
                .limit(limit)
                .map(EnzymeMapper::fromEntityLazy)
                .collect(Collectors.toList());

        Long count = JPAUtils.queryEntitiesCount(em, EnzymeEntity.class, queryParameters, (p, cb, r)
                -> (cb.and(p, cb.or(cb.equal(r.get("access"), SequenceAccessType.PUBLIC), cb.equal(r.get("ownerId"), userId)))));

        return new EntityList<>(enzymes, count);
    }

    @Override
    public Enzyme getEnzyme(String id, User user) {

        Assert.fieldNotNull(id, "id");

        try {
            EnzymeEntity entity = (EnzymeEntity) em.createNamedQuery(EnzymeEntity.GET_PUBLIC_OR_BY_OWNER_ID)
                    .setParameter("id", id)
                    .setParameter("owner_id", user != null ? user.getId() : null)
                    .getSingleResult();
            return EnzymeMapper.fromEntity(entity);

        } catch (NoResultException e) {
            throw new NotFoundException(Enzyme.class, id);
        }
    }

    @Override
    public Enzyme insertEnzyme(Enzyme enzyme, User user) {

        Assert.userNotNull(user);
        Assert.objectNotNull(enzyme, Enzyme.class);
        Assert.fieldNotEmpty(enzyme.getName(), "name", Enzyme.class);

        AuthorizationUtil.verifyModification(enzyme.getAccess(), user);

        EnzymeEntity enzymeEntity = EnzymeMapper.toEntity(enzyme);
        enzymeEntity.setOwnerId(user.getId());
        enzymeEntity.setAccess(enzyme.getAccess() == null
                ? SequenceAccessType.PRIVATE
                : enzyme.getAccess());

        Sequence insertedSequence = sequenceService.insertSequence(enzyme.getSequence(), SequenceType.ENZYME, user);
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
    public Enzyme updateEnzyme(Enzyme enzyme, String id, User user) {

        Assert.userNotNull(user);
        Assert.fieldNotNull(id, "id");
        Assert.objectNotNull(enzyme, Enzyme.class);

        EnzymeEntity old = getEnzymeEntity(id, user);
        if (old == null) throw new NotFoundException(Enzyme.class, id);

        EnzymeEntity entity = EnzymeMapper.toEntity(enzyme);
        entity.setId(id);
        entity.setCreated(old.getCreated());
        entity.setOwnerId(old.getOwnerId());
        entity.setAccess(old.getAccess());

        // Check if entity's access will change
        if(entity.getAccess() != null && entity.getAccess() != old.getAccess()) {

            // If access will change, verify that action is permitted
            AuthorizationUtil.verifyModification(old.getAccess(), user);
        }

        Sequence updatedSeq = sequenceService.updateSequence(enzyme.getSequence(), SequenceType.ENZYME, old.getSequence().getId(), user);
        entity.setSequence(SequenceMapper.toEntity(updatedSeq));

        // dynamic update of base sequence
        if(entity.getName() == null)
            entity.setName(old.getName());
        if(entity.getAccess() == null)
            entity.setAccess(old.getAccess());

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

        return EnzymeMapper.fromEntity(getEnzymeEntity(entity.getId(), user));
    }

    @Override
    public boolean removeEnzyme(String id, User user) {

        Assert.userNotNull(user);

        EnzymeEntity entity = getEnzymeEntity(id, user);

        if (entity == null)
            return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }

    public EnzymeEntity getEnzymeEntity(String id, User user) {
        try {
            return (EnzymeEntity) em.createNamedQuery(EnzymeEntity.GET_PUBLIC_OR_BY_OWNER_ID)
                    .setParameter("id", id)
                    .setParameter("owner_id", user.getId())
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
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
