package seedu.clinic.model.macro;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.AppUtil.checkArgument;
import static seedu.clinic.logic.parser.ClinicParser.BASIC_COMMAND_FORMAT;
import static seedu.clinic.model.macro.Macro.PREDEFINED_COMMANDS;

import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * Represents a savedCommandString for a macro.
 * Guarantees: immutable; is valid as declared in {@link #isValidSavedCommandString(String)}
 */
public class SavedCommandString {

    public static final String MESSAGE_CONSTRAINTS = "Command strings should be a pre-defined command word "
            + "(except for assignmacro), optionally followed by arguments. ";

    public final String internalString;

    /**
     * Constructs a {@code SavedCommandString}.
     *
     * @param internalString A valid string.
     */
    public SavedCommandString(String internalString) {
        requireNonNull(internalString);
        checkArgument(isValidSavedCommandString(internalString), MESSAGE_CONSTRAINTS);
        this.internalString = internalString;
    }

    /**
     * Returns true if a given string is a valid string for savedCommandString.
     */
    public static boolean isValidSavedCommandString(String test) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(test);
        if (!matcher.matches()) {
            return false;
        }
        final String commandWord = matcher.group("commandWord");
        return Arrays.asList(PREDEFINED_COMMANDS).contains(commandWord.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SavedCommandString // instanceof handles nulls
                && internalString.equals(((SavedCommandString) other).internalString)); // state check
    }

    @Override
    public int hashCode() {
        return internalString.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return internalString;
    }

}

