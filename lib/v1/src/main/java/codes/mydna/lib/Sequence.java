package codes.mydna.lib;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class Sequence extends BaseType {

    @Schema(description = "Sequence value", example = "ACTGAAAAGATATCCAGATACC")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
