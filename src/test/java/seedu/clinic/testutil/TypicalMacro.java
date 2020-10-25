package seedu.clinic.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.clinic.model.UserMacros;
import seedu.clinic.model.macro.Macro;

public class TypicalMacro {
    public static final Macro MACRO_AW = new MacroBuilder().withAlias("aw")
            .withSavedCommandString("add ct/w").build();
    public static final Macro MACRO_AS = new MacroBuilder().withAlias("as")
            .withSavedCommandString("add ct/s").build();
    public static final Macro MACRO_US = new MacroBuilder().withAlias("us")
            .withSavedCommandString("update ct/s").build();
    public static final Macro MACRO_UW = new MacroBuilder().withAlias("uw")
            .withSavedCommandString("update ct/w").build();
    public static final Macro MACRO_VW = new MacroBuilder().withAlias("vw")
            .withSavedCommandString("view ct/w").build();
    public static final Macro MACRO_VS = new MacroBuilder().withAlias("vs")
            .withSavedCommandString("view ct/s").build();

    private TypicalMacro() {} // prevents instantiation

    /**
     * Returns an {@code UserMacros} with all the typical macros.
     */
    public static UserMacros getTypicalUserMacros() {
        UserMacros userMacros = new UserMacros();

        for (Macro macro : getTypicalMacros()) {
            userMacros.addMacro(macro);
        }

        return userMacros;
    }

    public static List<Macro> getTypicalMacros() {
        return new ArrayList<>(Arrays.asList(MACRO_AS, MACRO_AW, MACRO_US, MACRO_UW, MACRO_VS, MACRO_VW));
    }
}
