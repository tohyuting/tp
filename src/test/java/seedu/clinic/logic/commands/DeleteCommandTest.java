package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_PANADOL;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static seedu.clinic.logic.commands.CommandTestUtil.showWarehouseAtIndex;
import static seedu.clinic.logic.commands.DeleteCommand.MESSAGE_DELETE_PRODUCT_IN_SUPPLIER_SUCCESS;
import static seedu.clinic.logic.commands.DeleteCommand.MESSAGE_DELETE_PRODUCT_IN_WAREHOUSE_SUCCESS;
import static seedu.clinic.logic.parser.Type.SUPPLIER;
import static seedu.clinic.logic.parser.Type.SUPPLIER_PRODUCT;
import static seedu.clinic.logic.parser.Type.WAREHOUSE;
import static seedu.clinic.logic.parser.Type.WAREHOUSE_PRODUCT;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_WAREHOUSE;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_WAREHOUSE;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalSupplierOnlyClinic;
import static seedu.clinic.testutil.TypicalWarehouse.getTypicalWarehouseOnlyClinic;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.Messages;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;
import seedu.clinic.testutil.SupplierBuilder;
import seedu.clinic.testutil.WarehouseBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model modelForSupplier = new ModelManager(getTypicalSupplierOnlyClinic(), new UserPrefs(),
            new UserMacros(), new CommandHistory());
    private Model modelForWarehouse = new ModelManager(getTypicalWarehouseOnlyClinic(), new UserPrefs(),
            new UserMacros(), new CommandHistory());

    @Test
    public void execute_noModel_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(SUPPLIER, INDEX_FIRST_SUPPLIER);

        assertThrows(NullPointerException.class, () -> deleteCommand.execute(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Supplier supplierToDelete = modelForSupplier.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(SUPPLIER, INDEX_FIRST_SUPPLIER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierToDelete);

        ModelManager expectedModel = new ModelManager(modelForSupplier.getClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        expectedModel.deleteSupplier(supplierToDelete);
        expectedModel.saveVersionedClinic();
        assertCommandSuccess(deleteCommand, modelForSupplier, expectedMessage, expectedModel);

        Warehouse warehouseToDelete = modelForWarehouse.getFilteredWarehouseList()
                .get(INDEX_FIRST_WAREHOUSE.getZeroBased());
        deleteCommand = new DeleteCommand(WAREHOUSE, INDEX_FIRST_WAREHOUSE);

        expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_WAREHOUSE_SUCCESS, warehouseToDelete);

        expectedModel = new ModelManager(modelForWarehouse.getClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        expectedModel.deleteWarehouse(warehouseToDelete);
        expectedModel.saveVersionedClinic();
        assertCommandSuccess(deleteCommand, modelForWarehouse, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexValidProductNameDelete_success() {
        Supplier supplierToUpdate = modelForSupplier.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        Supplier expectedSupplier = new SupplierBuilder()
                .withName(supplierToUpdate.getName().fullName)
                .withEmail(supplierToUpdate.getEmail().value)
                .withPhone(supplierToUpdate.getPhone().value)
                .withRemark(supplierToUpdate.getRemark().value).build();
        Name productToDeleteName = new Name(VALID_PRODUCT_NAME_PANADOL);
        DeleteCommand deleteCommand = new DeleteCommand(SUPPLIER_PRODUCT, INDEX_FIRST_SUPPLIER, productToDeleteName);

        String expectedMessage = String.format(MESSAGE_DELETE_PRODUCT_IN_SUPPLIER_SUCCESS,
                productToDeleteName, supplierToUpdate.getName());

        ModelManager expectedModel = new ModelManager(modelForSupplier.getClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        expectedModel.setSupplier(supplierToUpdate, expectedSupplier);
        expectedModel.saveVersionedClinic();
        assertCommandSuccess(deleteCommand, modelForSupplier, expectedMessage, expectedModel);

        //test warehouse product
        Warehouse warehouseToUpdate = modelForWarehouse.getFilteredWarehouseList()
                .get(INDEX_FIRST_WAREHOUSE.getZeroBased());
        Warehouse expectedWarehouse = new WarehouseBuilder()
                .withName(warehouseToUpdate.getName().fullName)
                .withAddress(warehouseToUpdate.getAddress().value)
                .withPhone(warehouseToUpdate.getPhone().value)
                .withRemark(warehouseToUpdate.getRemark().value).build();
        productToDeleteName = new Name(VALID_PRODUCT_NAME_PANADOL);
        deleteCommand = new DeleteCommand(WAREHOUSE_PRODUCT, INDEX_FIRST_WAREHOUSE, productToDeleteName);

        expectedMessage = String.format(MESSAGE_DELETE_PRODUCT_IN_WAREHOUSE_SUCCESS,
                productToDeleteName, warehouseToUpdate.getName());

        expectedModel = new ModelManager(modelForWarehouse.getClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        expectedModel.setWarehouse(warehouseToUpdate, expectedWarehouse);
        expectedModel.saveVersionedClinic();
        assertCommandSuccess(deleteCommand, modelForWarehouse, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(modelForSupplier.getFilteredSupplierList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(SUPPLIER, outOfBoundIndex);

        assertCommandFailure(deleteCommand, modelForSupplier, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);

        outOfBoundIndex = Index.fromOneBased(modelForWarehouse.getFilteredWarehouseList().size() + 1);
        deleteCommand = new DeleteCommand(WAREHOUSE, outOfBoundIndex);

        assertCommandFailure(deleteCommand, modelForWarehouse, Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexInvalidProductNameDeletion_throwsCommandException() {
        Supplier supplierToUpdate = modelForSupplier.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        Name invalidProductToDeleteName = new Name(VALID_PRODUCT_NAME_ASPIRIN);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PRODUCT_NAME_IN_SUPPLIER,
                invalidProductToDeleteName, supplierToUpdate.getName());
        DeleteCommand deleteCommand =
                new DeleteCommand(SUPPLIER_PRODUCT, INDEX_FIRST_SUPPLIER, invalidProductToDeleteName);

        assertCommandFailure(deleteCommand, modelForSupplier, expectedMessage);

        Warehouse warehouseToUpdate = modelForWarehouse.getFilteredWarehouseList()
                .get(INDEX_FIRST_WAREHOUSE.getZeroBased());
        invalidProductToDeleteName = new Name(VALID_PRODUCT_NAME_ASPIRIN);

        expectedMessage = String.format(Messages.MESSAGE_INVALID_PRODUCT_NAME_IN_WAREHOUSE,
                invalidProductToDeleteName, warehouseToUpdate.getName());
        deleteCommand = new DeleteCommand(WAREHOUSE_PRODUCT, INDEX_FIRST_WAREHOUSE, invalidProductToDeleteName);

        assertCommandFailure(deleteCommand, modelForWarehouse, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showSupplierAtIndex(modelForSupplier, INDEX_FIRST_SUPPLIER);

        Supplier supplierToDelete = modelForSupplier.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(SUPPLIER, INDEX_FIRST_SUPPLIER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierToDelete);

        Model expectedModel = new ModelManager(modelForSupplier.getClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        expectedModel.deleteSupplier(supplierToDelete);
        expectedModel.saveVersionedClinic();
        showNoSupplier(expectedModel);

        assertCommandSuccess(deleteCommand, modelForSupplier, expectedMessage, expectedModel);

        showWarehouseAtIndex(modelForWarehouse, INDEX_FIRST_WAREHOUSE);

        Warehouse warehouseToDelete = modelForWarehouse.getFilteredWarehouseList()
                .get(INDEX_FIRST_WAREHOUSE.getZeroBased());
        deleteCommand = new DeleteCommand(WAREHOUSE, INDEX_FIRST_WAREHOUSE);

        expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_WAREHOUSE_SUCCESS, warehouseToDelete);

        expectedModel = new ModelManager(modelForWarehouse.getClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        expectedModel.deleteWarehouse(warehouseToDelete);
        expectedModel.saveVersionedClinic();
        showNoWarehouse(expectedModel);

        assertCommandSuccess(deleteCommand, modelForWarehouse, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showSupplierAtIndex(modelForSupplier, INDEX_FIRST_SUPPLIER);

        Index outOfBoundIndex = INDEX_SECOND_SUPPLIER;
        // ensures that outOfBoundIndex is still in bounds of clinic list
        assertTrue(outOfBoundIndex.getZeroBased() < modelForSupplier.getClinic().getSupplierList().size());

        DeleteCommand deleteCommand = new DeleteCommand(SUPPLIER, outOfBoundIndex);

        assertCommandFailure(deleteCommand, modelForSupplier, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);

        showWarehouseAtIndex(modelForWarehouse, INDEX_FIRST_WAREHOUSE);

        outOfBoundIndex = INDEX_SECOND_WAREHOUSE;
        // ensures that outOfBoundIndex is still in bounds of clinic list
        assertTrue(outOfBoundIndex.getZeroBased() < modelForWarehouse.getClinic().getWarehouseList().size());

        deleteCommand = new DeleteCommand(WAREHOUSE, outOfBoundIndex);

        assertCommandFailure(deleteCommand, modelForWarehouse, Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(SUPPLIER, INDEX_FIRST_SUPPLIER);
        DeleteCommand deleteSecondCommand = new DeleteCommand(SUPPLIER, INDEX_SECOND_SUPPLIER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(SUPPLIER, INDEX_FIRST_SUPPLIER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different supplier -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered supplier list to show no one.
     */
    private void showNoSupplier(Model model) {
        model.updateFilteredSupplierList(p -> false);

        assertTrue(model.getFilteredSupplierList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered warehouse list to show no one.
     */
    private void showNoWarehouse(Model model) {
        model.updateFilteredWarehouseList(p -> false);

        assertTrue(model.getFilteredWarehouseList().isEmpty());
    }
}
