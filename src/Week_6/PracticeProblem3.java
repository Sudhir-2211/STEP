package Week6;

import java.util.Arrays;

/**
 * Practice Problem 3: The Late-Registration Penalty Override & Audit Trail
 *
 * TASK:
 * 1. EventTicket has applyLateFee(double amount) which records fee into an audit array.
 * 2. WorkshopTicket overrides applyLateFee with super.applyLateFee(amount * 2).
 * 3. getLateFeeHistory() returns a defensive copy.
 */
public class PracticeProblem3 {

    public static class EventTicket {
        protected double basePrice;
        protected double amountPaid;
        private final double[] lateFeeHistory;
        private int feeCount;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
            this.lateFeeHistory = new double[10];
            this.feeCount = 0;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.amountPaid += amount;
            }
        }

        public double getBalanceDue() {
            return basePrice - amountPaid;
        }

        protected void applyLateFee(double amount) {
            if (amount > 0) {
                this.basePrice += amount;
                if (feeCount < lateFeeHistory.length) {
                    lateFeeHistory[feeCount++] = amount;
                }
            }
        }

        public double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, feeCount);
        }
    }

    public static class WorkshopTicket extends EventTicket {
        public WorkshopTicket(double basePrice) {
            super(basePrice);
        }

        @Override
        public void applyLateFee(double amount) {
            // Reuses parent's logic, doubling the penalty for workshops
            super.applyLateFee(amount * 2.0);
        }
    }

    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println("Workshop balance due: " + w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println("History: " + Arrays.toString(history));
        history[0] = 999;
        System.out.println("Tamper-proof check: " + Arrays.toString(w.getLateFeeHistory()));
    }
}
