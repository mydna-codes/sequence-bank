package codes.mydna.services.impl;

import codes.mydna.entities.EnzymeEntity;
import codes.mydna.exceptions.BadRequestException;
import codes.mydna.exceptions.NotFoundException;
import codes.mydna.lib.Enzyme;
import codes.mydna.mappers.EnzymeMapper;
import codes.mydna.services.EnzymeService;
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
public class EnzymeServiceImpl implements EnzymeService {

    private static Logger LOG = Logger.getLogger(EnzymeServiceImpl.class.getSimpleName());
    private String uuid;

    @Inject
    private EntityManager em;

    @PostConstruct
    private void pc(){
        uuid = UUID.randomUUID().toString();
        LOG.info(EnzymeServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be initialized");
    }

    @PreDestroy
    private void pd(){
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
        if(id == null)
            throw new BadRequestException("Id cannot be null.");

        EnzymeEntity entity = getEnzymeEntity(id);
        Enzyme enzyme = EnzymeMapper.fromEntity(entity);
        if(enzyme == null)
            throw new NotFoundException(Enzyme.class, id);

        return enzyme;
    }

    @Override
    public Enzyme insertEnzyme(Enzyme enzyme) {

        if(enzyme == null)
            throw new BadRequestException("Enzyme object not provided.");
        if(enzyme.getName() == null || enzyme.getName().isEmpty())
            throw new BadRequestException("Field 'name' of Enzyme object is invalid.");

        EnzymeEntity enzymeEntity = EnzymeMapper.toEntity(enzyme);

        em.getTransaction().begin();
        em.persist(enzymeEntity);
        em.getTransaction().commit();

        return EnzymeMapper.fromEntity(enzymeEntity);
    }

    @Override
    public Enzyme updateEnzyme(Enzyme enzyme, String id) {
        if(id == null)
            throw new BadRequestException("Id cannot be null.");
        if(enzyme == null)
            throw new BadRequestException("Enzyme object not provided.");

        EnzymeEntity old = getEnzymeEntity(id);
        if(old == null)
            throw new NotFoundException(Enzyme.class, id);

        EnzymeEntity entity = EnzymeMapper.toEntity(enzyme);
        entity.setId(id);

        // Prevent cascade update if sequence is null or empty
        if(entity.getSequence() == null || entity.getSequence().getValue().isEmpty())
            entity.setSequence(old.getSequence());

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        return EnzymeMapper.fromEntity(entity);
    }

    @Override
    public boolean removeEnzyme(String id) {
        EnzymeEntity entity = getEnzymeEntity(id);
        if(entity == null)
            return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }

    public EnzymeEntity getEnzymeEntity(String id){
        if(id == null)
            return null;
        return em.find(EnzymeEntity.class, id);
    }

}
