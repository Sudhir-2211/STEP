package Week7;

public class Problem5 {

    // Abstract base class representing household devices
    public abstract static class HomeDevice {
        private static int deviceCounter = 1000;
        private final String serialNumber;

        public HomeDevice() {
            deviceCounter++;
            this.serialNumber = "HD-" + deviceCounter;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public abstract String activate();
    }

    // Separate interfaces for distinct device capabilities
    public interface RemoteControllable {
        String connect(String appId);
    }

    public interface EnergyTrackable {
        double getConsumptionWatts();
    }

    // WashingMachine: extends HomeDevice and implements BOTH interfaces
    public static class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
        private double consumptionWatts;

        public WashingMachine(double consumptionWatts) {
            super();
            if (consumptionWatts <= 0) {
                throw new IllegalArgumentException("consumptionWatts must be positive.");
            }
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        public String activate() {
            return "Washing machine " + getSerialNumber() + " started a cycle";
        }

        @Override
        public String connect(String appId) {
            return getSerialNumber() + " connected to " + appId;
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    // Refrigerator: sibling of WashingMachine, extends HomeDevice but implements ONLY EnergyTrackable
    public static class Refrigerator extends HomeDevice implements EnergyTrackable {
        private double consumptionWatts;

        public Refrigerator(double consumptionWatts) {
            super();
            if (consumptionWatts <= 0) {
                throw new IllegalArgumentException("consumptionWatts must be positive.");
            }
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        public String activate() {
            return "Refrigerator " + getSerialNumber() + " cooling";
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    // MobileApp: not a household device, but implements RemoteControllable
    public static class MobileApp implements RemoteControllable {
        private String appName;

        public MobileApp(String appName) {
            if (appName == null || appName.trim().isEmpty()) {
                throw new IllegalArgumentException("appName cannot be blank.");
            }
            this.appName = appName;
        }

        @Override
        public String connect(String appId) {
            return appName + " connected to " + appId;
        }
    }

    // Polymorphic connection of any RemoteControllable entity
    public static void connectAll(RemoteControllable[] items, String appId) {
        if (items != null) {
            for (RemoteControllable item : items) {
                if (item != null) {
                    System.out.println(item.connect(appId));
                }
            }
        }
    }

    // Reads energy consumption safely via instanceof check
    public static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            return ((EnergyTrackable) d).getConsumptionWatts();
        }
        return 0.0;
    }

    public static void main(String[] args) {
        System.out.println("--- WashingMachine Tests ---");
        WashingMachine wm = new WashingMachine(500.0);
        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));

        System.out.println("\n--- Refrigerator Energy Query ---");
        Refrigerator fridge = new Refrigerator(150.0);
        System.out.println(getConsumptionIfTrackable(fridge));

        System.out.println("\n--- MobileApp Connection ---");
        MobileApp app = new MobileApp("HomeConnect App");
        System.out.println(app.connect("HomeConnect"));

        System.out.println("\n--- Upcasting & Energy Tracking ---");
        HomeDevice ref = wm; // upcasting: WashingMachine stored as parent type
        System.out.println(getConsumptionIfTrackable(ref));
    }
}
