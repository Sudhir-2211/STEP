package Week5;

/**
 * Practice Problem 4: JavaBean Properties & Write-Once Identity
 *
 * TASK:
 * 1. Chained constructors via this(...).
 * 2. JavaBean naming: getPatientId(), setPatientId(), isDischarged(), setDischarged().
 * 3. Write-once setPatientId: subsequent updates are silently ignored.
 * 4. Write-only setLockerPin: settable, but no getter exists.
 */
public class PracticeProblem4 {

    public static class PatientProfile {
        private String patientId;
        private String name;
        private boolean discharged;
        private String lockerPinHash;

        public PatientProfile() {
            this(null, null);
        }

        public PatientProfile(String name) {
            this(null, name);
        }

        public PatientProfile(String patientId, String name) {
            this.patientId = patientId;
            this.name = name;
            this.discharged = false;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String id) {
            if (this.patientId == null) {
                this.patientId = id;
            }
            // If already set, silently ignore further mutations (write-once)
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isDischarged() {
            return discharged;
        }

        public void setDischarged(boolean discharged) {
            this.discharged = discharged;
        }

        public void setLockerPin(String pin) {
            if (pin != null && pin.matches("\\d{4,6}")) {
                this.lockerPinHash = "HASH_" + pin.hashCode();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("new PatientProfile(\"Arjun Iyer\").getPatientId() -> " +
                new PatientProfile("Arjun Iyer").getPatientId());

        System.out.println("new PatientProfile(\"MT2026-0142\", \"Arjun Iyer\").getPatientId() -> " +
                new PatientProfile("MT2026-0142", "Arjun Iyer").getPatientId());

        PatientProfile p = new PatientProfile();
        p.setPatientId("MT2026-0142");
        p.setPatientId("HACKED-0000"); // ignored
        System.out.println("Write-once test: " + p.getPatientId());
    }
}
