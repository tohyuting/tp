package seedu.clinic.testutil;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.ReadOnlyUserPrefs;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.VersionedClinic;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * A Simple model stub for commands test.
 */
public class ModelStub implements Model {
    private final VersionedClinic sampleClinic;
    private final UserMacros sampleUserMacros;

    /**
     * Initialize the stub with empty VersionedClinic and UserMacros.
     */
    public ModelStub() {
        sampleClinic = new VersionedClinic(new Clinic());
        sampleUserMacros = new UserMacros(new UserMacros());
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
