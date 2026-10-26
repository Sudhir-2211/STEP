package Week6;

public class Problem4 {

    public static class RaceEntry {
        private String bibNumber;
        private double entryFee;
        private double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public String getBibNumber() {
            return bibNumber;
        }

        public void setBalanceDue(double balanceDue) {
            this.balanceDue = balanceDue;
        }

        public String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + balanceDue;
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

        @Override
        public String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category + " | Balance: " + getBalanceDue();
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

        @Override
        public String announce() {
            return "Relay Team | Bib: " + getBibNumber() + " | Team Size: " + teamSize + " | Balance: " + getBalanceDue();
        }
    }

    // Builds announcement report using StringBuilder, polymorphic announce(), and instanceof-guarded downcast
    public static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();

        for (RaceEntry entry : entries) {
            if (entry != null) {
                report.append(entry.announce());

                // Guarded downcast to access RelayTeamEntry-specific method
                if (entry instanceof RelayTeamEntry) {
                    RelayTeamEntry relay = (RelayTeamEntry) entry;
                    report.append(" [Team size via downcast: ").append(relay.getTeamSize()).append("]");
                }

                report.append(" | ");
            }
        }

        return report.toString();
    }

    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        runner.setBalanceDue(90.0);

        RelayTeamEntry relay = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] fleet = {runner, relay};

        System.out.println("--- Announce All ---");
        System.out.println(announceAll(fleet));

        System.out.println("\n--- Unguarded Downcast Runtime Exception Demonstration ---");
        RaceEntry plain = new RaceEntry("BIB5001", 50);
        try {
            RelayTeamEntry bad = (RelayTeamEntry) plain;
            System.out.println(bad);
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}
