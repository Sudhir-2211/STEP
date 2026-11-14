package Week1;

/**
 * Practice Problem 3: BMI Calculator for a Team
 *
 * TASK:
 * Given height (in meters) and weight (in kg) for a team of people:
 * - Compute BMI = weight / (height * height).
 * - Classify health status:
 *   BMI < 18.5  -> Underweight
 *   18.5 - 24.9 -> Normal
 *   25 - 29.9   -> Overweight
 *   >= 30       -> Obese
 * - Print a tabular wellness report.
 */
public class PracticeProblem3 {

    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi <= 24.9) {
            return "Normal";
        } else if (bmi <= 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public static void printWellnessReport(double[] heights, double[] weights) {
        System.out.printf("%-10s | %-12s | %-12s | %-8s | %-12s%n", "Person", "Height (m)", "Weight (kg)", "BMI", "Status");
        System.out.println("---------------------------------------------------------------");

        for (int i = 0; i < heights.length; i++) {
            double h = heights[i];
            double w = weights[i];
            double bmi = w / (h * h);
            String status = getBmiStatus(bmi);

            System.out.printf("Person %-3d | %-12.2f | %-12.1f | %-8.2f | %-12s%n",
                    (i + 1), h, w, bmi, status);
        }
    }

    public static void main(String[] args) {
        double[] heights = {1.75, 1.60, 1.80, 1.65, 1.70};
        double[] weights = {70.0, 90.0, 65.0, 80.0, 50.0};

        printWellnessReport(heights, weights);
    }
}
