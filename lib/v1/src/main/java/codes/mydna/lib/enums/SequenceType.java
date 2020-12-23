package codes.mydna.lib.enums;

public enum SequenceType {

    // TODO: Move max lengths into configuration server

    DNA(
            25000,
            new char[]{'A', 'C', 'T', 'G'}
    ),
    ENZYME(
            20,
            new char[]{
                    'A', 'C', 'T', 'G', 'B',
                    'D', 'H', 'K', 'M', 'N',
                    'R', 'S', 'V', 'W', 'Y'
            }
    ),
    GENE(
            10000,
            new char[]{'A', 'C', 'T', 'G'}
    );

    private final int maxSequenceLength;
    private final char[] validCharacters;

    SequenceType(int maxSequenceLength, char[] validCharacters) {
        this.maxSequenceLength = maxSequenceLength;
        this.validCharacters = validCharacters;
    }

    public int getMaxSequenceLength() {
        return maxSequenceLength;
    }

    public char[] getValidCharacters() {
        return validCharacters;
    }
}
