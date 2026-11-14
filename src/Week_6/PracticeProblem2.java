package Week6;

/**
 * Practice Problem 2: Three Shapes of One Family Tree
 *
 * TASK:
 * 1. Multilevel: PremiumWorkshopTicket extends WorkshopTicket (3 levels).
 * 2. Hierarchical: HackathonTicket extends EventTicket directly.
 * 3. classifyGeneration(ticket) using instanceof.
 * 4. getTotalBalanceDue(tickets) leveraging polymorphism without manual type checks.
 */
public class PracticeProblem2 {

    public static class EventTicket {
        protected String attendeeId;
        protected double basePrice;
        protected double amountPaid;

        public EventTicket(String attendeeId, double basePrice) {
            this.attendeeId = attendeeId;
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
        }

        public double getBalanceDue() {
            return Math.max(0.0, basePrice - amountPaid);
        }

        public String printTicket() {
            return "Standard Event Ticket | Balance Due: " + getBalanceDue();
        }
    }

    public static class WorkshopTicket extends EventTicket {
        protected String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        @Override
        public String printTicket() {
            return "Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue();
        }
    }

    public static class PremiumWorkshopTicket extends WorkshopTicket {
        private final double kitFee;

        public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
            super(attendeeId, basePrice, track);
            this.kitFee = kitFee;
        }

        @Override
        public double getBalanceDue() {
            return Math.max(0.0, (basePrice + kitFee) - amountPaid);
        }

        @Override
        public String printTicket() {
            return "Premium Workshop Ticket | Track: " + track + " | Kit Fee: " + kitFee + " | Balance Due: " + getBalanceDue();
        }
    }

    public static class HackathonTicket extends EventTicket {
        private final String teamName;

        public HackathonTicket(String attendeeId, double basePrice, String teamName) {
            super(attendeeId, basePrice);
            this.teamName = teamName;
        }

        @Override
        public String printTicket() {
            return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue();
        }
    }

    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        } else if (ticket instanceof WorkshopTicket) {
            return "Single-inheritance child";
        } else {
            return "Root base class";
        }
    }

    public static double getTotalBalanceDue(EventTicket[] tickets) {
        if (tickets == null) return 0.0;
        double total = 0.0;
        for (EventTicket t : tickets) {
            if (t != null) {
                total += t.getBalanceDue();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket standard = new EventTicket("STU1", 500);
        WorkshopTicket workshop = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premium = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 0); // net 2000
        HackathonTicket hackathon = new HackathonTicket("STU4", 800, "Byte Force");

        System.out.println(standard.printTicket());
        System.out.println(workshop.printTicket());
        System.out.println(premium.printTicket());
        System.out.println(hackathon.printTicket());

        System.out.println(classifyGeneration(premium));
        System.out.println(classifyGeneration(hackathon));

        EventTicket[] all = {standard, workshop, premium, hackathon};
        System.out.println("Total Balance Due: " + getTotalBalanceDue(all));
    }
}
