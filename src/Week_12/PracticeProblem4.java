package Week12;

/**
 * Problem 4: Campus Map Pins
 *
 * TASK:
 * The campus map app has a general pin and a hostel pin. Both have a class-level banner
 * and an instance-level name, and both describe themselves with getDetails().
 * CampusPin declares:
 *   public static String banner = "Campus";
 *   public String name = "General Pin";
 *   public String getDetails() { return "Details: " + name; }
 *
 * HostelPin extends CampusPin and declares:
 *   public static String banner = "Hostel";
 *   public String name = "Hostel Pin";
 *   and an overriding getDetails() with the same body.
 *
 * In main, write CampusPin p = new HostelPin(); and print p.banner, p.name and p.getDetails().
 * For each line, state whether the reference type or the object type decided the value, and explain why.
 *
 * Complexity & Resolution Analysis:
 * 1. p.banner:
 *    - Resolved at COMPILE TIME.
 *    - In Java, static fields cannot be overridden; they are hidden.
 *    - Access through an object reference is resolved based solely on the compile-time
 *      reference type (CampusPin), not the runtime instance type.
 *
 * 2. p.name:
 *    - Resolved at COMPILE TIME.
 *    - In Java, instance fields are also hidden rather than polymorphic/overridden.
 *    - Field lookups are bound statically by the compiler based on the reference type (CampusPin).
 *
 * 3. p.getDetails():
 *    - Resolved at RUN TIME.
 *    - Instance methods participate in dynamic method dispatch (runtime polymorphism).
 *    - The JVM inspects the actual object type on the heap (HostelPin) and invokes HostelPin's
 *      overridden getDetails() method. Inside HostelPin's getDetails(), 'name' directly refers
 *      to HostelPin's own field ("Hostel Pin").
 */
public class PracticeProblem4 {

    public static class CampusPin {
        public static String banner = "Campus";
        public String name = "General Pin";

        public String getDetails() {
            return "Details: " + name;
        }
    }

    public static class HostelPin extends CampusPin {
        public static String banner = "Hostel";
        public String name = "Hostel Pin";

        @Override
        public String getDetails() {
            return "Details: " + name;
        }
    }

    public static void main(String[] args) {
        // Polymorphic reference assignment
        CampusPin p = new HostelPin();

        System.out.println("Expected Output:");
        System.out.println("banner: Campus (decided by reference type)");
        System.out.println("name: General Pin (decided by reference type)");
        System.out.println("Details: Hostel Pin (decided by object type)");
        System.out.println();

        System.out.println("Actual Output:");
        System.out.println("banner: " + p.banner + " (decided by reference type)");
        System.out.println("name: " + p.name + " (decided by reference type)");
        System.out.println(p.getDetails() + " (decided by object type)");
    }
}
