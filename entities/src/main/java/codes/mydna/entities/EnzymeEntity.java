package codes.mydna.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ENZYME_TABLE")
public class EnzymeEntity extends BaseSequenceEntity {

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
