package Week3;

/**
 * Practice Problem 4: Designing the Instance/Static Boundary for a College-Wide System
 *
 * TASK:
 * 1. Demonstrate why marking student attributes (name, regNo, attendance) static causes
 *    shared-state bugs where each new student overwrites the previous student's data.
 * 2. Redesign the class properly with instance fields for student data and static fields
 *    for global metadata (university, admissionCount).
 */
public class PracticeProblem4 {

    // --- BROKEN VERSION (Misusing static) ---
    public static class BrokenStudent {
        public static String name;
        public static String regNo;

        public BrokenStudent(String n, String r) {
            name = n;
            regNo = r;
        }

        public void printName() {
            System.out.println(name);
        }
    }

    // --- FIXED VERSION (Proper separation of instance vs static) ---
    public static class FixedStudent {
        public static final String UNIVERSITY = "SRM University";
        private static int admissionCount = 10;

        private final String name;
        private final String regNo;

        public FixedStudent(String name) {
            this.name = name;
            admissionCount++;
            this.regNo = "RA2311003010" + admissionCount;
        }

        public void printIdCard() {
            System.out.println(name + " | " + regNo);
        }

        public static void printTotalAdmissions() {
            System.out.println("Students admitted so far: " + (admissionCount - 10));
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Broken Version Demonstration ---");
        BrokenStudent s1 = new BrokenStudent("Ravi", "01");
        BrokenStudent s2 = new BrokenStudent("Meera", "02");
        s1.printName(); // Prints Meera because static field was overwritten!
        s2.printName(); // Prints Meera
        System.out.println("(Ravi's data was overwritten — both students now show \"Meera\")");

        System.out.println();
        System.out.println("--- Fixed Version Demonstration ---");
        FixedStudent f1 = new FixedStudent("Ravi");
        FixedStudent f2 = new FixedStudent("Meera");
        f1.printIdCard();
        f2.printIdCard();
        FixedStudent.printTotalAdmissions();
    }
}
