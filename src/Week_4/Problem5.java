package Week4;

public class Problem5 {

    static class DeliveryAccount {

        String studentId;
        double orderValue;

        static String companyName;

        static {
            companyName = "Campus Delivery";
        }

        public DeliveryAccount(String studentId, double orderValue) {
            this.studentId = studentId;
            this.orderValue = orderValue;
        }

        public DeliveryAccount(String studentId) {
            this(studentId, 0);
        }

        public final double calculateSurgeFee(int delayMinutes) {

            if (delayMinutes < 0 || orderValue < 0) {
                throw new IllegalArgumentException("Invalid input");
            }

            if (delayMinutes == 0) {
                return 0;
            }

            double fee = 0;

            int first = Math.min(delayMinutes, 5);
            fee += first * orderValue * 0.005;

            if (delayMinutes > 5) {
                int second = Math.min(delayMinutes - 5, 10);
                fee += second * orderValue * 0.01;
            }

            if (delayMinutes > 15) {
                int third = delayMinutes - 15;
                fee += third * orderValue * 0.02;
            }

            return fee;
        }
    }

    static class PremiumAccount extends DeliveryAccount {

        PremiumAccount(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        double premiumSurgeFee(int delayMinutes) {
            return calculateSurgeFee(delayMinutes) * 0.8;
        }
    }

    static void processAccount(DeliveryAccount account,
                               double amount,
                               int delayMinutes) {

        if (account instanceof PremiumAccount) {

            PremiumAccount premium =
                    (PremiumAccount) account;

            System.out.println(account.studentId
                    + " Premium | Surge Fee: Rs "
                    + premium.premiumSurgeFee(delayMinutes));

        } else {

            System.out.println(account.studentId
                    + " Regular | Surge Fee: Rs "
                    + account.calculateSurgeFee(delayMinutes));
        }
    }

    static void processBatch(DeliveryAccount[] accounts,
                             double[] amounts,
                             int[] delayMinutesArray) {

        if (accounts.length != amounts.length
                || accounts.length != delayMinutesArray.length) {

            System.out.println("Invalid batch: array lengths do not match");
            return;
        }

        int processed = 0;
        int skipped = 0;
        int premium = 0;
        int regular = 0;
        double total = 0;

        for (int i = 0; i < accounts.length; i++) {

            if (accounts[i] == null) {
                skipped++;
                continue;
            }

            double fee;

            if (accounts[i] instanceof PremiumAccount) {

                PremiumAccount account =
                        (PremiumAccount) accounts[i];

                fee = account.premiumSurgeFee(delayMinutesArray[i]);
                premium++;

            } else {

                fee = accounts[i].calculateSurgeFee(
                        delayMinutesArray[i]);

                regular++;
            }

            processAccount(
                    accounts[i],
                    amounts[i],
                    delayMinutesArray[i]
            );

            total += fee;
            processed++;
        }

        System.out.println();
        System.out.println(processed + " processed | "
                + skipped + " null skipped | "
                + premium + " premium | "
                + regular + " regular");

        System.out.println("Grand total surge fees = Rs " + total);
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {
                new PremiumAccount("STU001", 500),
                null,
                new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {
                500, 400, 300
        };

        int[] delayMinutes = {
                10, 5, 0
        };

        processBatch(accounts, amounts, delayMinutes);
    }
}