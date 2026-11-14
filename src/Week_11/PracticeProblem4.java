package Week11;

/**
 * Practice Problem 4: Find Intersection Node of Two Linked Lists
 *
 * TASK:
 * Implement a function findIntersection(a, b) that takes the heads of two singly linked
 * lists, a and b, which are known to eventually merge and share the same sequence of nodes.
 * The function should return the first shared node (where the two lists physically merge,
 * comparing node identity/reference, not just data value). If the lists do not intersect,
 * return null. Your solution must use O(1) additional space.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Calculating lengths takes O(m + n), where m and n are lengths of lists a and b.
 *    - Aligning the pointer of the longer list takes O(|m - n|) steps.
 *    - Traversing both lists until finding the intersection takes at most O(min(m, n)).
 *    - Total Time Complexity: O(m + n).
 *
 * 2. Space Complexity:
 *    - Only a few node reference pointers and length counters are maintained.
 *    - Total Auxiliary Space Complexity: O(1).
 */
public class PracticeProblem4 {

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

    public static Node findIntersection(Node a, Node b) {
        if (a == null || b == null) {
            return null;
        }

        // 1. Calculate lengths of both lists
        int lenA = getLength(a);
        int lenB = getLength(b);

        Node currA = a;
        Node currB = b;

        // 2. Advance pointer of longer list by the difference
        if (lenA > lenB) {
            int diff = lenA - lenB;
            while (diff > 0) {
                currA = currA.next;
                diff--;
            }
        } else if (lenB > lenA) {
            int diff = lenB - lenA;
            while (diff > 0) {
                currB = currB.next;
                diff--;
            }
        }

        // 3. Traverse in lockstep comparing reference identity
        while (currA != null && currB != null) {
            if (currA == currB) {
                return currA; // Found shared intersection node
            }
            currA = currA.next;
            currB = currB.next;
        }

        return null; // No intersection found
    }

    private static int getLength(Node head) {
        int length = 0;
        Node curr = head;
        while (curr != null) {
            length++;
            curr = curr.next;
        }
        return length;
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
        // Sample 1:
        // Shared segment: 8 -> 4 -> 5
        Node shared = new Node(8, new Node(4, new Node(5)));
        // a = 4 -> 1 -> (shared)
        Node a1 = new Node(4, new Node(1, shared));
        // b = 5 -> 6 -> 1 -> (shared)
        Node b1 = new Node(5, new Node(6, new Node(1, shared)));

        System.out.println("Sample 1 Input:");
        System.out.println("a = " + printList(a1));
        System.out.println("b = " + printList(b1));

        Node intersection1 = findIntersection(a1, b1);
        System.out.println("Expected Output: Node with value 8");
        System.out.println("Actual Output:   " + (intersection1 != null ? "Node with value " + intersection1.data : "null"));
        System.out.println();

        // Sample 2:
        // a = 2 -> 6 -> 4
        // b = 1 -> 5
        Node a2 = new Node(2, new Node(6, new Node(4)));
        Node b2 = new Node(1, new Node(5));

        System.out.println("Sample 2 Input:");
        System.out.println("a = " + printList(a2));
        System.out.println("b = " + printList(b2));

        Node intersection2 = findIntersection(a2, b2);
        System.out.println("Expected Output: null");
        System.out.println("Actual Output:   " + (intersection2 != null ? "Node with value " + intersection2.data : "null"));
    }
}
