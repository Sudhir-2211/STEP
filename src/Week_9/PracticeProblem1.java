package Week9;

import java.util.Arrays;

/**
 * Week 9 - Practice Problem 1: Pair Sum in a Sorted Array
 *
 * Task:
 * A finance application needs to determine if any two distinct account balances
 * in an already-sorted list of integers (which may include negative and positive values)
 * sum up to a specific target reconciliation amount.
 * Implement pairSumSorted(nums, target) that returns a Pair representing the matching
 * pair of values if found, or null / "Not Found" if no such pair exists.
 * Must leverage the sorted nature of the array to achieve optimal performance.
 *
 * Complexity Analysis:
 * 1. Two-Pointer Approach (Optimal):
 *    - Left pointer starts at index 0, right pointer starts at index n - 1.
 *    - At each step:
 *        sum = nums[left] + nums[right]
 *        if sum == target: return (nums[left], nums[right])
 *        if sum < target: increment left (to increase the sum)
 *        if sum > target: decrement right (to decrease the sum)
 *    - Time Complexity: O(n) as each step moves at least one pointer closer, taking at most n iterations.
 *    - Auxiliary Space Complexity: O(1) using only two pointer variables.
 *
 * 2. Comparison with Alternative Approaches:
 *    - Brute Force (Nested loops): O(n^2) time, O(1) space. Checks every pair, ignoring sorted order.
 *    - Hash Set: O(n) time, O(n) auxiliary space. Does not take advantage of sorted order and incurs
 *      memory overhead and hashing overhead.
 *    - Two pointers is superior because it achieves linear O(n) time with strictly O(1) auxiliary space.
 */
public class PracticeProblem1 {

    public static class Pair {
        public final int first;
        public final int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    /**
     * Finds two distinct elements in a sorted array that sum up to target.
     *
     * @param nums sorted array of integers in ascending order
     * @param target target sum
     * @return Pair containing the two numbers, or null if not found
     */
    public static Pair pairSumSorted(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int currentSum = nums[left] + nums[right];

            if (currentSum == target) {
                return new Pair(nums[left], nums[right]);
            } else if (currentSum < target) {
                left++; // Increase sum by moving left pointer to a larger value
            } else {
                right--; // Decrease sum by moving right pointer to a smaller value
            }
        }

        return null;
    }

    public static String formatResult(Pair pair) {
        return pair != null ? pair.toString() : "Not Found";
    }

    public static void main(String[] args) {
        System.out.println("=== Week 9 - Practice Problem 1: Pair Sum in a Sorted Array ===");

        // Sample 1
        int[] nums1 = {-4, -1, 0, 3, 5, 9};
        int target1 = 4;
        Pair res1 = pairSumSorted(nums1, target1);
        System.out.println("Test 1: nums = " + Arrays.toString(nums1) + ", target = " + target1);
        System.out.println("Output: " + formatResult(res1));
        System.out.println("Expected: (-1, 5)\n");

        // Sample 2
        int[] nums2 = {1, 2, 3};
        int target2 = 100;
        Pair res2 = pairSumSorted(nums2, target2);
        System.out.println("Test 2: nums = " + Arrays.toString(nums2) + ", target = " + target2);
        System.out.println("Output: " + formatResult(res2));
        System.out.println("Expected: Not Found\n");

        // Additional Test: Negative Numbers
        int[] nums3 = {-10, -7, -3, -1, 4, 8};
        int target3 = -10;
        Pair res3 = pairSumSorted(nums3, target3);
        System.out.println("Test 3: nums = " + Arrays.toString(nums3) + ", target = " + target3);
        System.out.println("Output: " + formatResult(res3));
        System.out.println("Expected: (-7, -3)\n");

        // Edge Case: 2 elements
        int[] nums4 = {2, 7};
        int target4 = 9;
        Pair res4 = pairSumSorted(nums4, target4);
        System.out.println("Test 4: nums = " + Arrays.toString(nums4) + ", target = " + target4);
        System.out.println("Output: " + formatResult(res4));
        System.out.println("Expected: (2, 7)\n");
    }
}
