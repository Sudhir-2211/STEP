package Week2;

/**
 * Practice Problem 5: Bank Transaction Reference Generator & Validator
 *
 * TASK:
 * Normalize and validate a 14-character bank transaction reference code:
 * - Format: 3 letters (bank code) + 6 digits (ddMMyy) + 5 digits (sequence number).
 * - Normalize: trim() whitespace, uppercase first 3 letters.
 * - Validate: exactly 14 characters; first 3 letters; remaining 11 digits (no regex).
 * - Format: "[BANKCODE] DATE: dd/MM/yy | SEQ: sequence".
 */
public class PracticeProblem5 {

    public static String normalizeReference(String raw) {
        if (raw == null) return null;
        String trimmed = raw.trim();
        if (trimmed.length() < 3) return trimmed;
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    public static String validateAndFormat(String reference) {
        if (reference == null || reference.length() != 14) {
            return "Invalid: wrong length (must be exactly 14 characters)";
        }

        // Validate first 3 are letters
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        // Validate next 11 are digits
        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: body must be 11 digits (6 date + 5 seq)";
            }
        }

        String bankCode = reference.substring(0, 3);
        String dd = reference.substring(3, 5);
        String mm = reference.substring(5, 7);
        String yy = reference.substring(7, 9);
        String seq = reference.substring(9, 14);

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(bankCode).append("] ");
        sb.append("DATE: ").append(dd).append("/").append(mm).append("/").append(yy);
        sb.append(" | SEQ: ").append(seq);

        return sb.toString();
    }

    public static void test(String raw) {
        String normalized = normalizeReference(raw);
        String result = validateAndFormat(normalized);
        System.out.println("Input: \"" + raw + "\"");
        System.out.println("Output: " + result);
    }

    public static void main(String[] args) {
        test("  hdf03022600042  ");
        System.out.println();
        test("12F03022600042");
    }
}
