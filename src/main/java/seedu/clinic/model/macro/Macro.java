package seedu.clinic.model.macro;

import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.clinic.logic.commands.AddCommand;
import seedu.clinic.logic.commands.ClearCommand;
import seedu.clinic.logic.commands.DeleteCommand;
import seedu.clinic.logic.commands.EditCommand;
import seedu.clinic.logic.commands.ExitCommand;
import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.logic.commands.HelpCommand;
import seedu.clinic.logic.commands.ListCommand;
import seedu.clinic.logic.commands.ListMacroCommand;
import seedu.clinic.logic.commands.RedoCommand;
import seedu.clinic.logic.commands.RemoveMacroCommand;
import seedu.clinic.logic.commands.UndoCommand;
import seedu.clinic.logic.commands.UpdateCommand;
import seedu.clinic.logic.commands.ViewCommand;

/**
 * Represents a user Macro.
 * Guarantees: fields are present and not null, field values are validated, immutable.
 */
public class Macro {
    public static final String[] PREDEFINED_COMMANDS = new String[] { AddCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, ListMacroCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, RemoveMacroCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD, UpdateCommand.COMMAND_WORD, ViewCommand.COMMAND_WORD};

    private final Alias alias;
    private final SavedCommandString savedCommandString;

    /**
     * Every field must be present and not null.
     */
    public Macro(Alias alias, SavedCommandString savedCommandString) {
        requireAllNonNull(alias, savedCommandString);
        this.alias = alias;
        this.savedCommandString = savedCommandString;
    }

    public Alias getAlias() {
        return alias;
    }

    public SavedCommandString getSavedCommandString() {
        return savedCommandString;
    }

    /**
     * Returns true if both macros have the same alias.
     * This defines a weaker notion of equality between two macros.
     */
    public boolean isSameMacro(Macro otherMacro) {
        if (otherMacro == this) {
            return true;
        }

        return otherMacro != null
                && otherMacro.getAlias().equals(getAlias());
    }

    /**
     * Returns true if both macros have the same identity and data fields.
     * This defines a stronger notion of equality between two macros.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Macro)) {
            return false;
        }

        Macro otherMacro = (Macro) other;
        return otherMacro.getAlias().equals(getAlias())
                && otherMacro.getSavedCommandString().equals(getSavedCommandString());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(alias, savedCommandString);
    }

    @Override
    public String toString() {
        return getAlias() + "\nSaved Command String: " + getSavedCommandString();
    }
}

