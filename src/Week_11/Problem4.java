package Week11;

/**
 * Problem 4: The Bus Bay Ring
 *
 * TASK:
 * The campus bus bay has room for a fixed number of buses. Buses can enter or leave
 * from either end of the bay. Design a class BusBay(int capacity) backed by a fixed-size
 * int array used as a ring buffer, with the methods insertFront(bus), insertLast(bus),
 * deleteFront(), deleteLast(), getFront(), getRear(), isEmpty() and isFull().
 * Insert and delete methods return true on success and false if the bay is full or empty;
 * getFront and getRear return -1 when the bay is empty.
 * Every method must run in O(1): no shifting of elements and no Java collection classes.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - All operations (insertFront, insertLast, deleteFront, deleteLast, getFront, getRear,
 *      isEmpty, isFull) perform modular arithmetic index calculations and direct array indexing.
 *    - No shifting of elements is ever performed.
 *    - Time Complexity for every method: O(1).
 *
 * 2. Space Complexity:
 *    - The storage is backed by a single fixed-size primitive int array of length 'capacity'.
 *    - Total Auxiliary Space Complexity: O(capacity).
 */
public class Problem4 {

    public static class BusBay {
        private final int[] buffer;
        private final int capacity;
        private int front;
        private int size;

        public BusBay(int capacity) {
            this.capacity = capacity;
            this.buffer = new int[capacity];
            this.front = 0;
            this.size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == capacity;
        }

        public boolean insertLast(int bus) {
            if (isFull()) {
                return false;
            }
            int rear = (front + size) % capacity;
            buffer[rear] = bus;
            size++;
            return true;
        }

        public boolean insertFront(int bus) {
            if (isFull()) {
                return false;
            }
            // Move front pointer counter-clockwise in the ring
            front = (front - 1 + capacity) % capacity;
            buffer[front] = bus;
            size++;
            return true;
        }

        public boolean deleteFront() {
            if (isEmpty()) {
                return false;
            }
            // Move front pointer clockwise in the ring
            front = (front + 1) % capacity;
            size--;
            return true;
        }

        public boolean deleteLast() {
            if (isEmpty()) {
                return false;
            }
            // Decrement size; the rear slot is automatically freed
            size--;
            return true;
        }

        public int getFront() {
            if (isEmpty()) {
                return -1;
            }
            return buffer[front];
        }

        public int getRear() {
            if (isEmpty()) {
                return -1;
            }
            int rear = (front + size - 1) % capacity;
            return buffer[rear];
        }
    }

    public static void main(String[] args) {
        System.out.println("Initializing BusBay with capacity: 3");
        BusBay b = new BusBay(3);

        boolean a1 = b.insertLast(101);
        boolean a2 = b.insertLast(102);
        boolean a3 = b.insertFront(100);
        boolean a4 = b.insertFront(99);
        int a5 = b.getRear();
        boolean a6 = b.isFull();
        boolean a7 = b.deleteLast();
        boolean a8 = b.insertFront(98);
        int a9 = b.getFront();

        String actualOutput = a1 + ", " + a2 + ", " + a3 + ", " + a4 + ", " + a5 + ", " + a6 + ", " + a7 + ", " + a8 + ", " + a9;
        System.out.println("Expected Output: true, true, true, false, 102, true, true, true, 98");
        System.out.println("Actual Output:   " + actualOutput);
    }
}
