package edu.ucsd.flappycow.util;

/*
 * Adapted from: https://www.leadingagile.com/2018/05/design-by-contract-part-three/
 */
public class Contract {
    public static boolean enabled = true;

    public static void require(boolean expression, String property) throws ViolationException {
        if (enabled && !expression) throw new ViolationException("Precondition violated: " + property);
    }

    public static void ensure(boolean expression, String property) throws ViolationException {
        if (enabled && !expression) throw new ViolationException("Postcondition violated: " + property);
    }

    public static void invariant(boolean expression, String property) throws ViolationException {
        if (enabled && !expression) throw new ViolationException("Class invariant violated: " + property);
    }

    public static class ViolationException extends RuntimeException {
        ViolationException(String message) { super(message); };
    }
}
