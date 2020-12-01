package codes.mydna.services.impl;

import codes.mydna.entities.DnaEntity;
import codes.mydna.exceptions.BadRequestException;
import codes.mydna.exceptions.NotFoundException;
import codes.mydna.lib.Dna;
import codes.mydna.mappers.DnaMapper;
import codes.mydna.services.DnaService;
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
public class DnaServiceImpl implements DnaService {

    private static Logger LOG = Logger.getLogger(DnaServiceImpl.class.getSimpleName());
    private String uuid;

    @Inject
    private EntityManager em;

    @PostConstruct
    private void pc(){
        uuid = UUID.randomUUID().toString();
        LOG.info(DnaServiceImpl.class.getSimpleName() + " with ID '" + uuid + "' is about to be initialized");
    }

    @PreDestroy
    private void pd(){
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
        if(id == null)
            throw new BadRequestException("Id cannot be null.");

        DnaEntity entity = getDnaEntity(id);
        Dna dna = DnaMapper.fromEntity(entity);
        if(dna == null)
            throw new NotFoundException(Dna.class, id);

        return dna;
    }

    @Override
    public Dna insertDna(Dna dna) {

        if(dna == null)
            throw new BadRequestException("Dna object not provided.");
        if(dna.getName() == null || dna.getName().isEmpty())
            throw new BadRequestException("Field 'name' of Dna object is invalid.");

        DnaEntity dnaEntity = DnaMapper.toEntity(dna);

        em.getTransaction().begin();
        em.persist(dnaEntity);
        em.getTransaction().commit();

        return DnaMapper.fromEntity(dnaEntity);
    }

    @Override
    public Dna updateDna(Dna dna, String id) {
        if(id == null)
            throw new BadRequestException("Id cannot be null.");
        if(dna == null)
            throw new BadRequestException("Dna object not provided.");

        DnaEntity old = getDnaEntity(id);
        if(old == null)
            throw new NotFoundException(Dna.class, id);

        DnaEntity entity = DnaMapper.toEntity(dna);
        entity.setId(id);

        // Prevent cascade update if sequence is null or empty
        if(entity.getSequence() == null || entity.getSequence().getValue().isEmpty())
            entity.setSequence(old.getSequence());

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        return DnaMapper.fromEntity(entity);
    }

    @Override
    public boolean removeDna(String id) {
        DnaEntity entity = getDnaEntity(id);
        if(entity == null)
            return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        return true;
    }

    public DnaEntity getDnaEntity(String id){
        if(id == null)
            return null;
        return em.find(DnaEntity.class, id);
    }

}
