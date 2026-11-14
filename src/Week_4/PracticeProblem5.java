package Week4;

/**
 * Practice Problem 5: Nightly Fleet Reconciliation Engine
 *
 * TASK:
 * Reconciliation processor handling real accounts, Sleeper accounts, and null entries safely.
 * Aggregates counts and total penalties across the batch.
 */
public class PracticeProblem5 {

    public static class BusTicketAccount {
        protected final String bookingId;
        protected final double ticketFare;

        public BusTicketAccount(String bookingId, double ticketFare) {
            this.bookingId = bookingId;
            this.ticketFare = ticketFare;
        }

        public BusTicketAccount(String bookingId) {
            this(bookingId, 1000.0);
        }

        public final double calculatePenalty(int minutesLate) {
            PracticeProblem4.BoardingPenaltyCalculator calc = new PracticeProblem4.BoardingPenaltyCalculator(1.0);
            return calc.calculatePenalty(ticketFare, minutesLate);
        }

        public String getBookingId() {
            return bookingId;
        }

        public double getTicketFare() {
            return ticketFare;
        }
    }

    public static class SleeperAccount extends BusTicketAccount {
        public SleeperAccount(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }
    }

    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double totalPenalties = 0.0;

        int n = accounts != null ? accounts.length : 0;

        for (int i = 0; i < n; i++) {
            BusTicketAccount acc = accounts[i];
            if (acc == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (acc instanceof SleeperAccount) {
                sleeperCount++;
            } else {
                regularCount++;
            }

            int minutesLate = (minutesLateArray != null && i < minutesLateArray.length) ? minutesLateArray[i] : 0;
            totalPenalties += acc.calculatePenalty(minutesLate);
        }

        System.out.println(processed + " processed | " + nullSkipped + " null skipped | " +
                sleeperCount + " sleeper | " + regularCount + " regular | grand total penalties = Rs " + totalPenalties);
    }

    public static void main(String[] args) {
        BusTicketAccount[] accounts = {
                new SleeperAccount("BK001", 2000),
                null,
                new BusTicketAccount("BK002", 1200)
        };
        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};

        processBatch(accounts, amounts, minutesLateArray);
    }
}
