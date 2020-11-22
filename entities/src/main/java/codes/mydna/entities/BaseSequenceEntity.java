package codes.mydna.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public class BaseSequenceEntity extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.TRUE)
    private SequenceEntity sequence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SequenceEntity getSequence() {
        return sequence;
    }

    public void setSequence(SequenceEntity sequence) {
        this.sequence = sequence;
    }
}
