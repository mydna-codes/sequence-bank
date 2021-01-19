package codes.mydna.sequence_bank.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PersistenceProducer {

    @PersistenceContext(unitName = "sequence-bank-jpa-unit")
    private EntityManager em;

    @Produces
    @ApplicationScoped
    public EntityManager getEntityManager() {
        return em;
    }

    public void disposeEntityManager(@Disposes EntityManager em){
        if (em.isOpen())
            em.close();
    }
}