package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_PRODUCT_A;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_PRODUCT_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_QUANTITY_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_QUANTITY_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_NAME_A;
import static seedu.clinic.logic.commands.UpdateCommand.getWarehouseByName;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_WAREHOUSE;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_WAREHOUSE;
import static seedu.clinic.testutil.TypicalWarehouse.ALICE;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.Type;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.ReadOnlyUserPrefs;
import seedu.clinic.model.VersionedClinic;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;
import seedu.clinic.testutil.UpdateProductDescriptorBuilder;
import seedu.clinic.testutil.WarehouseBuilder;

public class UpdateCommandTest {
    private static final Product VALID_PRODUCT_A = new Product(new Name(VALID_WAREHOUSE_PRODUCT_NAME_A),
            VALID_PRODUCT_QUANTITY_B);

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
        assertEquals(String.format(UpdateCommand.MESSAGE_SUCCESS, VALID_PRODUCT_A.toString(),
                editedWarehouse.getName()), commandResult.getFeedbackToUser());
        assertEquals(editedWarehouse, modelStub.warehouse);
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
    public void execute_productDoesNotExistInWarehouse_updateSuccessful() throws Exception {
        Warehouse emptyWarehouse = new WarehouseBuilder().build();
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(emptyWarehouse);
        CommandResult commandResult = new UpdateCommand(Type.WAREHOUSE, INDEX_FIRST_WAREHOUSE,
                new Name(VALID_WAREHOUSE_PRODUCT_NAME_A), DESC_PRODUCT_B).execute(modelStub);
        Warehouse editedWarehouse = new WarehouseBuilder().withProducts(
                Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_PRODUCT_QUANTITY_B)).build();
        assertEquals(String.format(UpdateCommand.MESSAGE_SUCCESS, VALID_PRODUCT_A.toString(),
                editedWarehouse.getName()), commandResult.getFeedbackToUser());
        assertEquals(editedWarehouse, modelStub.warehouse);
    }

    @Test
    public void execute_entityNotFound_throwsCommandException() {
        ModelStubWithWarehouse modelStub = new ModelStubWithWarehouse(ALICE);
        assertThrows(CommandException.class, () -> new UpdateCommand(Type.WAREHOUSE, INDEX_SECOND_WAREHOUSE,
                new Name(VALID_PRODUCT_NAME_ASPIRIN), DESC_PRODUCT_A).execute(modelStub));
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
        private final VersionedClinic sampleClinic;

        ModelStub() {
            sampleClinic = new VersionedClinic(new Clinic());
        }

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
        public Path getUserMacrosFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserMacros getUserMacros() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserMacrosFilePath(Path clinicFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserMacros(ReadOnlyUserMacros userMacros) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMacro(Macro macro) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Macro> getMacro(String aliasString) {
            throw new AssertionError("This method should not be called.");
        }

        @Override public Optional<Macro> getMacro(Alias alias) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMacro(Macro target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMacro(Macro macro) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMacro(Macro target, Macro editedMacro) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Macro> getMacroList() {
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

        @Override
        public boolean canUndoClinic() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoClinic() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoClinic() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoClinic() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveVersionedClinic() {
            sampleClinic.save();
        }

        @Override
        public Path getCommandHistoryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CommandHistory getCommandHistory() {
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

        @Override
        public ObservableList<Warehouse> getFilteredWarehouseList() {
            return FXCollections.observableArrayList(List.of(warehouse));
        }
    }
}
