package Week3;

/**
 * Practice Problem 5: Capstone: A Small Fee + Hostel Management Mini-System
 *
 * TASK:
 * Combine FeeAccount, HostelFeeAccount, and HostelRoom into a comprehensive SrmStudent
 * model demonstrating composition (objects containing objects), encapsulation,
 * inheritance, null-safe field handling, and static counters.
 */
public class PracticeProblem5 {

    public static class HostelRoom {
        private final String roomNo;

        public HostelRoom(String roomNo) {
            this.roomNo = roomNo;
        }

        public String getRoomNo() {
            return roomNo;
        }
    }

    public static class FeeAccount {
        protected double totalFee;
        protected double amountPaid;

        public FeeAccount(double totalFee, double amountPaid) {
            this.totalFee = totalFee;
            this.amountPaid = amountPaid;
        }

        public boolean pay(double amount) {
            if (amount <= 0) {
                return false;
            }
            this.amountPaid += amount;
            return true;
        }

        public double getDue() {
            return Math.max(0.0, totalFee - amountPaid);
        }
    }

    public static class HostelFeeAccount extends FeeAccount {
        public HostelFeeAccount(double totalFee, double amountPaid) {
            super(totalFee, amountPaid);
        }
    }

    public static class SrmStudent {
        public static int totalStudents = 0;

        private final String name;
        private final String regNo;
        private final HostelFeeAccount feeAccount;
        private HostelRoom room;

        public SrmStudent(String name, String regNo, HostelFeeAccount feeAccount) {
            this.name = name;
            this.regNo = regNo;
            this.feeAccount = feeAccount;
            this.room = null; // Unallotted by default
            totalStudents++;
        }

        public void setRoom(HostelRoom room) {
            this.room = room;
        }

        public HostelFeeAccount getFeeAccount() {
            return feeAccount;
        }

        public String fullStatus() {
            String roomDesc = (room != null) ? room.getRoomNo() : "unallotted";
            return name + " | Due: Rs " + feeAccount.getDue() + " | Room: " + roomDesc;
        }
    }

    public static void main(String[] args) {
        SrmStudent s1 = new SrmStudent("Ravi", "RA01", new HostelFeeAccount(200000, 60000));
        SrmStudent s2 = new SrmStudent("Anitha", "RA02", new HostelFeeAccount(200000, 20000));
        SrmStudent s3 = new SrmStudent("Karthik", "RA03", new HostelFeeAccount(200000, 0));

        // Allot rooms to two of them
        s1.setRoom(new HostelRoom("C-214"));
        s2.setRoom(new HostelRoom("C-507"));

        // Process a payment (including an invalid negative payment attempt)
        s1.getFeeAccount().pay(-5000); // rejected

        System.out.println(s1.fullStatus());
        System.out.println(s2.fullStatus());
        System.out.println(s3.fullStatus());
        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}
