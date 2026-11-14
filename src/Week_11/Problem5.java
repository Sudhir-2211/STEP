package Week11;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * Problem 5: The Fire Drill Exits
 *
 * TASK:
 * For the hostel fire drill, the floor plan is a grid where 'E' is an exit, '#' is a wall
 * and '.' is an open room. Students move up, down, left or right one room per step.
 * Implement int[][] exitDistance(char[][] plan) that returns, for every cell,
 * the minimum number of steps to the nearest exit: 0 for an exit, and -1 for a wall
 * or a room that cannot reach any exit.
 * Running a separate search from every room is too slow; solve it with one breadth-first search
 * that starts from all exits at once (multi-source BFS).
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - All exits 'E' are enqueued initially with distance 0.
 *    - BFS explores each cell and each of its 4 directional edges at most once.
 *    - Because BFS traverses in non-decreasing order of distance, the first time an open room
 *      is reached, its shortest distance to any exit is found.
 *    - Total Time Complexity: O(R * C), where R is rows and C is columns of the grid.
 *
 * 2. Space Complexity:
 *    - Auxiliary space for the BFS queue holds at most O(R * C) cells.
 *    - The result matrix of size R * C stores distances.
 *    - Total Auxiliary Space Complexity: O(R * C).
 */
public class Problem5 {

    public static int[][] exitDistance(char[][] plan) {
        if (plan == null || plan.length == 0 || plan[0].length == 0) {
            return new int[0][0];
        }

        int rows = plan.length;
        int cols = plan[0].length;
        int[][] dist = new int[rows][cols];
        Queue<int[]> queue = new ArrayDeque<>();

        // Initialize distances: -1 for all, 0 for exits added to queue
        for (int r = 0; r < rows; r++) {
            Arrays.fill(dist[r], -1);
            for (int c = 0; c < cols; c++) {
                if (plan[r][c] == 'E') {
                    dist[r][c] = 0;
                    queue.offer(new int[]{r, c});
                }
            }
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Multi-source BFS
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int currentDist = dist[r][c];

            for (int[] dir : directions) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                // Check bounds
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    // Only visit rooms '.' that haven't been visited yet (dist == -1)
                    if (plan[nr][nc] == '.' && dist[nr][nc] == -1) {
                        dist[nr][nc] = currentDist + 1;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }

        return dist;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < row.length; i++) {
                sb.append(row[i]);
                if (i < row.length - 1) {
                    sb.append(" ");
                }
            }
            System.out.println(sb);
        }
    }

    public static void main(String[] args) {
        char[][] plan = {
                {'.', '#', '.', 'E'},
                {'.', '.', '.', '#'},
                {'E', '#', '.', '.'}
        };

        System.out.println("Floor Plan:");
        for (char[] row : plan) {
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
        System.out.println();

        int[][] result = exitDistance(plan);

        System.out.println("Expected Output:");
        System.out.println("2 -1 1 0");
        System.out.println("1 2 2 -1");
        System.out.println("0 -1 3 4");
        System.out.println();

        System.out.println("Actual Output:");
        printMatrix(result);
    }
}
