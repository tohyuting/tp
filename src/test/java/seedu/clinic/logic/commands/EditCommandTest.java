package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_BOB;
import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Email;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_WAREHOUSE;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;


import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.EditCommand.EditSupplierDescriptor;
import seedu.clinic.logic.commands.EditCommand.EditWarehouseDescriptor;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    //private Model model = new ModelManager(getTypicalClinic(), new UserPrefs());
    /*
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Supplier editedSupplier = new SupplierBuilder().build();
        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder(editedSupplier).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SUPPLIER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier);

        Model expectedModel = new ModelManager(new Clinic(model.getClinic()), new UserPrefs());
        expectedModel.setSupplier(model.getFilteredSupplierList().get(0), editedSupplier);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastSupplier = Index.fromOneBased(model.getFilteredSupplierList().size());
        Supplier lastSupplier = model.getFilteredSupplierList().get(indexLastSupplier.getZeroBased());

        SupplierBuilder supplierInList = new SupplierBuilder(lastSupplier);
        Supplier editedSupplier = supplierInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withProducts(Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS})).build();

        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withProducts(Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS})).build();
        EditCommand editCommand = new EditCommand(indexLastSupplier, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier);

        Model expectedModel = new ModelManager(new Clinic(model.getClinic()), new UserPrefs());
        expectedModel.setSupplier(lastSupplier, editedSupplier);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SUPPLIER, new EditSupplierDescriptor());
        Supplier editedSupplier = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier);

        Model expectedModel = new ModelManager(new Clinic(model.getClinic()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        Supplier supplierInFilteredList = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        Supplier editedSupplier = new SupplierBuilder(supplierInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SUPPLIER,
                new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier);

        Model expectedModel = new ModelManager(new Clinic(model.getClinic()), new UserPrefs());
        expectedModel.setSupplier(model.getFilteredSupplierList().get(0), editedSupplier);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSupplierUnfilteredList_failure() {
        Supplier firstSupplier = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder(firstSupplier).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_SUPPLIER, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

    @Test
    public void execute_duplicateSupplierFilteredList_failure() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        // edit supplier in filtered list into a duplicate in clinic
        Supplier supplierInList = model.getClinic().getSupplierList().get(INDEX_SECOND_SUPPLIER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SUPPLIER,
                new EditSupplierDescriptorBuilder(supplierInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

    @Test
    public void execute_invalidSupplierIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSupplierList().size() + 1);
        EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }
    */

    /*
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of clinic
     */
    /*
    @Test
    public void execute_invalidSupplierIndexFilteredList_failure() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);
        Index outOfBoundIndex = INDEX_SECOND_SUPPLIER;
        // ensures that outOfBoundIndex is still in bounds of clinic list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getClinic().getSupplierList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }*/

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_SUPPLIER, DESC_AMY);

        // same values -> returns true
        EditSupplierDescriptor descriptorWithSameValues = new EditSupplierDescriptor();
        descriptorWithSameValues.setName(new Name("Test"));
        descriptorWithSameValues.setEmail(new Email("testing@gmail.com"));
        descriptorWithSameValues.setPhone(new Phone("98653257"));
        EditWarehouseDescriptor descriptorForWarehouse = new EditWarehouseDescriptor();
        descriptorForWarehouse.setName(new Name("warehouse here"));
        descriptorForWarehouse.setAddress(new Address("testing addr"));
        descriptorForWarehouse.setPhone(new Phone("54287163"));
        descriptorForWarehouse.setRemark(new Remark("hello there"));

        EditSupplierDescriptor descriptorWithSameValues2 = new EditSupplierDescriptor();
        descriptorWithSameValues2.setName(new Name("Test"));
        descriptorWithSameValues2.setEmail(new Email("testing@gmail.com"));
        descriptorWithSameValues2.setPhone(new Phone("98653257"));

        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_SUPPLIER, descriptorWithSameValues);
        EditCommand commandWithSameValues2 = new EditCommand(INDEX_FIRST_SUPPLIER, descriptorWithSameValues2);
        EditCommand commandForWarehouse = new EditCommand(INDEX_FIRST_WAREHOUSE, descriptorForWarehouse);
        //assertFalse(commandWithSameValues.equals(commandForWarehouse));
        assertTrue(commandWithSameValues.equals(commandWithSameValues2));
        /*
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_SUPPLIER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_SUPPLIER, DESC_BOB)));
        assertFalse(standardCommand.equals(commandForWarehouse));
         */
    }


}
