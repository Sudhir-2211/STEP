package Week2;

/**
 * Practice Problem 3: File Extension Validator
 *
 * TASK:
 * Extract the file extension using lastIndexOf('.') and substring().
 * Compare case-insensitively against accepted types (pdf, docx, zip).
 * Return "Accepted" or "Rejected — invalid file type".
 */
public class PracticeProblem3 {

    public static String validateFileExtension(String filename) {
        if (filename == null) {
            return "Rejected — invalid file type";
        }

        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "Rejected — invalid file type";
        }

        String ext = filename.substring(lastDotIndex + 1);
        if (ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("docx") || ext.equalsIgnoreCase("zip")) {
            return "Accepted";
        }

        return "Rejected — invalid file type";
    }

    public static void main(String[] args) {
        System.out.println("Assignment1.PDF -> " + validateFileExtension("Assignment1.PDF"));
        System.out.println("notes.txt -> " + validateFileExtension("notes.txt"));
    }
}
