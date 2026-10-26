package Week7;

public class Problem2 {

    // Shared counter tracking calls across unrelated classes implementing Exportable
    private static int totalExports = 0;

    public static int getTotalExports() {
        return totalExports;
    }

    public static void incrementExportCount() {
        totalExports++;
    }

    public static void resetExportCount() {
        totalExports = 0;
    }

    // Common action interface without forcing any shared class hierarchy
    public interface Exportable {
        String exportData();
    }

    // Class 1: ReportGenerator
    public static class ReportGenerator implements Exportable {
        private String reportName;

        public ReportGenerator(String reportName) {
            if (reportName == null || reportName.trim().isEmpty()) {
                throw new IllegalArgumentException("reportName cannot be blank");
            }
            this.reportName = reportName;
        }

        public String getReportName() {
            return reportName;
        }

        @Override
        public String exportData() {
            incrementExportCount();
            return "Exported report: " + reportName;
        }
    }

    // Class 2: UserProfile
    public static class UserProfile implements Exportable {
        private String username;

        public UserProfile(String username) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("username cannot be blank");
            }
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        @Override
        public String exportData() {
            incrementExportCount();
            return "Exported profile: " + username;
        }
    }

    // Loops through a mixed array of Exportables polymorphically
    public static void exportAll(Exportable[] items) {
        if (items != null) {
            for (Exportable item : items) {
                if (item != null) {
                    System.out.println(item.exportData());
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Individual Export Calls ---");
        ReportGenerator r = new ReportGenerator("Sales Q1");
        System.out.println(r.exportData());

        UserProfile u = new UserProfile("jane_doe");
        System.out.println(u.exportData());

        System.out.println("\n--- Polymorphic Batch Export ---");
        // Reset counter before running batch test matching the example
        resetExportCount();

        Exportable ref = r; // upcasting: ReportGenerator stored as Exportable interface
        exportAll(new Exportable[]{ ref, u });

        System.out.println("Total Exports: " + getTotalExports()); // 2
    }
}
