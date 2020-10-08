package seedu.clinic.logic.commands;

import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalClinic;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.Clinic;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyClinic_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyClinic_success() {
        Model model = new ModelManager(getTypicalClinic(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalClinic(), new UserPrefs());
        expectedModel.setClinic(new Clinic());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
