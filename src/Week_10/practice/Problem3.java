package Week10.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Week 10 Practice - Problem 3: Student Marks Grid
 *
 * Task:
 * Implement a system to manage student marks.
 * You will be given student names and their marks for various subjects.
 * Some students might not have marks for all subjects (ragged / jagged arrays).
 * Represent this data efficiently using a 2D grid structure.
 * The system should:
 *   1. Calculate and report the total marks for each student.
 *   2. Identify and report the topper (student with highest mark) for each subject,
 *      along with their score.
 *
 * Sample Input:
 *   Subjects Math, Sci, Eng.
 *   Anu [80, 90, 70]
 *   Ravi [60, 85] (missing Eng)
 *   Meena [95, 75, 88]
 *
 * Expected Output:
 *   Totals Anu 240, Ravi 145, Meena 258
 *   toppers Math Meena 95, Sci Anu 90, Eng Meena 88
 */
public class Problem3 {

    public static class StudentRecord {
        public final String name;
        public final Integer[] marks; // null or absent indicates missing mark

        public StudentRecord(String name, Integer[] marks) {
            this.name = name;
            this.marks = marks != null ? marks : new Integer[0];
        }

        public int getTotalMarks() {
            int sum = 0;
            for (Integer m : marks) {
                if (m != null) {
                    sum += m;
                }
            }
            return sum;
        }
    }

    public static class MarksGridSystem {
        private final List<String> subjects;
        private final List<StudentRecord> students = new ArrayList<>();

        public MarksGridSystem(List<String> subjects) {
            this.subjects = new ArrayList<>(subjects);
        }

        public void addStudent(String name, Integer[] marks) {
            students.add(new StudentRecord(name, marks));
        }

        /**
         * Reports totals for all students.
         */
        public String reportTotals() {
            StringBuilder sb = new StringBuilder("Totals ");
            for (int i = 0; i < students.size(); i++) {
                StudentRecord s = students.get(i);
                sb.append(s.name).append(" ").append(s.getTotalMarks());
                if (i < students.size() - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }

        /**
         * Reports the topper for each subject.
         */
        public String reportToppers() {
            StringBuilder sb = new StringBuilder("toppers ");
            for (int subjectIdx = 0; subjectIdx < subjects.size(); subjectIdx++) {
                String subjectName = subjects.get(subjectIdx);
                String topperName = "None";
                int highestScore = Integer.MIN_VALUE;

                for (StudentRecord student : students) {
                    if (subjectIdx < student.marks.length && student.marks[subjectIdx] != null) {
                        int score = student.marks[subjectIdx];
                        if (score > highestScore) {
                            highestScore = score;
                            topperName = student.name;
                        }
                    }
                }

                sb.append(subjectName).append(" ").append(topperName).append(" ").append(highestScore);
                if (subjectIdx < subjects.size() - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Week 10 Practice - Problem 3: Student Marks Grid ===");

        List<String> subjects = Arrays.asList("Math", "Sci", "Eng");
        MarksGridSystem system = new MarksGridSystem(subjects);

        system.addStudent("Anu", new Integer[]{80, 90, 70});
        system.addStudent("Ravi", new Integer[]{60, 85}); // missing Eng
        system.addStudent("Meena", new Integer[]{95, 75, 88});

        String totalsOutput = system.reportTotals();
        String toppersOutput = system.reportToppers();

        System.out.println(totalsOutput);
        System.out.println(toppersOutput);

        System.out.println("\nExpected Output:");
        System.out.println("Totals Anu 240, Ravi 145, Meena 258");
        System.out.println("toppers Math Meena 95, Sci Anu 90, Eng Meena 88");
    }
}
