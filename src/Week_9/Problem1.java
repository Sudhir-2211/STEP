package Week9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Problem 1: Mall Footfall Range Report
 *
 * Complexity Analysis:
 * 1. Naive Approach:
 *    - Each query scans the range from start to end: O(end - start + 1), which is O(n) in worst case.
 *    - Total Time Complexity for q queries: O(q * n).
 *    - Auxiliary Space: O(1).
 *
 * 2. Optimized Solution (Prefix Sums):
 *    - One-time preprocessing: Compute running totals in O(n) time and O(n) auxiliary space.
 *    - Each query is answered in O(1) time: sum(start, end) = prefix[end] - (start > 0 ? prefix[start - 1] : 0).
 *    - Total Time Complexity: O(n + q).
 *    - Auxiliary Space Complexity: O(n) for the prefix sum array.
 *
 * 3. When Preprocessing is Worth Paying:
 *    - Preprocessing is worth paying whenever multiple range queries (q > 1) are executed against
 *      the same static dataset. For thousands of queries (q = 10^5), naive takes ~10^10 operations,
 *      whereas prefix sum takes ~2 * 10^5 operations, yielding orders-of-magnitude speedup.
 */
public class Problem1 {

    public static List<Integer> footfallReport(int[] visitors, int[][] queries) {
        if (visitors == null || visitors.length == 0 || queries == null) {
            return new ArrayList<>();
        }

        int n = visitors.length;
        // Precompute prefix sums: prefix[i] stores sum of visitors from index 0 to i
        int[] prefix = new int[n];
        prefix[0] = visitors[0];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + visitors[i];
        }

        List<Integer> result = new ArrayList<>(queries.length);
        for (int[] query : queries) {
            int start = query[0];
            int end = query[1];

            // Range sum in O(1) time, handling boundary when start is 0
            int total = (start == 0) ? prefix[end] : (prefix[end] - prefix[start - 1]);
            result.add(total);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        int[][] queries = {
                {0, 2},
                {2, 5},
                {4, 6},
                {3, 3}
        };

        System.out.println("Visitors Array: " + Arrays.toString(visitors));
        System.out.println("Queries: " + Arrays.deepToString(queries));

        List<Integer> output = footfallReport(visitors, queries);
        System.out.println("Output: " + output); // Expected: [22, 31, 27, 9]
    }
}
