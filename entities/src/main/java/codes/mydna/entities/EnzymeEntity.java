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

}
