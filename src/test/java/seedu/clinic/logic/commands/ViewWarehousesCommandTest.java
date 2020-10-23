package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalClinic;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.Messages;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.parser.Type;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForWarehouse;
import seedu.clinic.model.warehouse.Warehouse;

public class ViewWarehousesCommandTest {
    private final Type type = Type.WAREHOUSE;
    private final Index index1 = Index.fromOneBased(1);
    private final Index index2 = Index.fromOneBased(20);

    private Model model = new ModelManager(getTypicalClinic(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClinic(), new UserPrefs());

    @Test
    public void equals() {

        ViewCommand viewFirstCommand = new ViewCommand(type, index1);
        ViewCommand viewSecondCommand = new ViewCommand(type, index2);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(type, index1);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different supplier -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void execute_viewFirstWarehouse_success() {
        List<Warehouse> warehouseList = model.getFilteredWarehouseList();
        Warehouse warehouseToView = warehouseList.get(index1.getZeroBased());
        ViewCommand viewFirstCommand = new ViewCommand(type, index1);
        NameContainsKeywordsPredicateForWarehouse warehousePredicate =
                new NameContainsKeywordsPredicateForWarehouse(
                        Arrays.asList(warehouseToView.getName().toString()));
        model.updateFilteredWarehouseList(warehousePredicate);
        expectedModel.updateFilteredWarehouseList(warehousePredicate);
        String expectedMessage = String.format(Messages.MESSAGE_WAREHOUSE_LISTED_OVERVIEW,
                expectedModel.getFilteredWarehouseList().size());
        assertCommandSuccess(viewFirstCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_viewOutOfRange_failure() {
        ViewCommand viewFirstCommand = new ViewCommand(type, index2);
        String expectedMessage = Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX;
        assertCommandFailure(viewFirstCommand, model, expectedMessage);
    }
}
