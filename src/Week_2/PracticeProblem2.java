package Week2;

/**
 * Practice Problem 2: CSV Student Record Parser
 *
 * TASK:
 * Parse a CSV line in the format "Name,RollNumber,Department".
 * Validate that exactly 3 fields are present; if not, print "Invalid Record".
 * Otherwise print: "Name: ... | Roll No: ... | Dept: ...".
 */
public class PracticeProblem2 {

    public static void parseStudentRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }

        String[] fields = csvLine.split(",");
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        String name = fields[0].trim();
        String rollNo = fields[1].trim();
        String dept = fields[2].trim();

        System.out.println("Name: " + name + " | Roll No: " + rollNo + " | Dept: " + dept);
    }

    public static void main(String[] args) {
        parseStudentRecord("Ananya Verma,RA2211003010123,CSE");
        parseStudentRecord("Ananya Verma,CSE");
    }
}
