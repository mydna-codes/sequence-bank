package codes.mydna.sequence_bank.lib;

public class Sequence extends BaseType {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLength(){
        return (value == null)
                ? 0
                : value.length();
    }
}
