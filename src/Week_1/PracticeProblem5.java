package Week1;

/**
 * Practice Problem 5: Reverse Customer Name
 *
 * TASK:
 * Given a customer's name, reverse the character sequence without modifying the original data.
 * Print both the original and reversed names.
 */
public class PracticeProblem5 {

    public static String reverseCustomerName(String customerName) {
        if (customerName == null) {
            return null;
        }
        char[] chars = customerName.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        String name = "Sunil";
        String reversed = reverseCustomerName(name);

        System.out.println("Original Name: " + name);
        System.out.println("Reversed Name: " + reversed);
    }
}
