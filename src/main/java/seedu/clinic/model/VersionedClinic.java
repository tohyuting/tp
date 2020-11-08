package seedu.clinic.model;

import java.util.Stack;

import seedu.clinic.model.exceptions.NoRedoableVersionException;
import seedu.clinic.model.exceptions.NoUndoableVersionException;

/**
 * {@code Clinic} that keeps track of its own versions.
 */
public class VersionedClinic extends Clinic {
    private final Stack<ReadOnlyClinic> redoVersionStack;
    private final Stack<ReadOnlyClinic> undoVersionStack;
    private ReadOnlyClinic currentClinic; // current version of Clinic data

    /**
     * Creates a {@code VersionedClinic} with the given {@code ReadOnlyClinic}.
     */
    public VersionedClinic(ReadOnlyClinic currentClinic) {
        super(currentClinic);

        redoVersionStack = new Stack<>();
        undoVersionStack = new Stack<>();
        this.currentClinic = currentClinic;
    }

    /**
     * Saves a copy of the current {@code Clinic} version on top of
     * the undo stack. Undone version are removed from the redo stack.
     */
    public void save() {
        redoVersionStack.clear();
        undoVersionStack.push(new Clinic(currentClinic));
        currentClinic = new Clinic(this);
    }

    /**
     * Returns true if the Clinic has older version to undo.
     */
    public boolean canUndo() {
        return !undoVersionStack.isEmpty();
    }

    /**
     * Returns true if the Clinic has newer version to redo.
     */
    public boolean canRedo() {
        return !redoVersionStack.isEmpty();
    }

    /**
     * Recover Clinic to its previous version
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableVersionException();
        }

        redoVersionStack.push(new Clinic(currentClinic));
        ReadOnlyClinic prev = undoVersionStack.pop();
        resetData(prev);
    }

    /**
     * Restores CLI-nic to its previously undone version
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableVersionException();
        }

        undoVersionStack.push(new Clinic(currentClinic));
        ReadOnlyClinic prev = redoVersionStack.pop();
        resetData(prev);
    }

    public ReadOnlyClinic getCurrentClinic() {
        return currentClinic;
    }

    public Stack<ReadOnlyClinic> getUndoVersion() {
        return undoVersionStack;
    }

    public Stack<ReadOnlyClinic> getRedoVersion() {
        return redoVersionStack;
    }

    @Override
    public void resetData(ReadOnlyClinic newData) {
        super.resetData(newData);
        this.currentClinic = newData;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedClinic)) {
            return false;
        }

        VersionedClinic otherVersionedClinic = (VersionedClinic) other;

        // check for both stack and current Clinic
        return super.equals(otherVersionedClinic)
                && currentClinic.equals(otherVersionedClinic.currentClinic)
                && undoVersionStack.equals(otherVersionedClinic.undoVersionStack)
                && redoVersionStack.equals(otherVersionedClinic.redoVersionStack);
    }
}
