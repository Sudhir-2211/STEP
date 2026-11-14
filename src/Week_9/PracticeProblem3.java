package Week9;

import java.util.ArrayList;
import java.util.List;

/**
 * Week 9 - Practice Problem 3: Library Catalog Lookup
 *
 * Task:
 * A library catalog is provided as a list of book records, where each record contains
 * an ISBN (a unique numeric string) and a title. The catalog is sorted by ISBN in ascending order.
 * Implement findBook(catalog, targetIsbn) that efficiently checks if a given ISBN exists
 * in the catalog and, if so, retrieves its title. If not found, return "Not Found".
 *
 * Complexity Requirements:
 * - Time Complexity: O(log n) where n is the number of books in the catalog.
 *   Because the catalog is pre-sorted, binary search halves the search space at each iteration.
 *   Comparing numeric strings of length L takes O(L) time, so overall time is O(L * log n).
 * - Auxiliary Space Complexity: O(1) for iterative binary search.
 *
 * Justification for Queries >> Updates:
 * - In library management systems, patrons and staff query the catalog constantly (thousands of searches
 *   per hour), whereas new books are added infrequently (a few per week or month).
 * - Paying a one-time sorting cost of O(n log n) or maintaining sorted order upon insertion O(n)
 *   is heavily amortized over thousands of O(log n) lookups.
 * - If unsorted, every lookup would require linear scan O(n), which becomes prohibitively slow
 *   as catalog size scales into millions of records.
 */
public class PracticeProblem3 {

    public static class BookRecord {
        public final String isbn;
        public final String title;

        public BookRecord(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }

        @Override
        public String toString() {
            return "(\"" + isbn + "\", \"" + title + "\")";
        }
    }

    /**
     * Efficiently looks up a book title by ISBN using Binary Search.
     *
     * @param catalog sorted list of BookRecord objects ordered by ISBN
     * @param targetIsbn ISBN string to find
     * @return Title of the book if found, or "Not Found"
     */
    public static String findBook(List<BookRecord> catalog, String targetIsbn) {
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
                low = mid + 1; // target is in right half
            } else {
                high = mid - 1; // target is in left half
            }
        }

        return "Not Found";
    }

    public static void main(String[] args) {
        System.out.println("=== Week 9 - Practice Problem 3: Library Catalog Lookup ===");

        List<BookRecord> catalog = new ArrayList<>();
        catalog.add(new BookRecord("0001112223", "Introduction to Algebra"));
        catalog.add(new BookRecord("0002223334", "Beginning Python"));
        catalog.add(new BookRecord("0003334445", "Classic Mythology"));
        catalog.add(new BookRecord("0004445556", "Data and Society"));
        catalog.add(new BookRecord("0005556667", "European History"));

        System.out.println("Catalog Size: " + catalog.size() + " books.");

        // Sample 1
        String target1 = "0003334445";
        String res1 = findBook(catalog, target1);
        System.out.println("Search ISBN: " + target1);
        System.out.println("Output: " + res1);
        System.out.println("Expected: Classic Mythology\n");

        // Sample 2
        String target2 = "0009998887";
        String res2 = findBook(catalog, target2);
        System.out.println("Search ISBN: " + target2);
        System.out.println("Output: " + res2);
        System.out.println("Expected: Not Found\n");

        // Boundary Tests
        String targetFirst = "0001112223";
        System.out.println("Search First ISBN: " + targetFirst);
        System.out.println("Output: " + findBook(catalog, targetFirst));
        System.out.println("Expected: Introduction to Algebra\n");

        String targetLast = "0005556667";
        System.out.println("Search Last ISBN: " + targetLast);
        System.out.println("Output: " + findBook(catalog, targetLast));
        System.out.println("Expected: European History\n");
    }
}
