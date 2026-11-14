package Week12;

/**
 * Problem 1: Order Walkthrough with Nested Types
 *
 * TASK:
 * An online order stores its line items in an internal array. Shop staff need to walk
 * through the order one line at a time and see the total cost.
 * Design an Order class with a LineItem static nested class (productName, quantity, unitPrice
 * and double total()) and a Cursor member inner class with hasNext(), next() and reset().
 * Use the cursor to print every line item and the order total, and justify in one line each
 * why LineItem is static nested and Cursor is a member inner class.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Adding an item to the internal array: O(1) amortized.
 *    - Walking through the entire order using Cursor: O(n), visiting each line item once.
 *
 * 2. Space Complexity:
 *    - LineItem instances in the internal array: O(n) to store n items.
 *    - Cursor: O(1) auxiliary space maintaining an index pointer.
 *
 * Design Justifications:
 * - cursor: inner (reads the order's array)
 * - line item: static nested (needs no order)
 */
public class PracticeProblem1 {

    public static class Order {
        private LineItem[] items;
        private int count;

        public Order(int capacity) {
            this.items = new LineItem[capacity];
            this.count = 0;
        }

        public void addItem(String productName, int quantity, double unitPrice) {
            if (count == items.length) {
                // Resize if needed
                LineItem[] nextItems = new LineItem[items.length * 2];
                System.arraycopy(items, 0, nextItems, 0, items.length);
                items = nextItems;
            }
            items[count++] = new LineItem(productName, quantity, unitPrice);
        }

        public double calculateOrderTotal() {
            double total = 0.0;
            for (int i = 0; i < count; i++) {
                total += items[i].total();
            }
            return total;
        }

        public Cursor getCursor() {
            return new Cursor();
        }

        /**
         * Static nested class: Does not require an implicit reference to an enclosing Order instance.
         * A LineItem is a standalone data holder containing its own product, quantity, and unit price.
         */
        public static class LineItem {
            private final String productName;
            private final int quantity;
            private final double unitPrice;

            public LineItem(String productName, int quantity, double unitPrice) {
                this.productName = productName;
                this.quantity = quantity;
                this.unitPrice = unitPrice;
            }

            public String getProductName() {
                return productName;
            }

            public int getQuantity() {
                return quantity;
            }

            public double getUnitPrice() {
                return unitPrice;
            }

            public double total() {
                return quantity * unitPrice;
            }
        }

        /**
         * Member inner class: Inherently bound to the enclosing Order instance,
         * granting direct access to Order's private items array and count.
         */
        public class Cursor {
            private int currentIndex = 0;

            public boolean hasNext() {
                return currentIndex < count;
            }

            public LineItem next() {
                if (!hasNext()) {
                    return null;
                }
                return items[currentIndex++];
            }

            public void reset() {
                currentIndex = 0;
            }
        }
    }

    private static String formatMoney(double val) {
        if (val == (long) val) {
            return String.valueOf((long) val);
        }
        return String.valueOf(val);
    }

    public static void main(String[] args) {
        Order order = new Order(10);
        order.addItem("Pen", 3, 10);
        order.addItem("Book", 2, 250);
        order.addItem("Bag", 1, 700);

        System.out.println("Expected Output:");
        System.out.println("Pen 30");
        System.out.println("Book 500");
        System.out.println("Bag 700");
        System.out.println("Total 1230");
        System.out.println("cursor: inner (reads the order's array)");
        System.out.println("line item: static nested (needs no order)");
        System.out.println();

        System.out.println("Actual Output:");
        Order.Cursor cursor = order.getCursor();
        double orderTotal = 0;
        while (cursor.hasNext()) {
            Order.LineItem item = cursor.next();
            double itemTotal = item.total();
            orderTotal += itemTotal;
            System.out.println(item.getProductName() + " " + formatMoney(itemTotal));
        }
        System.out.println("Total " + formatMoney(orderTotal));
        System.out.println("cursor: inner (reads the order's array)");
        System.out.println("line item: static nested (needs no order)");
    }
}
