package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_NAME_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_A;
import static seedu.clinic.logic.commands.UpdateCommand.getWarehouseByName;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalWarehouse.ALICE;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.Model;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.ReadOnlyUserPrefs;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;
import seedu.clinic.testutil.WarehouseBuilder;

public class UpdateCommandTest {
    private static final Product VALID_PRODUCT_A = new Product(new Name(VALID_WAREHOUSE_PRODUCT_NAME_A),
            VALID_WAREHOUSE_PRODUCT_QUANTITY_A);

    @Test
    public void constructor_nullWarehouse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateCommand(null, VALID_PRODUCT_A));
    }

    @Test
    public void constructor_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateCommand(ALICE.getName(), null));
    }

    @Test
    public void execute_productExistsInWarehouse_updateSuccessful() throws Exception {
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(ALICE);
        CommandResult commandResult = new UpdateCommand(ALICE.getName(), VALID_PRODUCT_A).execute(modelStub);
        WarehouseBuilder warehouseBuilder = new WarehouseBuilder(ALICE);
        Warehouse editedWarehouse = warehouseBuilder.withProducts(
                Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_WAREHOUSE_PRODUCT_QUANTITY_A)).build();
        assertEquals(String.format(UpdateCommand.MESSAGE_SUCCESS, VALID_PRODUCT_A, editedWarehouse),
                commandResult.getFeedbackToUser());
        assertEquals(editedWarehouse, modelStub.warehouse);
    }

    @Test
    public void execute_productDoesNotExistInWarehouse_updateSuccessful() throws Exception {
        WarehouseBuilder warehouseBuilder = new WarehouseBuilder(ALICE);
        Warehouse emptyWarehouse = warehouseBuilder.withProducts(new HashMap<>()).build();
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(emptyWarehouse);
        CommandResult commandResult = new UpdateCommand(emptyWarehouse.getName(), VALID_PRODUCT_A).execute(modelStub);
        Warehouse editedWarehouse = warehouseBuilder.withProducts(
                Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_WAREHOUSE_PRODUCT_QUANTITY_A)).build();
        assertEquals(String.format(UpdateCommand.MESSAGE_SUCCESS, VALID_PRODUCT_A, editedWarehouse),
                commandResult.getFeedbackToUser());
        assertEquals(editedWarehouse, modelStub.warehouse);
    }

    @Test
    public void execute_warehouseNotFound_throwsCommandException() {
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(ALICE);
        assertThrows(CommandException.class, () -> new UpdateCommand(new Name(VALID_NAME_AMY), VALID_PRODUCT_A)
                .execute(modelStub));
    }

    @Test
    public void getWarehouseByName_warehouseFound_success() {
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(ALICE);
        Warehouse warehouse = getWarehouseByName(ALICE.getName(), modelStub);
        assertEquals(warehouse, ALICE);
    }

    @Test
    public void getWarehouseByName_warehouseNotFound_throwsNoSuchElementException() {
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(ALICE);
        assertThrows(NoSuchElementException.class, () -> getWarehouseByName(new Name(VALID_NAME_AMY), modelStub));
    }

    /*
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getClinicFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClinicFilePath(Path clinicFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClinic(ReadOnlyClinic clinic) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyClinic getClinic() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSupplier(Supplier supplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSupplier(Supplier target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSupplier(Supplier supplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSupplier(Supplier target, Supplier editedSupplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Supplier> getFilteredSupplierList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSupplierList(Predicate<Supplier> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWarehouse(Warehouse warehouse) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteWarehouse(Warehouse target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWarehouse(Warehouse warehouse) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWarehouse(Warehouse target, Warehouse editedWarehouse) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Warehouse> getFilteredWarehouseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWarehouseList(Predicate<Warehouse> predicate) {
            throw new AssertionError("This method should not be called.");
        }
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
    }
}
