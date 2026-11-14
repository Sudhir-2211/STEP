package Week12;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem 3: Recent Pages Cache (LRU Cache)
 *
 * TASK:
 * The learning portal keeps the most recently opened pages in a small cache.
 * When the cache is full and a new page is added, the least recently used page is removed.
 * Reading or updating a page makes it the most recently used.
 * Implement a class RecentCache(int capacity) with String get(int key) and
 * void put(int key, String page), both in O(1) time.
 * Use your own doubly linked list (with prev and next) together with a HashMap from key to node;
 * do not use java.util.LinkedHashMap.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - get(int key): O(1). Hash map lookup takes O(1) average time, and moving a doubly linked
 *      node to the head requires O(1) pointer updates.
 *    - put(int key, String page): O(1). Inserting/updating in the hash map takes O(1) average time,
 *      and adding to the head or removing from the tail takes O(1) pointer operations.
 *
 * 2. Space Complexity:
 *    - The cache stores at most 'capacity' nodes in both the hash map and the doubly linked list.
 *    - Total Auxiliary Space Complexity: O(capacity).
 *
 * Why a Singly Linked List Cannot Remove an Arbitrary Node in O(1):
 * In a singly linked list, each node only holds a reference to its successor (next).
 * To remove an arbitrary node, its predecessor's 'next' pointer must be redirected.
 * Because a singly linked node has no backward link (prev), finding the predecessor requires
 * scanning linearly from the head of the list, which incurs an O(n) traversal cost.
 * A doubly linked list resolves this in O(1) by providing direct access to node.prev.
 */
public class Problem3 {

    public static class Node {
        public int key;
        public String page;
        public Node prev;
        public Node next;

        public Node(int key, String page) {
            this.key = key;
            this.page = page;
        }
    }

    public static class RecentCache {
        private final int capacity;
        private final Map<Integer, Node> map;
        private final Node head;
        private final Node tail;

        public RecentCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();

            // Sentinel dummy nodes
            this.head = new Node(-1, "");
            this.tail = new Node(-1, "");
            head.next = tail;
            tail.prev = head;
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addToFront(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        private void moveToFront(Node node) {
            removeNode(node);
            addToFront(node);
        }

        public String get(int key) {
            if (!map.containsKey(key)) {
                return "-1";
            }
            Node node = map.get(key);
            moveToFront(node);
            return node.page;
        }

        public String put(int key, String page) {
            if (map.containsKey(key)) {
                Node existing = map.get(key);
                existing.page = page;
                moveToFront(existing);
                return "updated";
            } else {
                String evictedMsg = "";
                if (map.size() >= capacity) {
                    // Evict LRU node (just before tail sentinel)
                    Node lru = tail.prev;
                    removeNode(lru);
                    map.remove(lru.key);
                    evictedMsg = " evicted " + lru.key;
                }
                Node newNode = new Node(key, page);
                addToFront(newNode);
                map.put(key, newNode);
                return evictedMsg;
            }
        }

        public String printCacheOrder() {
            StringBuilder sb = new StringBuilder();
            Node curr = head.next;
            boolean first = true;
            while (curr != tail) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(curr.key).append("=").append(curr.page);
                first = false;
                curr = curr.next;
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("Expected Output:");
        System.out.println("put(1,Home)");
        System.out.println("put(2,Notes)");
        System.out.println("get(1) -> Home");
        System.out.println("put(3,Quiz) evicted 2");
        System.out.println("get(2) -> -1");
        System.out.println("put(1,Home2) updated");
        System.out.println("put(4,Mail) evicted 3");
        System.out.println("get(3) -> -1");
        System.out.println("get(1) -> Home2");
        System.out.println("Cache (most recent first): 1=Home2, 4=Mail");
        System.out.println();

        System.out.println("Actual Output:");
        RecentCache cache = new RecentCache(2);

        String ev;
        ev = cache.put(1, "Home");
        System.out.println("put(1,Home)" + ev);

        ev = cache.put(2, "Notes");
        System.out.println("put(2,Notes)" + ev);

        System.out.println("get(1) -> " + cache.get(1));

        ev = cache.put(3, "Quiz");
        System.out.println("put(3,Quiz)" + ev);

        System.out.println("get(2) -> " + cache.get(2));

        ev = cache.put(1, "Home2");
        System.out.println("put(1,Home2) " + ev);

        ev = cache.put(4, "Mail");
        System.out.println("put(4,Mail)" + ev);

        System.out.println("get(3) -> " + cache.get(3));
        System.out.println("get(1) -> " + cache.get(1));

        System.out.println("Cache (most recent first): " + cache.printCacheOrder());
    }
}
