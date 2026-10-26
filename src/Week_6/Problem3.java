package Week6;

import java.util.Arrays;

public class Problem3 {

    public static class RaceEntry {
        private String bibNumber;
        private double entryFee;
        private double balanceDue;

        // Audit trail: private array storing applied late fees (up to 10 per constraint)
        private double[] lateFees = new double[10];
        private int lateFeeCount = 0;

        public RaceEntry(String bibNumber, double entryFee) {
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
        }

        public void pay(double amount) {
            this.balanceDue -= amount;
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public String getBibNumber() {
            return bibNumber;
        }

        // Base penalty deduction and internal history recording
        protected void applyLateFee(double amount) {
            this.balanceDue += amount;
            if (lateFeeCount < lateFees.length) {
                lateFees[lateFeeCount++] = amount;
            }
        }

        // Returns a defensive copy to prevent outside tampering
        public double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFees, lateFeeCount);
        }
    }

    public static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        public String getCategory() {
            return category;
        }

        // Overrides late fee to double the penalty, reusing base logic via super
        @Override
        public void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1: Doubled Late Fee for Runner ---");
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println(r.getBalanceDue()); // 80 - 30 + 40 = 90.0

        System.out.println("\n--- Test 2: Defensive Copy Audit Trail ---");
        double[] history = r.getLateFeeHistory();
        System.out.println(Arrays.toString(history)); // [40.0]

        // Attempt to tamper with the returned array
        history[0] = 999;
        System.out.println("After attempting outside tampering: " + Arrays.toString(r.getLateFeeHistory())); // [40.0]
    }
}
