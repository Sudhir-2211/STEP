package Week10;

/**
 * Problem 1: Cancelling the k-th Last Order
 *
 * TASK:
 * A restaurant keeps today's orders in a singly linked list, newest at the end.
 * A customer cancels the order that is k-th from the end.
 * Implement Node removeKthFromEnd(Node head, int k) that removes that node
 * in a single pass and returns the head.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - The lead (fast) pointer advances k + 1 steps initially.
 *    - Both lead and trailing (slow) pointers then traverse the remainder of the list until
 *      the lead pointer reaches null (end of list).
 *    - Each node is visited at most twice (once by lead, once by trailing), maintaining O(n) time
 *      strictly in a single pass without computing the list length first.
 *
 * 2. Space Complexity:
 *    - Auxiliary space is O(1) since only two pointer references and a single dummy node are used.
 *
 * 3. Handling Head Removal Without Special Cases:
 *    - By initializing a dummy node preceding the actual head (dummy.next = head) and starting
 *      the trailing pointer at the dummy node, the node to be removed is always trailing.next.
 *    - When removing the head node (e.g., k equals list length), the trailing pointer remains at dummy,
 *      so dummy.next = dummy.next.next seamlessly drops the head without any conditional checks
 *      or special-casing for head removal.
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

    public static Node removeKthFromEnd(Node head, int k) {
        if (head == null || k <= 0) {
            return head;
        }

        // Dummy node simplifies edge cases like removing the head node
        Node dummy = new Node(0, head);
        Node lead = dummy;
        Node trailing = dummy;

        // Advance lead pointer by k + 1 steps
        for (int i = 0; i <= k; i++) {
            if (lead == null) {
                // If k is greater than the length of the list, no node is removed
                return head;
            }
            lead = lead.next;
        }

        // Move both pointers until lead falls off the end
        while (lead != null) {
            lead = lead.next;
            trailing = trailing.next;
        }

        // Remove the k-th node from the end
        if (trailing.next != null) {
            trailing.next = trailing.next.next;
        }

        return dummy.next;
    }

    // Helper method to build a linked list from an array
    public static Node buildList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        Node head = new Node(values[0]);
        Node curr = head;
        for (int i = 1; i < values.length; i++) {
            curr.next = new Node(values[i]);
            curr = curr.next;
        }
        return head;
    }

    // Helper method to print the linked list in the required format
    public static String printList(Node head) {
        if (head == null) {
            return "(empty)";
        }
        StringBuilder sb = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.next != null) {
                sb.append(" -> ");
            }
            curr = curr.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // Sample 1: orders = 10 -> 20 -> 30 -> 40 -> 50, k = 2
        Node orders1 = buildList(new int[]{10, 20, 30, 40, 50});
        int k1 = 2;
        System.out.println("Sample 1 Input: orders = " + printList(orders1) + ", k = " + k1);
        orders1 = removeKthFromEnd(orders1, k1);
        System.out.println("Expected Output: 10 -> 20 -> 30 -> 50");
        System.out.println("Actual Output:   " + printList(orders1));
        System.out.println();

        // Sample 2: orders = 10 -> 20 -> 30 -> 40 -> 50, k = 5 (removing head)
        Node orders2 = buildList(new int[]{10, 20, 30, 40, 50});
        int k2 = 5;
        System.out.println("Sample 2 Input: orders = " + printList(orders2) + ", k = " + k2);
        orders2 = removeKthFromEnd(orders2, k2);
        System.out.println("Expected Output: 20 -> 30 -> 40 -> 50");
        System.out.println("Actual Output:   " + printList(orders2));
    }
}
