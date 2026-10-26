package Week8;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Problem2 {

    // Status enum strictly defining allowed progression order
    public enum ParcelStatus {
        BOOKED(0),
        PICKED_UP(1),
        IN_TRANSIT(2),
        OUT_FOR_DELIVERY(3),
        DELIVERED(4),
        CANCELLED(-1);

        private final int step;

        ParcelStatus(int step) {
            this.step = step;
        }

        public int getStep() {
            return step;
        }
    }

    // Shipping type abstraction
    public interface ShippingType {
        String getName();
        double calculateCharge(double weightKg);
    }

    public static class StandardShipping implements ShippingType {
        @Override
        public String getName() {
            return "Standard";
        }

        @Override
        public double calculateCharge(double weightKg) {
            return 40.0 + (10.0 * weightKg);
        }
    }

    public static class ExpressShipping implements ShippingType {
        @Override
        public String getName() {
            return "Express";
        }

        @Override
        public double calculateCharge(double weightKg) {
            return 80.0 + (15.0 * weightKg);
        }
    }

    // Fragile reuses Standard rule via composition
    public static class FragileShipping implements ShippingType {
        private StandardShipping standard = new StandardShipping();

        @Override
        public String getName() {
            return "Fragile";
        }

        @Override
        public double calculateCharge(double weightKg) {
            return standard.calculateCharge(weightKg) + 50.0;
        }
    }

    // Notification channel abstraction
    public interface NotificationChannel {
        void notify(String parcelId, ParcelStatus status);
    }

    public static class SmsChannel implements NotificationChannel {
        @Override
        public void notify(String parcelId, ParcelStatus status) {
            System.out.println("[SMS] " + parcelId + " is now " + status + ".");
        }
    }

    public static class EmailChannel implements NotificationChannel {
        @Override
        public void notify(String parcelId, ParcelStatus status) {
            System.out.println("[Email] " + parcelId + " is now " + status + ".");
        }
    }

    // Parcel maintains internal state and strictly guards transitions
    public static class Parcel {
        private String id;
        private double weightKg;
        private ShippingType shippingType;
        private ParcelStatus status;
        private List<NotificationChannel> channels = new ArrayList<>();

        public Parcel(String id, ShippingType shippingType, double weightKg) {
            this.id = id;
            this.shippingType = shippingType;
            this.weightKg = weightKg;
            this.status = ParcelStatus.BOOKED;
        }

        public String getId() {
            return id;
        }

        public double getWeightKg() {
            return weightKg;
        }

        public ShippingType getShippingType() {
            return shippingType;
        }

        public ParcelStatus getStatus() {
            return status;
        }

        public void subscribeChannel(NotificationChannel channel) {
            channels.add(channel);
        }

        public double getCharge() {
            return shippingType.calculateCharge(weightKg);
        }

        public void notifySubscribers() {
            for (NotificationChannel ch : channels) {
                ch.notify(id, status);
            }
        }

        public boolean updateStatus(ParcelStatus newStatus) {
            // Strict sequence enforcement: newStatus must be exactly currentStep + 1
            if (newStatus.getStep() == this.status.getStep() + 1) {
                this.status = newStatus;
                notifySubscribers();
                return true;
            } else {
                System.out.println("Invalid transition: " + this.status + " -> " + newStatus + " is not allowed.");
                return false;
            }
        }

        public boolean cancel() {
            if (this.status == ParcelStatus.BOOKED) {
                this.status = ParcelStatus.CANCELLED;
                System.out.println("Parcel " + id + " has been cancelled.");
                notifySubscribers();
                return true;
            } else {
                System.out.println("Cancellation failed: " + id + " can be cancelled only while BOOKED.");
                return false;
            }
        }
    }

    // Service class for booking and tracking
    public static class ParcelService {
        public Parcel bookParcel(String id, ShippingType type, double weightKg, List<NotificationChannel> channels) {
            Parcel parcel = new Parcel(id, type, weightKg);
            if (channels != null) {
                for (NotificationChannel ch : channels) {
                    parcel.subscribeChannel(ch);
                }
            }

            System.out.printf(Locale.US, "Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f.\n",
                    id, type.getName(), weightKg, parcel.getCharge());

            // Notify channels of initial BOOKED state
            parcel.notifySubscribers();
            return parcel;
        }
    }

    public static void main(String[] args) {
        ParcelService service = new ParcelService();

        // 1. Customer books Express parcel 'P101' weighing 2 kg and subscribes to SMS and Email updates
        List<NotificationChannel> channels = new ArrayList<>();
        channels.add(new SmsChannel());
        channels.add(new EmailChannel());

        Parcel p101 = service.bookParcel("P101", new ExpressShipping(), 2.0, channels);

        // 2. Courier marks 'P101' as picked up
        p101.updateStatus(ParcelStatus.PICKED_UP);

        // 3. Customer attempts to cancel 'P101'
        p101.cancel();

        // 4. Courier marks 'P101' as in transit
        p101.updateStatus(ParcelStatus.IN_TRANSIT);

        // 5. Courier attempts to mark 'P101' as delivered (skipping OUT_FOR_DELIVERY)
        p101.updateStatus(ParcelStatus.DELIVERED);
    }
}
