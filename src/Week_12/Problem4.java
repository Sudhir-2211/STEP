package Week12;

/**
 * Problem 4: Library Returns by Due Day
 *
 * TASK:
 * Returned books are chained in the order they were dropped at the desk, each with the day it was due.
 * Sort the chain by due day so overdue books are handled first. Books with the same due day must keep
 * their drop order (stable sort).
 * Implement Node sortByDueDay(Node head) using merge sort on the linked list. Change links only;
 * do not copy values into an array. Each node holds a title and a due day.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Splitting the list takes O(n) via slow and fast pointers.
 *    - Merging two sorted linked lists takes O(n) pointer updates.
 *    - Recurrence relation: T(n) = 2T(n/2) + O(n), resolving to O(n log n).
 *
 * 2. Space Complexity:
 *    - In-place relinking requires no additional heap nodes.
 *    - The call stack depth for recursive splitting is O(log n).
 *    - Total Auxiliary Space Complexity: O(log n).
 *
 * Why Merge Sort Suits Linked Lists Better than Quick Sort:
 * 1. Linked lists do not support O(1) random access by index. Quicksort relies on pivot partitioning
 *    with bidirectional indexing (like Hoare partition), which causes cache misses and traversal overhead.
 * 2. Merging two sorted linked lists requires zero auxiliary buffer space ($O(1)$ extra space) because
 *    pointers are rewired in-place, whereas merging arrays requires allocating an $O(n)$ temporary buffer.
 * 3. Merge sort on linked lists is naturally stable (taking from the left half on equal due days preserves
 *    drop order), while standard in-place quicksort is unstable.
 */
public class Problem4 {

    public static class Node {
        public String title;
        public int dueDay;
        public Node next;

        public Node(String title, int dueDay) {
            this.title = title;
            this.dueDay = dueDay;
            this.next = null;
        }

        public Node(String title, int dueDay, Node next) {
            this.title = title;
            this.dueDay = dueDay;
            this.next = next;
        }
    }

    public static Node sortByDueDay(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 1. Split the list in half using slow and fast pointers
        Node prev = null;
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // Cut link between first and second half
        prev.next = null;

        // 2. Recursively sort both halves
        Node leftSorted = sortByDueDay(head);
        Node rightSorted = sortByDueDay(slow);

        // 3. Merge the two sorted halves stably
        return merge(leftSorted, rightSorted);
    }

    private static Node merge(Node left, Node right) {
        Node dummy = new Node("", 0);
        Node tail = dummy;

        while (left != null && right != null) {
            // <= ensures stability: equal due days pick left (earlier drop order) first
            if (left.dueDay <= right.dueDay) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
        }

        if (left != null) {
            tail.next = left;
        } else {
            tail.next = right;
        }

        return dummy.next;
    }

    public static String printList(Node head) {
        if (head == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            sb.append(curr.title).append(":").append(curr.dueDay).append(" -> ");
            curr = curr.next;
        }
        sb.append("null");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Sample 1: Java:15 -> DSA:3 -> OS:9 -> DBMS:3 -> CN:12 -> AI:1
        Node returns1 = new Node("Java", 15,
                new Node("DSA", 3,
                        new Node("OS", 9,
                                new Node("DBMS", 3,
                                        new Node("CN", 12,
                                                new Node("AI", 1))))));

        System.out.println("Sample 1 Input: returns = " + printList(returns1));
        returns1 = sortByDueDay(returns1);
        System.out.println("Expected Output: AI:1 -> DSA:3 -> DBMS:3 -> OS:9 -> CN:12 -> Java:15 -> null");
        System.out.println("Actual Output:   " + printList(returns1));
        System.out.println();

        // Sample 2: returns = (empty)
        Node returns2 = null;
        System.out.println("Sample 2 Input: returns = " + printList(returns2));
        returns2 = sortByDueDay(returns2);
        System.out.println("Expected Output: null");
        System.out.println("Actual Output:   " + printList(returns2));
    }
}
