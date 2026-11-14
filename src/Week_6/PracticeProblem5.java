package Week6;

/**
 * Practice Problem 5: Fest-Wide Ticket Issuance, Promo Codes & Nightly Settlement Engine
 *
 * TASK:
 * 1. Auto-incrementing final ticketId from static counter.
 * 2. isValidPromoCode: exact format 'F' + 3 digits + 1 uppercase letter (no regex).
 * 3. Overloaded pay: pay(amount) and pay(amount, mode).
 * 4. GroupTicket subclass.
 * 5. processNightlySettlement(tickets) handles null entries and separates GroupTicket vs regular.
 */
public class PracticeProblem5 {

    public static class EventTicket {
        private static int counter = 1000;
        private static int ticketsIssued = 0;

        public final String ticketId;
        protected double basePrice;
        protected double amountPaid;

        public EventTicket(double basePrice) {
            this.ticketId = "TCK-" + (++counter);
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
            ticketsIssued++;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.amountPaid += amount;
            }
        }

        public void pay(double amount, String mode) {
            pay(amount);
        }

        public double getBalanceDue() {
            return Math.max(0.0, basePrice - amountPaid);
        }

        public static int getTicketsIssued() {
            return ticketsIssued;
        }

        public static boolean isValidPromoCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'F') {
                return false;
            }
            for (int i = 1; i <= 3; i++) {
                if (!Character.isDigit(code.charAt(i))) {
                    return false;
                }
            }
            return Character.isUpperCase(code.charAt(4));
        }
    }

    public static class GroupTicket extends EventTicket {
        private final int groupSize;

        public GroupTicket(double basePrice, int groupSize) {
            super(basePrice);
            this.groupSize = groupSize;
        }

        public int getGroupSize() {
            return groupSize;
        }
    }

    public static String processNightlySettlement(EventTicket[] tickets) {
        if (tickets == null) return "0 processed | 0 null skipped | 0 group | 0 individual";

        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        for (EventTicket t : tickets) {
            if (t == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (t instanceof GroupTicket) {
                groupCount++;
            } else {
                individualCount++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " +
                groupCount + " group | " + individualCount + " individual";
    }

    public static void main(String[] args) {
        EventTicket t1 = new EventTicket(500);
        System.out.println("t1.ticketId -> " + t1.ticketId);
        System.out.println("Tickets issued: " + EventTicket.getTicketsIssued());

        System.out.println("Promo F123A: " + EventTicket.isValidPromoCode("F123A"));
        System.out.println("Promo F12A:  " + EventTicket.isValidPromoCode("F12A"));
        System.out.println("Promo X123A: " + EventTicket.isValidPromoCode("X123A"));

        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println("Balance due: " + t1.getBalanceDue());

        EventTicket[] batch = {
                new GroupTicket(2000, 5),
                null,
                new EventTicket(500)
        };
        System.out.println(processNightlySettlement(batch));
    }
}
