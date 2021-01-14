package codes.mydna.entities;

import codes.mydna.lib.enums.SequenceAccessType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@MappedSuperclass
public class BaseSequenceEntity extends BaseEntity {

    @Column(name="OWNER_ID", updatable = false)
    private String ownerId;

    @Column(name="ACCESS_TYPE")
    @Enumerated(value = EnumType.STRING)
    private SequenceAccessType access;

    @Column(name = "NAME")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.TRUE)
    private SequenceEntity sequence;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public SequenceAccessType getAccess() {
        return access;
    }

    public void setAccess(SequenceAccessType access) {
        this.access = (access == null)
                ? SequenceAccessType.PRIVATE
                : access;
    }

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
