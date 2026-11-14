package Week8;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Practice Problem 3: Hotel Booking and Cancellation System
 *
 * TASK:
 * 1. Room hierarchy: StandardRoom, DeluxeRoom, Suite.
 * 2. Overlapping reservation prevention for the same room.
 * 3. Dynamic pricing based on duration and room category.
 * 4. Cancellation handling before deadlines.
 */
public class PracticeProblem3 {

    public static abstract class Room {
        private final String roomNumber;
        private final String typeName;

        public Room(String roomNumber, String typeName) {
            this.roomNumber = roomNumber;
            this.typeName = typeName;
        }

        public abstract double getDailyRate();

        public String getRoomNumber() { return roomNumber; }
        public String getTypeName() { return typeName; }
    }

    public static class DeluxeRoom extends Room {
        public DeluxeRoom(String roomNumber) {
            super(roomNumber, "Deluxe Room " + roomNumber);
        }

        @Override
        public double getDailyRate() {
            return 200.0;
        }
    }

    public static class StandardRoom extends Room {
        public StandardRoom(String roomNumber) {
            super(roomNumber, "Standard Room " + roomNumber);
        }

        @Override
        public double getDailyRate() {
            return 100.0;
        }
    }

    public static class Reservation {
        public final String id;
        public final Room room;
        public final LocalDate checkIn;
        public final LocalDate checkOut;
        public boolean cancelled;

        public Reservation(String id, Room room, LocalDate checkIn, LocalDate checkOut) {
            this.id = id;
            this.room = room;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            this.cancelled = false;
        }

        public boolean overlaps(LocalDate otherIn, LocalDate otherOut) {
            return !cancelled && !(otherOut.isBefore(checkIn) || otherIn.isAfter(checkOut));
        }
    }

    public static class HotelBookingService {
        private final List<Reservation> reservations = new ArrayList<>();
        private int resCounter = 100;

        public Reservation bookRoom(Room room, LocalDate checkIn, LocalDate checkOut) {
            for (Reservation r : reservations) {
                if (r.room.getRoomNumber().equals(room.getRoomNumber()) && r.overlaps(checkIn, checkOut)) {
                    System.out.println("Booking failed: Room " + room.getTypeName() + " has an overlapping reservation.");
                    return null;
                }
            }
            long days = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
            double total = days * room.getDailyRate();
            Reservation res = new Reservation("RES-" + (++resCounter), room, checkIn, checkOut);
            reservations.add(res);
            System.out.printf("Booked %s from %s to %s. Total: $%.2f (ID: %s)%n",
                    room.getTypeName(), checkIn, checkOut, total, res.id);
            return res;
        }

        public void cancelReservation(Reservation res) {
            if (res != null && !res.cancelled) {
                res.cancelled = true;
                System.out.println("Reservation " + res.id + " for " + res.room.getTypeName() + " cancelled successfully.");
            }
        }
    }

    public static void main(String[] args) {
        HotelBookingService service = new HotelBookingService();
        Room deluxe101 = new DeluxeRoom("101");
        Room standard205 = new StandardRoom("205");

        Reservation r1 = service.bookRoom(deluxe101, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        service.bookRoom(standard205, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));

        // Overlapping reservation attempt
        service.bookRoom(deluxe101, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));

        // Cancellation
        service.cancelReservation(r1);
    }
}
