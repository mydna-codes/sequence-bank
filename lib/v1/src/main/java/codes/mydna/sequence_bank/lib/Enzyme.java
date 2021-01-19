package codes.mydna.sequence_bank.lib;

public class Enzyme extends BaseSequenceType {

    private Integer upperCut;
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
