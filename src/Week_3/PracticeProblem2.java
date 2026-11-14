package Week3;

/**
 * Practice Problem 2: Extending FeeAccount Without Touching It
 *
 * TASK:
 * Open-Closed Principle demo:
 * - Base class FeeAccount with regNo, totalFee, amountPaid, pay(double), getDue().
 * - HostelFeeAccount extends FeeAccount, adding payInTwoInstallments(double).
 * - ScholarshipFeeAccount extends FeeAccount, adding scholarshipPercent and effectiveDue().
 */
public class PracticeProblem2 {

    public static class FeeAccount {
        private final String regNo;
        protected double totalFee;
        protected double amountPaid;

        public FeeAccount(String regNo, double totalFee, double amountPaid) {
            this.regNo = regNo;
            this.totalFee = totalFee;
            this.amountPaid = amountPaid;
        }

        public void pay(double amount) {
            if (amount <= 0) {
                System.out.println("Invalid payment amount: must be positive.");
                return;
            }
            this.amountPaid += amount;
        }

        public double getDue() {
            return Math.max(0.0, totalFee - amountPaid);
        }

        public String getRegNo() {
            return regNo;
        }
    }

    public static class HostelFeeAccount extends FeeAccount {
        public HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
            super(regNo, totalFee, amountPaid);
        }

        public void payInTwoInstallments(double totalInstallmentAmount) {
            double half = totalInstallmentAmount / 2.0;
            pay(half);
            pay(half);
        }
    }

    public static class ScholarshipFeeAccount extends FeeAccount {
        private final double scholarshipPercent;

        public ScholarshipFeeAccount(String regNo, double totalFee, double amountPaid, double scholarshipPercent) {
            super(regNo, totalFee, amountPaid);
            this.scholarshipPercent = scholarshipPercent;
        }

        public double effectiveDue() {
            double rawDue = getDue();
            double discount = (rawDue * scholarshipPercent) / 100.0;
            return Math.max(0.0, rawDue - discount);
        }
    }

    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("RA01", 150000, 150000);
        HostelFeeAccount hostel = new HostelFeeAccount("RA02", 200000, 60000);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("RA03", 180000, 0, 20);

        FeeAccount[] accounts = {plain, hostel, scholarship};

        for (FeeAccount acc : accounts) {
            if (acc instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount sAcc = (ScholarshipFeeAccount) acc;
                System.out.println("Scholarship account effective due: Rs " + sAcc.effectiveDue());
            } else if (acc instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + acc.getDue());
            } else {
                System.out.println("Plain account due: Rs " + acc.getDue());
            }
        }
    }
}
