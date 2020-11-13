package codes.mydna.services.impl;

import codes.mydna.entities.SampleEntity;
import codes.mydna.services.SampleService;
import com.kumuluz.ee.rest.utils.JPAUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class SampleServiceImpl implements SampleService {

    @Inject
    private EntityManager em;

    public List<SampleEntity> getSamples(){
        return new ArrayList<>(JPAUtils.queryEntities(em, SampleEntity.class));
    }

    public SampleEntity addSampleEntity(SampleEntity entity){
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

}