package Week8;

import java.util.*;

public class Problem5 {

    // Pricing plan interface for polymorphic discount calculation
    public interface PricingPlan {
        String getPlanName();
        double calculateDiscountedPrice(double originalPrice);
    }

    public static class DayScholarPlan implements PricingPlan {
        @Override
        public String getPlanName() {
            return "Day Scholar";
        }

        @Override
        public double calculateDiscountedPrice(double originalPrice) {
            return originalPrice; // full price
        }
    }

    public static class HostellerPlan implements PricingPlan {
        @Override
        public String getPlanName() {
            return "Hosteller";
        }

        @Override
        public double calculateDiscountedPrice(double originalPrice) {
            return originalPrice * 0.90; // 10% off
        }
    }

    public static class StaffPlan implements PricingPlan {
        @Override
        public String getPlanName() {
            return "Staff";
        }

        @Override
        public double calculateDiscountedPrice(double originalPrice) {
            return originalPrice * 0.80; // 20% off
        }
    }

    // Transaction model
    public static class Transaction {
        private String description;
        private double amount; // positive for top-up/refund, negative for purchase
        private boolean isPurchase;
        private boolean refunded;

        public Transaction(String description, double amount, boolean isPurchase) {
            this.description = description;
            this.amount = amount;
            this.isPurchase = isPurchase;
            this.refunded = false;
        }

        public String getDescription() {
            return description;
        }

        public double getAmount() {
            return amount;
        }

        public boolean isPurchase() {
            return isPurchase;
        }

        public boolean isRefunded() {
            return refunded;
        }

        public void setRefunded(boolean refunded) {
            this.refunded = refunded;
        }
    }

    // SmartCard maintains balance invariant (balance == sum of transactions)
    public static class SmartCard {
        private String cardId;
        private PricingPlan plan;
        private boolean isBlocked;
        private List<Transaction> transactions = new ArrayList<>();

        public SmartCard(String cardId, PricingPlan plan) {
            this.cardId = cardId;
            this.plan = plan;
            this.isBlocked = false;
        }

        public String getCardId() {
            return cardId;
        }

        public PricingPlan getPlan() {
            return plan;
        }

        public boolean isBlocked() {
            return isBlocked;
        }

        public void block() {
            this.isBlocked = true;
        }

        public void unblock() {
            this.isBlocked = false;
        }

        // Balance is strictly derived or confirmed from transactions
        public double getBalance() {
            double total = 0.0;
            for (Transaction t : transactions) {
                total += t.getAmount();
            }
            return total;
        }

        public boolean topUp(double amount) {
            if (isBlocked) {
                System.out.println("Top-up rejected: Card " + cardId + " is blocked.");
                return false;
            }
            if (amount < 100.0) {
                System.out.println("Top-up rejected: Minimum top-up amount is ₹100.00.");
                return false;
            }
            if (getBalance() + amount > 5000.0) {
                System.out.println("Top-up rejected: Maximum allowed balance is ₹5,000.00.");
                return false;
            }

            transactions.add(new Transaction("Top-up", amount, false));
            System.out.printf(Locale.US, "%s topped up with ₹%.2f. Balance: ₹%.2f.\n",
                    cardId, amount, getBalance());
            return true;
        }

        public boolean purchase(String itemName, double price) {
            if (isBlocked) {
                System.out.println("Purchase rejected: Card " + cardId + " is blocked.");
                return false;
            }

            double chargedAmount = plan.calculateDiscountedPrice(price);
            double currentBalance = getBalance();

            if (currentBalance < chargedAmount) {
                System.out.printf(Locale.US, "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).\n",
                        chargedAmount, currentBalance);
                return false;
            }

            transactions.add(new Transaction(itemName, -chargedAmount, true));
            System.out.printf(Locale.US, "%s purchased for ₹%.2f. Balance: ₹%.2f.\n",
                    itemName, chargedAmount, getBalance());
            return true;
        }

        public boolean refund(String itemName) {
            if (isBlocked) {
                System.out.println("Refund rejected: Card " + cardId + " is blocked.");
                return false;
            }

            // Find purchase transaction matching the item name that hasn't been refunded yet
            Transaction targetPurchase = null;
            for (Transaction t : transactions) {
                if (t.isPurchase() && t.getDescription().equals(itemName)) {
                    targetPurchase = t;
                    break;
                }
            }

            if (targetPurchase == null) {
                System.out.println("Refund rejected: No purchase found for " + itemName + ".");
                return false;
            }

            if (targetPurchase.isRefunded()) {
                System.out.println("Refund rejected: " + itemName + " has already been refunded.");
                return false;
            }

            double refundAmount = Math.abs(targetPurchase.getAmount());
            targetPurchase.setRefunded(true);
            transactions.add(new Transaction("Refund for " + itemName, refundAmount, false));

            System.out.printf(Locale.US, "Refund of ₹%.2f for %s processed. Balance: ₹%.2f.\n",
                    refundAmount, itemName, getBalance());
            return true;
        }

        public void printMiniStatement() {
            StringBuilder sb = new StringBuilder();
            sb.append("Mini-statement for ").append(cardId).append(": ");

            for (int i = 0; i < transactions.size(); i++) {
                Transaction t = transactions.get(i);
                if (t.getAmount() >= 0) {
                    sb.append(String.format(Locale.US, "+%.2f", t.getAmount()));
                } else {
                    sb.append(String.format(Locale.US, "-%.2f", Math.abs(t.getAmount())));
                }

                if (i < transactions.size() - 1) {
                    sb.append(", ");
                }
            }

            sb.append(String.format(Locale.US, " = ₹%.2f.", getBalance()));
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {
        // 1. Card 'C-2045' (Hosteller plan) is topped up with ₹500
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());
        card.topUp(500.0);

        // 2. Holder buys 'Veg Thali' priced at ₹120 (10% off -> ₹108)
        card.purchase("Veg Thali", 120.0);

        // 3. Holder buys 'Cold Coffee' priced at ₹60 (10% off -> ₹54)
        card.purchase("Cold Coffee", 60.0);

        // 4. Holder attempts to buy items worth ₹400 (10% off -> ₹360, but balance is 338)
        card.purchase("Assorted Items", 400.0);

        // 5. Canteen refunds the 'Veg Thali' purchase
        card.refund("Veg Thali");

        // 6. Canteen attempts to refund the 'Veg Thali' purchase again
        card.refund("Veg Thali");

        // 7. Holder requests a mini-statement
        card.printMiniStatement();
    }
}
