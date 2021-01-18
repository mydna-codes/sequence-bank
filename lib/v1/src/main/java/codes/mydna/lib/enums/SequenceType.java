package codes.mydna.lib.enums;

public enum SequenceType {

    DNA(
            new char[]{'A', 'C', 'T', 'G'}
    ),
    ENZYME(
            new char[]{
                    'A', 'C', 'T', 'G', 'B',
                    'D', 'H', 'K', 'M', 'N',
                    'R', 'S', 'V', 'W', 'Y'
            }
    ),
    GENE(
            new char[]{'A', 'C', 'T', 'G'}
    );

    private final char[] validCharacters;

    SequenceType(char[] validCharacters) {
        this.validCharacters = validCharacters;
    }

    public char[] getValidCharacters() {
        return validCharacters;
    }
}
