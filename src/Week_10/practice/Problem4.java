package Week10.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Week 10 Practice - Problem 4: Product Price Finder
 *
 * Task:
 * You have a list of product prices, which are guaranteed to be sorted in ascending order.
 * Implement a function that efficiently counts how many products fall within a specified price range
 * (inclusive of both lower and upper bounds). You need to return the count of such products,
 * along with the lower bound index and upper bound index.
 *
 * Complexity Requirements:
 * - Binary Search Lower Bound: Finds the first index i such that prices[i] >= lowerBound in O(log n) time.
 * - Binary Search Upper Bound: Finds the first index j such that prices[j] > upperBound in O(log n) time.
 * - Total Count = j - i in O(1) time after finding indices.
 * - Overall Time Complexity: O(log n).
 * - Auxiliary Space Complexity: O(1).
 *
 * Sample Input:
 *   Prices [100, 150, 150, 200, 300, 450]
 *   Range 150 to 300
 *
 * Expected Output:
 *   Lower bound index 1
 *   Upper bound index 5
 *   Count 4 (products with prices 150, 150, 200, 300)
 */
public class Problem4 {

    public static class RangeResult {
        public final int lowerBoundIndex;
        public final int upperBoundIndex;
        public final int count;
        public final List<Integer> productsInRange;

        public RangeResult(int lowerBoundIndex, int upperBoundIndex, int count, List<Integer> productsInRange) {
            this.lowerBoundIndex = lowerBoundIndex;
            this.upperBoundIndex = upperBoundIndex;
            this.count = count;
            this.productsInRange = productsInRange;
        }

        public void printSummary() {
            System.out.println("Lower bound index " + lowerBoundIndex);
            System.out.println("Upper bound index " + upperBoundIndex);

            StringBuilder sb = new StringBuilder();
            sb.append("Count ").append(count);
            if (count > 0) {
                sb.append(" (products with prices ");
                for (int i = 0; i < productsInRange.size(); i++) {
                    sb.append(productsInRange.get(i));
                    if (i < productsInRange.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")");
            }
            System.out.println(sb.toString());
        }
    }

    /**
     * Finds the first index where prices[index] >= target.
     * If all elements are < target, returns prices.length.
     */
    public static int findLowerBound(int[] prices, int target) {
        int low = 0;
        int high = prices.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (prices[mid] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    /**
     * Finds the first index where prices[index] > target.
     * If all elements are <= target, returns prices.length.
     */
    public static int findUpperBound(int[] prices, int target) {
        int low = 0;
        int high = prices.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (prices[mid] > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    /**
     * Queries product price range in O(log n) time.
     */
    public static RangeResult queryPriceRange(int[] prices, int lowerPrice, int upperPrice) {
        if (prices == null || prices.length == 0 || lowerPrice > upperPrice) {
            return new RangeResult(0, 0, 0, new ArrayList<>());
        }

        int lowerIndex = findLowerBound(prices, lowerPrice);
        int upperIndex = findUpperBound(prices, upperPrice);

        int count = Math.max(0, upperIndex - lowerIndex);
        List<Integer> products = new ArrayList<>();
        for (int i = lowerIndex; i < upperIndex; i++) {
            products.add(prices[i]);
        }

        return new RangeResult(lowerIndex, upperIndex, count, products);
    }

    public static void main(String[] args) {
        System.out.println("=== Week 10 Practice - Problem 4: Product Price Finder ===");

        int[] prices = {100, 150, 150, 200, 300, 450};
        int rangeMin = 150;
        int rangeMax = 300;

        System.out.println("Prices: " + Arrays.toString(prices));
        System.out.println("Range: " + rangeMin + " to " + rangeMax);
        System.out.println("\nCalculated Output:");

        RangeResult result = queryPriceRange(prices, rangeMin, rangeMax);
        result.printSummary();

        System.out.println("\nExpected Output:");
        System.out.println("Lower bound index 1");
        System.out.println("Upper bound index 5");
        System.out.println("Count 4 (products with prices 150, 150, 200, 300)");
    }
}
