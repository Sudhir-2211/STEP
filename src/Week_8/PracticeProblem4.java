package Week8;

import java.time.LocalDate;

/**
 * Practice Problem 4: Employee Leave Request Management
 *
 * TASK:
 * 1. Employee hierarchy (Full-time, Part-time, Contract).
 * 2. LeaveRequest states: PENDING, APPROVED, REJECTED.
 * 3. Enforces valid state transitions: once approved or rejected, cannot revert to pending!
 */
public class PracticeProblem4 {

    public enum LeaveStatus {
        PENDING, APPROVED, REJECTED
    }

    public static class LeaveRequest {
        private final String employeeName;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private LeaveStatus status;

        public LeaveRequest(String employeeName, LocalDate startDate, LocalDate endDate) {
            this.employeeName = employeeName;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = LeaveStatus.PENDING;
            System.out.printf("Leave request submitted by %s for %s to %s. Status: %s.%n",
                    employeeName, startDate, endDate, status);
        }

        public boolean approve() {
            if (status != LeaveStatus.PENDING) {
                System.out.println("Cannot approve: request is already " + status);
                return false;
            }
            this.status = LeaveStatus.APPROVED;
            System.out.println("Leave request for " + employeeName + " approved. Status: Approved.");
            return true;
        }

        public boolean reject() {
            if (status != LeaveStatus.PENDING) {
                System.out.println("Cannot reject: request is already " + status);
                return false;
            }
            this.status = LeaveStatus.REJECTED;
            System.out.println("Leave request for " + employeeName + " rejected. Status: Rejected.");
            return true;
        }

        public boolean revertToPending() {
            if (status == LeaveStatus.APPROVED || status == LeaveStatus.REJECTED) {
                System.out.println("Cannot change status: " + status + " request cannot revert to Pending.");
                return false;
            }
            return true;
        }

        public LeaveStatus getStatus() { return status; }
    }

    public static void main(String[] args) {
        LeaveRequest r1 = new LeaveRequest("John Doe", LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        r1.approve();

        LeaveRequest r2 = new LeaveRequest("Jane Smith", LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));

        // Attempt invalid state transition
        r1.revertToPending();
    }
}
