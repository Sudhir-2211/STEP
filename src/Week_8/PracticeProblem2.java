package Week8;

import java.util.HashMap;
import java.util.Map;

/**
 * Practice Problem 2: Vehicle Rental System with Dynamic Pricing
 *
 * TASK:
 * 1. Vehicle hierarchy: abstract Vehicle with subclasses StandardCar, LuxuryCar, SUV.
 * 2. Polymorphic rental charge calculation based on duration.
 * 3. RentalService manages availability, prevents double-booking, and handles returns.
 */
public class PracticeProblem2 {

    public static abstract class Vehicle {
        private final String id;
        private final String model;
        private boolean available;

        public Vehicle(String id, String model) {
            this.id = id;
            this.model = model;
            this.available = true;
        }

        public abstract double calculateRentalFee(int days);

        public String getId() { return id; }
        public String getModel() { return model; }
        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }
    }

    public static class StandardCar extends Vehicle {
        public StandardCar(String id, String model) {
            super(id, model);
        }

        @Override
        public double calculateRentalFee(int days) {
            return days * 50.0;
        }
    }

    public static class LuxuryCar extends Vehicle {
        public LuxuryCar(String id, String model) {
            super(id, model);
        }

        @Override
        public double calculateRentalFee(int days) {
            return days * 100.0;
        }
    }

    public static class RentalService {
        private final Map<String, Vehicle> fleet = new HashMap<>();

        public void addVehicle(Vehicle v) {
            fleet.put(v.getId(), v);
        }

        public void rentVehicle(String vehicleId, int days) {
            Vehicle v = fleet.get(vehicleId);
            if (v == null) {
                System.out.println("Vehicle not found.");
                return;
            }
            if (!v.isAvailable()) {
                System.out.println("Cannot rent " + v.getModel() + ": already rented.");
                return;
            }

            v.setAvailable(false);
            double charge = v.calculateRentalFee(days);
            System.out.printf("%s rented for %d days. Total charge: $%.2f.%n", v.getModel(), days, charge);
        }

        public void returnVehicle(String vehicleId) {
            Vehicle v = fleet.get(vehicleId);
            if (v != null && !v.isAvailable()) {
                v.setAvailable(true);
                System.out.println(v.getModel() + " returned. Now available.");
            }
        }
    }

    public static void main(String[] args) {
        RentalService service = new RentalService();
        service.addVehicle(new LuxuryCar("V01", "Luxury Car A"));
        service.addVehicle(new StandardCar("V02", "Standard Car B"));

        service.rentVehicle("V01", 3);
        service.rentVehicle("V02", 5);
        service.returnVehicle("V01");
    }
}
