package seedu.clinic.model.exceptions;

public class NoRedoableVersionException extends RuntimeException {
    public NoRedoableVersionException() {
        super("There is no newer version to redo");
    }
}
