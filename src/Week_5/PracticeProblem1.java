package Week5;

/**
 * Practice Problem 1: Field Visibility & Intake Validator
 *
 * TASK:
 * 1. classifyAccess(fieldModifier, accessorContext): evaluates Java visibility rules
 *    for private, default, protected, public across SAME_CLASS, SAME_PACKAGE, DIFFERENT_PACKAGE.
 * 2. summarizeBatch(attempts): summarizes allowed vs denied attempts.
 * 3. PatientRecord constructor validates patientId (non-blank, trimmed length >= 4).
 */
public class PracticeProblem1 {

    public static class PatientRecord {
        private final String patientId;
        String wardCode;            // default package-private
        protected double vitalsScore;
        public String facilityName;

        public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
            if (patientId == null || patientId.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid patientId: must be at least 4 non-whitespace characters.");
            }
            this.patientId = patientId.trim();
            this.wardCode = wardCode;
            this.vitalsScore = vitalsScore;
            this.facilityName = facilityName;
        }

        public String getPatientId() {
            return patientId;
        }
    }

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        String mod = fieldModifier.toLowerCase().trim();
        String ctx = accessorContext.toUpperCase().trim();

        if (mod.equals("public")) {
            return "ALLOWED";
        }
        if (mod.equals("private")) {
            return ctx.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
        }
        if (mod.equals("default") || mod.equals("protected")) {
            if (ctx.equals("SAME_CLASS") || ctx.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }
        return "DENIED";
    }

    public static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;

        for (String[] attempt : attempts) {
            if (attempt != null && attempt.length >= 2) {
                String result = classifyAccess(attempt[0], attempt[1]);
                if (result.equals("ALLOWED")) {
                    allowed++;
                } else {
                    denied++;
                }
            }
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    public static void main(String[] args) {
        System.out.println("private, SAME_CLASS -> " + classifyAccess("private", "SAME_CLASS"));
        System.out.println("default, DIFFERENT_PACKAGE -> " + classifyAccess("default", "DIFFERENT_PACKAGE"));

        String[][] batch = {
                {"protected", "SAME_PACKAGE"},
                {"protected", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeBatch(batch));

        try {
            new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
            System.out.println("MT9 constructed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("new PatientRecord(\"MT9\", ...) -> construction rejected");
        }

        try {
            PatientRecord p = new PatientRecord("MT94", "W3", 98.2, "MediTrack Central");
            System.out.println("new PatientRecord(\"MT94\", ...) -> successfully created with ID: " + p.getPatientId());
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
    }
}
