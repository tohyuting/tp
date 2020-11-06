package seedu.clinic.model.attribute;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.AppUtil.checkArgument;

/**
 * Represents a name for the supplier/warehouse/product in CLI-nic app.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS = "Names should start with an alphanumeric character,"
            + " and it should not be blank. Name should not contain forward slashes (i.e. '/')";

    /**
     * The first character of the name must be alphanumeric.
     * String must not contain forward slashes (i.e. '/')
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][^/]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if two Name objects are equal, by doing a comparison using {@code fullName}. This check
     * is a weaker equality as it is case sensitive.
     */
    public boolean equalsCaseSensitive(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.toLowerCase().equals(((Name) other).fullName.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
