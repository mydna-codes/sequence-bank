package codes.mydna.sequence_bank.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "GENE_TABLE")
@NamedQueries({
        @NamedQuery(
                name = GeneEntity.GET_PUBLIC_OR_BY_OWNER_ID,
                query = "SELECT g FROM GeneEntity g WHERE (g.access = 'PUBLIC' OR g.ownerId = :owner_id) AND g.id = :id"
        ),
        @NamedQuery(
                name = GeneEntity.GET_BY_OWNER_ID,
                query = "SELECT g FROM GeneEntity g WHERE g.ownerId = :owner_id AND g.id = :id"
        ),
        @NamedQuery(
                name = GeneEntity.GET_ALL_BY_OWNER_ID,
                query = "SELECT g FROM GeneEntity g WHERE g.ownerId = :owner_id"
        )
})
public class GeneEntity extends BaseSequenceEntity {

    public static final String GET_PUBLIC_OR_BY_OWNER_ID = "GeneEntity.GET_PUBLIC_OR_BY_OWNER_ID";
    public static final String GET_BY_OWNER_ID = "GeneEntity.GET_ONE_BY_OWNER_ID";
    public static final String GET_ALL_BY_OWNER_ID = "GeneEntity.GET_ALL_BY_OWNER_ID";

}
