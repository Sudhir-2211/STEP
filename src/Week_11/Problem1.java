package Week11;

import java.util.Arrays;
import java.util.Stack;

/**
 * Problem 1: The Canteen Price Streak
 *
 * TASK:
 * The canteen manager records the price of a samosa plate every day.
 * For each day, she wants the price streak: the number of consecutive days ending today
 * (including today) on which the price was less than or equal to today's price.
 * Implement int[] priceStreak(int[] price) that returns the streak for every day.
 * A nested-loop answer is O(n^2); your solution must run in O(n) by keeping on a stack
 * only the earlier days that can still block a future streak.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Each day index i from 0 to n - 1 is pushed onto the stack exactly once.
 *    - Each day index is popped from the stack at most once when a higher or equal price is encountered.
 *    - The total number of push and pop operations across the entire array is at most 2n.
 *    - Total Time Complexity: O(n), running linearly compared to the O(n^2) brute-force nested loops.
 *
 * 2. Space Complexity:
 *    - Auxiliary space for the stack storing day indices is O(n) in the worst case (monotonically decreasing prices).
 *    - Total Auxiliary Space: O(n).
 */
public class Problem1 {

    public static int[] priceStreak(int[] price) {
        if (price == null || price.length == 0) {
            return new int[0];
        }

        int n = price.length;
        int[] streaks = new int[n];
        // Monotonic stack storing indices of prices in strictly decreasing order
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            // Pop earlier days whose prices are less than or equal to today's price
            while (!stack.isEmpty() && price[stack.peek()] <= price[i]) {
                stack.pop();
            }

            // If stack is empty, today's price is >= all previous days' prices
            // Otherwise, the streak extends back to the index currently on top of the stack
            streaks[i] = stack.isEmpty() ? (i + 1) : (i - stack.peek());

            // Push today's index onto the stack
            stack.push(i);
        }

        return streaks;
    }

    public static void main(String[] args) {
        // Sample 1: price = [100, 80, 60, 70, 60, 75, 85]
        int[] price1 = {100, 80, 60, 70, 60, 75, 85};
        System.out.println("Sample 1 Input: " + Arrays.toString(price1));
        int[] result1 = priceStreak(price1);
        System.out.println("Expected Output: [1, 1, 1, 2, 1, 4, 6]");
        System.out.println("Actual Output:   " + Arrays.toString(result1));
        System.out.println();

        // Sample 2: price = [10, 20, 30]
        int[] price2 = {10, 20, 30};
        System.out.println("Sample 2 Input: " + Arrays.toString(price2));
        int[] result2 = priceStreak(price2);
        System.out.println("Expected Output: [1, 2, 3]");
        System.out.println("Actual Output:   " + Arrays.toString(result2));
    }
}
