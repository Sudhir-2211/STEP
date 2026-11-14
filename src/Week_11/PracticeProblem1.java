package Week11;

/**
 * Practice Problem 1: Remove the N-th Node From the End of a Linked List
 *
 * TASK:
 * Implement a function removeNthFromEnd(head, n) that removes the node located 'n'
 * positions from the end of a singly linked list and returns the head of the modified list.
 * You are guaranteed that 1 <= n <= length of the list.
 * Your solution must achieve this in a single pass through the list.
 * Describe your chosen approach, including how you handle the edge case where the head
 * itself needs to be removed.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - The fast pointer advances n + 1 steps ahead of the slow pointer starting at dummy node.
 *    - Both pointers then advance in lockstep until the fast pointer falls off the end of the list.
 *    - Each node is visited at most once by fast and once by slow.
 *    - Total Time Complexity: O(L) in a strictly single pass, where L is the list length.
 *
 * 2. Space Complexity:
 *    - Uses only two pointer references (fast and slow) and a single dummy node.
 *    - Total Auxiliary Space Complexity: O(1).
 *
 * 3. Handling Head Node Removal:
 *    - A dummy node is placed before head (dummy.next = head), and slow starts at dummy.
 *    - Fast starts at dummy and moves n + 1 steps forward.
 *    - When the head itself must be removed (i.e. n == length), fast reaches null immediately,
 *      and slow remains at dummy. Then slow.next = slow.next.next seamlessly drops the head
 *      without requiring any special if-conditions.
 */
public class PracticeProblem1 {

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

    public static Node removeNthFromEnd(Node head, int n) {
        if (head == null || n <= 0) {
            return head;
        }

        Node dummy = new Node(0, head);
        Node fast = dummy;
        Node slow = dummy;

        // Advance fast by n + 1 steps
        for (int i = 0; i <= n; i++) {
            if (fast == null) {
                return head;
            }
            fast = fast.next;
        }

        // Advance both until fast reaches null
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // Remove the n-th node from end
        if (slow.next != null) {
            slow.next = slow.next.next;
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
            return "(empty list)";
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
        // Sample 1: head = 1 -> 2 -> 3 -> 4 -> 5, n = 2
        Node list1 = buildList(new int[]{1, 2, 3, 4, 5});
        int n1 = 2;
        System.out.println("Sample 1 Input: head = " + printList(list1) + ", n = " + n1);
        list1 = removeNthFromEnd(list1, n1);
        System.out.println("Expected Output: 1 -> 2 -> 3 -> 5");
        System.out.println("Actual Output:   " + printList(list1));
        System.out.println();

        // Sample 2: head = 1, n = 1
        Node list2 = buildList(new int[]{1});
        int n2 = 1;
        System.out.println("Sample 2 Input: head = " + printList(list2) + ", n = " + n2);
        list2 = removeNthFromEnd(list2, n2);
        System.out.println("Expected Output: (empty list)");
        System.out.println("Actual Output:   " + printList(list2));
    }
}
