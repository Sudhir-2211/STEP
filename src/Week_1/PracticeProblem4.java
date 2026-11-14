package Week1;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Practice Problem 4: First Non-Repeating Character
 *
 * TASK:
 * Given an input string, compute character frequencies and identify the first character
 * that occurs exactly once. Print the character or a message if none exists.
 */
public class PracticeProblem4 {

    public static Character findFirstNonRepeatingChar(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        Map<Character, Integer> freqMap = new LinkedHashMap<>();
        for (char ch : text.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        for (char ch : text.toCharArray()) {
            if (freqMap.get(ch) == 1) {
                return ch;
            }
        }

        return null;
    }

    public static void test(String text) {
        Character result = findFirstNonRepeatingChar(text);
        System.out.println("Input: \"" + text + "\"");
        if (result != null) {
            System.out.println("First Non-Repeating Character: '" + result + "'");
        } else {
            System.out.println("No Non-Repeating Character Found");
        }
    }

    public static void main(String[] args) {
        test("swiss");
        System.out.println();
        test("aabbcc");
    }
}
