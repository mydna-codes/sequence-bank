package codes.mydna.sequence_bank.lib.util;

import codes.mydna.sequence_bank.lib.enums.SequenceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    /**
     * Validates if sequence contains only allowed characters.
     *
     * @param sequence - String to be validated
     * @param type     - SequenceType [DNA, ENZYME, GENE]
     * @return Returns true if sequence contains only allowed characters.
     */
    public static boolean isSequenceValid(String sequence, SequenceType type) {
        return sequence.matches(Arrays.toString(type.getValidCharacters()) + "+");
    }

    /**
     * Finds all occurrences of pattern in a string.
     *
     * @param sequence - String to be searched
     * @param pattern  - Substring to be found in sequence (genes)
     * @param type     - SequenceType [DNA, ENZYME, GENE]
     * @return List of indexes where pattern was found
     */
    public static List<Integer> findAll(String sequence, String pattern, SequenceType type) {
        if (type == SequenceType.ENZYME)
            return findAllWithMapping(sequence, pattern);
        return findAll(sequence, pattern);

    }

    /**
     * Finds all occurrences of pattern in a string.
     *
     * @param sequence - String to be searched
     * @param pattern  - Substring to be found in sequence (genes)
     * @return List of indexes where pattern was found
     */
    private static List<Integer> findAll(String sequence, String pattern) {
        List<Integer> indexes = new ArrayList<>();
        int index = sequence.indexOf(pattern);
        while (index >= 0) {
            indexes.add(index);
            index = sequence.indexOf(pattern, index + 1);
        }
        return indexes;
    }

    /**
     * Finds all occurrences of pattern in a string using 'mapping' list.
     *
     * @param sequence - String to be searched
     * @param pattern  - Substring to be found in sequence (enzymes/genes)
     * @return List of indexes where pattern was found
     */
    private static List<Integer> findAllWithMapping(String sequence, String pattern) {

        List<Integer> indexes = new ArrayList<>();

        char[] seq = sequence.toCharArray();
        char[] pat = pattern.toCharArray();

        boolean found;
        for (int i = 0; i <= seq.length - pat.length; i++) {
            found = true;
            for (int j = 0; j < pat.length; j++) {
                if (!compare(pat[j], seq[i + j])) {
                    found = false;
                    break;
                }
            }
            if (found)
                indexes.add(i);
        }
        return indexes;
    }

    /**
     * Compares if character 'any' equals character 'base'.
     *
     * @param any  - Any character from mapping
     * @param base - One of the base characters [A, C, T, G]
     */
    public static boolean compare(char any, char base) {
        char[] valid = mapping.get(any);
        for (char c : valid)
            if (c == base)
                return true;
        return false;
    }

    /**
     * Reverses string.
     *
     * @param str - String to be reversed
     * @return Reversed string
     */
    public static String reverse(String str) {
        char[] value = str.toCharArray();
        int n = value.length - 1;
        for (int j = (n - 1) >> 1; j >= 0; --j) {
            char temp = value[j];
            char temp2 = value[n - j];
            value[j] = temp2;
            value[n - j] = temp;
        }
        return String.valueOf(value);
    }

}
