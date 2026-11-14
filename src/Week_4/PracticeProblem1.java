package Week4;

import java.util.HashSet;
import java.util.Set;

/**
 * Practice Problem 1: Bus Ticket Booking Validator
 *
 * TASK:
 * Design BusTicket so invalid input fails at construction time.
 * Validate that passengerName and destination are non-empty and valid (letters/spaces only).
 * Write processBatch that classifies raw attempts into Valid, Rejected, or Duplicates.
 */
public class PracticeProblem1 {

    public static class BusTicket {
        private final String passengerName;
        private final String destination;
        private boolean checkedIn;

        public BusTicket(String passengerName, String destination) {
            if (!isValidName(passengerName) || !isValidDestination(destination)) {
                throw new IllegalArgumentException("Invalid passenger name or destination");
            }
            this.passengerName = passengerName.trim();
            this.destination = destination.trim();
            this.checkedIn = false;
        }

        public void markCheckedIn() {
            if (checkedIn) {
                System.out.println("Already checked in.");
                return;
            }
            this.checkedIn = true;
        }

        public String getPassengerName() {
            return passengerName;
        }

        public String getDestination() {
            return destination;
        }

        private static boolean isValidName(String name) {
            if (name == null) return false;
            String trimmed = name.trim();
            if (trimmed.isEmpty()) return false;
            for (char ch : trimmed.toCharArray()) {
                if (!Character.isLetter(ch) && ch != ' ') {
                    return false; // No numbers or special characters allowed in name
                }
            }
            return true;
        }

        private static boolean isValidDestination(String dest) {
            if (dest == null) return false;
            String trimmed = dest.trim();
            if (trimmed.isEmpty()) return false;
            for (char ch : trimmed.toCharArray()) {
                if (!Character.isLetter(ch) && ch != ' ') {
                    return false;
                }
            }
            return true;
        }
    }

    public static void processBatch(String[][] rawBookings) {
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        Set<String> acceptedPairs = new HashSet<>();

        for (String[] attempt : rawBookings) {
            if (attempt == null || attempt.length < 2) {
                rejected++;
                continue;
            }

            String name = attempt[0];
            String dest = attempt[1];

            try {
                BusTicket ticket = new BusTicket(name, dest);
                String key = ticket.getPassengerName().toLowerCase() + "|" + ticket.getDestination().toLowerCase();

                if (acceptedPairs.contains(key)) {
                    duplicates++;
                } else {
                    acceptedPairs.add(key);
                    valid++;
                }
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected + " | Duplicates skipped: " + duplicates);
    }

    public static void main(String[] args) {
        String[][] raw = {
                {"Divya", "Chennai"},
                {"", "Bangalore"},
                {"Ravi123", "Pune"},
                {"Divya", "Chennai"},
                {"   ", "  "}
        };

        processBatch(raw);
    }
}
