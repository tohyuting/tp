<<<<<<< HEAD:src/main/java/seedu/clinic/model/attribute/Name.java
package seedu.address.model.attribute;
=======
package seedu.clinic.model.supplier;
>>>>>>> upstream/master:src/main/java/seedu/clinic/model/supplier/Name.java

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.AppUtil.checkArgument;

/**
<<<<<<< HEAD:src/main/java/seedu/clinic/model/attribute/Name.java
 * Represents a name for the supplier/warehouse/product in CLI-nic app.
=======
 * Represents a Supplier's or Product's name in the CLI-nic app.
>>>>>>> upstream/master:src/main/java/seedu/clinic/model/supplier/Name.java
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the name must be alphanumeric.
     * The .
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Print}]*";

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


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
