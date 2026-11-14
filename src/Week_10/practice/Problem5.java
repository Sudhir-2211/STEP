package Week10.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * Week 10 Practice - Problem 5: Library Platform
 *
 * Task:
 * Design and implement a system for a library platform that manages different types of resources
 * (e.g., physical books, ebooks).
 * - All resources have a unique ID.
 * - Some resources can be reserved (Reservable), and some can be downloaded (Downloadable), but not all.
 * - The system should store resources in an array, ordered by their ID.
 * - Implement functionality to add resources, rejecting duplicates.
 * - Implement operations for reserving and downloading resources, rejecting commands for unsupported capabilities.
 * - Provide a way to find a resource by its ID, reporting its index in the ordered array.
 *
 * Design Patterns & Principles:
 * - Interface Segregation: Reservable and Downloadable are distinct capability interfaces.
 * - Polymorphism & Dynamic Dispatch: Subclasses implement specific capabilities.
 * - Sorted Array Invariant: Maintained via Binary Search insertion point O(n) and lookup O(log n).
 *
 * Sample Input:
 *   ADD Book B1
 *   ADD Book B1
 *   ADD EBook E1
 *   RESERVE B1 by M1
 *   DOWNLOAD B1
 *   DOWNLOAD E1
 *   FIND E1
 *
 * Expected Output:
 *   B1 added
 *   duplicate rejected
 *   E1 added
 *   B1 reserved for M1
 *   B1 rejected: download unsupported
 *   E1 downloaded
 *   E1 found at index 1
 */
public class Problem5 {

    // --- Capability Interfaces ---
    public interface Reservable {
        String reserve(String memberId);
    }

    public interface Downloadable {
        String download();
    }

    // --- Base Resource Class ---
    public static abstract class Resource implements Comparable<Resource> {
        protected final String id;

        public Resource(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        @Override
        public int compareTo(Resource other) {
            return this.id.compareTo(other.id);
        }
    }

    // --- Concrete Resource Subclasses ---
    public static class Book extends Resource implements Reservable {
        private String reservedBy;

        public Book(String id) {
            super(id);
        }

        @Override
        public String reserve(String memberId) {
            this.reservedBy = memberId;
            return id + " reserved for " + memberId;
        }
    }

    public static class EBook extends Resource implements Downloadable {
        public EBook(String id) {
            super(id);
        }

        @Override
        public String download() {
            return id + " downloaded";
        }
    }

    // --- Library Platform System ---
    public static class LibraryPlatform {
        // Ordered array/list of resources maintained in ascending order by ID
        private final List<Resource> resources = new ArrayList<>();

        /**
         * Binary searches for resource by ID.
         * Returns index >= 0 if found, or (-insertionPoint - 1) if not found.
         */
        public int binarySearchId(String id) {
            int low = 0;
            int high = resources.size() - 1;

            while (low <= high) {
                int mid = low + (high - low) / 2;
                int cmp = resources.get(mid).getId().compareTo(id);
                if (cmp == 0) {
                    return mid;
                } else if (cmp < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return -(low + 1);
        }

        /**
         * Adds a resource to the system maintaining sorted order and rejecting duplicates.
         */
        public String addResource(Resource res) {
            int idx = binarySearchId(res.getId());
            if (idx >= 0) {
                return "duplicate rejected";
            }

            int insertionPoint = -idx - 1;
            resources.add(insertionPoint, res);
            return res.getId() + " added";
        }

        /**
         * Reserves a resource for a member if supported.
         */
        public String reserveResource(String id, String memberId) {
            int idx = binarySearchId(id);
            if (idx < 0) {
                return id + " rejected: not found";
            }
            Resource res = resources.get(idx);
            if (res instanceof Reservable) {
                return ((Reservable) res).reserve(memberId);
            } else {
                return id + " rejected: reserve unsupported";
            }
        }

        /**
         * Downloads a resource if supported.
         */
        public String downloadResource(String id) {
            int idx = binarySearchId(id);
            if (idx < 0) {
                return id + " rejected: not found";
            }
            Resource res = resources.get(idx);
            if (res instanceof Downloadable) {
                return ((Downloadable) res).download();
            } else {
                return id + " rejected: download unsupported";
            }
        }

        /**
         * Finds resource by ID and returns index message.
         */
        public String findResource(String id) {
            int idx = binarySearchId(id);
            if (idx >= 0) {
                return id + " found at index " + idx;
            } else {
                return id + " not found";
            }
        }

        /**
         * Processes formatted string command.
         */
        public String processCommand(String commandLine) {
            if (commandLine == null || commandLine.trim().isEmpty()) {
                return "";
            }

            String[] parts = commandLine.trim().split("\\s+");
            String action = parts[0].toUpperCase();

            switch (action) {
                case "ADD": {
                    String type = parts[1];
                    String id = parts[2];
                    if ("BOOK".equalsIgnoreCase(type)) {
                        return addResource(new Book(id));
                    } else if ("EBOOK".equalsIgnoreCase(type)) {
                        return addResource(new EBook(id));
                    } else {
                        return "Unknown resource type: " + type;
                    }
                }
                case "RESERVE": {
                    // Syntax: RESERVE B1 by M1
                    String id = parts[1];
                    String memberId = parts.length >= 4 ? parts[3] : "Member";
                    return reserveResource(id, memberId);
                }
                case "DOWNLOAD": {
                    String id = parts[1];
                    return downloadResource(id);
                }
                case "FIND": {
                    String id = parts[1];
                    return findResource(id);
                }
                default:
                    return "Unknown command: " + action;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Week 10 Practice - Problem 5: Library Platform ===");

        LibraryPlatform platform = new LibraryPlatform();

        String[] commands = {
            "ADD Book B1",
            "ADD Book B1",
            "ADD EBook E1",
            "RESERVE B1 by M1",
            "DOWNLOAD B1",
            "DOWNLOAD E1",
            "FIND E1"
        };

        System.out.println("Executing Commands:");
        for (String cmd : commands) {
            System.out.println(platform.processCommand(cmd));
        }

        System.out.println("\nExpected Output:");
        System.out.println("B1 added");
        System.out.println("duplicate rejected");
        System.out.println("E1 added");
        System.out.println("B1 reserved for M1");
        System.out.println("B1 rejected: download unsupported");
        System.out.println("E1 downloaded");
        System.out.println("E1 found at index 1");
    }
}
