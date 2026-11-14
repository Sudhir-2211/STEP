package Week12;

/**
 * Problem 2: Ride Fare Engine
 *
 * TASK:
 * A ride app prices autos, cabs and bikes differently. All rides must be priced together in one loop.
 * Create an abstract Ride class with the distance and an abstract double calculateFare(),
 * and subclasses Auto, Cab and Bike. Store all rides in one Ride[] array, print each fare
 * and the grand total.
 * - Auto: base 30 + 15 per km.
 * - Cab: base 50 + 20 per km, and the whole fare is multiplied by the surge factor.
 * - Bike: base 20 + 10 per km, plus 2 per waiting minute.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Iterating through an array of n Ride objects and calling calculateFare() via dynamic polymorphism:
 *      Each calculateFare() invocation takes O(1) arithmetic operations.
 *    - Total Time Complexity: O(n) to price all rides.
 *
 * 2. Space Complexity:
 *    - Array of size n storing Ride references: O(n).
 *    - Auxiliary calculation memory: O(1).
 */
public class PracticeProblem2 {

    public abstract static class Ride {
        protected double distance;

        public Ride(double distance) {
            this.distance = distance;
        }

        public abstract double calculateFare();
        public abstract String getType();
    }

    public static class Auto extends Ride {
        public Auto(double distance) {
            super(distance);
        }

        @Override
        public double calculateFare() {
            return 30.0 + 15.0 * distance;
        }

        @Override
        public String getType() {
            return "Auto";
        }
    }

    public static class Cab extends Ride {
        private final double surge;

        public Cab(double distance, double surge) {
            super(distance);
            this.surge = surge;
        }

        @Override
        public double calculateFare() {
            // (50 + 20 * distance) * surge
            return (50.0 + 20.0 * distance) * surge;
        }

        @Override
        public String getType() {
            return "Cab";
        }
    }

    public static class Bike extends Ride {
        private final int waitingMinutes;

        public Bike(double distance, int waitingMinutes) {
            super(distance);
            this.waitingMinutes = waitingMinutes;
        }

        @Override
        public double calculateFare() {
            // base 20 + 10 per km + 2 per waiting minute
            return 20.0 + 10.0 * distance + 2.0 * waitingMinutes;
        }

        @Override
        public String getType() {
            return "Bike";
        }
    }

    private static String formatFare(double fare) {
        if (fare == (long) fare) {
            return String.valueOf((long) fare);
        }
        return String.valueOf(fare);
    }

    public static void main(String[] args) {
        Ride[] rides = new Ride[]{
                new Auto(4),
                new Cab(6, 1.5),
                new Bike(3, 5)
        };

        System.out.println("Expected Output:");
        System.out.println("Auto: 90");
        System.out.println("Cab: 255");
        System.out.println("Bike: 60");
        System.out.println("Total: 405");
        System.out.println();

        System.out.println("Actual Output:");
        double totalFare = 0.0;
        for (Ride ride : rides) {
            double fare = ride.calculateFare();
            totalFare += fare;
            System.out.println(ride.getType() + ": " + formatFare(fare));
        }
        System.out.println("Total: " + formatFare(totalFare));
    }
}
