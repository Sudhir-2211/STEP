package Week11;

/**
 * Practice Problem 3: Add Two Numbers Represented by Linked Lists
 *
 * TASK:
 * Implement a function addNumbers(a, b) that takes the heads of two singly linked lists,
 * a and b, where each list represents a non-negative integer. Each node in the list stores
 * a single digit, and the digits are stored in reverse order (e.g., 2 -> 4 -> 3 represents 342).
 * The function should return the head of a new linked list representing the sum of the two numbers.
 * The input lists may have different lengths, and the sum might require an additional node for a final carry.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Both lists are traversed simultaneously from head to tail.
 *    - Loop executes max(len(a), len(b)) times plus at most 1 extra iteration for a carry.
 *    - Total Time Complexity: O(max(m, n)), where m and n are lengths of lists a and b.
 *
 * 2. Space Complexity:
 *    - The new resulting linked list contains at most max(m, n) + 1 nodes.
 *    - Total Auxiliary Space Complexity: O(max(m, n)).
 */
public class PracticeProblem3 {

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

    public static Node addNumbers(Node a, Node b) {
        Node dummy = new Node(0);
        Node tail = dummy;
        int carry = 0;

        Node currA = a;
        Node currB = b;

        while (currA != null || currB != null || carry != 0) {
            int sum = carry;

            if (currA != null) {
                sum += currA.data;
                currA = currA.next;
            }

            if (currB != null) {
                sum += currB.data;
                currB = currB.next;
            }

            carry = sum / 10;
            tail.next = new Node(sum % 10);
            tail = tail.next;
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
        // Sample 1: a = 2 -> 4 -> 3, b = 5 -> 6 -> 4 (342 + 465 = 807)
        Node a1 = buildList(new int[]{2, 4, 3});
        Node b1 = buildList(new int[]{5, 6, 4});
        System.out.println("Sample 1 Input:");
        System.out.println("a = " + printList(a1));
        System.out.println("b = " + printList(b1));
        Node sum1 = addNumbers(a1, b1);
        System.out.println("Expected Output: 7 -> 0 -> 8      (342 + 465 = 807)");
        System.out.println("Actual Output:   " + printList(sum1));
        System.out.println();

        // Sample 2: a = 9 -> 9, b = 1 (99 + 1 = 100)
        Node a2 = buildList(new int[]{9, 9});
        Node b2 = buildList(new int[]{1});
        System.out.println("Sample 2 Input:");
        System.out.println("a = " + printList(a2));
        System.out.println("b = " + printList(b2));
        Node sum2 = addNumbers(a2, b2);
        System.out.println("Expected Output: 0 -> 0 -> 1      (99 + 1 = 100)");
        System.out.println("Actual Output:   " + printList(sum2));
    }
}
