package Week10;

/**
 * Problem 4: The Palindrome Train Code
 *
 * TASK:
 * Each train has a code stored as a singly linked list of digits.
 * Codes that read the same from both ends are reserved for special trains.
 * Implement boolean isPalindrome(Node head) in O(n) time and O(1) extra space,
 * restoring the list before returning.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Finding the middle node using slow and fast pointers: O(n/2) = O(n).
 *    - Reversing the second half of the list: O(n/2) = O(n).
 *    - Comparing the two halves element-by-element: O(n/2) = O(n).
 *    - Re-reversing the second half to restore original list structure: O(n/2) = O(n).
 *    - Total Time Complexity: O(n), running linearly with constant multiplicative factor.
 *
 * 2. Space Complexity:
 *    - All operations modify existing pointers in-place.
 *    - Total Auxiliary Space Complexity: O(1).
 *
 * 3. Comparison with Array Copy Approach:
 *    - Copying elements to an array/list uses O(n) auxiliary space to duplicate node data.
 *    - The in-place pointer reversal requires O(1) extra memory while preserving the integrity
 *      of the caller's linked list through restoration.
 */
public class Problem4 {

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

        // 1. Find the middle of the list (slow will be end of first half)
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half
        Node secondHalfStart = reverseList(slow.next);

        // 3. Compare first half and reversed second half
        Node p1 = head;
        Node p2 = secondHalfStart;
        boolean isPal = true;
        while (p2 != null) {
            if (p1.data != p2.data) {
                isPal = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 4. Restore the list by reversing the second half back
        slow.next = reverseList(secondHalfStart);

        return isPal;
    }

    private static Node reverseList(Node node) {
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
        // Sample 1: 1 -> 2 -> 3 -> 2 -> 1
        Node list1 = buildList(new int[]{1, 2, 3, 2, 1});
        System.out.println("Sample 1 Input: " + printList(list1));
        boolean res1 = isPalindrome(list1);
        System.out.println("Expected Output: true");
        System.out.println("Actual Output:   " + res1);
        System.out.println("List afterwards: " + printList(list1));
        System.out.println();

        // Sample 2: 1 -> 2 -> 3 -> 2
        Node list2 = buildList(new int[]{1, 2, 3, 2});
        System.out.println("Sample 2 Input: " + printList(list2));
        boolean res2 = isPalindrome(list2);
        System.out.println("Expected Output: false");
        System.out.println("Actual Output:   " + res2);
        System.out.println("List afterwards: " + printList(list2));
        System.out.println();

        // Sample 3: 1 -> 2 -> 2 -> 1
        Node list3 = buildList(new int[]{1, 2, 2, 1});
        System.out.println("Sample 3 Input: " + printList(list3));
        boolean res3 = isPalindrome(list3);
        System.out.println("Expected Output: true (list afterwards: 1 -> 2 -> 2 -> 1)");
        System.out.println("Actual Output:   " + res3);
        System.out.println("List afterwards: " + printList(list3));
    }
}
