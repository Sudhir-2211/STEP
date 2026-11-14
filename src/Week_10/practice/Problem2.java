package Week10.practice;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Week 10 Practice - Problem 2: Customer Registry
 *
 * Task:
 * Design a Customer class with an ID and name.
 * Implement a customer registry system that stores Customer objects in a collection
 * where only unique customers are counted, even if they are added from different branches.
 * Specifically, if two Customer objects represent the same real-world customer
 * (i.e., same ID and name), they should be considered equal for the purpose of unique counts
 * and behave correctly in hash-based collections (like a HashSet or HashMap keys).
 * The system should report whether a customer already exists and the total unique customer count.
 *
 * Key Concepts Demonstrated:
 * - Proper implementation of the equals() and hashCode() contract:
 *   1. If a.equals(b) is true, then a.hashCode() MUST equal b.hashCode().
 *   2. Reflexive, symmetric, transitive, and consistent equality checks.
 *   3. Safe handling of null values and type safety using instanceof.
 * - HashSet lookup and duplicate prevention achieving O(1) average time complexity.
 */
public class Problem2 {

    public static class Customer {
        private final int id;
        private final String name;

        public Customer(int id, String name) {
            this.id = id;
            this.name = name != null ? name.trim() : "";
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Customer customer = (Customer) o;
            return id == customer.id && Objects.equals(name, customer.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "(" + id + ", " + name + ")";
        }
    }

    public static class CustomerRegistry {
        private final Set<Customer> uniqueCustomers = new HashSet<>();

        /**
         * Adds a customer from a branch.
         *
         * @param customer Customer instance
         * @param branchName Name of the originating branch
         * @return formatted message indicating success or rejection
         */
        public String addCustomer(Customer customer, String branchName) {
            boolean added = uniqueCustomers.add(customer);
            if (added) {
                return "true";
            } else {
                return "false (duplicate rejected)";
            }
        }

        public String addCustomer(Customer customer) {
            return addCustomer(customer, "Main");
        }

        public boolean contains(Customer customer) {
            return uniqueCustomers.contains(customer);
        }

        public int getUniqueCount() {
            return uniqueCustomers.size();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Week 10 Practice - Problem 2: Customer Registry ===");

        CustomerRegistry registry = new CustomerRegistry();

        Customer c1_branchA = new Customer(101, "Asha");
        Customer c1_branchB = new Customer(101, "Asha");
        Customer c2 = new Customer(102, "Ravi");

        // Execute sample actions
        System.out.println(registry.addCustomer(c1_branchA, "Branch A"));
        System.out.println(registry.addCustomer(c1_branchB, "Branch B"));
        System.out.println(registry.addCustomer(c2));
        System.out.println("unique count " + registry.getUniqueCount());

        Customer lookup = new Customer(101, "Asha");
        System.out.println("contains: " + registry.contains(lookup));

        System.out.println("\nExpected Output:");
        System.out.println("true");
        System.out.println("false (duplicate rejected)");
        System.out.println("true");
        System.out.println("unique count 2");
        System.out.println("contains: true");
    }
}
