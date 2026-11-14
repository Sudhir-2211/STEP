package Week11;

import java.util.Stack;

/**
 * Problem 2: The Hackathon Banner Printer
 *
 * TASK:
 * The hackathon banner printer accepts a compressed design code. A pattern k[text]
 * means "print text k times", and patterns can be nested inside each other, like 2[x3[y]].
 * Letters outside any brackets are printed as they are.
 * Implement String expandBanner(String code) that returns the fully expanded text.
 * k is a positive integer that may have more than one digit, and the code is always well formed.
 * Do not use recursion: simulate the nesting with stacks.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Each character of the input string is parsed once.
 *    - Appending characters to the StringBuilder occurs in proportion to the total number of characters
 *      in the final expanded output.
 *    - Total Time Complexity: O(L), where L is the length of the fully expanded result string.
 *
 * 2. Space Complexity:
 *    - The stacks store intermediate repeat counts and string builders for each level of nesting bracket.
 *    - Space used by the stacks is bounded by O(D + L), where D is the maximum nesting depth
 *      and L is the total length of the expanded string.
 */
public class Problem2 {

    public static String expandBanner(String code) {
        if (code == null || code.isEmpty()) {
            return "";
        }

        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> textStack = new Stack<>();
        StringBuilder currentText = new StringBuilder();
        int k = 0;

        for (int i = 0; i < code.length(); i++) {
            char ch = code.charAt(i);

            if (Character.isDigit(ch)) {
                // Multi-digit multiplier support
                k = k * 10 + (ch - '0');
            } else if (ch == '[') {
                // Push current multiplier and accumulated text, then begin fresh segment
                countStack.push(k);
                textStack.push(currentText);
                currentText = new StringBuilder();
                k = 0;
            } else if (ch == ']') {
                // Pop count and previous text builder
                int repeatCount = countStack.pop();
                StringBuilder prevText = textStack.pop();

                // Append current segment repeatCount times to previous segment
                for (int count = 0; count < repeatCount; count++) {
                    prevText.append(currentText);
                }
                currentText = prevText;
            } else {
                // Regular character outside or inside brackets
                currentText.append(ch);
            }
        }

        return currentText.toString();
    }

    public static void main(String[] args) {
        // Sample 1: code = "3[ab]2[c]"
        String code1 = "3[ab]2[c]";
        System.out.println("Sample 1 Input: code = \"" + code1 + "\"");
        String result1 = expandBanner(code1);
        System.out.println("Expected Output: \"abababcc\"");
        System.out.println("Actual Output:   \"" + result1 + "\"");
        System.out.println();

        // Sample 2: code = "2[x3[y]]z"
        String code2 = "2[x3[y]]z";
        System.out.println("Sample 2 Input: code = \"" + code2 + "\"");
        String result2 = expandBanner(code2);
        System.out.println("Expected Output: \"xyyyxyyyz\"");
        System.out.println("Actual Output:   \"" + result2 + "\"");
    }
}
