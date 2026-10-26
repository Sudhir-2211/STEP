package Week8;

import java.util.*;

public class Problem4 {

    // CreditPolicy interface allowing pluggable student types
    public interface CreditPolicy {
        String getTypeName();
        int getMaxCredits();
    }

    public static class RegularPolicy implements CreditPolicy {
        @Override
        public String getTypeName() {
            return "Regular";
        }

        @Override
        public int getMaxCredits() {
            return 24;
        }
    }

    public static class HonorsPolicy implements CreditPolicy {
        @Override
        public String getTypeName() {
            return "Honors";
        }

        @Override
        public int getMaxCredits() {
            return 28;
        }
    }

    public static class ExchangePolicy implements CreditPolicy {
        @Override
        public String getTypeName() {
            return "Exchange";
        }

        @Override
        public int getMaxCredits() {
            return 20;
        }
    }

    // Student model
    public static class Student {
        private String name;
        private CreditPolicy policy;
        private int currentCredits;

        public Student(String name, CreditPolicy policy, int currentCredits) {
            this.name = name;
            this.policy = policy;
            this.currentCredits = currentCredits;
        }

        public String getName() {
            return name;
        }

        public CreditPolicy getPolicy() {
            return policy;
        }

        public int getCurrentCredits() {
            return currentCredits;
        }

        public void addCredits(int credits) {
            this.currentCredits += credits;
        }

        public void deductCredits(int credits) {
            this.currentCredits -= credits;
        }

        public boolean canTake(int credits) {
            return (currentCredits + credits) <= policy.getMaxCredits();
        }
    }

    // Elective model protecting invariants and managing waitlist
    public static class Elective {
        private String name;
        private int creditValue;
        private int capacity;

        private List<Student> enrolled = new ArrayList<>();
        private Queue<Student> waitlist = new LinkedList<>();

        public Elective(String name, int creditValue, int capacity) {
            this.name = name;
            this.creditValue = creditValue;
            this.capacity = capacity;
        }

        public String getName() {
            return name;
        }

        public int getCreditValue() {
            return creditValue;
        }

        public int getCapacity() {
            return capacity;
        }

        public boolean enroll(Student student) {
            // Check duplicate enrollment or waitlist
            if (enrolled.contains(student) || waitlist.contains(student)) {
                System.out.println("Enrollment failed: " + student.getName() + " is already enrolled or on waitlist.");
                return false;
            }

            // 1. Credit limit checked BEFORE seat availability
            if (!student.canTake(creditValue)) {
                int attempted = student.getCurrentCredits() + creditValue;
                System.out.println("Enrollment failed: " + student.getName() + " would exceed the " +
                        student.getPolicy().getTypeName() + " credit limit (" + attempted + "/" + student.getPolicy().getMaxCredits() + ").");
                return false;
            }

            // 2. Check seat availability
            if (enrolled.size() < capacity) {
                enrolled.add(student);
                student.addCredits(creditValue);
                System.out.print(student.getName() + " enrolled in " + name + " (credits: " +
                        student.getCurrentCredits() + "/" + student.getPolicy().getMaxCredits() + ").");

                if (enrolled.size() == capacity) {
                    System.out.print(" " + name + " is full.");
                }
                System.out.println();
                return true;
            } else {
                // Course is full -> add to waitlist
                waitlist.add(student);
                System.out.println(student.getName() + " added to waitlist (position " + waitlist.size() + ").");
                return true;
            }
        }

        public boolean drop(Student student) {
            if (!enrolled.contains(student)) {
                System.out.println("Drop failed: " + student.getName() + " is not enrolled in " + name + ".");
                return false;
            }

            enrolled.remove(student);
            student.deductCredits(creditValue);
            System.out.println(student.getName() + " dropped " + name + " (credits: " +
                    student.getCurrentCredits() + "/" + student.getPolicy().getMaxCredits() + ").");

            // Auto-promote first eligible student from waitlist
            while (!waitlist.isEmpty() && enrolled.size() < capacity) {
                Student candidate = waitlist.poll();
                if (candidate.canTake(creditValue)) {
                    enrolled.add(candidate);
                    candidate.addCredits(creditValue);
                    System.out.println(candidate.getName() + " promoted from waitlist and enrolled in " +
                            name + " (credits: " + candidate.getCurrentCredits() + "/" + candidate.getPolicy().getMaxCredits() + ").");
                    break;
                } else {
                    System.out.println("Promotion skipped: " + candidate.getName() + " no longer meets credit limits.");
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        // 1. Elective 'Cloud Computing' is created with 4 credits and a capacity of 2
        Elective cloudComputing = new Elective("Cloud Computing", 4, 2);

        // 2. Asha (Regular, currently 20 credits) enrolls
        Student asha = new Student("Asha", new RegularPolicy(), 20);
        cloudComputing.enroll(asha);

        // 3. Ravi (Honors, currently 22 credits) enrolls
        Student ravi = new Student("Ravi", new HonorsPolicy(), 22);
        cloudComputing.enroll(ravi);

        // 4. Neha (Exchange, currently 12 credits) enrolls
        Student neha = new Student("Neha", new ExchangePolicy(), 12);
        cloudComputing.enroll(neha);

        // 5. Kiran (Regular, currently 22 credits) enrolls
        Student kiran = new Student("Kiran", new RegularPolicy(), 22);
        cloudComputing.enroll(kiran);

        // 6. Asha drops 'Cloud Computing'
        cloudComputing.drop(asha);
    }
}
