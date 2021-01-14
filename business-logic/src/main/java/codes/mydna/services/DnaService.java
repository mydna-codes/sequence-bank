package codes.mydna.services;

import codes.mydna.auth.common.models.User;
import codes.mydna.lib.Dna;
import codes.mydna.utils.EntityList;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.services.impl.DnaServiceImpl
 */
public interface DnaService {

    EntityList<Dna> getDnas(QueryParameters qp, User user);
    Dna getDna(String id, User user);
    Dna insertDna(Dna dna, User user);
    Dna updateDna(Dna dna, String id, User user);
    boolean removeDna(String id, User user);
}
