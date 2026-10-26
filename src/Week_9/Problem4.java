package Week9;

import java.util.Arrays;

/**
 * Problem 4: Exam Score Band Counter
 *
 * Complexity Analysis:
 * 1. Linear Scan Approach:
 *    - Scans every student's score from start to finish.
 *    - Time Complexity: O(n) per query.
 *    - Auxiliary Space Complexity: O(1).
 *
 * 2. Optimized Solution (Binary Search - Lower & Upper Bounds):
 *    - Since the array is sorted, we use two logarithmic binary searches:
 *      a) lowerBound(low): finds first index where score >= low.
 *      b) upperBound(high): finds first index where score > high.
 *    - Number of scores in [low, high] is simply: (upperBound - lowerBound).
 *    - Time Complexity: O(log n) per query.
 *    - Auxiliary Space Complexity: O(1).
 *
 * 3. Handling Duplicates at Band Boundaries:
 *    - A standard binary search stops as soon as any matching element is found, which fails when
 *      duplicate scores exist at the edges (e.g. multiple 42s or multiple 58s).
 *    - By searching for the extreme boundaries (first occurrence >= low, and first occurrence strictly > high),
 *      all duplicates of 'low' and 'high' are correctly and completely included in the range.
 */
public class Problem4 {

    // Finds first index where scores[i] >= target
    public static int lowerBound(int[] scores, int target) {
        int left = 0;
        int right = scores.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (scores[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // Finds first index where scores[i] > target
    public static int upperBound(int[] scores, int target) {
        int left = 0;
        int right = scores.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (scores[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public static int countInBand(int[] scores, int low, int high) {
        if (scores == null || scores.length == 0 || low > high) {
            return 0;
        }

        int firstValid = lowerBound(scores, low);
        int firstPastValid = upperBound(scores, high);

        return Math.max(0, firstPastValid - firstValid);
    }

    public static void main(String[] args) {
        int[] scores = {35, 42, 42, 50, 58, 58, 58, 63, 71, 88};
        System.out.println("Scores: " + Arrays.toString(scores));

        // Sample 1: low = 42, high = 58
        int low1 = 42, high1 = 58;
        System.out.println("\nQuery Band: [" + low1 + ", " + high1 + "]");
        System.out.println("Expected: 6");
        System.out.println("Output: " + countInBand(scores, low1, high1));

        // Sample 2: low = 90, high = 100
        int low2 = 90, high2 = 100;
        System.out.println("\nQuery Band: [" + low2 + ", " + high2 + "]");
        System.out.println("Expected: 0");
        System.out.println("Output: " + countInBand(scores, low2, high2));
    }
}
