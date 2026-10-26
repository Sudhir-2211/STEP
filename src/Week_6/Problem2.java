package Week6;

public class Problem2 {

    // Base class
    public static class RaceEntry {
        private String bibNumber;
        private double entryFee;
        private double balanceDue;

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

        public double getEntryFee() {
            return entryFee;
        }

        public String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + balanceDue;
        }
    }

    // RunnerEntry: Direct subclass of RaceEntry (Single inheritance)
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

    // EliteRunnerEntry: Multilevel inheritance (RaceEntry -> RunnerEntry -> EliteRunnerEntry)
    public static class EliteRunnerEntry extends RunnerEntry {
        private double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        public double getSponsorBonus() {
            return sponsorBonus;
        }

        @Override
        public String announce() {
            return "Elite Runner | Bib: " + getBibNumber() + " | Category: " + getCategory() + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
        }
    }

    // RelayTeamEntry: Hierarchical inheritance (RaceEntry -> RelayTeamEntry, independent branch)
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

    // Classify family shape using instanceof checks only
    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else if (entry instanceof RunnerEntry) {
            return "Direct subclass (2 generations deep)";
        }
        return "Base class";
    }

    // Polymorphic balance calculation
    public static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0.0;
        for (RaceEntry entry : entries) {
            if (entry != null) {
                total += entry.getBalanceDue();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println("--- Announcements ---");
        System.out.println(runnerEntry.announce());
        System.out.println(eliteEntry.announce());
        System.out.println(relayEntry.announce());

        System.out.println("\n--- Generation Classification ---");
        System.out.println(classifyGeneration(eliteEntry));
        System.out.println(classifyGeneration(relayEntry));

        System.out.println("\n--- Total Outstanding Balance ---");
        RaceEntry[] entries = {runnerEntry, eliteEntry, relayEntry};
        System.out.println(getTotalBalanceDue(entries));
    }
}
