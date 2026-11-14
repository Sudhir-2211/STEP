package Week10;

/**
 * Problem 2: Campus Portal Browser History
 *
 * TASK:
 * A campus portal browser opens on a home page and supports three actions:
 * 1. visit(url): visits a new page, which clears all forward history.
 * 2. back(steps): goes back up to x pages, stopping at the first page available.
 * 3. forward(steps): goes forward up to x pages, stopping at the last page available.
 * Back and forward return the current page URL.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - visit(url): O(1). Creating and appending a new node to current and severing the forward link
 *      involves constant-time pointer assignments.
 *    - back(steps): O(min(steps, k)), where k is the number of backward pages available (at most O(steps)).
 *    - forward(steps): O(min(steps, k)), where k is the number of forward pages available (at most O(steps)).
 *
 * 2. Space Complexity:
 *    - Total auxiliary space is O(N), where N is the total number of unique active pages visited.
 *
 * 3. Discarding Forward History & Node Reclamation:
 *    - When visit(url) is invoked from an intermediate page, current.next is updated to point to the new node,
 *      and the new node's prev pointer points to current.
 *    - The previous forward chain starting at the old current.next is disconnected from the reachable path.
 *    - In Java's garbage-collected runtime, once no live references point to the orphaned forward nodes,
 *      they become unreachable and are automatically reclaimed by the Garbage Collector (GC).
 */
public class Problem2 {

    public static class Node {
        public String url;
        public Node prev;
        public Node next;

        public Node(String url) {
            this.url = url;
            this.prev = null;
            this.next = null;
        }
    }

    public static class BrowserHistory {
        private Node current;

        public BrowserHistory(String homepage) {
            this.current = new Node(homepage);
        }

        // Visits a new page and clears all forward history
        public void visit(String url) {
            Node newNode = new Node(url);
            current.next = newNode;
            newNode.prev = current;
            current = newNode;
        }

        // Moves back up to steps pages and returns current url
        public String back(int steps) {
            while (steps > 0 && current.prev != null) {
                current = current.prev;
                steps--;
            }
            return current.url;
        }

        // Moves forward up to steps pages and returns current url
        public String forward(int steps) {
            while (steps > 0 && current.next != null) {
                current = current.next;
                steps--;
            }
            return current.url;
        }

        public String getCurrentUrl() {
            return current.url;
        }
    }

    public static void main(String[] args) {
        System.out.println("Initializing BrowserHistory with homepage: srm.edu");
        BrowserHistory browser = new BrowserHistory("srm.edu");

        // Action sequence from example:
        // visit a.com, visit b.com, visit c.com
        browser.visit("a.com");
        browser.visit("b.com");
        browser.visit("c.com");

        // back 1, back 1, forward 1, visit d.com, forward 2, back 2, back 7
        String r1 = browser.back(1);      // b.com
        String r2 = browser.back(1);      // a.com
        String r3 = browser.forward(1);   // b.com
        browser.visit("d.com");          // clears c.com
        String r4 = browser.forward(2);   // d.com (cannot go further)
        String r5 = browser.back(2);      // a.com
        String r6 = browser.back(7);      // srm.edu (stops at home)

        String output = String.join(", ", r1, r2, r3, r4, r5, r6);
        System.out.println("Expected Output: b.com, a.com, b.com, d.com, a.com, srm.edu");
        System.out.println("Actual Output:   " + output);
    }
}
