package Week6;

public class Problem5 {

    public static class RaceEntry {
        // Shared static counter tracking bib issuance across all entries
        private static int bibCounter = 0;

        private final String entryCode;
        private String bibNumber;
        private double entryFee;
        private double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            // Static counter incremented inside base constructor, guaranteed once per object
            bibCounter++;
            this.entryCode = "M-" + bibCounter;
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
        }

        public static int getBibCounter() {
            return bibCounter;
        }

        public String getEntryCode() {
            return entryCode;
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        // Overloaded pay method 1: flat-amount payment
        public void pay(double amount) {
            this.balanceDue -= amount;
        }

        // Overloaded pay method 2: mode-aware payment reusing flat version internally
        public void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
        }

        // Discount code validation using character-by-character checks
        public static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }

            // Must start with 'M'
            if (code.charAt(0) != 'M') {
                return false;
            }

            // Next three characters must be digits
            for (int i = 1; i <= 3; i++) {
                if (!Character.isDigit(code.charAt(i))) {
                    return false;
                }
            }

            // Final character must be an uppercase letter
            return Character.isUpperCase(code.charAt(4));
        }
    }

    public static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }
    }

    public static class EliteRunnerEntry extends RunnerEntry {
        private double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }
    }

    public static class RelayTeamEntry extends RaceEntry {
        private int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        public int getTeamSize() {
            return teamSize;
        }
    }

    // Reconciles nightly batch without crashing on null entries
    public static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        if (entries != null) {
            for (RaceEntry entry : entries) {
                if (entry == null) {
                    nullSkipped++;
                    continue;
                }

                processed++;
                if (entry instanceof RelayTeamEntry) {
                    relay++;
                } else {
                    individual++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + relay + " relay | " + individual + " individual";
    }

    public static void main(String[] args) {
        System.out.println("--- Discount Code Validation ---");
        System.out.println(RaceEntry.isValidDiscountCode("M123A")); // true
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));  // false
        System.out.println(RaceEntry.isValidDiscountCode("X123A")); // false

        System.out.println("\n--- Mode-Aware Payment & Nightly Settlement ---");
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        runner.pay(10, "UPI");

        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] batch = {eliteEntry, null, relayEntry};
        System.out.println(settleNight(batch));

        System.out.println("\n--- Total Bib Counter ---");
        // Created: runner (1), eliteEntry (2), relayEntry (3) -> plus one plain entry to demonstrate 4
        new RaceEntry("BIB5001", 50);
        System.out.println(RaceEntry.getBibCounter()); // 4
    }
}
