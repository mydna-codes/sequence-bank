package codes.mydna.sequence_bank.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "DNA_TABLE")
@NamedQueries({
        @NamedQuery(
                name = DnaEntity.GET_PUBLIC_OR_BY_OWNER_ID,
                query = "SELECT d FROM DnaEntity d WHERE (d.access = 'PUBLIC' OR d.ownerId = :owner_id) AND d.id = :id"
        ),
        @NamedQuery(
                name = DnaEntity.GET_BY_OWNER_ID,
                query = "SELECT d FROM DnaEntity d WHERE d.ownerId = :owner_id AND d.id = :id"
        ),
        @NamedQuery(
                name = DnaEntity.GET_ALL_BY_OWNER_ID,
                query = "SELECT d FROM DnaEntity d WHERE d.ownerId = :owner_id"
        )
})
public class DnaEntity extends BaseSequenceEntity {

    public static final String GET_PUBLIC_OR_BY_OWNER_ID = "DnaEntity.GET_PUBLIC_OR_BY_OWNER_ID";
    public static final String GET_BY_OWNER_ID = "DnaEntity.GET_ONE_BY_OWNER_ID";
    public static final String GET_ALL_BY_OWNER_ID = "DnaEntity.GET_ALL_BY_OWNER_ID";

}
