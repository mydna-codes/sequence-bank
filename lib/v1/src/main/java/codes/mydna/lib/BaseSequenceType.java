package codes.mydna.lib;

import codes.mydna.lib.enums.SequenceAccessType;

public class BaseSequenceType extends BaseType {

    private String name;
    private Sequence sequence;
    private SequenceAccessType access;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public SequenceAccessType getAccess() {
        return access;
    }

    public void setAccess(SequenceAccessType access) {
        this.access = access;
    }
}
