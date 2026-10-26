package Week9;

import java.util.Arrays;

/**
 * Problem 2: Longest Budget-Friendly Streak
 *
 * Complexity Analysis:
 * 1. Brute-Force Approach:
 *    - Check all possible subarrays (pairs of start and end): O(n^2).
 *    - For each subarray, sum its elements: O(n) or O(1) with prefix sum.
 *    - Total Time Complexity: O(n^2).
 *
 * 2. Optimized Solution (Sliding Window / Two Pointers):
 *    - Both 'left' and 'right' pointers move strictly forward from 0 to n-1.
 *    - Each element is added to 'currentSum' once and subtracted at most once.
 *    - Total Time Complexity: O(n).
 *    - Auxiliary Space Complexity: O(1).
 *
 * 3. Why Non-Negative Costs Are Essential:
 *    - The sliding window relies on monotonicity: expanding 'right' never decreases the window sum,
 *      and advancing 'left' never increases the window sum. If costs could be negative, adding an element
 *      could decrease the sum, so a larger window might become valid again later, causing the greedy
 *      two-pointer approach to fail.
 */
public class Problem2 {

    public static class StreakResult {
        public final int length;
        public final int startIndex;

        public StreakResult(int length, int startIndex) {
            this.length = length;
            this.startIndex = startIndex;
        }

        @Override
        public String toString() {
            return "(" + length + ", " + startIndex + ")";
        }
    }

    public static StreakResult longestStreak(int[] costs, long budget) {
        if (costs == null || costs.length == 0 || budget < 0) {
            return new StreakResult(0, -1);
        }

        int left = 0;
        long currentSum = 0;
        int maxLen = 0;
        int bestStartIndex = -1;

        for (int right = 0; right < costs.length; right++) {
            currentSum += costs[right];

            // Shrink window from the left while total cost exceeds budget
            while (currentSum > budget && left <= right) {
                currentSum -= costs[left];
                left++;
            }

            // If current window is valid, evaluate its length
            if (currentSum <= budget && left <= right) {
                int currentLen = right - left + 1;
                // Strictly greater (>) ensures ties preserve the earlier starting index
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                    bestStartIndex = left;
                }
            }
        }

        if (maxLen == 0) {
            return new StreakResult(0, -1);
        }

        return new StreakResult(maxLen, bestStartIndex);
    }

    public static void main(String[] args) {
        // Sample 1
        int[] costs1 = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        long budget1 = 8;
        System.out.println("Costs: " + Arrays.toString(costs1) + ", Budget: " + budget1);
        System.out.println("Expected: (4, 4)");
        System.out.println("Output: " + longestStreak(costs1, budget1));

        // Sample 2
        int[] costs2 = {9, 10};
        long budget2 = 8;
        System.out.println("\nCosts: " + Arrays.toString(costs2) + ", Budget: " + budget2);
        System.out.println("Expected: (0, -1)");
        System.out.println("Output: " + longestStreak(costs2, budget2));
    }
}
