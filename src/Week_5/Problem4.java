package Week5;

public class Problem4 {

    static class LibraryMember {

        private String membershipId;
        private String name;
        private boolean premiumMember;
        private String securityAnswer;

        public LibraryMember() {
            this(null, null);
        }

        public LibraryMember(String name) {
            this(null, name);
        }

        public LibraryMember(String membershipId, String name) {
            this.membershipId = membershipId;
            this.name = name;
            this.premiumMember = false;
        }

        public String getMembershipId() {
            return membershipId;
        }

        public void setMembershipId(String id) {
            if (membershipId == null) {
                membershipId = id;
            }
        }

        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premium) {
            premiumMember = premium;
        }

        public void setSecurityAnswer(String answer) {
            if (answer != null) {
                securityAnswer = Integer.toHexString(
                        answer.hashCode()
                );
            }
        }
    }

    public static void main(String[] args) {

        LibraryMember member1 =
                new LibraryMember("Priya Nair");

        System.out.println(member1.getMembershipId());

        LibraryMember member2 =
                new LibraryMember(
                        "LIB-8841",
                        "Priya Nair"
                );

        System.out.println(member2.getMembershipId());

        LibraryMember member3 =
                new LibraryMember();

        member3.setMembershipId("LIB-8841");
        member3.setMembershipId("FAKE-0000");

        System.out.println(member3.getMembershipId());

        member3.setPremiumMember(true);

        System.out.println(member3.isPremiumMember());

        member3.setSecurityAnswer("myanswer");
    }
}