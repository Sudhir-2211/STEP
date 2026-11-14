package Week4;

import java.util.ArrayList;
import java.util.List;

/**
 * Practice Problem 3: Bus Route Ranking Engine
 *
 * TASK:
 * BusRoute with routeCode, routeName, and priority (higher priority integer ranked first).
 * Ties broken by case-insensitive routeCode, then routeName.
 * Custom stable sorting algorithm (rankRoutes).
 */
public class PracticeProblem3 {

    public static class BusRoute implements Comparable<BusRoute> {
        private final String routeCode;
        private final String routeName;
        private final int priority;

        public BusRoute(String routeCode, String routeName, int priority) {
            this.routeCode = routeCode;
            this.routeName = routeName;
            this.priority = priority;
        }

        public BusRoute(String routeCode, String routeName) {
            this(routeCode, routeName, 1); // Default priority
        }

        public String getRouteCode() {
            return routeCode;
        }

        public String getRouteName() {
            return routeName;
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public int compareTo(BusRoute other) {
            // Higher priority ranked first (descending)
            if (this.priority != other.priority) {
                return Integer.compare(other.priority, this.priority);
            }
            // Tie break 1: routeCode case-insensitive
            int codeCmp = this.routeCode.compareToIgnoreCase(other.routeCode);
            if (codeCmp != 0) {
                return codeCmp;
            }
            // Tie break 2: routeName
            return this.routeName.compareToIgnoreCase(other.routeName);
        }
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null || routes.length <= 1) return routes;

        BusRoute[] sorted = routes.clone();
        // Stable insertion sort
        for (int i = 1; i < sorted.length; i++) {
            BusRoute key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].compareTo(key) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }
        return sorted;
    }

    public static void main(String[] args) {
        BusRoute[] input = {
                new BusRoute("RT205L", "Airport Express", 3),
                new BusRoute("rt201j", "City Central", 4),
                new BusRoute("RT299T", "Night Service")
        };

        BusRoute[] ranked = rankRoutes(input);
        List<String> codes = new ArrayList<>();
        for (BusRoute r : ranked) {
            codes.add(r.getRouteCode());
        }

        System.out.println("Ranked Routes: " + codes);
        // Expected: ["rt201j", "RT205L", "RT299T"]
    }
}
