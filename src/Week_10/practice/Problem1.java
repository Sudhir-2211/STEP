package Week10.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Week 10 Practice - Problem 1: Smart Home Platform
 *
 * Task:
 * Create a smart home system to manage various devices.
 * - All devices have an ID and an on/off state.
 * - Some devices have additional capabilities:
 *     - dimming (for lights)
 *     - scheduling (for lights and fans)
 *     - energy monitoring (for plugs)
 * - Implement a system that can process commands for these devices.
 * - If a command is given for a capability that a specific device does not support,
 *   the system should reject the command.
 *
 * Capabilities modeled via Interfaces (CAN-DO relationship):
 *   - Dimmable: dim(int level)
 *   - Schedulable: schedule(String time)
 *   - EnergyMonitorable: getEnergy()
 *
 * Devices modeled via Class Hierarchy (IS-A relationship):
 *   - Device (abstract base class)
 *   - Light extends Device implements Dimmable, Schedulable
 *   - Fan extends Device implements Schedulable
 *   - SmartPlug extends Device implements EnergyMonitorable
 */
public class Problem1 {

    // --- Capability Interfaces ---
    public interface Dimmable {
        String dim(int level);
    }

    public interface Schedulable {
        String schedule(String time);
    }

    public interface EnergyMonitorable {
        String getEnergyReading();
    }

    // --- Base Device Class ---
    public static abstract class Device {
        protected final String id;
        protected boolean isOn;

        public Device(String id) {
            this.id = id;
            this.isOn = false;
        }

        public String getId() {
            return id;
        }

        public boolean isOn() {
            return isOn;
        }

        public String turnOn() {
            this.isOn = true;
            return id + " is ON";
        }

        public String turnOff() {
            this.isOn = false;
            return id + " is OFF";
        }
    }

    // --- Concrete Device Implementations ---
    public static class Light extends Device implements Dimmable, Schedulable {
        private int brightness;
        private String scheduledTime;

        public Light(String id) {
            super(id);
            this.brightness = 100;
        }

        @Override
        public String dim(int level) {
            this.brightness = level;
            return id + " dimmed to " + level;
        }

        @Override
        public String schedule(String time) {
            this.scheduledTime = time;
            return id + " scheduled " + time;
        }
    }

    public static class Fan extends Device implements Schedulable {
        private String scheduledTime;

        public Fan(String id) {
            super(id);
        }

        @Override
        public String schedule(String time) {
            this.scheduledTime = time;
            return id + " scheduled " + time;
        }
    }

    public static class SmartPlug extends Device implements EnergyMonitorable {
        private final int energyKwh;

        public SmartPlug(String id, int energyKwh) {
            super(id);
            this.energyKwh = energyKwh;
        }

        @Override
        public String getEnergyReading() {
            return id + " energy " + energyKwh + " kWh";
        }
    }

    // --- Smart Home Platform Controller ---
    public static class SmartHomePlatform {
        private final Map<String, Device> devices = new HashMap<>();

        public void registerDevice(Device device) {
            devices.put(device.getId(), device);
        }

        public String processCommand(String commandLine) {
            if (commandLine == null || commandLine.trim().isEmpty()) {
                return "Empty command";
            }

            String[] parts = commandLine.trim().split("\\s+");
            String action = parts[0].toUpperCase();

            switch (action) {
                case "ON": {
                    if (parts.length < 2) return "Invalid command syntax";
                    String id = parts[1];
                    Device dev = devices.get(id);
                    if (dev == null) return id + " rejected: device not found";
                    return dev.turnOn();
                }
                case "OFF": {
                    if (parts.length < 2) return "Invalid command syntax";
                    String id = parts[1];
                    Device dev = devices.get(id);
                    if (dev == null) return id + " rejected: device not found";
                    return dev.turnOff();
                }
                case "DIM": {
                    if (parts.length < 3) return "Invalid command syntax";
                    String id = parts[1];
                    int level = Integer.parseInt(parts[2]);
                    Device dev = devices.get(id);
                    if (dev == null) return id + " rejected: device not found";
                    if (dev instanceof Dimmable) {
                        return ((Dimmable) dev).dim(level);
                    } else {
                        return id + " rejected: DIM unsupported";
                    }
                }
                case "SCHEDULE": {
                    if (parts.length < 3) return "Invalid command syntax";
                    String id = parts[1];
                    String time = parts[2];
                    Device dev = devices.get(id);
                    if (dev == null) return id + " rejected: device not found";
                    if (dev instanceof Schedulable) {
                        return ((Schedulable) dev).schedule(time);
                    } else {
                        return id + " rejected: SCHEDULE unsupported";
                    }
                }
                case "ENERGY": {
                    if (parts.length < 2) return "Invalid command syntax";
                    String id = parts[1];
                    Device dev = devices.get(id);
                    if (dev == null) return id + " rejected: device not found";
                    if (dev instanceof EnergyMonitorable) {
                        return ((EnergyMonitorable) dev).getEnergyReading();
                    } else {
                        return id + " rejected: ENERGY unsupported";
                    }
                }
                default:
                    return "Unknown command: " + action;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Week 10 Practice - Problem 1: Smart Home Platform ===");

        SmartHomePlatform platform = new SmartHomePlatform();

        // Register sample devices
        platform.registerDevice(new Light("L1"));
        platform.registerDevice(new Fan("F1"));
        platform.registerDevice(new SmartPlug("P1", 12));

        String[] commands = {
            "ON L1",
            "DIM L1 40",
            "DIM F1 30",
            "SCHEDULE F1 22:00",
            "ENERGY P1",
            "ENERGY L1"
        };

        System.out.println("Executing Commands:");
        for (String cmd : commands) {
            String result = platform.processCommand(cmd);
            System.out.println(result);
        }

        System.out.println("\nExpected Output:");
        System.out.println("L1 is ON");
        System.out.println("L1 dimmed to 40");
        System.out.println("F1 rejected: DIM unsupported");
        System.out.println("F1 scheduled 22:00");
        System.out.println("P1 energy 12 kWh");
        System.out.println("L1 rejected: ENERGY unsupported");
    }
}
