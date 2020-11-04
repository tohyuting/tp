package seedu.clinic.logic.commands;

import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static seedu.clinic.logic.commands.CommandTestUtil.showWarehouseAtIndex;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_WAREHOUSE;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalVersionedClinic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalVersionedClinic(), new UserPrefs(), new UserMacros(), new CommandHistory());
        expectedModel = new ModelManager(model.getClinic(), new UserPrefs(), new UserMacros(), new CommandHistory());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFilteredSupplier_showsAllWarehouseShowsOneSupplier() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFilteredWarehouse_showsAllSupplierShowsOneWarehouse() {
        showWarehouseAtIndex(model, INDEX_FIRST_WAREHOUSE);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
