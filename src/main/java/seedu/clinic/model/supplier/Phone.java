<<<<<<< HEAD:src/main/java/seedu/clinic/model/attribute/Phone.java
package seedu.address.model.attribute;
=======
package seedu.clinic.model.supplier;
>>>>>>> upstream/master:src/main/java/seedu/clinic/model/supplier/Phone.java

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.AppUtil.checkArgument;

/**
<<<<<<< HEAD:src/main/java/seedu/clinic/model/attribute/Phone.java
 * Represents a phone number for the supplier/warehouse/product in CLI-nic app.
=======
 * Represents a Supplier's phone number in the CLI-nic app.
>>>>>>> upstream/master:src/main/java/seedu/clinic/model/supplier/Phone.java
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
