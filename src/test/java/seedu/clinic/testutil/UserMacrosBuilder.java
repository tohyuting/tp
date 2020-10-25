package seedu.clinic.testutil;

import seedu.clinic.model.UserMacros;
import seedu.clinic.model.macro.Macro;

/**
 * A utility class to help with building userMacros objects.
 * Example usage: <br>
 *     {@code UserMacros userMacros = new UserMacrosBuilder().withMacro(MACRO_A).build();}
 */
public class UserMacrosBuilder {

    private UserMacros userMacros;

    public UserMacrosBuilder() {
        userMacros = new UserMacros();
    }

    public UserMacrosBuilder(UserMacros userMacros) {
        this.userMacros = userMacros;
    }

    /**
     * Adds a new {@code Supplier} to the {@code Clinic} that we are building.
     */
    public UserMacrosBuilder withMacro(Macro macro) {
        userMacros.addMacro(macro);
        return this;
    }

    public UserMacros build() {
        return userMacros;
    }
}
