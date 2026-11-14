package Week12;

/**
 * Problem 2: Exam Script Batches
 *
 * TASK:
 * Answer scripts arrive in a chain and must be evaluated in batches of k,
 * but each batch is stacked in reverse. Reverse the list in groups of k nodes.
 * A final group with fewer than k nodes stays in its original order.
 * Implement Node reverseInGroups(Node head, int k) by changing links only
 * (no new nodes, no arrays) and return the new head.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - In each group, the algorithm checks if k nodes exist by advancing a pointer k steps: O(k).
 *    - Reversing the k links requires one traversal of the group: O(k).
 *    - Every node is visited at most twice.
 *    - Total Time Complexity: O(n), where n is the number of nodes in the list.
 *
 * 2. Space Complexity:
 *    - While a recursive approach uses O(n/k) stack space for recursion frames,
 *      this iterative solution maintains only a fixed set of local pointers (dummy, prevGroupTail,
 *      curr, nextGroupStart) operating purely in-place.
 *    - Total Auxiliary Space Complexity: O(1).
 */
public class Problem2 {

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

    public static Node reverseInGroups(Node head, int k) {
        if (head == null || k <= 1) {
            return head;
        }

        Node dummy = new Node(0, head);
        Node prevGroupTail = dummy;

        while (true) {
            // Check if there are at least k nodes remaining
            Node kth = prevGroupTail;
            for (int i = 0; i < k; i++) {
                kth = kth.next;
                if (kth == null) {
                    // Fewer than k nodes remain: leave them in their original order
                    return dummy.next;
                }
            }

            Node nextGroupStart = kth.next;
            Node groupHead = prevGroupTail.next;

            // In-place reverse of the k nodes
            Node prev = nextGroupStart;
            Node curr = groupHead;
            while (curr != nextGroupStart) {
                Node nextTemp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextTemp;
            }

            // Connect previous group's tail to new head of this reversed group (kth)
            prevGroupTail.next = kth;
            // The old groupHead is now the tail of this reversed group
            prevGroupTail = groupHead;
        }
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
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            sb.append(curr.data).append(" -> ");
            curr = curr.next;
        }
        sb.append("null");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Sample 1: scripts = 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8, k = 3
        Node scripts1 = buildList(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        int k1 = 3;
        System.out.println("Sample 1 Input: scripts = " + printList(scripts1) + ", k = " + k1);
        scripts1 = reverseInGroups(scripts1, k1);
        System.out.println("Expected Output: 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 7 -> 8 -> null");
        System.out.println("Actual Output:   " + printList(scripts1));
        System.out.println();

        // Sample 2: scripts = 11 -> 12, k = 3
        Node scripts2 = buildList(new int[]{11, 12});
        int k2 = 3;
        System.out.println("Sample 2 Input: scripts = " + printList(scripts2) + ", k = " + k2);
        scripts2 = reverseInGroups(scripts2, k2);
        System.out.println("Expected Output: 11 -> 12 -> null");
        System.out.println("Actual Output:   " + printList(scripts2));
    }
}
