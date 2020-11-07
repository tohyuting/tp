package seedu.clinic.model.exceptions;

public class NoUndoableVersionException extends RuntimeException {
    public NoUndoableVersionException () {
        super("There is no earlier version to undo");
    }
}
