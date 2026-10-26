package Week8;

import java.util.*;

public class Problem3 {

    // Capability interface guarding valid state and applying settings
    public interface Capability {
        String getCapabilityName();
        boolean applySetting(String deviceName, Object value);
    }

    // 1. PowerCapability (ON / OFF)
    public static class PowerCapability implements Capability {
        private boolean powerOn;

        public PowerCapability(boolean powerOn) {
            this.powerOn = powerOn;
        }

        @Override
        public String getCapabilityName() {
            return "Power";
        }

        @Override
        public boolean applySetting(String deviceName, Object value) {
            if (value instanceof Boolean) {
                this.powerOn = (Boolean) value;
                System.out.println(deviceName + ": " + (powerOn ? "ON." : "OFF."));
                return true;
            } else if (value instanceof String) {
                this.powerOn = ((String) value).equalsIgnoreCase("ON");
                System.out.println(deviceName + ": " + (powerOn ? "ON." : "OFF."));
                return true;
            }
            return false;
        }

        public boolean isPowerOn() {
            return powerOn;
        }
    }

    // 2. BrightnessCapability (0 - 100%)
    public static class BrightnessCapability implements Capability {
        private int brightness;

        public BrightnessCapability(int initialBrightness) {
            this.brightness = initialBrightness;
        }

        @Override
        public String getCapabilityName() {
            return "Brightness";
        }

        @Override
        public boolean applySetting(String deviceName, Object value) {
            int val = (Integer) value;
            if (val < 0 || val > 100) {
                System.out.println("Rejected: " + deviceName + " brightness must be between 0% and 100%.");
                return false;
            }
            this.brightness = val;
            System.out.println(deviceName + ": brightness set to " + val + "%.");
            return true;
        }

        public int getBrightness() {
            return brightness;
        }
    }

    // 3. TemperatureCapability (16 - 30°C)
    public static class TemperatureCapability implements Capability {
        private int temperature;

        public TemperatureCapability(int initialTemperature) {
            this.temperature = initialTemperature;
        }

        @Override
        public String getCapabilityName() {
            return "Temperature";
        }

        @Override
        public boolean applySetting(String deviceName, Object value) {
            int val = (Integer) value;
            if (val < 16 || val > 30) {
                System.out.println("Rejected: " + deviceName + " temperature must be between 16°C and 30°C.");
                return false;
            }
            this.temperature = val;
            System.out.println(deviceName + ": temperature set to " + val + "°C.");
            return true;
        }

        public int getTemperature() {
            return temperature;
        }
    }

    // Device class: composition over inheritance
    public static class Device {
        private String name;
        private Map<String, Capability> capabilities = new LinkedHashMap<>();

        public Device(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addCapability(Capability capability) {
            capabilities.put(capability.getCapabilityName(), capability);
        }

        public void addCapabilityWithNotice(Capability capability) {
            capabilities.put(capability.getCapabilityName(), capability);
            System.out.println(name + ": " + capability.getCapabilityName() + " capability added.");
        }

        public boolean hasCapability(String capabilityName) {
            return capabilities.containsKey(capabilityName);
        }

        public boolean execute(String capabilityName, Object value) {
            Capability cap = capabilities.get(capabilityName);
            if (cap != null) {
                return cap.applySetting(name, value);
            }
            return false;
        }
    }

    // Step in a scene targeting a specific capability
    public static class SceneStep {
        private String capabilityName;
        private Object targetValue;

        public SceneStep(String capabilityName, Object targetValue) {
            this.capabilityName = capabilityName;
            this.targetValue = targetValue;
        }

        public String getCapabilityName() {
            return capabilityName;
        }

        public Object getTargetValue() {
            return targetValue;
        }
    }

    // Scene model: ordered list of steps applied to matching devices
    public static class Scene {
        private String name;
        private List<SceneStep> steps = new ArrayList<>();

        public Scene(String name) {
            this.name = name;
        }

        public void addStep(String capabilityName, Object value) {
            steps.add(new SceneStep(capabilityName, value));
        }

        public void execute(List<Device> devices) {
            System.out.println("Scene '" + name + "' started.");
            int appliedCount = 0;

            for (SceneStep step : steps) {
                for (Device device : devices) {
                    if (device.hasCapability(step.getCapabilityName())) {
                        boolean applied = device.execute(step.getCapabilityName(), step.getTargetValue());
                        if (applied) {
                            appliedCount++;
                        }
                    }
                }
            }

            System.out.println("Scene '" + name + "' completed: " + appliedCount + " actions applied.");
        }
    }

    public static void main(String[] args) {
        // 1. Create devices with distinct combinations of capabilities
        Device labAC = new Device("Lab AC");
        labAC.addCapability(new PowerCapability(false));
        labAC.addCapability(new TemperatureCapability(20));

        Device ceilingLights = new Device("Ceiling Lights");
        ceilingLights.addCapability(new PowerCapability(false));
        ceilingLights.addCapability(new BrightnessCapability(100));

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability(false));

        List<Device> labDevices = Arrays.asList(labAC, ceilingLights, projector);

        // 2. Run scene 'Lecture Mode': turn everything ON, brightness 40%, temp 24°C
        Scene lectureMode = new Scene("Lecture Mode");
        lectureMode.addStep("Power", "ON");
        lectureMode.addStep("Brightness", 40);
        lectureMode.addStep("Temperature", 24);

        lectureMode.execute(labDevices);

        // 3. Admin sets temperature of 'Lab AC' to 12°C (invalid)
        labAC.execute("Temperature", 12);

        // 4. Admin adds Brightness to 'Projector' at runtime and sets brightness to 70%
        projector.addCapabilityWithNotice(new BrightnessCapability(0));
        projector.execute("Brightness", 70);
    }
}
