package seedu.clinic.logic.commands;

import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalVersionedClinic;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.Clinic;
import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyClinic_throwsCommandException() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.saveVersionedClinic();
        assertCommandFailure(new ClearCommand(), model, ClearCommand.MESSAGE_EMPTY_CLINIC);
    }

    @Test
    public void execute_nonEmptyClinic_success() {
        Model model = new ModelManager(getTypicalVersionedClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        Model expectedModel = new ModelManager(getTypicalVersionedClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        expectedModel.setClinic(new Clinic());
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
