package codes.mydna.services;

import codes.mydna.auth.common.models.User;
import codes.mydna.lib.Gene;
import codes.mydna.utils.EntityList;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.services.impl.GeneServiceImpl
 */
public interface GeneService {

    EntityList<Gene> getGenes(QueryParameters qp, User user);
    Gene getGene(String id, User user);
    Gene insertGene(Gene gene, User user);
    Gene updateGene(Gene gene, String id, User user);
    boolean removeGene(String id, User user);
    
}
