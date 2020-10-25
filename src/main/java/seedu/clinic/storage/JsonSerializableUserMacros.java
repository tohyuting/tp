package seedu.clinic.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.macro.Macro;

/**
 * An Immutable Macro list that is serializable to JSON format.
 */
@JsonRootName(value = "userMacros")
class JsonSerializableUserMacros {

    public static final String MESSAGE_DUPLICATE_MACRO = "List contains duplicate macros(s).";

    private final List<JsonAdaptedMacro> macros = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableUserMacros} with the given macros.
     */
    @JsonCreator
    public JsonSerializableUserMacros(@JsonProperty("macros") List<JsonAdaptedMacro> macros) {
        this.macros.addAll(macros);
    }

    /**
     * Converts a given {@code ReadOnlyUserMacros} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUserMacros}.
     */
    public JsonSerializableUserMacros(ReadOnlyUserMacros source) {
        macros.addAll(source.getMacroList().stream().map(JsonAdaptedMacro::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this userMacros into the model's {@code UserMacros} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UserMacros toModelType() throws IllegalValueException {
        UserMacros userMacros = new UserMacros();
        for (JsonAdaptedMacro jsonAdaptedMacro : macros) {
            Macro macro = jsonAdaptedMacro.toModelType();
            if (userMacros.hasMacro(macro)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MACRO);
            }
            userMacros.addMacro(macro);
        }

        return userMacros;
    }

}
