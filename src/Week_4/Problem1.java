package Week4;

public class Problem1 {

    static class FoodOrder {

        String studentName;
        String dishName;
        boolean delivered;

        public FoodOrder(String studentName, String dishName) {
            if (studentName == null || studentName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid student name");
            }

            if (dishName == null || dishName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid dish name");
            }

            this.studentName = studentName;
            this.dishName = dishName;
            this.delivered = false;
        }

        void markDelivered() {
            if (!delivered) {
                delivered = true;
                System.out.println("Order marked as delivered");
            } else {
                System.out.println("Order was already delivered");
            }
        }
    }

    static void processBatch(String[][] rawOrders) {

        int valid = 0;
        int rejected = 0;

        for (int i = 0; i < rawOrders.length; i++) {

            try {
                new FoodOrder(rawOrders[i][0], rawOrders[i][1]);
                valid++;
            } catch (Exception e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {

        String[][] orders = {
                {"Ravi", "Paneer Butter Masala"},
                {"", "Chole Bhature"},
                {"Meera", " "},
                {"Divya", "Veg Biryani"}
        };

        processBatch(orders);

        FoodOrder order = new FoodOrder("Ravi", "Pizza");

        order.markDelivered();
        order.markDelivered();
    }
}