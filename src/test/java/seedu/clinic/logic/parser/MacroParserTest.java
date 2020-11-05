package seedu.clinic.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.WAREHOUSE_NAME_DESC_A;
import static seedu.clinic.testutil.TypicalMacro.MACRO_AW;
import static seedu.clinic.testutil.TypicalMacro.getTypicalUserMacros;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.Clinic;
import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;

class MacroParserTest {
    private final MacroParser macroParser = new MacroParser();
    private final Model model = new ModelManager(new Clinic(), new UserPrefs(),
            new UserMacros(getTypicalUserMacros()), new CommandHistory());


    @Test
    void parseMacro_macroFound_macroTranslated() {
        String additionalArguments = WAREHOUSE_NAME_DESC_A + PHONE_DESC_AMY + ADDRESS_DESC_AMY + REMARK_DESC_AMY;
        String inputString = MACRO_AW.getAlias().aliasString + additionalArguments;
        String expectedOutput = MACRO_AW.getSavedCommandString().internalString + additionalArguments;
        String parsedOutput = macroParser.parseMacro(model, inputString);
        assertEquals(expectedOutput, parsedOutput);
    }

    @Test
    void parseMacro_macroNotFound_inputStringReturned() {
        String additionalArguments = WAREHOUSE_NAME_DESC_A;
        String inputString = "lorem" + additionalArguments;
        String expectedOutput = "lorem" + additionalArguments;
        String parsedOutput = macroParser.parseMacro(model, inputString);
        assertEquals(expectedOutput, parsedOutput);
    }

    @Test
    void parseMacro_macroFoundAndErroneousUserInput_macroTranslated() {
        String additionalArguments = " lorem ipsum";
        String inputString = MACRO_AW.getAlias().aliasString + additionalArguments;
        String expectedOutput = MACRO_AW.getSavedCommandString().internalString + additionalArguments;
        String parsedOutput = macroParser.parseMacro(model, inputString);
        assertEquals(expectedOutput, parsedOutput);
    }

    @Test
    void parseMacro_macroNotFoundAndErroneousUserInput_inputStringReturned() {
        String inputString = "lorem ipsum";
        String expectedOutput = "lorem ipsum";
        String parsedOutput = macroParser.parseMacro(model, inputString);
        assertEquals(expectedOutput, parsedOutput);
    }

}
