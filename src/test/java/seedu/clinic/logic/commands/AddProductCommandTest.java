package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.logic.commands.AddProductCommand.MESSAGE_DUPLICATE_PRODUCT;
import static seedu.clinic.logic.commands.AddProductCommand.getSupplierByName;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_SUPPLIER_NAME_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_PANADOL;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_TAG_ANTIBIOTICS;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinic.model.util.SampleDataUtil.getTagSet;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalSupplier.ALICE;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.testutil.ModelStub;
import seedu.clinic.testutil.SupplierBuilder;

class AddProductCommandTest {
    private static final Product VALID_PRODUCT_A = new Product(new Name(VALID_PRODUCT_NAME_ASPIRIN),
            getTagSet(VALID_TAG_ANTIBIOTICS));

    @Test
    public void constructor_nullSupplierName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProductCommand(null, VALID_PRODUCT_A));
    }

    @Test
    public void constructor_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProductCommand(ALICE.getName(), null));
    }

    @Test
    public void getSupplierByName_supplierFound_success() {
        ModelStubWithSupplier modelStub = new ModelStubWithSupplier(ALICE);
        Supplier supplier = getSupplierByName(ALICE.getName(), modelStub);
        assertEquals(supplier, ALICE);
    }

    @Test
    public void geSupplierByName_supplierNotFound_throwsNoSuchElementException() {
        ModelStubWithSupplier modelStub = new ModelStubWithSupplier(ALICE);
        assertThrows(NoSuchElementException.class, () -> getSupplierByName(new Name(VALID_NAME_AMY), modelStub));
    }

    @Test
    public void execute_supplierNotFound_throwsCommandException() {
        ModelStubWithSupplier modelStub = new ModelStubWithSupplier(ALICE);
        assertThrows(CommandException.class, () -> new AddProductCommand(new Name(VALID_NAME_AMY), VALID_PRODUCT_A)
                .execute(modelStub));
    }

    @Test
    public void execute_productAlreadyExist_throwsCommandException() {
        ModelStubWithSupplier modelStub = new ModelStubWithSupplier(ALICE);
        Product duplicateProduct = new Product(new Name(VALID_PRODUCT_NAME_PANADOL), new HashSet<>());
        assertThrows(CommandException.class, () -> new AddProductCommand(ALICE.getName(), duplicateProduct)
                .execute(modelStub));
        assertThrows(CommandException.class, MESSAGE_DUPLICATE_PRODUCT,
                () -> new AddProductCommand(ALICE.getName(), duplicateProduct).execute(modelStub));
    }

    @Test
    public void execute_newProduct_addSuccessful() throws Exception {
        ModelStubWithSupplier modelStub = new ModelStubWithSupplier(ALICE);
        CommandResult commandResult = new AddProductCommand(ALICE.getName(), VALID_PRODUCT_A).execute(modelStub);
        Supplier editedSupplier = new SupplierBuilder(ALICE).withProducts(
                Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS})).build();
        assertEquals(String.format(AddProductCommand.MESSAGE_SUCCESS, editedSupplier.getName().fullName,
                VALID_PRODUCT_A.getProductName()), commandResult.getFeedbackToUser());
        assertEquals(editedSupplier, modelStub.supplier);
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
    }
}