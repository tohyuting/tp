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

class UndoCommandTest {

    @Test
    public void execute_cannotUndoClinic_throwCommandException() {
        Model model = new ModelManager(getTypicalVersionedClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_canUndoClinic_success() {
        Model model = new ModelManager(getTypicalVersionedClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        Model expectedModel = new ModelManager(getTypicalVersionedClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        model.setClinic(new Clinic());
        expectedModel.setClinic(new Clinic());
        expectedModel.undoClinic();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
