package Week12;

/**
 * Problem 5: Token Mirror Check
 *
 * TASK:
 * A kiosk prints a chain of token numbers and wants to know whether the chain reads the same
 * from both ends. Memory on the kiosk is very limited.
 * Implement boolean isPalindrome(Node head) using O(1) extra space.
 * You may temporarily change links, but the list must be exactly as it was when the method returns.
 * Write your own node class with data and next fields; do not use java.util.LinkedList.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Finding the middle node with fast and slow pointers: O(n/2) = O(n).
 *    - Reversing the second half: O(n/2) = O(n).
 *    - Comparing the two halves node by node: O(n/2) = O(n).
 *    - Reversing the second half back to restore the original list: O(n/2) = O(n).
 *    - Total Time Complexity: O(n).
 *
 * 2. Space Complexity:
 *    - Operates purely in-place by temporarily redirecting next pointers.
 *    - Total Auxiliary Space Complexity: O(1).
 *
 * Why Copying into an Array or Stack Fails the Requirement:
 * Copying elements to an array or pushing them onto a stack requires allocating O(n) auxiliary
 * memory proportional to the list size. On memory-constrained devices like kiosks or embedded
 * microcontrollers, O(n) memory allocation may trigger out-of-memory errors or garbage collection
 * pauses, failing the strict O(1) extra space requirement.
 */
public class Problem5 {

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

    public static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        // 1. Find end of first half using slow and fast pointers
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse second half
        Node secondHalfHead = reverse(slow.next);

        // 3. Compare two halves
        Node p1 = head;
        Node p2 = secondHalfHead;
        boolean isPal = true;
        while (p2 != null) {
            if (p1.data != p2.data) {
                isPal = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 4. Restore original list by reversing second half again
        slow.next = reverse(secondHalfHead);

        return isPal;
    }

    private static Node reverse(Node head) {
        Node prev = null;
        Node curr = head;
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
        // Sample 1: tokens = 4 -> 7 -> 9 -> 7 -> 4
        Node tokens1 = buildList(new int[]{4, 7, 9, 7, 4});
        System.out.println("Sample 1 Input: tokens = " + printList(tokens1));
        boolean res1 = isPalindrome(tokens1);
        System.out.println("Expected Output:");
        System.out.println("Palindrome: true");
        System.out.println("List after check: 4 -> 7 -> 9 -> 7 -> 4 -> null");
        System.out.println("Actual Output:");
        System.out.println("Palindrome: " + res1);
        System.out.println("List after check: " + printList(tokens1));
        System.out.println();

        // Sample 2: tokens = 4 -> 7 -> 7 -> 9
        Node tokens2 = buildList(new int[]{4, 7, 7, 9});
        System.out.println("Sample 2 Input: tokens = " + printList(tokens2));
        boolean res2 = isPalindrome(tokens2);
        System.out.println("Expected Output:");
        System.out.println("Palindrome: false");
        System.out.println("List after check: 4 -> 7 -> 7 -> 9 -> null");
        System.out.println("Actual Output:");
        System.out.println("Palindrome: " + res2);
        System.out.println("List after check: " + printList(tokens2));
    }
}
