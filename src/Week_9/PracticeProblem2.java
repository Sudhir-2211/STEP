package Week9;

import java.util.Arrays;

/**
 * Week 9 - Practice Problem 2: Warehouse Bin Grid Scan
 *
 * Task:
 * A warehouse floor layout is represented as a 2D grid, where each cell (row, col) contains
 * a non-negative integer representing the count of items in a storage bin (0 indicates an empty bin).
 * Management requires a summary that includes:
 *   1. Total number of items across all bins.
 *   2. Coordinates (row, col) of the bin holding the most items.
 * If multiple bins have the same maximum count, return the coordinates of the first one encountered
 * when scanning row by row, from left to right.
 *
 * Complexity Analysis:
 * - Time Complexity: O(m * n), where m is the number of rows and n is the number of columns.
 *   Every cell must be inspected exactly once to accumulate the total and determine the maximum.
 * - Space Complexity: O(1) auxiliary space beyond variables used to store the running sum,
 *   maximum count, and the corresponding row and column indices.
 */
public class PracticeProblem2 {

    public static class Coordinate {
        public final int row;
        public final int col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

    public static class WarehouseSummary {
        public final long total;
        public final Coordinate maxCoordinate;
        public final int maxCount;

        public WarehouseSummary(long total, Coordinate maxCoordinate, int maxCount) {
            this.total = total;
            this.maxCoordinate = maxCoordinate;
            this.maxCount = maxCount;
        }

        @Override
        public String toString() {
            return "total = " + total + ", maxCoordinate = " + maxCoordinate;
        }
    }

    /**
     * Scans the 2D warehouse grid and generates total count and coordinates of the maximum bin.
     *
     * @param grid 2D array of non-negative integers representing item counts
     * @return WarehouseSummary object
     */
    public static WarehouseSummary warehouseSummary(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return new WarehouseSummary(0, new Coordinate(-1, -1), 0);
        }

        long total = 0;
        int maxCount = -1;
        int maxRow = 0;
        int maxCol = 0;

        int m = grid.length;
        for (int r = 0; r < m; r++) {
            int n = grid[r].length;
            for (int c = 0; c < n; c++) {
                int count = grid[r][c];
                total += count;

                // Strict inequality ensures we keep the first occurrence encountered
                if (count > maxCount) {
                    maxCount = count;
                    maxRow = r;
                    maxCol = c;
                }
            }
        }

        return new WarehouseSummary(total, new Coordinate(maxRow, maxCol), maxCount);
    }

    public static void main(String[] args) {
        System.out.println("=== Week 9 - Practice Problem 2: Warehouse Bin Grid Scan ===");

        // Sample
        int[][] grid1 = {
            {4, 9, 2},
            {7, 1, 6},
            {3, 12, 5}
        };

        WarehouseSummary summary1 = warehouseSummary(grid1);
        System.out.println("Grid 1:");
        for (int[] row : grid1) {
            System.out.println("  " + Arrays.toString(row));
        }
        System.out.println("Output: " + summary1);
        System.out.println("Expected: total = 49, maxCoordinate = (2, 1)\n");

        // Test with tie for maximum: first encountered must be selected
        int[][] grid2 = {
            {5, 15, 8},
            {15, 2, 7},
            {3, 4, 15}
        };

        WarehouseSummary summary2 = warehouseSummary(grid2);
        System.out.println("Grid 2 (Multiple max values 15):");
        for (int[] row : grid2) {
            System.out.println("  " + Arrays.toString(row));
        }
        System.out.println("Output: " + summary2);
        System.out.println("Expected: total = 74, maxCoordinate = (0, 1)\n");

        // Test with empty/zero cells
        int[][] grid3 = {
            {0, 0},
            {0, 1}
        };
        WarehouseSummary summary3 = warehouseSummary(grid3);
        System.out.println("Grid 3:");
        System.out.println("Output: " + summary3);
        System.out.println("Expected: total = 1, maxCoordinate = (1, 1)\n");
    }
}
