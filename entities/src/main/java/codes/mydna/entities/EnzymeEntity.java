package codes.mydna.entities;

import javax.persistence.*;

@Entity
@Table(name = "ENZYME_TABLE")
@NamedQueries({
        @NamedQuery(
                name = EnzymeEntity.GET_PUBLIC_OR_BY_OWNER_ID,
                query = "SELECT e FROM EnzymeEntity e WHERE (e.access = 'PUBLIC' OR e.ownerId = :owner_id) AND e.id = :id"
        ),
        @NamedQuery(
                name = EnzymeEntity.GET_BY_OWNER_ID,
                query = "SELECT e FROM EnzymeEntity e WHERE e.ownerId = :owner_id AND e.id = :id"
        ),
        @NamedQuery(
                name = EnzymeEntity.GET_ALL_BY_OWNER_ID,
                query = "SELECT e FROM EnzymeEntity e WHERE e.ownerId = :owner_id"
        )
})
public class EnzymeEntity extends BaseSequenceEntity {

    public static final String GET_PUBLIC_OR_BY_OWNER_ID = "EnzymeEntity.GET_PUBLIC_OR_BY_OWNER_ID";
    public static final String GET_BY_OWNER_ID = "EnzymeEntity.GET_ONE_BY_OWNER_ID";
    public static final String GET_ALL_BY_OWNER_ID = "EnzymeEntity.GET_ALL_BY_OWNER_ID";

    @Column(name = "UPPER_CUT")
    private Integer upperCut;

    @Column(name = "LOWER_CUT")
    private Integer lowerCut;

    public Integer getUpperCut() {
        return upperCut;
    }

    public void setUpperCut(Integer upperCut) {
        this.upperCut = upperCut;
    }

    public Integer getLowerCut() {
        return lowerCut;
    }

    public void setLowerCut(Integer lowerCut) {
        this.lowerCut = lowerCut;
    }
}
