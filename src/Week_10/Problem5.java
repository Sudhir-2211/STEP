package Week10;

/**
 * Problem 5: Polynomial Calculator
 *
 * TASK:
 * A maths tool stores each polynomial as a singly linked list of terms (coefficient, exponent)
 * in decreasing order of exponent. Two polynomials must be added into a new list in the same order;
 * terms whose coefficients add up to 0 must not appear.
 * Implement Node addPolynomials(Node p, Node q) and print the result in standard form.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - Similar to merging two sorted lists, both polynomial lists are scanned simultaneously
 *      in decreasing order of exponents.
 *    - Each term from list 'p' (m terms) and list 'q' (n terms) is inspected exactly once.
 *    - Total Time Complexity: O(m + n).
 *
 * 2. Space Complexity:
 *    - A new linked list is constructed containing at most (m + n) terms.
 *    - Total Auxiliary Space Complexity: O(m + n).
 */
public class Problem5 {

    public static class Node {
        public int coeff;
        public int exp;
        public Node next;

        public Node(int coeff, int exp) {
            this.coeff = coeff;
            this.exp = exp;
            this.next = null;
        }

        public Node(int coeff, int exp, Node next) {
            this.coeff = coeff;
            this.exp = exp;
            this.next = next;
        }
    }

    public static Node addPolynomials(Node p, Node q) {
        Node dummy = new Node(0, 0);
        Node tail = dummy;

        Node currP = p;
        Node currQ = q;

        while (currP != null && currQ != null) {
            if (currP.exp > currQ.exp) {
                tail.next = new Node(currP.coeff, currP.exp);
                tail = tail.next;
                currP = currP.next;
            } else if (currQ.exp > currP.exp) {
                tail.next = new Node(currQ.coeff, currQ.exp);
                tail = tail.next;
                currQ = currQ.next;
            } else {
                // Same exponent: sum the coefficients
                int sumCoeff = currP.coeff + currQ.coeff;
                if (sumCoeff != 0) {
                    tail.next = new Node(sumCoeff, currP.exp);
                    tail = tail.next;
                }
                currP = currP.next;
                currQ = currQ.next;
            }
        }

        // Append remaining terms from p
        while (currP != null) {
            if (currP.coeff != 0) {
                tail.next = new Node(currP.coeff, currP.exp);
                tail = tail.next;
            }
            currP = currP.next;
        }

        // Append remaining terms from q
        while (currQ != null) {
            if (currQ.coeff != 0) {
                tail.next = new Node(currQ.coeff, currQ.exp);
                tail = tail.next;
            }
            currQ = currQ.next;
        }

        return dummy.next;
    }

    // Formats polynomial linked list in standard algebraic mathematical notation
    public static String formatPolynomial(Node head) {
        if (head == null) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        Node curr = head;
        boolean isFirst = true;

        while (curr != null) {
            int c = curr.coeff;
            int e = curr.exp;

            if (c != 0) {
                if (isFirst) {
                    if (c < 0) {
                        sb.append("-");
                    }
                    formatTerm(sb, Math.abs(c), e);
                    isFirst = false;
                } else {
                    if (c > 0) {
                        sb.append(" + ");
                    } else {
                        sb.append(" - ");
                    }
                    formatTerm(sb, Math.abs(c), e);
                }
            }
            curr = curr.next;
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    private static void formatTerm(StringBuilder sb, int absCoeff, int exp) {
        if (exp == 0) {
            sb.append(absCoeff);
        } else if (exp == 1) {
            if (absCoeff == 1) {
                sb.append("x");
            } else {
                sb.append(absCoeff).append("x");
            }
        } else {
            if (absCoeff == 1) {
                sb.append("x^").append(exp);
            } else {
                sb.append(absCoeff).append("x^").append(exp);
            }
        }
    }

    public static void main(String[] args) {
        // Sample 1:
        // p = 5x^3 + 4x^2 + 2
        // q = 5x^2 - 3x + 1
        Node p1 = new Node(5, 3, new Node(4, 2, new Node(2, 0)));
        Node q1 = new Node(5, 2, new Node(-3, 1, new Node(1, 0)));

        System.out.println("Sample 1 Input:");
        System.out.println("p = " + formatPolynomial(p1));
        System.out.println("q = " + formatPolynomial(q1));

        Node sum1 = addPolynomials(p1, q1);
        System.out.println("Expected Output: 5x^3 + 9x^2 - 3x + 3");
        System.out.println("Actual Output:   " + formatPolynomial(sum1));
        System.out.println();

        // Sample 2:
        // p = 3x^2 + 1
        // q = -3x^2 + 4x
        Node p2 = new Node(3, 2, new Node(1, 0));
        Node q2 = new Node(-3, 2, new Node(4, 1));

        System.out.println("Sample 2 Input:");
        System.out.println("p = " + formatPolynomial(p2));
        System.out.println("q = " + formatPolynomial(q2));

        Node sum2 = addPolynomials(p2, q2);
        System.out.println("Expected Output: 4x + 1");
        System.out.println("Actual Output:   " + formatPolynomial(sum2));
    }
}
