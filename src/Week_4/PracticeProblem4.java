package Week4;

/**
 * Practice Problem 4: Tiered Boarding Penalty Calculator
 *
 * TASK:
 * Escalating late-boarding penalty:
 * - Minutes 1–5: 0.5% of ticketFare per minute.
 * - Minutes 6–15: 1.0% of ticketFare per minute.
 * - Minutes >= 16: 2.0% of ticketFare per minute.
 * If passenger is late (> 0 min), penalty is at least minimumPenaltyPercent of ticketFare (floor).
 * If on-time (minutesLate == 0), penalty is Rs 0.0.
 */
public class PracticeProblem4 {

    public static final class BoardingPenaltyCalculator {
        private final double minimumPenaltyPercent;

        public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
            this.minimumPenaltyPercent = minimumPenaltyPercent;
        }

        public final double calculatePenalty(double ticketFare, int minutesLate) {
            if (ticketFare < 0 || minutesLate < 0) {
                throw new IllegalArgumentException("Fare and minutes late must be non-negative");
            }
            if (minutesLate == 0) {
                return 0.0;
            }

            double tieredRatePercent = 0.0;

            // Bracket 1: minutes 1 to 5 (up to 5 min at 0.5% per min)
            int b1Minutes = Math.min(minutesLate, 5);
            tieredRatePercent += b1Minutes * 0.5;

            // Bracket 2: minutes 6 to 15 (up to 10 min at 1.0% per min)
            if (minutesLate > 5) {
                int b2Minutes = Math.min(minutesLate - 5, 10);
                tieredRatePercent += b2Minutes * 1.0;
            }

            // Bracket 3: minutes 16+ (at 2.0% per min)
            if (minutesLate > 15) {
                int b3Minutes = minutesLate - 15;
                tieredRatePercent += b3Minutes * 2.0;
            }

            double calculatedPenalty = (ticketFare * tieredRatePercent) / 100.0;
            double minimumFloor = (ticketFare * minimumPenaltyPercent) / 100.0;

            return Math.max(calculatedPenalty, minimumFloor);
        }
    }

    public static void main(String[] args) {
        BoardingPenaltyCalculator calc = new BoardingPenaltyCalculator(1.0); // 1.0% floor

        System.out.println("ticketFare = 1000, minutesLate = 0  -> Rs " + calc.calculatePenalty(1000, 0));
        System.out.println("ticketFare = 1000, minutesLate = 1  -> Rs " + calc.calculatePenalty(1000, 1));
        System.out.println("ticketFare = 1000, minutesLate = 16 -> Rs " + calc.calculatePenalty(1000, 16));
    }
}
