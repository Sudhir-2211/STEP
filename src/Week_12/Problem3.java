package Week12;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Problem 3: Round-Robin Lab Scheduler
 *
 * TASK:
 * One lab machine is shared by several jobs. Each job may run for at most one quantum
 * at a time; an unfinished job goes to the back of the line, and a finished job leaves.
 * Implement a LabScheduler that uses a queue of jobs, each storing its remaining time.
 * Print every time slice as Job(start-end) and then each job's finishing time in the order
 * the jobs finish.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Let N be the number of jobs, Q be the time quantum, and T be the sum of execution times of all jobs.
 *    - Each scheduling slice advances time by up to Q units. The total number of scheduling slices
 *      is at most ceil(T / Q) + N.
 *    - Each queue dequeue, time computation, and enqueue operation takes O(1) time.
 *    - Total Time Complexity: O(T / Q + N).
 *
 * 2. Space Complexity:
 *    - The queue holds at most N Job instances at any given time.
 *    - Total Auxiliary Space Complexity: O(N).
 */
public class Problem3 {

    public static class Job {
        public final String name;
        public int remainingTime;

        public Job(String name, int totalTime) {
            this.name = name;
            this.remainingTime = totalTime;
        }
    }

    public static class FinishedJob {
        public final String name;
        public final int finishTime;

        public FinishedJob(String name, int finishTime) {
            this.name = name;
            this.finishTime = finishTime;
        }
    }

    public static class LabScheduler {
        private final int quantum;
        private final Queue<Job> queue;
        private final List<String> timeSlices;
        private final List<FinishedJob> finishedJobs;

        public LabScheduler(int quantum) {
            this.quantum = quantum;
            this.queue = new ArrayDeque<>();
            this.timeSlices = new ArrayList<>();
            this.finishedJobs = new ArrayList<>();
        }

        public void addJob(String name, int totalTime) {
            queue.offer(new Job(name, totalTime));
        }

        public void run() {
            int currentTime = 0;

            while (!queue.isEmpty()) {
                Job currentJob = queue.poll();
                int executionTime = Math.min(quantum, currentJob.remainingTime);
                int startTime = currentTime;
                int endTime = currentTime + executionTime;
                currentTime = endTime;

                currentJob.remainingTime -= executionTime;
                timeSlices.add(currentJob.name + "(" + startTime + "-" + endTime + ")");

                if (currentJob.remainingTime > 0) {
                    queue.offer(currentJob);
                } else {
                    finishedJobs.add(new FinishedJob(currentJob.name, endTime));
                }
            }
        }

        public void printSchedule() {
            // Print time slices line
            System.out.println(String.join(" ", timeSlices));

            // Print finished jobs line
            StringBuilder sb = new StringBuilder("finish ");
            for (int i = 0; i < finishedJobs.size(); i++) {
                FinishedJob fj = finishedJobs.get(i);
                sb.append(fj.name).append(" ").append(fj.finishTime);
                if (i < finishedJobs.size() - 1) {
                    sb.append(", ");
                }
            }
            System.out.println(sb);
        }
    }

    public static void main(String[] args) {
        System.out.println("Expected Output:");
        System.out.println("P1(0-3) P2(3-6) P3(6-9) P1(9-11) P3(11-14) P3(14-15)");
        System.out.println("finish P2 6, P1 11, P3 15");
        System.out.println();

        System.out.println("Actual Output:");
        LabScheduler scheduler = new LabScheduler(3);
        scheduler.addJob("P1", 5);
        scheduler.addJob("P2", 3);
        scheduler.addJob("P3", 7);
        scheduler.run();
        scheduler.printSchedule();
    }
}
