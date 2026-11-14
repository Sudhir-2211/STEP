package Week5;

/**
 * Practice Problem 2: Cross-Package Inheritance Reach
 *
 * TASK:
 * Extend access rules to 5 contexts including:
 * - SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE: protected is ALLOWED.
 * - SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE: protected is DENIED.
 * Implement describeContext(String accessorContext) formatting context into title case.
 */
public class PracticeProblem2 {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        String mod = fieldModifier.toLowerCase().trim();
        String ctx = accessorContext.toUpperCase().trim();

        if (mod.equals("public")) {
            return "ALLOWED";
        }
        if (mod.equals("private")) {
            return ctx.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
        }
        if (mod.equals("default")) {
            return (ctx.equals("SAME_CLASS") || ctx.equals("SAME_PACKAGE")) ? "ALLOWED" : "DENIED";
        }
        if (mod.equals("protected")) {
            if (ctx.equals("SAME_CLASS") || ctx.equals("SAME_PACKAGE") || ctx.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }
        return "DENIED";
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.isEmpty()) return "";
        String[] words = accessorContext.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                sb.append(Character.toUpperCase(words[i].charAt(0)));
                sb.append(words[i].substring(1));
                if (i < words.length - 1) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("protected, SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE -> " +
                classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println("protected, SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE -> " +
                classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println("describeContext -> " +
                describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
    }
}
