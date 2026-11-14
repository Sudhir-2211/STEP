package Week6;

/**
 * Practice Problem 4: Fest-Wide Program Announcement & Downcast Guard
 *
 * TASK:
 * 1. batchPrint(EventTicket[] tickets) prints tickets polymorphically using StringBuilder.
 * 2. Guard downcast to WorkshopTicket using instanceof to access track safely.
 */
public class PracticeProblem4 {

    public static class EventTicket {
        protected double basePrice;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
        }

        public String printTicket() {
            return "Standard | Balance: " + basePrice;
        }
    }

    public static class WorkshopTicket extends EventTicket {
        private final String track;

        public WorkshopTicket(double basePrice, String track) {
            super(basePrice);
            this.track = track;
        }

        public String getTrack() {
            return track;
        }

        @Override
        public String printTicket() {
            return "Workshop | Track: " + track + " | Balance: " + basePrice;
        }
    }

    public static String batchPrint(EventTicket[] tickets) {
        if (tickets == null) return "";
        StringBuilder sb = new StringBuilder();

        for (EventTicket t : tickets) {
            if (t != null) {
                sb.append(t.printTicket());
                // Safe downcast guarded by instanceof
                if (t instanceof WorkshopTicket) {
                    WorkshopTicket wt = (WorkshopTicket) t;
                    sb.append(" [Track via downcast: ").append(wt.getTrack()).append("]");
                }
                sb.append(" | ");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        EventTicket[] tickets = {
                new EventTicket(500),
                new WorkshopTicket(1200, "AI/ML")
        };

        System.out.println(batchPrint(tickets));
    }
}
