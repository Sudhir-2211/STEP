package Week4;

public class Problem3 {

    static class Canteen {

        String canteenCode;
        String canteenName;
        int trustScore;

        public Canteen(String canteenCode, String canteenName, int trustScore) {
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        public Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, 3);
        }

        int compareTo(Canteen other) {

            if (this.trustScore != other.trustScore) {
                return other.trustScore - this.trustScore;
            }

            int codeResult =
                    this.canteenCode.compareToIgnoreCase(other.canteenCode);

            if (codeResult != 0) {
                return codeResult;
            }

            return this.canteenName.length() - other.canteenName.length();
        }
    }

    static Canteen[] rankCanteens(Canteen[] canteens) {

        Canteen[] result = canteens.clone();

        for (int i = 0; i < result.length - 1; i++) {

            for (int j = i + 1; j < result.length; j++) {

                if (result[i].compareTo(result[j]) > 0) {
                    Canteen temp = result[i];
                    result[i] = result[j];
                    result[j] = temp;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {

        Canteen[] canteens = {
                new Canteen("HB3-C", "Spice Junction", 3),
                new Canteen("hb1-c", "Grand Mess", 5),
                new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = rankCanteens(canteens);

        for (int i = 0; i < ranked.length; i++) {
            System.out.println(ranked[i].canteenCode);
        }
    }
}