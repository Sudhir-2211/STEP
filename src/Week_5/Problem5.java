package Week5;

public class Problem5 {

    static final class LoanReceipt {

        private final String memberId;
        private final String[] bookIds;

        static String systemName;

        static {
            systemName = "PageTurner Library";
        }

        public LoanReceipt(String memberId, String[] bookIds) {

            if (bookIds == null) {
                throw new IllegalArgumentException(
                        "Invalid book list"
                );
            }

            for (int i = 0; i < bookIds.length; i++) {

                if (!bookIds[i].matches("BK-[0-9]{3}")) {
                    throw new IllegalArgumentException(
                            "Invalid book ID"
                    );
                }
            }

            this.memberId = memberId;
            this.bookIds = bookIds.clone();
        }

        public String[] getBookIds() {
            return bookIds.clone();
        }

        public LoanReceipt withCorrectedBookId(
                int index,
                String newId) {

            if (index < 0 || index >= bookIds.length) {
                throw new IllegalArgumentException(
                        "Invalid index"
                );
            }

            if (!newId.matches("BK-[0-9]{3}")) {
                throw new IllegalArgumentException(
                        "Invalid book ID"
                );
            }

            String[] newBooks = bookIds.clone();
            newBooks[index] = newId;

            return new LoanReceipt(memberId, newBooks);
        }
    }

    static class ReferenceOnlyLoanReceipt
            extends LoanReceipt {

        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(
                String memberId,
                String[] bookIds,
                String roomNumber) {

            super(memberId, bookIds);
            this.roomNumber = roomNumber;
        }
    }

    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int skipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (int i = 0; i < receipts.length; i++) {

            if (receipts[i] == null) {
                skipped++;
                continue;
            }

            processed++;

            if (receipts[i]
                    instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | "
                + skipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {

        try {
            new LoanReceipt(
                    "LIB-8841",
                    new String[]{"BK-100", "bad"}
            );
        } catch (Exception e) {
            System.out.println("construction rejected");
        }

        LoanReceipt receipt =
                new LoanReceipt(
                        "LIB-8841",
                        new String[]{"BK-100", "BK-101"}
                );

        String[] ids = receipt.getBookIds();
        ids[0] = "HACKED";

        System.out.println(
                receipt.getBookIds()[0]
        );

        LoanReceipt corrected =
                receipt.withCorrectedBookId(
                        0,
                        "BK-200"
                );

        System.out.println(
                corrected.getBookIds()[0]
        );

        LoanReceipt[] receipts = {
                new ReferenceOnlyLoanReceipt(
                        "LIB-001",
                        new String[]{"BK-200"},
                        "Reading Room 3"
                ),
                null,
                new LoanReceipt(
                        "LIB-002",
                        new String[]{"BK-201"}
                )
        };

        System.out.println(
                processNightlyCirculation(receipts)
        );
    }
}