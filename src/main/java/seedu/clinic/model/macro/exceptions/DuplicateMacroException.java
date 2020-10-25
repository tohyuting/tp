package seedu.clinic.model.macro.exceptions;

/**
 * Signals that the operation will result in duplicate Macros (Macros are considered duplicates if they
 * have the same alias).
 */
public class DuplicateMacroException extends RuntimeException {
    public DuplicateMacroException() {
        super("Operation would result in duplicate macros");
    }
}
