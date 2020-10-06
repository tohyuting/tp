package seedu.address.model.warehouse;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Warehouse's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWarehouse(String)}
 */
public class Remark {
    public static final String MESSAGE_CONSTRAINTS =
            "Remarks can take any values, and it should not have more that 100 characters";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidWarehouse(remark), MESSAGE_CONSTRAINTS);
        value = remark;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidWarehouse(String remark) {
        if (remark.isEmpty()) {
            return true;
        }
        return remark.matches(VALIDATION_REGEX) && remark.length() <= 100;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
