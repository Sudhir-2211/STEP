package Week5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Practice Problem 3: Vitals Monitoring Encapsulation Guard
 *
 * TASK:
 * 1. All fields private.
 * 2. recordReading(...) silently rejects readings <= 0 or > 45.
 * 3. Constructor PatientVitals(double[] initialReadings) reuses recordReading.
 * 4. getAllReadings() returns a defensive copy.
 */
public class PracticeProblem3 {

    public static class PatientVitals {
        private final List<Double> readings;

        public PatientVitals(double[] initialReadings) {
            this.readings = new ArrayList<>();
            if (initialReadings != null) {
                for (double r : initialReadings) {
                    recordReading(r);
                }
            }
        }

        public void recordReading(double reading) {
            if (reading <= 0.0 || reading > 45.0) {
                return; // Silently reject invalid vitals
            }
            readings.add(reading);
        }

        public double getAverage() {
            if (readings.isEmpty()) return 0.0;
            double sum = 0;
            for (double r : readings) {
                sum += r;
            }
            return sum / readings.size();
        }

        public double[] getAllReadings() {
            double[] copy = new double[readings.size()];
            for (int i = 0; i < readings.size(); i++) {
                copy[i] = readings.get(i);
            }
            return copy;
        }
    }

    public static void main(String[] args) {
        PatientVitals v = new PatientVitals(new double[]{36.5, -2, 37.1});
        System.out.println("v.getAllReadings(): " + Arrays.toString(v.getAllReadings()));

        double[] copy = v.getAllReadings();
        copy[0] = 999;
        System.out.println("After mutating copy, v.getAllReadings()[0]: " + v.getAllReadings()[0]);
    }
}
