package Week2;

/**
 * Practice Problem 1: Vowel & Consonant Counter
 *
 * TASK:
 * Count vowels (a, e, i, o, u) and consonants in a string (case-insensitive),
 * ignoring spaces. Print the total count of vowels and consonants.
 */
public class PracticeProblem1 {

    public static void countVowelsAndConsonants(String text) {
        if (text == null) {
            System.out.println("Vowels: 0 | Consonants: 0");
            return;
        }

        int vowels = 0;
        int consonants = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = Character.toLowerCase(text.charAt(i));
            if (ch >= 'a' && ch <= 'z') {
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }

        System.out.println("Vowels: " + vowels + " | Consonants: " + consonants);
    }

    public static void main(String[] args) {
        String input = "Java Programming";
        System.out.println("Input: \"" + input + "\"");
        countVowelsAndConsonants(input);
    }
}
