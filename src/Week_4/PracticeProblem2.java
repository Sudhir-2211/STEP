package Week4;

import java.util.Arrays;

/**
 * Practice Problem 2: Remainder-Fair FareSplitter
 *
 * TASK:
 * 1. Constructor chaining via this(...).
 * 2. Reject negative fare, non-positive passengerCount.
 * 3. fareBreakdown() returns split shares that sum to EXACTLY totalFare without losing cents/paise.
 *    Any leftover cents are distributed to the final share(s).
 */
public class PracticeProblem2 {

    public static class FareSplitter {
        private final String tripId;
        private final double totalFare;
        private final int passengerCount;

        public FareSplitter(String tripId, double totalFare, int passengerCount) {
            if (totalFare < 0) {
                throw new IllegalArgumentException("Total fare cannot be negative");
            }
            if (passengerCount <= 0) {
                throw new IllegalArgumentException("Passenger count must be positive");
            }
            this.tripId = tripId;
            this.totalFare = totalFare;
            this.passengerCount = passengerCount;
        }

        public FareSplitter(String tripId, double totalFare) {
            this(tripId, totalFare, 2); // Default to 2 passengers if unspecified
        }

        public FareSplitter(String tripId) {
            this(tripId, 0.0, 2); // Provisional ID-only split defaults to 0 fare, 2 passengers
        }

        public double[] fareBreakdown() {
            if (passengerCount == 0) return new double[0];

            long totalCents = Math.round(totalFare * 100);
            long baseShareCents = totalCents / passengerCount;
            long remainderCents = totalCents % passengerCount;

            double[] breakdown = new double[passengerCount];
            for (int i = 0; i < passengerCount; i++) {
                // The last 'remainderCents' passengers absorb 1 extra cent so sum is exact
                long shareCents = baseShareCents + (i >= (passengerCount - remainderCents) ? 1 : 0);
                breakdown[i] = shareCents / 100.0;
            }

            return breakdown;
        }

        public boolean isConfirmationOverdue(int confirmed, int expected) {
            return confirmed < expected;
        }
    }

    public static void main(String[] args) {
        FareSplitter f1 = new FareSplitter("TRIP001", 100000, 3);
        System.out.println("TRIP001 fareBreakdown: " + Arrays.toString(f1.fareBreakdown()));

        FareSplitter f2 = new FareSplitter("TRIP003");
        System.out.println("TRIP003 fareBreakdown: " + Arrays.toString(f2.fareBreakdown()));
    }
}
