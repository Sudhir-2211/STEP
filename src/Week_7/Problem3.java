package Week7;

public class Problem3 {

    // Abstract base class representing any serviceable machine
    public abstract static class ServiceableVehicle {
        private String assetTag;
        private double mileage;

        public ServiceableVehicle(String assetTag) {
            this.assetTag = assetTag;
            this.mileage = 0.0;
        }

        public String getAssetTag() {
            return assetTag;
        }

        public double getMileage() {
            return mileage;
        }

        // Must reject negative distance before altering internal state
        public void addMileage(double km) {
            if (km < 0) {
                throw new IllegalArgumentException("Distance cannot be negative: " + km);
            }
            this.mileage += km;
        }

        public abstract String performMaintenance();
    }

    // Separate concern interface for insurance
    public interface Insurable {
        String getInsuranceInfo();
    }

    // Forklift extends ServiceableVehicle and implements Insurable
    public static class Forklift extends ServiceableVehicle implements Insurable {

        public Forklift(String assetTag) {
            super(assetTag);
        }

        @Override
        public String performMaintenance() {
            return "Forklift " + getAssetTag() + ": hydraulic and fork inspection complete";
        }

        @Override
        public String getInsuranceInfo() {
            return "Insured under fleet policy - Asset " + getAssetTag();
        }
    }

    // HeavyDutyForklift extends Forklift (multilevel hierarchy)
    public static class HeavyDutyForklift extends Forklift {

        public HeavyDutyForklift(String assetTag) {
            super(assetTag);
        }

        @Override
        public String performMaintenance() {
            // Reuses parent's maintenance message via super and appends extra check
            return super.performMaintenance() + " | high-pressure hydraulic check complete";
        }
    }

    // Accepts ServiceableVehicle, safely checks with instanceof whether it implements Insurable
    public static String getInsuranceIfApplicable(ServiceableVehicle v) {
        if (v instanceof Insurable) {
            return ((Insurable) v).getInsuranceInfo();
        }
        return "No insurance record exists";
    }

    public static void main(String[] args) {
        System.out.println("--- Forklift Mileage & Maintenance ---");
        Forklift f = new Forklift("FL-22");
        f.addMileage(120);
        System.out.println("Mileage: " + f.getMileage());
        System.out.println(f.performMaintenance());

        System.out.println("\n--- HeavyDutyForklift Maintenance ---");
        HeavyDutyForklift hd = new HeavyDutyForklift("HD-9");
        System.out.println(hd.performMaintenance());

        System.out.println("\n--- Insurance Query via instanceof Check ---");
        System.out.println(getInsuranceIfApplicable(f));
    }
}
