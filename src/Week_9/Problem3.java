package Week9;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem 3: Net-Balance Period Counter
 *
 * Complexity Analysis:
 * 1. Brute-Force Approach:
 *    - Examines every pair (i, j) and sums the elements in transactions[i..j].
 *    - Number of subarrays: n * (n + 1) / 2.
 *    - Time Complexity: O(n^2).
 *    - Auxiliary Space Complexity: O(1).
 *
 * 2. Optimized Solution (Prefix Sum + Frequency Map):
 *    - A subarray transactions[i..j] has sum == k if and only if: prefix[j] - prefix[i-1] == k,
 *      which rearranges to: prefix[i-1] == prefix[j] - k.
 *    - We iterate through the array once while maintaining a hash map of running total frequencies.
 *    - Time Complexity: O(n) on average (O(1) hash map operations per element).
 *    - Auxiliary Space Complexity: O(n) to store up to n distinct prefix sums in the map.
 */
public class Problem3 {

    public static int countPeriods(int[] transactions, long k) {
        if (transactions == null || transactions.length == 0) {
            return 0;
        }

        // Map stores: (prefixSum -> number of occurrences)
        Map<Long, Integer> prefixFrequency = new HashMap<>();
        // Base case: a prefix sum of 0 has occurred once before processing any elements
        prefixFrequency.put(0L, 1);

        long runningSum = 0;
        int count = 0;

        for (int tx : transactions) {
            runningSum += tx;

            // Check how many times (runningSum - k) has appeared previously
            long targetPrefix = runningSum - k;
            if (prefixFrequency.containsKey(targetPrefix)) {
                count += prefixFrequency.get(targetPrefix);
            }

            // Record current running sum into the frequency map
            prefixFrequency.put(runningSum, prefixFrequency.getOrDefault(runningSum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        // Sample 1
        int[] tx1 = {3, 4, -7, 1, 3, 3, 1, -4};
        long k1 = 7;
        System.out.println("Transactions: " + Arrays.toString(tx1) + ", k = " + k1);
        System.out.println("Expected: 4");
        System.out.println("Output: " + countPeriods(tx1, k1));

        // Sample 2
        int[] tx2 = {1, 2, 3};
        long k2 = 10;
        System.out.println("\nTransactions: " + Arrays.toString(tx2) + ", k = " + k2);
        System.out.println("Expected: 0");
        System.out.println("Output: " + countPeriods(tx2, k2));
    }
}
