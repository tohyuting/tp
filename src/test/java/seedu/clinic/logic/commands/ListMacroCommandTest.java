package seedu.clinic.logic.commands;

import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalMacro.getTypicalUserMacros;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;

public class ListMacroCommandTest {

    @Test
    public void execute_listMacroWithEmptyList_throwCommandException() {
        Model emptyModel = new ModelManager(new Clinic(), new UserPrefs(), new UserMacros(), new CommandHistory());
        assertThrows(CommandException.class, () -> new ListMacroCommand().execute(emptyModel));
    }

    @Test
    public void execute_listMacro_success() {
        Model model = new ModelManager(new Clinic(), new UserPrefs(), getTypicalUserMacros(), new CommandHistory());
        Model expectedModel = new ModelManager(new Clinic(), new UserPrefs(), model.getUserMacros(),
                new CommandHistory());
        String expectedListString = "1. as\nSaved Command String: add ct/s\n\n"
                + "2. aw\nSaved Command String: add ct/w\n\n"
                + "3. us\nSaved Command String: update ct/s\n\n"
                + "4. uw\nSaved Command String: update ct/w\n\n"
                + "5. vs\nSaved Command String: view ct/s\n\n"
                + "6. vw\nSaved Command String: view ct/w\n\n";
        assertCommandSuccess(new ListMacroCommand(), model,
                String.format(ListMacroCommand.MESSAGE_SUCCESS, expectedListString), expectedModel);
    }
}
