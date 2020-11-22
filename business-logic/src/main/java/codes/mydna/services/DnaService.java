package codes.mydna.services;

import codes.mydna.lib.Dna;
import codes.mydna.utils.EntityList;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.services.impl.DnaServiceImpl
 */
public interface DnaService {

    EntityList<Dna> getDnas(QueryParameters qp);
    Dna getDna(String id);
    Dna insertDna(Dna dna);
    Dna updateDna(Dna dna, String id);
    boolean removeDna(String id);
}
