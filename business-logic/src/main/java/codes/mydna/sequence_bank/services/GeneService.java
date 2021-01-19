package codes.mydna.sequence_bank.services;

import codes.mydna.auth.common.models.User;
import codes.mydna.rest.utils.EntityList;
import codes.mydna.sequence_bank.lib.Gene;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.sequence_bank.services.impl.GeneServiceImpl
 */
public interface GeneService {

    EntityList<Gene> getGenes(QueryParameters qp, User user);
    Gene getGene(String id, User user);
    Gene insertGene(Gene gene, User user);
    Gene updateGene(Gene gene, String id, User user);
    boolean removeGene(String id, User user);
    
}
