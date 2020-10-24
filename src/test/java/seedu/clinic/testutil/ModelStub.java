package seedu.clinic.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.model.Model;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.ReadOnlyUserPrefs;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

public class ModelStub implements Model {
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
    public void addSupplier(Supplier supplier) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setClinic(ReadOnlyClinic newData) {
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
    public boolean hasSupplierByName(Supplier supplier) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteSupplier(Supplier target) {
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
