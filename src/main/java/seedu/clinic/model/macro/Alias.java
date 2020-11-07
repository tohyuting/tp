package seedu.clinic.model.macro;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.AppUtil.checkArgument;
import static seedu.clinic.model.macro.Macro.PREDEFINED_COMMANDS;

import java.util.Arrays;

/**
 * Represents an alias for a macro.
 * Guarantees: immutable; is valid as declared in {@link #isValidAlias(String)}
 */
public class Alias {
    public static final String MESSAGE_CONSTRAINTS =
            "Alias should contain alphanumeric characters or underscores, should not be an predefined command"
                    + " word, and it should not be blank";
    public static final String VALIDATION_REGEX = "\\w+";

    public final String aliasString;

    /**
     * Constructs an {@code Alias}.
     *
     * @param aliasString A valid alias string.
     */
    public Alias(String aliasString) {
        requireNonNull(aliasString);
        checkArgument(isValidAlias(aliasString), MESSAGE_CONSTRAINTS);
        this.aliasString = aliasString;
    }

    /**
     * Returns true if a given string is a valid alias.
     */
    public static boolean isValidAlias(String test) {
        return test.matches(VALIDATION_REGEX) && !Arrays.asList(PREDEFINED_COMMANDS).contains(test);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Alias // instanceof handles nulls
                && aliasString.equals(((Alias) other).aliasString)); // state check
    }

    @Override
    public int hashCode() {
        return aliasString.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return aliasString;
    }

}
