package Week7;

/**
 * Practice Problem 1: Checkout Payment Handler
 *
 * TASK:
 * 1. PaymentMethod abstract class with final transactionId from static counter.
 * 2. Abstract processPayment(double amount).
 * 3. Overloaded processPayment(double amount, String note).
 * 4. CreditCardPayment and CashPayment subclasses.
 * 5. Upcasting and polymorphic execution in printConfirmation.
 */
public class PracticeProblem1 {

    public static abstract class PaymentMethod {
        private static int counter = 1000;
        private final String transactionId;

        public PaymentMethod() {
            this.transactionId = "TXN-" + (++counter);
        }

        public abstract String processPayment(double amount);

        public String processPayment(double amount, String note) {
            return processPayment(amount) + " (" + note + ")";
        }

        public String getTransactionId() {
            return transactionId;
        }
    }

    public static class CreditCardPayment extends PaymentMethod {
        private final String cardNumberLastFour;

        public CreditCardPayment(String cardNumberLastFour) {
            super();
            this.cardNumberLastFour = cardNumberLastFour;
        }

        @Override
        public String processPayment(double amount) {
            return "Charged $" + amount + " to card ending " + cardNumberLastFour + " - Txn " + getTransactionId();
        }
    }

    public static class CashPayment extends PaymentMethod {
        public CashPayment() {
            super();
        }

        @Override
        public String processPayment(double amount) {
            return "Received $" + amount + " in cash - Txn " + getTransactionId();
        }
    }

    public static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    public static void main(String[] args) {
        CreditCardPayment cc = new CreditCardPayment("4471");
        System.out.println(cc.processPayment(250.0));

        CashPayment cash = new CashPayment();
        System.out.println(cash.processPayment(40.0));

        System.out.println(cc.processPayment(250.0, "Birthday gift"));

        // Upcasting: CreditCardPayment stored in PaymentMethod reference
        PaymentMethod ref = cc;
        printConfirmation(ref, 250.0);
    }
}
