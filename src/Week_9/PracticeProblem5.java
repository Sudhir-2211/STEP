package Week9;

import java.util.Arrays;

/**
 * Week 9 - Practice Problem 5: Maximum Sum Subarray of Fixed Size K
 *
 * Task:
 * A retail analytics tool needs to identify the highest total sales recorded across
 * any k consecutive days within a given sales record. The sales record is provided
 * as an array of daily sales figures, and an integer k (where 1 <= k <= sales.length).
 * Implement maxSumSubarray(sales, k) that returns the maximum sum found among all
 * contiguous subarrays of size k.
 *
 * Complexity Analysis:
 * 1. Naive Approach:
 *    - For each possible starting index i from 0 to n - k:
 *        Iterate through the subarray of length k from i to i + k - 1 and sum elements.
 *    - There are (n - k + 1) such windows, each requiring k additions.
 *    - Time Complexity: O((n - k + 1) * k) = O(n * k). For k ~ n / 2, this is O(n^2).
 *    - Auxiliary Space: O(1).
 *
 * 2. Optimized Solution (Sliding Window):
 *    - Compute the sum of the first window of size k (indices 0 to k - 1) in O(k) time.
 *    - Slide the window one element to the right at each subsequent step:
 *        newWindowSum = currentWindowSum + sales[i] - sales[i - k]
 *    - Update the maximum sum observed so far:
 *        maxSum = Math.max(maxSum, newWindowSum)
 *    - Each element enters the window once and leaves the window once.
 *    - Time Complexity: O(n) total operations, completely eliminating redundant recomputations.
 *    - Auxiliary Space Complexity: O(1) as only a few primitive accumulator variables are maintained.
 */
public class PracticeProblem5 {

    /**
     * Finds the maximum sum of any contiguous subarray of size k using the Sliding Window technique.
     *
     * @param sales array of daily sales figures
     * @param k size of consecutive days window
     * @return maximum sum found
     * @throws IllegalArgumentException if input is invalid
     */
    public static long maxSumSubarray(int[] sales, int k) {
        if (sales == null || sales.length == 0) {
            throw new IllegalArgumentException("Sales array cannot be null or empty.");
        }
        if (k <= 0 || k > sales.length) {
            throw new IllegalArgumentException("Invalid window size k: " + k + " for array of size " + sales.length);
        }

        // Compute sum of initial window
        long windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }

        long maxSum = windowSum;

        // Slide the window across the remaining elements
        for (int i = k; i < sales.length; i++) {
            windowSum += sales[i] - sales[i - k]; // add incoming element, subtract outgoing element
            if (windowSum > maxSum) {
                maxSum = windowSum;
            }
        }

        return maxSum;
    }

    /**
     * Naive implementation for verification and performance comparison.
     */
    public static long maxSumSubarrayNaive(int[] sales, int k) {
        if (sales == null || sales.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        long maxSum = Long.MIN_VALUE;
        for (int i = 0; i <= sales.length - k; i++) {
            long currentSum = 0;
            for (int j = i; j < i + k; j++) {
                currentSum += sales[j];
            }
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        System.out.println("=== Week 9 - Practice Problem 5: Maximum Sum Subarray of Fixed Size K ===");

        // Sample 1
        int[] sales1 = {2, 1, 5, 1, 3, 2};
        int k1 = 3;
        long res1 = maxSumSubarray(sales1, k1);
        System.out.println("Sales: " + Arrays.toString(sales1) + ", k = " + k1);
        System.out.println("Output: " + res1);
        System.out.println("Expected: 9  (from subarray [5, 1, 3])\n");

        // Sample 2: All negatives
        int[] sales2 = {-2, -3, -1, -5};
        int k2 = 2;
        long res2 = maxSumSubarray(sales2, k2);
        System.out.println("Sales: " + Arrays.toString(sales2) + ", k = " + k2);
        System.out.println("Output: " + res2);
        System.out.println("Expected: -4  (from subarray [-3, -1])\n");

        // Sample 3: k equals array length
        int[] sales3 = {4, 2, 7, 1};
        int k3 = 4;
        long res3 = maxSumSubarray(sales3, k3);
        System.out.println("Sales: " + Arrays.toString(sales3) + ", k = " + k3);
        System.out.println("Output: " + res3);
        System.out.println("Expected: 14\n");

        // Verify equivalence with naive
        int[] salesLarge = {10, 20, -5, 30, 40, -15, 60, 5};
        int kLarge = 4;
        long optRes = maxSumSubarray(salesLarge, kLarge);
        long naiveRes = maxSumSubarrayNaive(salesLarge, kLarge);
        System.out.println("Optimized: " + optRes + ", Naive: " + naiveRes + " -> Matches: " + (optRes == naiveRes));
    }
}
