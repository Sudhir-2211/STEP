package Week5;

public class Problem1 {

    static class LibraryMember {

        private String membershipId;
        private String branchCode;
        private double finesOwed;
        private String displayName;

        public LibraryMember(String membershipId, String branchCode,
                             double finesOwed, String displayName) {

            String id = membershipId.trim();

            if (id.isEmpty() || id.length() < 4) {
                throw new IllegalArgumentException("Invalid membership ID");
            }

            this.membershipId = id;
            this.branchCode = branchCode;
            this.finesOwed = finesOwed;
            this.displayName = displayName;
        }
    }

    static String classifyAccess(String fieldModifier,
                                 String accessorContext) {

        if (fieldModifier.equals("private")) {
            return accessorContext.equals("SAME_CLASS")
                    ? "ALLOWED" : "DENIED";
        }

        if (fieldModifier.equals("default")) {
            return accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")
                    ? "ALLOWED" : "DENIED";
        }

        if (fieldModifier.equals("protected")) {
            return accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")
                    ? "ALLOWED" : "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    static String summarizeByModifier(String[][] attempts) {

        int privateAllowed = 0, privateDenied = 0;
        int defaultAllowed = 0, defaultDenied = 0;
        int protectedAllowed = 0, protectedDenied = 0;
        int publicAllowed = 0, publicDenied = 0;

        for (int i = 0; i < attempts.length; i++) {

            String modifier = attempts[i][0];
            String context = attempts[i][1];

            String result = classifyAccess(modifier, context);

            if (modifier.equals("private")) {
                if (result.equals("ALLOWED"))
                    privateAllowed++;
                else
                    privateDenied++;
            }

            if (modifier.equals("default")) {
                if (result.equals("ALLOWED"))
                    defaultAllowed++;
                else
                    defaultDenied++;
            }

            if (modifier.equals("protected")) {
                if (result.equals("ALLOWED"))
                    protectedAllowed++;
                else
                    protectedDenied++;
            }

            if (modifier.equals("public")) {
                if (result.equals("ALLOWED"))
                    publicAllowed++;
                else
                    publicDenied++;
            }
        }

        return "private: " + privateAllowed + " allowed / "
                + privateDenied + " denied | default: "
                + defaultAllowed + " allowed / "
                + defaultDenied + " denied | protected: "
                + protectedAllowed + " allowed / "
                + protectedDenied + " denied | public: "
                + publicAllowed + " allowed / "
                + publicDenied + " denied";
    }

    public static void main(String[] args) {

        String[][] attempts = {
                {"private", "SAME_CLASS"},
                {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"},
                {"protected", "SAME_CLASS"},
                {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
                classifyAccess("private", "SAME_CLASS")
        );

        System.out.println(
                classifyAccess("protected", "DIFFERENT_PACKAGE")
        );

        System.out.println(
                summarizeByModifier(attempts)
        );

        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (Exception e) {
            System.out.println("construction rejected");
        }
    }
}