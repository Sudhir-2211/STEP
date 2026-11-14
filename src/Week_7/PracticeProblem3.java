package Week7;

/**
 * Practice Problem 3: Quarterly Bonus Calculator
 *
 * TASK:
 * 1. StaffMember abstract class with chained constructors via this(baseSalary, 0.10).
 * 2. JavaBean getSalary(), setSalary() rejecting negative values.
 * 3. Auditable interface with String auditRecord().
 * 4. TeamLead extends StaffMember implements Auditable.
 * 5. getAuditIfApplicable(StaffMember s) checks instanceof Auditable and calls auditRecord().
 */
public class PracticeProblem3 {

    public interface Auditable {
        String auditRecord();
    }

    public static abstract class StaffMember {
        private double baseSalary;
        protected final double bonusRate;

        public StaffMember(double baseSalary) {
            this(baseSalary, 0.10);
        }

        public StaffMember(double baseSalary, double bonusRate) {
            if (baseSalary < 0) {
                throw new IllegalArgumentException("Salary cannot be negative");
            }
            this.baseSalary = baseSalary;
            this.bonusRate = bonusRate;
        }

        public abstract double calculateBonus();

        public double getSalary() {
            return baseSalary;
        }

        public void setSalary(double baseSalary) {
            if (baseSalary >= 0) {
                this.baseSalary = baseSalary;
            } else {
                System.out.println("rejected, salary unchanged");
            }
        }
    }

    public static class TeamLead extends StaffMember implements Auditable {
        private final int teamSize;

        public TeamLead(double baseSalary, int teamSize) {
            super(baseSalary);
            this.teamSize = teamSize;
        }

        public TeamLead(double baseSalary, double bonusRate, int teamSize) {
            super(baseSalary, bonusRate);
            this.teamSize = teamSize;
        }

        @Override
        public double calculateBonus() {
            return getSalary() * bonusRate;
        }

        @Override
        public String auditRecord() {
            return "TeamLead audit: " + teamSize + " team members, salary $" + getSalary();
        }
    }

    public static String getAuditIfApplicable(StaffMember s) {
        if (s instanceof Auditable) {
            return ((Auditable) s).auditRecord();
        }
        return "No audit required";
    }

    public static void main(String[] args) {
        TeamLead t1 = new TeamLead(60000, 5);
        System.out.println("t1.calculateBonus() -> " + t1.calculateBonus());

        TeamLead t2 = new TeamLead(60000, 0.20, 5);
        System.out.println("t2.calculateBonus() -> " + t2.calculateBonus());

        t1.setSalary(-5000);

        StaffMember ref = t1;
        System.out.println(getAuditIfApplicable(ref));
    }
}
