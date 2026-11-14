package Week11;

/**
 * Practice Problem 2: Check if a Linked List is a Palindrome
 *
 * TASK:
 * Implement a function isPalindrome(head) that determines if a singly linked list
 * represents a palindrome (reads the same forwards and backwards).
 * The function should return true if it's a palindrome and false otherwise.
 * Your solution must achieve this using O(1) additional space.
 * You have the option to restore the list to its original order after the check.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Finding the middle using slow/fast pointers: O(n/2) = O(n).
 *    - Reversing the second half: O(n/2) = O(n).
 *    - Comparing first half and second half: O(n/2) = O(n).
 *    - Reversing back to restore list: O(n/2) = O(n).
 *    - Total Time Complexity: O(n).
 *
 * 2. Space Complexity:
 *    - Only a few pointer variables are maintained in-place.
 *    - Total Auxiliary Space Complexity: O(1).
 */
public class PracticeProblem2 {

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

        // 1. Find the middle of the linked list
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half of the list
        Node secondHalfHead = reverse(slow.next);

        // 3. Compare the two halves
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

        // 4. Restore original list
        slow.next = reverse(secondHalfHead);

        return isPal;
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
        // Sample 1: head = 1 -> 2 -> 2 -> 1
        Node list1 = buildList(new int[]{1, 2, 2, 1});
        System.out.println("Sample 1 Input: " + printList(list1));
        boolean res1 = isPalindrome(list1);
        System.out.println("Expected Output: true");
        System.out.println("Actual Output:   " + res1);
        System.out.println();

        // Sample 2: head = 1 -> 2 -> 3
        Node list2 = buildList(new int[]{1, 2, 3});
        System.out.println("Sample 2 Input: " + printList(list2));
        boolean res2 = isPalindrome(list2);
        System.out.println("Expected Output: false");
        System.out.println("Actual Output:   " + res2);
    }
}
