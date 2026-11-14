package Week2;

/**
 * Practice Problem 4: Masked Phone Number Formatter
 *
 * TASK:
 * Validate that a phone string is exactly 10 digits (numeric only).
 * Mask the first 6 digits with "XXXXXX", format with a hyphen ("XXXXXX-last4"),
 * or return "Invalid phone number".
 */
public class PracticeProblem4 {

    public static String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() != 10) {
            return "Invalid phone number";
        }

        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return "Invalid phone number";
            }
        }

        StringBuilder sb = new StringBuilder("XXXXXX");
        sb.append("-");
        sb.append(phone.substring(6));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("9876543210 -> " + maskPhoneNumber("9876543210"));
        System.out.println("98765 -> " + maskPhoneNumber("98765"));
    }
}
