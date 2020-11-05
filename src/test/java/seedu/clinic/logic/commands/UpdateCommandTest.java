package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_PRODUCT_A;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_PRODUCT_B;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_PRODUCT_C;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_QUANTITY_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_QUANTITY_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_TAG_ANTIBIOTICS;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_NAME_A;
import static seedu.clinic.logic.commands.UpdateCommand.UpdateProductDescriptor;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_WAREHOUSE;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_WAREHOUSE;
import static seedu.clinic.testutil.TypicalSupplier.BOB;
import static seedu.clinic.testutil.TypicalWarehouse.ALICE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.Type;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Tag;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;
import seedu.clinic.testutil.ModelStub;
import seedu.clinic.testutil.SupplierBuilder;
import seedu.clinic.testutil.UpdateProductDescriptorBuilder;
import seedu.clinic.testutil.WarehouseBuilder;

public class UpdateCommandTest {
    private static final Product VALID_PRODUCT_A = new Product(new Name(VALID_WAREHOUSE_PRODUCT_NAME_A),
            VALID_PRODUCT_QUANTITY_B);
    private static final Product VALID_PRODUCT_B = new Product(new Name(VALID_PRODUCT_NAME_ASPIRIN),
            new HashSet<>(Arrays.asList(new Tag(VALID_TAG_ANTIBIOTICS))));

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateCommand(Type.SUPPLIER, null,
                new Name(VALID_PRODUCT_NAME_ASPIRIN), DESC_PRODUCT_A));
    }

    @Test
    public void constructor_nullProductName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateCommand(Type.WAREHOUSE, INDEX_FIRST_WAREHOUSE,
                null, DESC_PRODUCT_A));
    }

    @Test
    public void constructor_nullEntityType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateCommand(null, INDEX_FIRST_WAREHOUSE,
                new Name(VALID_PRODUCT_NAME_ASPIRIN), DESC_PRODUCT_A));
    }

    @Test
    public void constructor_nullUpdateProductDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateCommand(Type.WAREHOUSE, INDEX_FIRST_WAREHOUSE,
                new Name(VALID_PRODUCT_NAME_ASPIRIN), null));
    }

    @Test
    public void execute_productExistsInWarehouseAndFieldEdited_updateSuccessful() throws Exception {
        Warehouse originalWarehouse = new WarehouseBuilder().withProducts(
                Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_PRODUCT_QUANTITY_A)).build();
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(originalWarehouse);
        CommandResult commandResult = new UpdateCommand(Type.WAREHOUSE, INDEX_FIRST_WAREHOUSE,
                new Name(VALID_WAREHOUSE_PRODUCT_NAME_A), DESC_PRODUCT_B).execute(modelStub);
        Warehouse editedWarehouse = new WarehouseBuilder().withProducts(
                Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_PRODUCT_QUANTITY_B)).build();
        assertEquals(String.format(UpdateCommand.MESSAGE_SUCCESS, VALID_PRODUCT_A.toStringWithTags().trim(),
                editedWarehouse.getName()), commandResult.getFeedbackToUser());
        assertEquals(editedWarehouse, modelStub.warehouse);
    }

    @Test
    public void execute_productExistsInSupplierAndFieldEdited_updateSuccessful() throws Exception {
        Supplier originalSupplier = new SupplierBuilder().withProducts(
                Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS})).build();
        ModelStubWithSupplier modelStub = new ModelStubWithSupplier(originalSupplier);
        CommandResult commandResult = new UpdateCommand(Type.SUPPLIER, INDEX_FIRST_SUPPLIER,
                new Name(VALID_PRODUCT_NAME_ASPIRIN), DESC_PRODUCT_C).execute(modelStub);
        Supplier editedSupplier = new SupplierBuilder().withProducts(
                Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS})).build();
        assertEquals(String.format(UpdateCommand.MESSAGE_SUCCESS, VALID_PRODUCT_B.toStringWithTags().trim(),
                editedSupplier.getName()), commandResult.getFeedbackToUser());
        assertEquals(editedSupplier, modelStub.supplier);
    }

    @Test
    public void execute_invalidTypeSpecified_throwsCommandException() {
        Warehouse originalWarehouse = new WarehouseBuilder().withProducts(
                Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_PRODUCT_QUANTITY_A)).build();
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(originalWarehouse);
        UpdateCommand updateCommand = new UpdateCommand(Type.WAREHOUSE_PRODUCT, INDEX_FIRST_WAREHOUSE,
                new Name(VALID_WAREHOUSE_PRODUCT_NAME_A), DESC_PRODUCT_B);
        assertThrows(CommandException.class, () -> updateCommand.execute(modelStub));
    }

    @Test
    public void execute_productExistsInWarehouseAndNoFieldEdited_throwsCommandException() throws Exception {
        Warehouse originalWarehouse = new WarehouseBuilder().withProducts(
                Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_PRODUCT_QUANTITY_A)).build();
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(originalWarehouse);
        assertThrows(CommandException.class, () -> new UpdateCommand(Type.WAREHOUSE, INDEX_FIRST_WAREHOUSE,
                new Name(VALID_WAREHOUSE_PRODUCT_NAME_A), new UpdateProductDescriptorBuilder().build())
                .execute(modelStub));
    }

    @Test
    public void execute_productExistsInSupplierAndNoFieldEdited_throwsCommandException() throws Exception {
        Supplier originalSupplier = new SupplierBuilder().withProducts(
                Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS})).build();
        ModelStubWithSupplier modelStub = new ModelStubWithSupplier(originalSupplier);
        assertThrows(CommandException.class, () -> new UpdateCommand(Type.SUPPLIER, INDEX_FIRST_SUPPLIER,
                new Name(VALID_PRODUCT_NAME_ASPIRIN), new UpdateProductDescriptorBuilder().build())
                .execute(modelStub));
    }

    @Test
    public void execute_productDoesNotExistInWarehouse_updateSuccessful() throws Exception {
        Warehouse emptyWarehouse = new WarehouseBuilder().build();
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(emptyWarehouse);
        CommandResult commandResult = new UpdateCommand(Type.WAREHOUSE, INDEX_FIRST_WAREHOUSE,
                new Name(VALID_WAREHOUSE_PRODUCT_NAME_A), DESC_PRODUCT_B).execute(modelStub);
        Warehouse editedWarehouse = new WarehouseBuilder().withProducts(
                Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_PRODUCT_QUANTITY_B)).build();
        assertEquals(String.format(UpdateCommand.MESSAGE_SUCCESS, VALID_PRODUCT_A.toStringWithTags().trim(),
                editedWarehouse.getName()), commandResult.getFeedbackToUser());
        assertEquals(editedWarehouse, modelStub.warehouse);
    }

    @Test
    public void execute_productDoesNotExistInSupplier_updateSuccessful() throws Exception {
        Supplier originalSupplier = new SupplierBuilder().build();
        ModelStubWithSupplier modelStub = new ModelStubWithSupplier(originalSupplier);
        CommandResult commandResult = new UpdateCommand(Type.SUPPLIER, INDEX_FIRST_SUPPLIER,
                new Name(VALID_PRODUCT_NAME_ASPIRIN), DESC_PRODUCT_C).execute(modelStub);
        Supplier editedSupplier = new SupplierBuilder().withProducts(
                Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS})).build();
        assertEquals(String.format(UpdateCommand.MESSAGE_SUCCESS, VALID_PRODUCT_B.toStringWithTags().trim(),
                editedSupplier.getName()), commandResult.getFeedbackToUser());
        assertEquals(editedSupplier, modelStub.supplier);
    }

    @Test
    public void execute_warehouseEntityNotFound_throwsCommandException() {
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(ALICE);
        assertThrows(CommandException.class, () -> new UpdateCommand(Type.WAREHOUSE, INDEX_SECOND_WAREHOUSE,
                new Name(VALID_PRODUCT_NAME_ASPIRIN), DESC_PRODUCT_A).execute(modelStub));
    }

    @Test
    public void execute_supplierEntityNotFound_throwsCommandException() {
        ModelStubWithSupplier modelStub = new ModelStubWithSupplier(BOB);
        assertThrows(CommandException.class, () -> new UpdateCommand(Type.SUPPLIER, INDEX_SECOND_SUPPLIER,
                new Name(VALID_PRODUCT_NAME_ASPIRIN), DESC_PRODUCT_A).execute(modelStub));
    }

    @Test
    public void updateProductDescriptor_equalsTest() {
        UpdateProductDescriptor descriptorWithSameValues = new UpdateProductDescriptor(DESC_PRODUCT_A);
        // same values -> returns true
        assertTrue(DESC_PRODUCT_A.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(descriptorWithSameValues.equals(descriptorWithSameValues));

        // null -> returns false
        assertFalse(descriptorWithSameValues.equals(null));

        // different types -> returns false
        assertFalse(descriptorWithSameValues.equals(5));

        // different values -> returns false
        assertFalse(descriptorWithSameValues.equals(DESC_PRODUCT_B));

        // different tag -> returns false
        UpdateProductDescriptor editedProduct = new UpdateProductDescriptorBuilder(DESC_PRODUCT_A)
                .withTags(VALID_TAG_ANTIBIOTICS).build();
        assertFalse(DESC_PRODUCT_A.equals(editedProduct));

        // different quantity -> returns false
        UpdateProductDescriptor editedProduct2 = new UpdateProductDescriptorBuilder(DESC_PRODUCT_A)
                .withQuantity(20).build();
        assertFalse(DESC_PRODUCT_A.equals(editedProduct2));
    }

    private class ModelStubWithWarehouse extends ModelStub {
        private Warehouse warehouse;
        private Clinic clinic = new Clinic();

        ModelStubWithWarehouse(Warehouse warehouse) {
            requireNonNull(warehouse);
            this.warehouse = warehouse;
            clinic.setWarehouses(List.of(warehouse));
        }

        @Override public ReadOnlyClinic getClinic() {
            return clinic;
        }

        @Override
        public void setWarehouse(Warehouse target, Warehouse editedWarehouse) {
            this.warehouse = editedWarehouse;
            clinic.setWarehouses(List.of(editedWarehouse));
        }

        @Override
        public void updateFilteredWarehouseList(Predicate<Warehouse> predicate) {
        }

        @Override
        public ObservableList<Warehouse> getFilteredWarehouseList() {
            return FXCollections.observableArrayList(List.of(warehouse));
        }
    }

    private class ModelStubWithSupplier extends ModelStub {
        private Supplier supplier;
        private Clinic clinic = new Clinic();

        ModelStubWithSupplier(Supplier supplier) {
            requireNonNull(supplier);
            this.supplier = supplier;
            clinic.setSuppliers(List.of(supplier));
        }

        @Override public ReadOnlyClinic getClinic() {
            return clinic;
        }

        @Override
        public void setSupplier(Supplier target, Supplier editedSupplier) {
            this.supplier = editedSupplier;
            clinic.setSuppliers(List.of(editedSupplier));
        }

        @Override
        public void updateFilteredSupplierList(Predicate<Supplier> predicate) {
        }

        @Override
        public ObservableList<Supplier> getFilteredSupplierList() {
            return FXCollections.observableArrayList(List.of(supplier));
        }
    }
}
