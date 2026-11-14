package Week11;

/**
 * Practice Problem 5: Reorder Linked List Alternately from Ends
 *
 * TASK:
 * Implement a function reorderList(head) that rearranges the nodes of a singly linked
 * list in-place to form the pattern: L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 -> ...
 * The rearrangement must be achieved by modifying the next pointers of the existing nodes
 * without creating any new nodes or copying values into other data structures.
 * You must clearly state which standard linked list techniques you combine to solve this problem.
 *
 * Techniques Combined:
 * 1. Two Pointers (Slow & Fast): Finding the midpoint to split the list evenly into two halves.
 * 2. In-Place List Reversal: Reversing the second half iteratively using three pointers (prev, curr, next).
 * 3. Alternating Splice / Merge: Interleaving nodes alternately from the first half and the reversed second half.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Finding the middle: O(n/2) = O(n).
 *    - Reversing the second half: O(n/2) = O(n).
 *    - Interleaving the two halves: O(n).
 *    - Total Time Complexity: O(n).
 *
 * 2. Space Complexity:
 *    - All operations modify existing node references strictly in-place.
 *    - Total Auxiliary Space Complexity: O(1).
 */
public class PracticeProblem5 {

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

    public static Node reorderList(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        // Step 1: Find middle using slow and fast pointers
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Split list into two halves and reverse the second half
        Node secondHalfHead = reverse(slow.next);
        slow.next = null; // Sever the first half

        // Step 3: Merge first half and reversed second half alternately
        Node first = head;
        Node second = secondHalfHead;

        while (second != null) {
            Node nextFirst = first.next;
            Node nextSecond = second.next;

            first.next = second;
            if (nextFirst == null) {
                break;
            }
            second.next = nextFirst;

            first = nextFirst;
            second = nextSecond;
        }

        return head;
    }

    private static Node reverse(Node node) {
        Node prev = null;
        Node curr = node;
        while (curr != null) {
            Node nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
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
        // Sample 1: head = 1 -> 2 -> 3 -> 4
        Node list1 = buildList(new int[]{1, 2, 3, 4});
        System.out.println("Sample 1 Input: head = " + printList(list1));
        list1 = reorderList(list1);
        System.out.println("Expected Output: 1 -> 4 -> 2 -> 3");
        System.out.println("Actual Output:   " + printList(list1));
        System.out.println();

        // Sample 2: head = 1 -> 2 -> 3 -> 4 -> 5
        Node list2 = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Sample 2 Input: head = " + printList(list2));
        list2 = reorderList(list2);
        System.out.println("Expected Output: 1 -> 5 -> 2 -> 4 -> 3");
        System.out.println("Actual Output:   " + printList(list2));
    }
}
