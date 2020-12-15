package codes.mydna.lib;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class BaseSequenceType extends BaseType {

    @Schema(description = "Sequence name", example = "My Sequence")
    private String name;
    private Sequence sequence;

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
}
