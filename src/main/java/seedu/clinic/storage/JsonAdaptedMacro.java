package seedu.clinic.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.model.macro.SavedCommandString;

/**
 * Jackson-friendly version of {@link Macro}.
 */
class JsonAdaptedMacro {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Macro's %s field is missing!";

    private final String alias;
    private final String savedCommandString;

    /**
     * Constructs a {@code JsonAdaptedMacro} with the given macro details.
     */
    @JsonCreator
    public JsonAdaptedMacro(@JsonProperty("alias") String alias,
            @JsonProperty("savedCommandString") String savedCommandString) {
        this.alias = alias;
        this.savedCommandString = savedCommandString;
    }

    /**
     * Converts a given {@code Macro} into this class for Jackson use.
     */
    public JsonAdaptedMacro(Macro source) {
        alias = source.getAlias().aliasString;
        savedCommandString = source.getSavedCommandString().internalString;
    }

    /**
     * Converts this Jackson-friendly adapted macro object into the model's {@code Macro} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted macro.
     */
    public Macro toModelType() throws IllegalValueException {

        if (alias == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Alias.class.getSimpleName()));
        }
        if (!Alias.isValidAlias(alias)) {
            throw new IllegalValueException(Alias.MESSAGE_CONSTRAINTS);
        }
        final Alias modelAlias = new Alias(alias);

        if (savedCommandString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SavedCommandString.class.getSimpleName()));
        }
        if (!SavedCommandString.isValidSavedCommandString(savedCommandString)) {
            throw new IllegalValueException(SavedCommandString.MESSAGE_CONSTRAINTS);
        }
        final SavedCommandString modelSavedCommandString = new SavedCommandString(savedCommandString);

        return new Macro(modelAlias, modelSavedCommandString);
    }

}

