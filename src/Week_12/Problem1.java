package Week12;

/**
 * Problem 1: Delivery Route Loop Detector
 *
 * TASK:
 * A delivery app stores the stops of a route as a singly linked list. A data-entry bug
 * sometimes links the last stop back to an earlier stop, which makes the driver go round forever.
 * Detect whether the route has a loop and, if so, report the stop where the loop starts
 * and how many stops are in the loop.
 * Implement String detectLoop(Node head) using only O(1) extra space: no HashSet,
 * no visited flags and no changes to the nodes.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Phase 1 (Cycle Detection): Fast moves 2 steps, Slow moves 1 step. Slow enters cycle of length L
 *      after k steps; fast catches slow within L steps. Time: O(k + L) = O(n).
 *    - Phase 2 (Loop Length): Fast stays at meeting point while pointer traverses the cycle once. Time: O(L) = O(n).
 *    - Phase 3 (Loop Start): One pointer resets to head, both advance 1 step at a time until meeting. Time: O(k) = O(n).
 *    - Total Time Complexity: O(n).
 *
 * 2. Space Complexity:
 *    - Auxiliary space is strictly O(1) using only two pointer references.
 *
 * Why Slow and Fast Pointers Must Meet Inside the Loop:
 * Inside a cycle of length L, the fast pointer closes the distance to the slow pointer by exactly
 * 1 node per iteration (relative speed = 2 - 1 = 1). Because the gap decreases by 1 on every step modulo L,
 * the distance must eventually reach 0, guaranteeing they meet in at most L steps.
 */
public class Problem1 {

    public static class Node {
        public int data;
        public Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public static String detectLoop(Node head) {
        if (head == null || head.next == null) {
            return "Loop: no";
        }

        Node slow = head;
        Node fast = head;
        boolean hasLoop = false;

        // Phase 1: Floyd's cycle-finding algorithm
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasLoop = true;
                break;
            }
        }

        if (!hasLoop) {
            return "Loop: no";
        }

        // Phase 2: Calculate loop length
        int length = 1;
        Node curr = slow.next;
        while (curr != slow) {
            length++;
            curr = curr.next;
        }

        // Phase 3: Find loop start node
        Node ptr1 = head;
        Node ptr2 = slow;
        while (ptr1 != ptr2) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        return "Loop: yes, starts at stop " + ptr1.data + ", length " + length;
    }

    public static Node buildListWithLoop(int[] values, int pos) {
        if (values == null || values.length == 0) {
            return null;
        }
        Node head = new Node(values[0]);
        Node curr = head;
        Node loopTarget = (pos == 0) ? head : null;

        for (int i = 1; i < values.length; i++) {
            curr.next = new Node(values[i]);
            curr = curr.next;
            if (i == pos) {
                loopTarget = curr;
            }
        }

        if (pos >= 0 && loopTarget != null) {
            curr.next = loopTarget;
        }

        return head;
    }

    public static void main(String[] args) {
        // Sample 1: stops = 10 -> 20 -> 30 -> 40 -> 50, pos = 2
        Node list1 = buildListWithLoop(new int[]{10, 20, 30, 40, 50}, 2);
        System.out.println("Sample 1 Input: stops = 10 -> 20 -> 30 -> 40 -> 50, pos = 2");
        System.out.println("Expected Output: Loop: yes, starts at stop 30, length 3");
        System.out.println("Actual Output:   " + detectLoop(list1));
        System.out.println();

        // Sample 2: stops = 10 -> 20 -> 30, pos = -1
        Node list2 = buildListWithLoop(new int[]{10, 20, 30}, -1);
        System.out.println("Sample 2 Input: stops = 10 -> 20 -> 30, pos = -1");
        System.out.println("Expected Output: Loop: no");
        System.out.println("Actual Output:   " + detectLoop(list2));
        System.out.println();

        // Sample 3: stops = 7, pos = 0
        Node list3 = buildListWithLoop(new int[]{7}, 0);
        System.out.println("Sample 3 Input: stops = 7, pos = 0");
        System.out.println("Expected Output: Loop: yes, starts at stop 7, length 1");
        System.out.println("Actual Output:   " + detectLoop(list3));
    }
}
