package Week9;

import java.util.ArrayList;
import java.util.List;

/**
 * Week 9 - Practice Problem 4: Library Catalog Lookup (Iterative & Recursive Variants)
 *
 * Task:
 * A library maintains its catalog as a list of book records, where each record contains
 * a unique ISBN (a numeric string) and a title. The catalog is pre-sorted by ISBN in ascending order.
 * Implement findBook(catalog, targetIsbn) that efficiently retrieves the title of a book given its ISBN.
 * If the ISBN is not found, return "Not Found".
 *
 * Complexity Analysis:
 * 1. Iterative Binary Search:
 *    - Time Complexity: O(log n), where n is the number of catalog entries. At each step, the search
 *      interval [low, high] is divided in half.
 *    - Auxiliary Space Complexity: O(1). Only two pointer indices (low, high) and a midpoint are stored.
 *
 * 2. Recursive Binary Search:
 *    - Time Complexity: O(log n).
 *    - Auxiliary Space Complexity: O(log n) call stack space for recursion frames.
 *
 * Why Pre-sorting / Binary Search is Ideal for Frequent Queries:
 * - When queries vastly outnumber updates (e.g., Q >> N), the one-time preprocessing cost of sorting O(N log N)
 *   is amortized across all Q queries.
 * - Total time for Q queries with binary search: O(N log N + Q log N).
 * - Total time for Q queries with linear scan on unsorted data: O(Q * N).
 * - For N = 10^5 and Q = 10^6, binary search executes ~1.7 * 10^7 operations versus 10^11 operations
 *   for linear search, representing a massive ~6000x performance enhancement.
 */
public class PracticeProblem4 {

    public static class BookRecord {
        public final String isbn;
        public final String title;

        public BookRecord(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }

        @Override
        public String toString() {
            return "(" + isbn + ", \"" + title + "\")";
        }
    }

    /**
     * Iterative Binary Search implementation.
     */
    public static String findBookIterative(List<BookRecord> catalog, String targetIsbn) {
        if (catalog == null || catalog.isEmpty() || targetIsbn == null) {
            return "Not Found";
        }

        int low = 0;
        int high = catalog.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            BookRecord midRecord = catalog.get(mid);
            int cmp = midRecord.isbn.compareTo(targetIsbn);

            if (cmp == 0) {
                return midRecord.title;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return "Not Found";
    }

    /**
     * Recursive Binary Search implementation.
     */
    public static String findBookRecursive(List<BookRecord> catalog, String targetIsbn) {
        if (catalog == null || catalog.isEmpty() || targetIsbn == null) {
            return "Not Found";
        }
        return binarySearchRecursiveHelper(catalog, targetIsbn, 0, catalog.size() - 1);
    }

    private static String binarySearchRecursiveHelper(List<BookRecord> catalog, String targetIsbn, int low, int high) {
        if (low > high) {
            return "Not Found";
        }

        int mid = low + (high - low) / 2;
        BookRecord midRecord = catalog.get(mid);
        int cmp = midRecord.isbn.compareTo(targetIsbn);

        if (cmp == 0) {
            return midRecord.title;
        } else if (cmp < 0) {
            return binarySearchRecursiveHelper(catalog, targetIsbn, mid + 1, high);
        } else {
            return binarySearchRecursiveHelper(catalog, targetIsbn, low, mid - 1);
        }
    }

    /**
     * Default function required by problem statement.
     */
    public static String findBook(List<BookRecord> catalog, String targetIsbn) {
        return findBookIterative(catalog, targetIsbn);
    }

    public static void main(String[] args) {
        System.out.println("=== Week 9 - Practice Problem 4: Library Catalog Lookup ===");

        List<BookRecord> catalog = new ArrayList<>();
        catalog.add(new BookRecord("0001112223", "Introduction to Algebra"));
        catalog.add(new BookRecord("0002223334", "Beginning Python"));
        catalog.add(new BookRecord("0003334445", "Classic Mythology"));
        catalog.add(new BookRecord("0004445556", "Data and Society"));
        catalog.add(new BookRecord("0005556667", "European History"));

        // Example 1
        String target1 = "0003334445";
        System.out.println("Search ISBN: " + target1);
        System.out.println("Iterative Output: " + findBookIterative(catalog, target1));
        System.out.println("Recursive Output: " + findBookRecursive(catalog, target1));
        System.out.println("Expected: Classic Mythology\n");

        // Example 2
        String target2 = "0009998887";
        System.out.println("Search ISBN: " + target2);
        System.out.println("Iterative Output: " + findBookIterative(catalog, target2));
        System.out.println("Recursive Output: " + findBookRecursive(catalog, target2));
        System.out.println("Expected: Not Found\n");
    }
}
