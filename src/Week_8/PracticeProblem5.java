package Week8;

import java.util.ArrayList;
import java.util.List;

/**
 * Practice Problem 5: Food Order and Flexible Payment System
 *
 * TASK:
 * 1. Order requires at least one LineItem before it can be placed.
 * 2. IPaymentMethod interface (CreditCardPayment, DigitalWalletPayment).
 * 3. Event notification upon placement and payment completion or failure.
 */
public class PracticeProblem5 {

    public interface IPaymentMethod {
        boolean pay(double amount);
        String getName();
    }

    public static class CreditCardPayment implements IPaymentMethod {
        @Override
        public boolean pay(double amount) {
            return true; // Simulate success
        }

        @Override
        public String getName() { return "Credit Card"; }
    }

    public static class DigitalWalletPayment implements IPaymentMethod {
        private final boolean simulateSuccess;

        public DigitalWalletPayment(boolean simulateSuccess) {
            this.simulateSuccess = simulateSuccess;
        }

        @Override
        public boolean pay(double amount) {
            return simulateSuccess;
        }

        @Override
        public String getName() { return "Digital Wallet"; }
    }

    public static class LineItem {
        public final String name;
        public final int quantity;
        public final double pricePerUnit;

        public LineItem(String name, int quantity, double pricePerUnit) {
            this.name = name;
            this.quantity = quantity;
            this.pricePerUnit = pricePerUnit;
        }

        public double getTotal() {
            return quantity * pricePerUnit;
        }
    }

    public static class Order {
        private static int counter = 122;
        public final int orderId;
        private final List<LineItem> items = new ArrayList<>();
        public String status = "Created";

        public Order() {
            this.orderId = ++counter;
            System.out.println("Order created.");
        }

        public void addItem(String name, int quantity, double price) {
            items.add(new LineItem(name, quantity, price));
            System.out.println("Added " + name + " (Qty " + quantity + ").");
        }

        public boolean placeOrder(IPaymentMethod paymentMethod) {
            if (items.isEmpty()) {
                System.out.println("Cannot place order: Order must contain at least one item.");
                return false;
            }

            double total = 0;
            for (LineItem item : items) {
                total += item.getTotal();
            }

            System.out.println("Order placed.");
            boolean success = paymentMethod.pay(total);
            if (success) {
                this.status = "Paid";
                System.out.println("Payment via " + paymentMethod.getName() + " successful. Order status: Paid.");
                System.out.println("Notification: Order #" + orderId + " placed and paid.");
                return true;
            } else {
                this.status = "Pending Payment";
                System.out.println("Payment via " + paymentMethod.getName() + " failed. Order status: Pending Payment.");
                System.out.println("Notification: Order #" + orderId + " placed, awaiting payment.");
                return false;
            }
        }
    }

    public static void main(String[] args) {
        Order o1 = new Order();
        o1.addItem("Pizza", 2, 12.0);
        o1.addItem("Soda", 1, 3.0);

        Order emptyOrder = new Order();
        emptyOrder.placeOrder(new CreditCardPayment());

        o1.placeOrder(new CreditCardPayment());

        Order o2 = new Order();
        o2.addItem("Burger", 1, 8.0);
        o2.placeOrder(new DigitalWalletPayment(false)); // simulate failure
    }
}
