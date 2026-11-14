package Week10;

/**
 * Problem 3: Merging Two Ticket Counters
 *
 * TASK:
 * Two railway counters each produce a singly linked list of ticket numbers in ascending order.
 * At closing time, both lists must be combined into one ascending list.
 * Implement Node mergeSorted(Node a, Node b) that merges the lists by relinking
 * the existing nodes and returns the new head. Duplicates are kept.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - In each iteration of the while loop, one node from either list 'a' or 'b' is attached to 'tail'.
 *    - When one list is exhausted, the remainder of the other list is attached in O(1) time.
 *    - Total Time Complexity: O(m + n), where m and n are the lengths of lists 'a' and 'b'.
 *
 * 2. Space Complexity:
 *    - Relinking operates strictly in-place without allocating any new list nodes.
 *    - A single dummy node is utilized to track the merged list's head.
 *    - Total Auxiliary Space Complexity: O(1).
 *
 * 3. Comparison with Array Copy and Sort:
 *    - Copying both lists into an array and sorting requires:
 *      * Time: O((m + n) log(m + n)) due to comparison-based sorting.
 *      * Space: O(m + n) auxiliary memory to store elements in an array.
 *    - The in-place linked list merge avoids the logarithmic sorting overhead and eliminates
 *      extra memory allocation entirely, running linearly in O(m + n) time and O(1) space.
 */
public class Problem3 {

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

    public static Node mergeSorted(Node a, Node b) {
        // Dummy node acts as an anchor for the head of the merged list
        Node dummy = new Node(0);
        Node tail = dummy;

        while (a != null && b != null) {
            if (a.data <= b.data) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }

        // Attach whatever remains in either list
        if (a != null) {
            tail.next = a;
        } else {
            tail.next = b;
        }

        return dummy.next;
    }

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
        // Sample 1: a = 3 -> 8 -> 15, b = 5 -> 8 -> 12 -> 20
        Node a1 = buildList(new int[]{3, 8, 15});
        Node b1 = buildList(new int[]{5, 8, 12, 20});
        System.out.println("Sample 1 Input:");
        System.out.println("a = " + printList(a1));
        System.out.println("b = " + printList(b1));

        Node merged1 = mergeSorted(a1, b1);
        System.out.println("Expected Output: 3 -> 5 -> 8 -> 8 -> 12 -> 15 -> 20");
        System.out.println("Actual Output:   " + printList(merged1));
        System.out.println();

        // Sample 2: a = (empty), b = 4
        Node a2 = null;
        Node b2 = buildList(new int[]{4});
        System.out.println("Sample 2 Input:");
        System.out.println("a = " + printList(a2));
        System.out.println("b = " + printList(b2));

        Node merged2 = mergeSorted(a2, b2);
        System.out.println("Expected Output: 4");
        System.out.println("Actual Output:   " + printList(merged2));
    }
}
