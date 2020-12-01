package codes.mydna.services;

import codes.mydna.lib.Enzyme;
import codes.mydna.utils.EntityList;
import com.kumuluz.ee.rest.beans.QueryParameters;

/**
 * @see codes.mydna.services.impl.EnzymeServiceImpl
 */
public interface EnzymeService {

    EntityList<Enzyme> getEnzymes(QueryParameters qp);
    Enzyme getEnzyme(String id);
    Enzyme insertEnzyme(Enzyme enzyme);
    Enzyme updateEnzyme(Enzyme enzyme, String id);
    boolean removeEnzyme(String id);

}
