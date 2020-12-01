package codes.mydna.services;

import codes.mydna.lib.Gene;
import codes.mydna.utils.EntityList;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.services.impl.GeneServiceImpl
 */
public interface GeneService {

    EntityList<Gene> getGenes(QueryParameters qp);
    Gene getGene(String id);
    Gene insertGene(Gene gene);
    Gene updateGene(Gene gene, String id);
    boolean removeGene(String id);
    
}
