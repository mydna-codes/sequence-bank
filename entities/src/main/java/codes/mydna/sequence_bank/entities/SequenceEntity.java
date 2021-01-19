package codes.mydna.sequence_bank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SEQUENCE_TABLE")
public class SequenceEntity extends BaseEntity {

    @Column(name = "SEQUENCE")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
