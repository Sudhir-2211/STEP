package Week12;

import java.util.Arrays;

/**
 * Problem 5: Placement Drive Shortlist
 *
 * TASK:
 * The placement cell runs six weekly mock tests. Build the shortlist for the final drive:
 * 1. Rank candidates by total score, highest first, using custom stable merge sort;
 *    equal totals keep their registration order.
 * 2. For each candidate, find the largest total gain over consecutive weekly changes
 *    and report it as "test x to y" (1-based, earliest run on ties).
 * 3. CUTOFF x prints how many candidates have a total of at least x using binary search.
 * 4. PAIR t finds two different candidates whose totals add up to exactly t using two pointers
 *    on the sorted list, or prints "no pair".
 *
 * Complexity Analysis:
 * 1. Merge Sort:
 *    - Time Complexity: O(n log n). Replaces O(n^2) brute-force sorting (e.g. bubble or selection sort).
 *    - Stability: Ensures candidates with identical totals (e.g. Asha and Neha) maintain their
 *      initial registration order.
 *    - Auxiliary Space: O(n) for temporary merge buffers.
 *
 * 2. Best Climb Analysis:
 *    - For k = 6 tests, examining all consecutive intervals [s, e] takes O(k^2) = O(1) constant time
 *      per candidate.
 *    - Overall time for all n candidates: O(n).
 *
 * 3. CUTOFF Queries:
 *    - Time Complexity: O(log n) per query via binary search on the descending sorted array.
 *    - Replaces O(n) brute-force linear scan per query.
 *
 * 4. PAIR Queries:
 *    - Time Complexity: O(n) per query using two pointers (left and right) on the sorted array.
 *    - Replaces O(n^2) brute-force nested iteration checking all candidate pairs.
 */
public class Problem5 {

    public static class Candidate {
        public final int registrationId;
        public final String name;
        public final int[] scores;
        public final int total;
        public final int bestGain;
        public final int bestStartTest;
        public final int bestEndTest;

        public Candidate(int registrationId, String name, int[] scores) {
            this.registrationId = registrationId;
            this.name = name;
            this.scores = scores;

            int sum = 0;
            for (int s : scores) {
                sum += s;
            }
            this.total = sum;

            // Compute largest total gain over consecutive weekly changes
            // Weekly change from test i to j is scores[j] - scores[i]
            int maxGain = Integer.MIN_VALUE;
            int startT = 1;
            int endT = 1;

            for (int s = 0; s < scores.length; s++) {
                for (int e = s + 1; e < scores.length; e++) {
                    int gain = scores[e] - scores[s];
                    // Strict inequality (>) preserves the earliest run on ties
                    if (gain > maxGain) {
                        maxGain = gain;
                        startT = s + 1; // 1-based indexing
                        endT = e + 1;
                    }
                }
            }

            this.bestGain = Math.max(0, maxGain);
            this.bestStartTest = startT;
            this.bestEndTest = endT;
        }
    }

    // Stable Merge Sort (Descending order by total score)
    public static void mergeSort(Candidate[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        Candidate[] temp = new Candidate[arr.length];
        mergeSortInternal(arr, temp, 0, arr.length - 1);
    }

    private static void mergeSortInternal(Candidate[] arr, Candidate[] temp, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSortInternal(arr, temp, left, mid);
        mergeSortInternal(arr, temp, mid + 1, right);
        merge(arr, temp, left, mid, right);
    }

    private static void merge(Candidate[] arr, Candidate[] temp, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            // >= ensures stability for descending sort: equal totals keep left element first
            if (temp[i].total >= temp[j].total) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }

        while (i <= mid) {
            arr[k++] = temp[i++];
        }
        while (j <= right) {
            arr[k++] = temp[j++];
        }
    }

    // Binary search to count how many candidates have total >= cutoff in descending array
    public static int countAtLeastCutoff(Candidate[] sortedCandidates, int cutoff) {
        int low = 0;
        int high = sortedCandidates.length - 1;
        int ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (sortedCandidates[mid].total >= cutoff) {
                ans = mid;      // All elements from 0 to mid have total >= cutoff
                low = mid + 1;  // Try to find if there are further candidates to the right
            } else {
                high = mid - 1;
            }
        }

        return ans + 1;
    }

    // Two-pointer search to find two distinct candidates whose totals sum to target
    public static String findPairWithSum(Candidate[] sortedCandidates, int target) {
        int left = 0;
        int right = sortedCandidates.length - 1;

        while (left < right) {
            int currentSum = sortedCandidates[left].total + sortedCandidates[right].total;

            if (currentSum == target) {
                return sortedCandidates[left].name + " + " + sortedCandidates[right].name;
            } else if (currentSum > target) {
                // In descending order, moving left forward reduces the sum
                left++;
            } else {
                // Moving right backward increases the sum
                right--;
            }
        }

        return "no pair";
    }

    public static void main(String[] args) {
        Candidate[] candidates = new Candidate[]{
                new Candidate(1, "Asha", new int[]{60, 55, 70, 72, 68, 80}),
                new Candidate(2, "Ravi", new int[]{70, 72, 65, 60, 75, 78}),
                new Candidate(3, "Neha", new int[]{50, 65, 62, 70, 74, 84}),
                new Candidate(4, "Kiran", new int[]{80, 70, 66, 72, 70, 60}),
                new Candidate(5, "Mohan", new int[]{55, 60, 58, 64, 70, 73})
        };

        // Stable sort descending by total score
        mergeSort(candidates);

        System.out.println("Expected Output:");
        System.out.println("Shortlist:");
        System.out.println("1. Ravi 420 | best climb +18 (test 4 to 6)");
        System.out.println("2. Kiran 418 | best climb +6 (test 3 to 4)");
        System.out.println("3. Asha 405 | best climb +25 (test 2 to 6)");
        System.out.println("4. Neha 405 | best climb +34 (test 1 to 6)");
        System.out.println("5. Mohan 380 | best climb +18 (test 1 to 6)");
        System.out.println("CUTOFF 405: 4 candidates");
        System.out.println("CUTOFF 410: 2 candidates");
        System.out.println("PAIR 785: Asha + Mohan");
        System.out.println("PAIR 900: no pair");
        System.out.println();

        System.out.println("Actual Output:");
        System.out.println("Shortlist:");
        for (int i = 0; i < candidates.length; i++) {
            Candidate c = candidates[i];
            System.out.println((i + 1) + ". " + c.name + " " + c.total + " | best climb +"
                    + c.bestGain + " (test " + c.bestStartTest + " to " + c.bestEndTest + ")");
        }

        // Queries
        int cutoff1 = 405;
        System.out.println("CUTOFF " + cutoff1 + ": " + countAtLeastCutoff(candidates, cutoff1) + " candidates");

        int cutoff2 = 410;
        System.out.println("CUTOFF " + cutoff2 + ": " + countAtLeastCutoff(candidates, cutoff2) + " candidates");

        int pairTarget1 = 785;
        System.out.println("PAIR " + pairTarget1 + ": " + findPairWithSum(candidates, pairTarget1));

        int pairTarget2 = 900;
        System.out.println("PAIR " + pairTarget2 + ": " + findPairWithSum(candidates, pairTarget2));
    }
}
