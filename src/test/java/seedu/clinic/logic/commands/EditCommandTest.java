package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_NAME_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_50;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static seedu.clinic.logic.commands.CommandTestUtil.showWarehouseAtIndex;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_SUPPLIER_UNCHANGED;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_WAREHOUSE_UNCHANGED;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_WAREHOUSE;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_WAREHOUSE;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalVersionedClinic;

import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.EditCommand.EditSupplierDescriptor;
import seedu.clinic.logic.commands.EditCommand.EditWarehouseDescriptor;
import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Email;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;
import seedu.clinic.testutil.EditSupplierDescriptorBuilder;
import seedu.clinic.testutil.EditWarehouseDescriptorBuilder;
import seedu.clinic.testutil.SupplierBuilder;
import seedu.clinic.testutil.WarehouseBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalVersionedClinic(), new UserPrefs(), new UserMacros(),
            new CommandHistory());
    private Model modelBackup = new ModelManager(getTypicalVersionedClinic(), new UserPrefs(), new UserMacros(),
            new CommandHistory());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        //supplier
        Supplier editedSupplier = new SupplierBuilder()
                .withProducts(Map.of("Panadol", new String[]{"fever"})).build();
        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder(editedSupplier)
                .build();
        EditCommand editCommandSupplier = new EditCommand(INDEX_FIRST_SUPPLIER, descriptorSupplier);

        String expectedMessageSupplier = String.format(EditCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS,
                editedSupplier);

        Model expectedModelSupplier = new ModelManager(model.getClinic(), new UserPrefs(),
                new UserMacros(), new CommandHistory());
        expectedModelSupplier.setSupplier(model.getFilteredSupplierList().get(0), editedSupplier);
        expectedModelSupplier.saveVersionedClinic();
        assertCommandSuccess(editCommandSupplier, model, expectedMessageSupplier, expectedModelSupplier);

        //warehouse
        Warehouse editedWarehouse = new WarehouseBuilder().withProducts(Map.of("Panadol", 100)).build();
        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder(editedWarehouse)
                .build();
        EditCommand editCommandWarehouse = new EditCommand(INDEX_FIRST_WAREHOUSE, descriptorWarehouse);

        String expectedMessageWarehouse = String.format(EditCommand.MESSAGE_EDIT_WAREHOUSE_SUCCESS,
                editedWarehouse);

        Model expectedModelWarehouse = new ModelManager(modelBackup.getClinic(), new UserPrefs(),
                new UserMacros(), new CommandHistory());
        expectedModelWarehouse.setWarehouse(modelBackup.getFilteredWarehouseList().get(0), editedWarehouse);
        expectedModelWarehouse.saveVersionedClinic();
        assertCommandSuccess(editCommandWarehouse, modelBackup, expectedMessageWarehouse, expectedModelWarehouse);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        //supplier
        Index indexLastSupplier = Index.fromOneBased(model.getFilteredSupplierList().size());
        Supplier lastSupplier = model.getFilteredSupplierList().get(indexLastSupplier.getZeroBased());

        SupplierBuilder supplierInList = new SupplierBuilder(lastSupplier);
        Supplier editedSupplier = supplierInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .build();

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommandSupplier = new EditCommand(indexLastSupplier, descriptorSupplier);

        String expectedMessageSupplier = String.format(EditCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS,
                editedSupplier);

        Model expectedModelSupplier = new ModelManager(model.getClinic(), new UserPrefs(),
                new UserMacros(), new CommandHistory());
        expectedModelSupplier.setSupplier(lastSupplier, editedSupplier);
        expectedModelSupplier.saveVersionedClinic();
        assertCommandSuccess(editCommandSupplier, model, expectedMessageSupplier, expectedModelSupplier);

        //warehouse
        Index indexLastWarehouse = Index.fromOneBased(modelBackup.getFilteredWarehouseList().size());
        Warehouse lastWarehouse = modelBackup.getFilteredWarehouseList().get(indexLastWarehouse.getZeroBased());

        WarehouseBuilder warehouseInList = new WarehouseBuilder(lastWarehouse);
        Warehouse editedWarehouse = warehouseInList.withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_WAREHOUSE_PRODUCT_QUANTITY_50)).build();

        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder()
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_WAREHOUSE_PRODUCT_QUANTITY_50)).build();
        EditCommand editCommandWarehouse = new EditCommand(indexLastWarehouse, descriptorWarehouse);

        String expectedMessageWarehouse = String.format(EditCommand.MESSAGE_EDIT_WAREHOUSE_SUCCESS,
                editedWarehouse);

        Model expectedModelWarehouse = new ModelManager(modelBackup.getClinic(), new UserPrefs(),
                new UserMacros(), new CommandHistory());
        expectedModelWarehouse.setWarehouse(lastWarehouse, editedWarehouse);
        expectedModelWarehouse.saveVersionedClinic();
        assertCommandSuccess(editCommandWarehouse, modelBackup, expectedMessageWarehouse, expectedModelWarehouse);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        //supplier
        EditCommand editCommandSupplier = new EditCommand(INDEX_FIRST_SUPPLIER, new EditSupplierDescriptor());

        String expectedMessageSupplier = String.format(MESSAGE_SUPPLIER_UNCHANGED);

        assertCommandFailure(editCommandSupplier, model, expectedMessageSupplier);

        //warehouse
        EditCommand editCommandWarehouse = new EditCommand(INDEX_FIRST_WAREHOUSE,
                new EditWarehouseDescriptor());

        String expectedMessageWarehouse = MESSAGE_WAREHOUSE_UNCHANGED;

        assertCommandFailure(editCommandWarehouse, modelBackup, expectedMessageWarehouse);
    }

    @Test
    public void execute_filteredList_success() {
        //supplier
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        Supplier supplierInFilteredList = model.getFilteredSupplierList()
                .get(INDEX_FIRST_SUPPLIER.getZeroBased());
        Supplier editedSupplier = new SupplierBuilder(supplierInFilteredList).withName(VALID_NAME_BOB)
                .withProducts(Map.of("Panadol", new String[]{"fever"})).build();
        EditCommand editCommandSupplier = new EditCommand(INDEX_FIRST_SUPPLIER,
                new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessageSupplier = String.format(EditCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS,
                editedSupplier);

        Model expectedModelSupplier = new ModelManager(model.getClinic(), new UserPrefs(),
                new UserMacros(), new CommandHistory());
        expectedModelSupplier.setSupplier(model.getFilteredSupplierList().get(0), editedSupplier);
        expectedModelSupplier.saveVersionedClinic();
        assertCommandSuccess(editCommandSupplier, model, expectedMessageSupplier, expectedModelSupplier);

        //warehouse
        showWarehouseAtIndex(modelBackup, INDEX_FIRST_WAREHOUSE);

        Warehouse warehouseInFilteredList = modelBackup.getFilteredWarehouseList()
                .get(INDEX_FIRST_WAREHOUSE.getZeroBased());
        Warehouse editedWarehouse = new WarehouseBuilder(warehouseInFilteredList).withName(VALID_NAME_BOB)
                .withProducts(Map.of("Panadol", 100)).build();
        EditCommand editCommandWarehouse = new EditCommand(INDEX_FIRST_WAREHOUSE,
                new EditWarehouseDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessageWarehouse = String.format(EditCommand.MESSAGE_EDIT_WAREHOUSE_SUCCESS,
                editedWarehouse);

        Model expectedModelWarehouse = new ModelManager(modelBackup.getClinic(), new UserPrefs(),
                new UserMacros(), new CommandHistory());
        expectedModelWarehouse.setWarehouse(modelBackup.getFilteredWarehouseList().get(0), editedWarehouse);
        expectedModelWarehouse.saveVersionedClinic();
        assertCommandSuccess(editCommandWarehouse, modelBackup, expectedMessageWarehouse, expectedModelWarehouse);
    }

    @Test
    public void execute_duplicateSupplierUnfilteredList_failure() {
        //supplier
        Supplier firstSupplier = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder(firstSupplier).build();
        EditCommand editCommandSupplier = new EditCommand(INDEX_SECOND_SUPPLIER, descriptorSupplier);

        assertCommandFailure(editCommandSupplier, model, EditCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

    @Test
    public void execute_duplicateWarehouseUnfilteredList_failure() {
        //warehouse
        Warehouse firstWarehouse = model.getFilteredWarehouseList().get(INDEX_FIRST_WAREHOUSE.getZeroBased());
        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder(firstWarehouse)
                .build();
        EditCommand editCommandWarehouse = new EditCommand(INDEX_SECOND_WAREHOUSE, descriptorWarehouse);

        assertCommandFailure(editCommandWarehouse, model, EditCommand.MESSAGE_DUPLICATE_WAREHOUSE);
    }

    @Test
    public void execute_duplicateSupplierFilteredList_failure() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        // edit supplier in filtered list into a duplicate in clinic
        Supplier supplierInList = model.getClinic().getSupplierList().get(INDEX_SECOND_SUPPLIER
                .getZeroBased());
        EditCommand editCommandSupplier = new EditCommand(INDEX_FIRST_SUPPLIER,
                new EditSupplierDescriptorBuilder(supplierInList).build());

        assertCommandFailure(editCommandSupplier, model, EditCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

    @Test
    public void execute_duplicateWarehouseFilteredList_failure() {
        showWarehouseAtIndex(model, INDEX_FIRST_WAREHOUSE);

        // edit warehouse in filtered list into a duplicate in clinic
        Warehouse warehouseInList = model.getClinic().getWarehouseList().get(INDEX_SECOND_WAREHOUSE
                .getZeroBased());
        EditCommand editCommandWarehouse = new EditCommand(INDEX_FIRST_WAREHOUSE,
                new EditWarehouseDescriptorBuilder(warehouseInList).build());

        assertCommandFailure(editCommandWarehouse, model, EditCommand.MESSAGE_DUPLICATE_WAREHOUSE);
    }


    @Test
    public void execute_invalidSupplierIndexUnfilteredList_failure() {
        Index outOfBoundIndexSupplier = Index.fromOneBased(model.getFilteredSupplierList().size() + 1);
        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommandSupplier = new EditCommand(outOfBoundIndexSupplier, descriptorSupplier);

        assertCommandFailure(editCommandSupplier, model, MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidWarehouseIndexUnfilteredList_failure() {
        Index outOfBoundIndexWarehouse = Index.fromOneBased(model.getFilteredWarehouseList().size() + 1);
        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommandWarehouse = new EditCommand(outOfBoundIndexWarehouse, descriptorWarehouse);

        assertCommandFailure(editCommandWarehouse, model, MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
    }

    /*
     * Edit filtered supplier list where index is larger than size of filtered list,
     * but smaller than size of clinic
     */
    @Test
    public void execute_invalidSupplierIndexFilteredList_failure() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);
        Index outOfBoundIndexSupplier = INDEX_SECOND_SUPPLIER;
        // ensures that outOfBoundIndexSupplier is still in bounds of clinic list
        assertTrue(outOfBoundIndexSupplier.getZeroBased()
                < model.getClinic().getSupplierList().size());

        EditCommand editCommandSupplier = new EditCommand(outOfBoundIndexSupplier,
                new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommandSupplier, model, MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    /*
     * Edit filtered warehouse list where index is larger than size of filtered list,
     * but smaller than size of clinic
     */
    @Test
    public void execute_invalidWarehouseIndexFilteredList_failure() {
        showWarehouseAtIndex(model, INDEX_FIRST_WAREHOUSE);
        Index outOfBoundIndexWarehouse = INDEX_SECOND_WAREHOUSE;
        // ensures that outOfBoundIndexWarehouse is still in bounds of clinic list
        assertTrue(outOfBoundIndexWarehouse.getZeroBased()
                < model.getClinic().getWarehouseList().size());

        EditCommand editCommandWarehouse = new EditCommand(outOfBoundIndexWarehouse,
                new EditWarehouseDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommandWarehouse, model, MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommandSupplier = new EditCommand(INDEX_FIRST_SUPPLIER, DESC_AMY);
        final EditCommand standardCommandWarehouse = new EditCommand(INDEX_FIRST_SUPPLIER, DESC_BOB);
        // same values -> returns true
        EditSupplierDescriptor descriptorForSupplier = new EditSupplierDescriptor();
        descriptorForSupplier.setName(new Name("Test"));
        descriptorForSupplier.setEmail(new Email("testing@gmail.com"));
        descriptorForSupplier.setPhone(new Phone("98653257"));

        EditSupplierDescriptor descriptorForSupplier2 = new EditSupplierDescriptor();
        descriptorForSupplier2.setName(new Name("Test2"));
        descriptorForSupplier2.setEmail(new Email("testing@yahoo.com"));
        descriptorForSupplier2.setPhone(new Phone("98653257"));

        EditWarehouseDescriptor descriptorForWarehouse = new EditWarehouseDescriptor();
        descriptorForWarehouse.setName(new Name("warehouse here"));
        descriptorForWarehouse.setAddress(new Address("testing addr"));
        descriptorForWarehouse.setPhone(new Phone("54287163"));
        descriptorForWarehouse.setRemark(new Remark("first warehouse"));

        EditWarehouseDescriptor descriptorForWarehouse2 = new EditWarehouseDescriptor();
        descriptorForWarehouse.setName(new Name("warehouse2 here"));
        descriptorForWarehouse.setAddress(new Address("testing addr2"));
        descriptorForWarehouse.setPhone(new Phone("78564210"));
        descriptorForWarehouse.setRemark(new Remark("second warehouse"));

        EditCommand commandForSupplier = new EditCommand(INDEX_FIRST_SUPPLIER, descriptorForSupplier);
        EditCommand commandForSupplier2 = new EditCommand(INDEX_FIRST_SUPPLIER, descriptorForSupplier2);
        EditCommand commandForWarehouse = new EditCommand(INDEX_FIRST_WAREHOUSE, descriptorForWarehouse);
        EditCommand commandForWarehouse2 = new EditCommand(INDEX_FIRST_WAREHOUSE, descriptorForWarehouse2);

        //different types -> returns false
        assertFalse(commandForSupplier.equals(commandForWarehouse));
        assertFalse(commandForSupplier.equals(new ClearCommand()));
        assertFalse(commandForWarehouse.equals(new ClearCommand()));

        //different values -> returns false
        assertFalse(commandForSupplier.equals(commandForSupplier2));
        assertFalse(commandForWarehouse.equals(commandForWarehouse2));

        // same object -> returns true
        assertTrue(commandForSupplier.equals(commandForSupplier));
        assertTrue(commandForWarehouse2.equals(commandForWarehouse2));

        // null -> returns false
        assertFalse(commandForSupplier.equals(null));
        assertFalse(commandForWarehouse.equals(null));

        // different index -> returns false
        assertFalse(commandForSupplier.equals(new EditCommand(INDEX_SECOND_SUPPLIER, DESC_AMY)));
        assertFalse(commandForWarehouse.equals(new EditCommand(INDEX_SECOND_WAREHOUSE, DESC_BOB)));

        // different descriptor -> returns false
        assertFalse(standardCommandSupplier.equals(new EditCommand(INDEX_FIRST_SUPPLIER, DESC_BOB)));
        assertFalse(standardCommandWarehouse.equals(commandForWarehouse));

    }

}
