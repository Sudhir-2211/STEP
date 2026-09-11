package Week1;

import java.util.Scanner;

public class Problem3 {

    static void findLongestStreak(String signalLog) {
        if (signalLog.length() == 0) {
            System.out.println("Empty Signal Log");
            return;
        }

        int current = 1;
        int longest = 1;
        char color = signalLog.charAt(0);

        for (int i = 1; i < signalLog.length(); i++) {
            if (signalLog.charAt(i) == signalLog.charAt(i - 1)) {
                current++;
            } else {
                current = 1;
            }

            if (current > longest) {
                longest = current;
                color = signalLog.charAt(i);
            }
        }

        System.out.println("Longest Streak: '" + color
                + "' repeated " + longest + " times");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter signal log: ");
        String signalLog = sc.nextLine();

        findLongestStreak(signalLog);
    }
}