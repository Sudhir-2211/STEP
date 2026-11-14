package Week1;

/**
 * Practice Problem 2: Palindrome Checker (3 Approaches)
 *
 * TASK:
 * Verify whether a string is a palindrome using three independent techniques:
 * 1. Iterative two-pointer comparison.
 * 2. Recursive substring comparison.
 * 3. Character array reversal and string comparison.
 */
public class PracticeProblem2 {

    public static boolean isPalindromeIterative(String text) {
        if (text == null) return false;
        int left = 0, right = text.length() - 1;
        while (left < right) {
            if (Character.toLowerCase(text.charAt(left)) != Character.toLowerCase(text.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isPalindromeRecursive(String text) {
        if (text == null) return false;
        return checkRecursive(text.toLowerCase(), 0, text.length() - 1);
    }

    private static boolean checkRecursive(String s, int left, int right) {
        if (left >= right) return true;
        if (s.charAt(left) != s.charAt(right)) return false;
        return checkRecursive(s, left + 1, right - 1);
    }

    public static boolean isPalindromeArrayReversal(String text) {
        if (text == null) return false;
        char[] original = text.toLowerCase().toCharArray();
        char[] reversed = new char[original.length];
        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }
        return new String(original).equals(new String(reversed));
    }

    public static void verifyAndPrint(String text) {
        boolean it = isPalindromeIterative(text);
        boolean rec = isPalindromeRecursive(text);
        boolean arr = isPalindromeArrayReversal(text);

        String itStr = it ? "Palindrome" : "Not Palindrome";
        String recStr = rec ? "Palindrome" : "Not Palindrome";
        String arrStr = arr ? "Palindrome" : "Not Palindrome";

        System.out.println("Input: \"" + text + "\"");
        System.out.println("Iterative: " + itStr + " | Recursive: " + recStr + " | Array Reversal: " + arrStr);
    }

    public static void main(String[] args) {
        verifyAndPrint("madam");
        System.out.println();
        verifyAndPrint("hello");
    }
}
