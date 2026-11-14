package Week3;

/**
 * Practice Problem 3: Object References, Null Safety, and a Mutating Method
 *
 * TASK:
 * Define HostelRoom with roomNo, beds, occupied, and allot(studentName).
 * Static utility findAvailableRoom(HostelRoom[] rooms) returns first available room or null.
 * Static utility safeAllot(HostelRoom[] rooms, String studentName) handles null safely.
 *
 * Explanation of Object References:
 * In Java, objects are passed by reference value (the reference address is copied, not the object itself).
 * Passing a HostelRoom[] array to a method passes references pointing to the existing room objects on the heap.
 * Therefore, mutating a room inside safeAllot modifies the actual room without copying the objects.
 */
public class PracticeProblem3 {

    public static class HostelRoom {
        private final String roomNo;
        private final int beds;
        private int occupied;

        public HostelRoom(String roomNo, int occupied, int beds) {
            this.roomNo = roomNo;
            this.occupied = occupied;
            this.beds = beds;
        }

        public boolean allot(String studentName) {
            if (occupied < beds) {
                occupied++;
                System.out.println(studentName + " allotted to room " + roomNo);
                return true;
            }
            return false;
        }

        public boolean isAvailable() {
            return occupied < beds;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public int getOccupied() {
            return occupied;
        }

        public int getBeds() {
            return beds;
        }
    }

    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) return null;
        for (HostelRoom room : rooms) {
            if (room != null && room.isAvailable()) {
                return room;
            }
        }
        return null;
    }

    public static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom availableRoom = findAvailableRoom(rooms);
        if (availableRoom != null) {
            availableRoom.allot(studentName);
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {
        // Case 1: Room available
        HostelRoom[] rooms1 = {
                new HostelRoom("C-214", 2, 3),
                new HostelRoom("C-507", 2, 2)
        };
        System.out.println("Rooms: C-214 (2/3), C-507 (2/2)");
        safeAllot(rooms1, "Divya");

        System.out.println();

        // Case 2: All rooms full
        HostelRoom[] rooms2 = {
                new HostelRoom("C-214", 3, 3),
                new HostelRoom("C-507", 2, 2)
        };
        System.out.println("Rooms: C-214 (3/3), C-507 (2/2)");
        safeAllot(rooms2, "Divya");
    }
}
