package seedu.clinic.model.macro.exceptions;

/**
 * Signals that the operation is unable to find the specified macro.
 */
public class MacroNotFoundException extends RuntimeException {
    public MacroNotFoundException() {
        super("The macro is not found");
    }
}
