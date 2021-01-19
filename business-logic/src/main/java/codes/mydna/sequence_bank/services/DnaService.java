package codes.mydna.sequence_bank.services;

import codes.mydna.auth.common.models.User;
import codes.mydna.rest.utils.EntityList;
import codes.mydna.sequence_bank.lib.Dna;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.sequence_bank.services.impl.DnaServiceImpl
 */
public interface DnaService {

    EntityList<Dna> getDnas(QueryParameters qp, User user);
    Dna getDna(String id, User user);
    Dna insertDna(Dna dna, User user);
    Dna updateDna(Dna dna, String id, User user);
    boolean removeDna(String id, User user);
}
