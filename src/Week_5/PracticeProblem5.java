package Week5;

import java.util.Arrays;

/**
 * Practice Problem 5: Immutable Discharge Summary & Nightly Ledger
 *
 * TASK:
 * 1. DischargeSummary: all fields final, class final.
 * 2. Medication codes validated against "MED-[A-Z]".
 * 3. Defensive copy of array on construction and access.
 * 4. withCorrectedMedication(index, newCode) returns a new immutable instance.
 * 5. CriticalCareDischargeSummary: subclass or wrapper variant.
 * 6. processNightlyBatch(...) processes batch safely with null check and instanceof.
 */
public class PracticeProblem5 {

    public static class DischargeSummary {
        private final String patientId;
        private final String[] medicationCodes;

        public DischargeSummary(String patientId, String[] medicationCodes) {
            if (patientId == null || patientId.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid patientId");
            }
            if (medicationCodes == null) {
                throw new IllegalArgumentException("Medication codes cannot be null");
            }
            // Validate all codes
            for (String code : medicationCodes) {
                if (!isValidMedCode(code)) {
                    throw new IllegalArgumentException("Invalid medication code format: " + code);
                }
            }
            this.patientId = patientId;
            // Defensive copy on entry
            this.medicationCodes = medicationCodes.clone();
        }

        private static boolean isValidMedCode(String code) {
            return code != null && code.matches("^MED-[A-Z]$");
        }

        public String getPatientId() {
            return patientId;
        }

        public String[] getMedicationCodes() {
            // Defensive copy on exit
            return medicationCodes.clone();
        }

        public DischargeSummary withCorrectedMedication(int index, String newCode) {
            if (!isValidMedCode(newCode)) {
                throw new IllegalArgumentException("Invalid new medication code: " + newCode);
            }
            if (index < 0 || index >= medicationCodes.length) {
                throw new IndexOutOfBoundsException("Invalid medication index");
            }
            String[] updated = medicationCodes.clone();
            updated[index] = newCode;
            return new DischargeSummary(this.patientId, updated);
        }
    }

    public static class CriticalCareDischargeSummary extends DischargeSummary {
        private final int icuDays;

        public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
            super(patientId, medicationCodes);
            this.icuDays = icuDays;
        }

        public int getIcuDays() {
            return icuDays;
        }
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        if (summaries == null) return "0 processed | 0 null skipped | 0 critical-care | 0 routine";

        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        for (DischargeSummary s : summaries) {
            if (s == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (s instanceof CriticalCareDischargeSummary) {
                criticalCare++;
            } else {
                routine++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " +
                criticalCare + " critical-care | " + routine + " routine";
    }

    public static void main(String[] args) {
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
            System.out.println("Construction succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("new DischargeSummary with \"bad\" code -> construction rejected");
        }

        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println("Defensive copy test: d.getMedicationCodes()[0] is still " + d.getMedicationCodes()[0]);

        DischargeSummary[] batch = {
                new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
                null,
                new DischargeSummary("MT002", new String[]{"MED-Y"})
        };
        System.out.println(processNightlyBatch(batch));
    }
}
