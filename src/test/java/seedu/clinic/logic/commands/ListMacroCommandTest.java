package seedu.clinic.logic.commands;

import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalMacro.getTypicalUserMacros;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;

public class ListMacroCommandTest {

    private Model model;
    private Model expectedModel;
    private Model emptyModel = new ModelManager(new Clinic(), new UserPrefs(), new UserMacros());

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new Clinic(), new UserPrefs(), getTypicalUserMacros());
        expectedModel = new ModelManager(new Clinic(), new UserPrefs(), model.getUserMacros());
    }

    @Test
    public void execute_listMacroWithEmptyList_throwCommandException() {
        assertThrows(CommandException.class, () -> new ListMacroCommand().execute(emptyModel));
    }
}
