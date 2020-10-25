package seedu.clinic.model;

import javafx.collections.ObservableList;
import seedu.clinic.model.macro.Macro;

/**
 * Unmodifiable view of user macros.
 */
public interface ReadOnlyUserMacros {

    /**
     * Returns an unmodifiable view of the macro list.
     * This list will not contain any duplicate macros.
     */
    ObservableList<Macro> getMacroList();
}
