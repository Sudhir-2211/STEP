package Week3;

/**
 * Practice Problem 1: From Procedural Mess to a Working Attendance System
 *
 * TASK:
 * Design SrmStudent with fields name, regNo, and attendance.
 * Instance methods: addAttendanceUpdate(int), isEligible() (>= 75%).
 * Static method: classAverage(SrmStudent[] students).
 *
 * Justification:
 * classAverage is static because the class average is an aggregate metric of the cohort
 * as a whole, independent of any individual student object. Conversely, isEligible is an
 * instance method because eligibility depends strictly on that specific student's attendance.
 */
public class PracticeProblem1 {

    public static class SrmStudent {
        private String name;
        private String regNo;
        private int attendance;

        public SrmStudent(String name, String regNo, int attendance) {
            this.name = name;
            this.regNo = regNo;
            this.attendance = attendance;
        }

        public void addAttendanceUpdate(int newAttendance) {
            this.attendance = newAttendance;
        }

        public boolean isEligible() {
            return this.attendance >= 75;
        }

        public String getName() {
            return name;
        }

        public int getAttendance() {
            return attendance;
        }

        public static double classAverage(SrmStudent[] students) {
            if (students == null || students.length == 0) return 0.0;
            double sum = 0;
            for (SrmStudent s : students) {
                sum += s.getAttendance();
            }
            return sum / students.length;
        }
    }

    public static void main(String[] args) {
        SrmStudent[] students = {
                new SrmStudent("Ravi", "RA01", 82),
                new SrmStudent("Anitha", "RA02", 68),
                new SrmStudent("Karthik", "RA03", 91),
                new SrmStudent("Meera", "RA04", 74),
                new SrmStudent("Suresh", "RA05", 60)
        };

        for (SrmStudent s : students) {
            String status = s.isEligible() ? "Eligible" : "Detained";
            System.out.println(s.getName() + " - " + s.getAttendance() + "% - " + status);
        }

        System.out.printf("Class average: %.1f%%%n", SrmStudent.classAverage(students));
    }
}
