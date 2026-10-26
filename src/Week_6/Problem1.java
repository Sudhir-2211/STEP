package Week6;

public class Problem1 {

    // Base class representing a general race entry
    public static class RaceEntry {
        private String bibNumber;
        private double entryFee;
        private double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Bib number cannot be blank and must be at least 4 characters long.");
            }
            if (entryFee <= 0) {
                throw new IllegalArgumentException("Entry fee must be a positive amount.");
            }

            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.balanceDue -= amount;
            }
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public String getBibNumber() {
            return bibNumber;
        }

        public double getEntryFee() {
            return entryFee;
        }
    }

    // RunnerEntry specializes RaceEntry via single inheritance
    public static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            // Forward shared fields to super constructor
            super(bibNumber, entryFee);
            this.category = category;
        }

        public String getCategory() {
            return category;
        }
    }

    // Batch validator: attempts to construct entries and counts rejections via try-catch
    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        System.out.println("--- Test 1: Invalid Bib Number Construction ---");
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        System.out.println("\n--- Test 2: RunnerEntry Payment & Balance Due ---");
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println(r.getBalanceDue());

        System.out.println("\n--- Test 3: Batch Bib Registration ---");
        String[] batch = {"BIB1", "B1", "BIB2"};
        System.out.println(registerBatch(batch, 80));
    }
}
