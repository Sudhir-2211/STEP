package Week7;

/**
 * Practice Problem 5: Community Library Checkout System
 *
 * TASK:
 * 1. LibraryItem abstract class with final itemId from static counter, abstract getLoanPeriodDays().
 * 2. Interfaces: Renewable (renew()), Reservable (reserve()).
 * 3. Textbook extends LibraryItem implements Renewable, Reservable.
 * 4. Magazine extends LibraryItem implements Renewable (not Reservable).
 * 5. DigitalPass implements Renewable (unrelated class hierarchy).
 * 6. processCheckouts(LibraryItem[] items) processes items polymorphically.
 * 7. reserveIfSupported(Object o) checks instanceof Reservable on any Object.
 */
public class PracticeProblem5 {

    public interface Renewable {
        String renew();
    }

    public interface Reservable {
        String reserve();
    }

    public static abstract class LibraryItem {
        private static int counter = 1000;
        private final String itemId;

        public LibraryItem() {
            this.itemId = "ITEM-" + (++counter);
        }

        public abstract int getLoanPeriodDays();

        public String getItemId() {
            return itemId;
        }
    }

    public static class Textbook extends LibraryItem implements Renewable, Reservable {
        private final String title;

        public Textbook(String title) {
            super();
            this.title = title;
        }

        @Override
        public int getLoanPeriodDays() {
            return 14;
        }

        @Override
        public String renew() {
            return title + " renewed";
        }

        @Override
        public String reserve() {
            return title + " reserved";
        }

        public String getTitle() {
            return title;
        }
    }

    public static class Magazine extends LibraryItem implements Renewable {
        private final String title;

        public Magazine(String title) {
            super();
            this.title = title;
        }

        @Override
        public int getLoanPeriodDays() {
            return 7;
        }

        @Override
        public String renew() {
            return title + " renewed";
        }

        public String getTitle() {
            return title;
        }
    }

    public static class DigitalPass implements Renewable {
        private final String resourceName;

        public DigitalPass(String resourceName) {
            this.resourceName = resourceName;
        }

        @Override
        public String renew() {
            return resourceName + " pass renewed";
        }
    }

    public static void processCheckouts(LibraryItem[] items) {
        if (items == null) return;
        for (LibraryItem item : items) {
            if (item != null) {
                System.out.println("Item " + item.getItemId() + " | Loan Period: " + item.getLoanPeriodDays() + " days");
            }
        }
    }

    public static String reserveIfSupported(Object o) {
        if (o instanceof Reservable) {
            return ((Reservable) o).reserve();
        }
        return "Reservation not supported";
    }

    public static void main(String[] args) {
        Textbook t = new Textbook("Java Fundamentals");
        System.out.println("Loan period: " + t.getLoanPeriodDays());
        System.out.println(t.renew());
        System.out.println(t.reserve());

        Magazine m = new Magazine("Tech Monthly");
        System.out.println(reserveIfSupported(m));

        DigitalPass d = new DigitalPass("E-Journal Access");
        System.out.println(reserveIfSupported(d));

        LibraryItem ref = t; // upcasting
        System.out.println(reserveIfSupported(ref));
    }
}
