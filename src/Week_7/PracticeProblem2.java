package Week7;

/**
 * Practice Problem 2: Home Safety Alert Network
 *
 * TASK:
 * 1. Interface Alertable: String sendAlert(String message).
 * 2. SecuritySensor base class: zoneName, getZoneName().
 * 3. MotionSensor extends SecuritySensor implements Alertable.
 * 4. DualZoneMotionSensor extends MotionSensor (multilevel) adding zone2 and calling super.sendAlert(message).
 * 5. SmokeDetector implements Alertable (unrelated class hierarchy).
 * 6. getZoneIfMotionSensor(Alertable a) using instanceof.
 */
public class PracticeProblem2 {

    public interface Alertable {
        String sendAlert(String message);
    }

    public static class SecuritySensor {
        protected final String zoneName;

        public SecuritySensor(String zoneName) {
            this.zoneName = zoneName;
        }

        public String getZoneName() {
            return zoneName;
        }
    }

    public static class MotionSensor extends SecuritySensor implements Alertable {
        public MotionSensor(String zoneName) {
            super(zoneName);
        }

        @Override
        public String sendAlert(String message) {
            return "Motion alert in " + zoneName + ": " + message;
        }
    }

    public static class DualZoneMotionSensor extends MotionSensor {
        private final String zone2;

        public DualZoneMotionSensor(String zone1, String zone2) {
            super(zone1);
            this.zone2 = zone2;
        }

        @Override
        public String sendAlert(String message) {
            return super.sendAlert(message) + " [and Zone 2: " + zone2 + "]";
        }
    }

    public static class SmokeDetector implements Alertable {
        private final String location;

        public SmokeDetector(String location) {
            this.location = location;
        }

        @Override
        public String sendAlert(String message) {
            return "Smoke alert at " + location + ": " + message;
        }
    }

    public static String getZoneIfMotionSensor(Alertable a) {
        if (a instanceof MotionSensor) {
            return ((MotionSensor) a).getZoneName();
        }
        return "Not a motion sensor";
    }

    public static void main(String[] args) {
        MotionSensor m = new MotionSensor("Living Room");
        SmokeDetector s = new SmokeDetector("Kitchen");
        DualZoneMotionSensor d = new DualZoneMotionSensor("Front Porch", "Driveway");

        System.out.println(m.sendAlert("Movement detected"));
        System.out.println(s.sendAlert("Smoke detected"));
        System.out.println(d.sendAlert("Perimeter breached"));

        System.out.println("getZoneIfMotionSensor(m) -> " + getZoneIfMotionSensor(m));
        System.out.println("getZoneIfMotionSensor(s) -> " + getZoneIfMotionSensor(s));
    }
}
