package codes.mydna.util;

import codes.mydna.entities.enums.SequenceType;

import java.util.HashMap;

public class BasePairUtil {

    private static final HashMap<Character, char[]> mapping;

    static {
        mapping = new HashMap<>();
        mapping.put('A', new char[]{'A'});
        mapping.put('C', new char[]{'C'});
        mapping.put('T', new char[]{'T'});
        mapping.put('G', new char[]{'G'});
        mapping.put('B', new char[]{'C', 'G', 'T'});
        mapping.put('D', new char[]{'A', 'G', 'T'});
        mapping.put('H', new char[]{'A', 'C', 'T'});
        mapping.put('K', new char[]{'G', 'T'});
        mapping.put('M', new char[]{'A', 'C'});
        mapping.put('N', new char[]{'A', 'C', 'G', 'T'});
        mapping.put('R', new char[]{'A', 'G'});
        mapping.put('S', new char[]{'C', 'G'});
        mapping.put('V', new char[]{'A', 'C', 'G'});
        mapping.put('W', new char[]{'A', 'T'});
        mapping.put('Y', new char[]{'C', 'T'});
    }

    public static boolean isSequenceValid(String sequence, SequenceType type) {

        char[] valid = type.getValidCharacters();
        char[] seq = sequence.toCharArray();
        boolean isValid;

        for (char seqChar : seq) {

            isValid = false;

            for (char c : valid) {
                if (seqChar == c) {
                    isValid = true;
                    break;
                }
            }

            if(!isValid)
                return false;
        }

        return true;
    }

    public static boolean compare(char any, char base) {
        char[] valid = mapping.get(any);
        for (char c : valid)
            if (c == base)
                return true;
        return false;
    }

}
