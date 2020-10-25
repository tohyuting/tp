package seedu.clinic.testutil;

import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.model.macro.SavedCommandString;

/**
 * A utility class to help with building Macro objects.
 */
public class MacroBuilder {

    public static final String DEFAULT_ALIAS = "u";
    public static final String DEFAULT_SAVED_COMMAND_STRING = "update";

    private Alias alias;
    private SavedCommandString savedCommandString;

    /**
     * Creates a {@code MacroBuilder} with the default details.
     */
    public MacroBuilder() {
        alias = new Alias(DEFAULT_ALIAS);
        savedCommandString = new SavedCommandString(DEFAULT_SAVED_COMMAND_STRING);
    }

    /**
     * Initializes the MacroBuilder with the data of {@code macroToCopy}.
     */
    public MacroBuilder(Macro macroToCopy) {
        alias = macroToCopy.getAlias();
        savedCommandString = macroToCopy.getSavedCommandString();
    }

    /**
     * Sets the {@code Alias} of the {@code Macro} that we are building.
     */
    public MacroBuilder withAlias(String alias) {
        this.alias = new Alias(alias);
        return this;
    }

    /**
     * Sets the {@code SavedCommandString} of the {@code Macro} that we are building.
     */
    public MacroBuilder withSavedCommandString(String savedCommandString) {
        this.savedCommandString = new SavedCommandString(savedCommandString);
        return this;
    }

    public Macro build() {
        return new Macro(alias, savedCommandString);
    }
}
