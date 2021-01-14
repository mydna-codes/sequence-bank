package codes.mydna.services;

import codes.mydna.auth.common.models.User;
import codes.mydna.lib.Enzyme;
import codes.mydna.utils.EntityList;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.services.impl.EnzymeServiceImpl
 */
public interface EnzymeService {

    EntityList<Enzyme> getEnzymes(QueryParameters qp, User user);
    Enzyme getEnzyme(String id, User user);
    Enzyme insertEnzyme(Enzyme enzyme, User user);
    Enzyme updateEnzyme(Enzyme enzyme, String id, User user);
    boolean removeEnzyme(String id, User user);

}
