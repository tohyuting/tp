package seedu.clinic.logic.parser;

import java.util.Arrays;
import java.util.Optional;

import seedu.clinic.model.Model;
import seedu.clinic.model.macro.Macro;

public class MacroParser {

    /**
     * Converts user input into the corresponding command string if a macro is identified to be used.
     *
     * @param model the model which contains the user macros
     * @param userInput full user input string
     * @return the command string saved by the macro, or the original input string if no macro is used
     */
    public String parseMacro(Model model, String userInput) {
        String[] inputWords = userInput.trim().split(" ");
        Optional<Macro> targetMacro = model.getMacro(inputWords[0]);

        if (targetMacro.isEmpty()) { // macro does not exist
            return userInput;
        }

        String savedCommandText = targetMacro.get().getSavedCommandString().internalString;
        String argumentString = String.join(" ", Arrays.copyOfRange(inputWords, 1, inputWords.length));
        return savedCommandText + " " + argumentString;
    }
}
