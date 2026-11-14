package Week11;

import java.util.Stack;

/**
 * Problem 3: The Quiz Scoring Formula
 *
 * TASK:
 * The quiz portal lets faculty type a scoring formula such as 2 * (3 + 4) - 10 / 5.
 * Implement int evaluate(String expr) that returns the value of the formula.
 * It may contain non-negative integers (possibly multi-digit), the operators + - * /,
 * round brackets and spaces.
 * * and / bind tighter than + and -, operators of equal precedence are applied left to right,
 * and / is integer division. Use an operand stack and an operator stack in a single left-to-right scan;
 * do not convert the whole expression to postfix first.
 *
 * Complexity Analysis:
 * 1. Time Complexity:
 *    - The expression of length N is traversed character-by-character from left to right.
 *    - Each operand is pushed onto the operand stack once and popped once.
 *    - Each operator is pushed onto the operator stack once and popped once when evaluated.
 *    - Total Time Complexity: O(N).
 *
 * 2. Space Complexity:
 *    - Auxiliary space for values and operators stacks is bounded by O(N).
 *    - Total Auxiliary Space: O(N).
 */
public class Problem3 {

    public static int evaluate(String expr) {
        if (expr == null || expr.trim().isEmpty()) {
            return 0;
        }

        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        int i = 0;
        int n = expr.length();

        while (i < n) {
            char ch = expr.charAt(i);

            // Skip whitespace
            if (ch == ' ') {
                i++;
                continue;
            }

            // Parse full multi-digit number
            if (Character.isDigit(ch)) {
                int val = 0;
                while (i < n && Character.isDigit(expr.charAt(i))) {
                    val = val * 10 + (expr.charAt(i) - '0');
                    i++;
                }
                values.push(val);
                continue;
            }

            // Opening parenthesis
            if (ch == '(') {
                ops.push(ch);
            }
            // Closing parenthesis: evaluate until matching '('
            else if (ch == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    applyOperation(values, ops.pop());
                }
                if (!ops.isEmpty()) {
                    ops.pop(); // Pop '('
                }
            }
            // Operator (+, -, *, /)
            else if (isOperator(ch)) {
                // Apply all operators on stack with greater or equal precedence
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(ch)) {
                    applyOperation(values, ops.pop());
                }
                ops.push(ch);
            }

            i++;
        }

        // Apply remaining operators
        while (!ops.isEmpty()) {
            applyOperation(values, ops.pop());
        }

        return values.isEmpty() ? 0 : values.pop();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static int precedence(char op) {
        if (op == '+' || op == '-') {
            return 1;
        }
        if (op == '*' || op == '/') {
            return 2;
        }
        return 0; // For '(' and other characters
    }

    private static void applyOperation(Stack<Integer> values, char op) {
        if (values.size() < 2) {
            return;
        }
        int b = values.pop();
        int a = values.pop();
        switch (op) {
            case '+':
                values.push(a + b);
                break;
            case '-':
                values.push(a - b);
                break;
            case '*':
                values.push(a * b);
                break;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                values.push(a / b);
                break;
        }
    }

    public static void main(String[] args) {
        // Sample 1: expr = "2 * (3 + 4) - 10 / 5"
        String expr1 = "2 * (3 + 4) - 10 / 5";
        System.out.println("Sample 1 Input: expr = \"" + expr1 + "\"");
        int res1 = evaluate(expr1);
        System.out.println("Expected Output: 12");
        System.out.println("Actual Output:   " + res1);
        System.out.println();

        // Sample 2: expr = "(8 - 2) * (1 + 2) / 4"
        String expr2 = "(8 - 2) * (1 + 2) / 4";
        System.out.println("Sample 2 Input: expr = \"" + expr2 + "\"");
        int res2 = evaluate(expr2);
        System.out.println("Expected Output: 4 (18 / 4 with integer division)");
        System.out.println("Actual Output:   " + res2);
    }
}
