package Week9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Problem 5: Spiral Stock Audit Route
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - The warehouse grid has m rows and n columns, containing m * n total bins.
 *    - Every bin is visited and added to the output list exactly once.
 *    - Total Time Complexity: O(m * n).
 *
 * 2. Space Complexity:
 *    - Additional Auxiliary Space (excluding the output list): O(1).
 *    - Only a constant number of boundary index variables (top, bottom, left, right) are maintained.
 *
 * 3. Boundary Crossing Guard:
 *    - Before traversing the bottom row (right to left) or the left column (bottom to top),
 *      we must verify that the boundaries haven't already crossed (top <= bottom and left <= right).
 *    - This prevents single remaining rows or columns from being audited more than once.
 */
public class Problem5 {

    public static List<Integer> auditRoute(int[][] grid) {
        List<Integer> route = new ArrayList<>();
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return route;
        }

        int top = 0;
        int bottom = grid.length - 1;
        int left = 0;
        int right = grid[0].length - 1;

        while (top <= bottom && left <= right) {
            // 1. Traverse top row from left to right
            for (int col = left; col <= right; col++) {
                route.add(grid[top][col]);
            }
            top++;

            // 2. Traverse right column downwards
            for (int row = top; row <= bottom; row++) {
                route.add(grid[row][right]);
            }
            right--;

            // 3. Traverse bottom row from right to left (check boundary hasn't crossed)
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    route.add(grid[bottom][col]);
                }
                bottom--;
            }

            // 4. Traverse left column upwards (check boundary hasn't crossed)
            if (left <= right && top <= bottom) {
                for (int row = bottom; row >= top; row--) {
                    route.add(grid[row][left]);
                }
                left++;
            }
        }

        return route;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1,  2,  3,  4},
                {5,  6,  7,  8},
                {9, 10, 11, 12}
        };

        System.out.println("Warehouse Grid:");
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }

        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7);
        System.out.println("\nExpected Route: " + expected);

        List<Integer> actual = auditRoute(grid);
        System.out.println("Actual Route:   " + actual);
    }
}
