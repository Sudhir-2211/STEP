package Week6;

/**
 * Practice Problem 1: Ticket Hierarchy Foundation & Batch Registration Validator
 *
 * TASK:
 * 1. EventTicket foundation class validating attendeeId (not blank/whitespace, len >= 4).
 * 2. WorkshopTicket extends EventTicket forwarding fields via super(...).
 * 3. registerBatch(...) creates tickets and counts valid vs rejected registrations.
 */
public class PracticeProblem1 {

    public static class EventTicket {
        protected final String attendeeId;
        protected final double basePrice;
        protected double amountPaid;

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid attendeeId: must be at least 4 non-whitespace characters.");
            }
            this.attendeeId = attendeeId.trim();
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.amountPaid += amount;
            }
        }

        public double getBalanceDue() {
            return Math.max(0.0, basePrice - amountPaid);
        }

        public String getAttendeeId() {
            return attendeeId;
        }
    }

    public static class WorkshopTicket extends EventTicket {
        private final String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        public String getTrack() {
            return track;
        }
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        if (attendeeIds == null) return "Registered: 0 | Rejected: 0";

        int registered = 0;
        int rejected = 0;

        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        try {
            new EventTicket("ST1", 500);
            System.out.println("ST1 succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("new EventTicket(\"ST1\", 500) -> construction rejected");
        }

        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println("Workshop balance due: " + w.getBalanceDue());

        String[] batch = {"STU1", "ST1", "STU2", "  ", "STU3"};
        System.out.println(registerBatch(batch, 500));
    }
}
