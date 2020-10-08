package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalClinic;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.Messages;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.supplier.Supplier;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalClinic(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Supplier supplierToDelete = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SUPPLIER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierToDelete);

        ModelManager expectedModel = new ModelManager(model.getClinic(), new UserPrefs());
        expectedModel.deleteSupplier(supplierToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSupplierList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        Supplier supplierToDelete = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SUPPLIER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierToDelete);

        Model expectedModel = new ModelManager(model.getClinic(), new UserPrefs());
        expectedModel.deleteSupplier(supplierToDelete);
        showNoSupplier(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        Index outOfBoundIndex = INDEX_SECOND_SUPPLIER;
        // ensures that outOfBoundIndex is still in bounds of clinic list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getClinic().getSupplierList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_SUPPLIER);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_SUPPLIER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_SUPPLIER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different supplier -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoSupplier(Model model) {
        model.updateFilteredSupplierList(p -> false);

        assertTrue(model.getFilteredSupplierList().isEmpty());
    }
}
