package seedu.clinic.model;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.model.exceptions.NoRedoableVersionException;
import seedu.clinic.model.exceptions.NoUndoableVersionException;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Represents the in-memory model of the clinic data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedClinic clinic;
    private final UserPrefs userPrefs;
    private final UserMacros userMacros;
    private final CommandHistory commandHistory;
    private final FilteredList<Supplier> filteredSuppliers;
    private final FilteredList<Warehouse> filteredWarehouses;
    private final ObservableList<Macro> macroList;

    /**
     * Initializes a ModelManager with the given clinic, userPrefs, and userMacros.
     */
    public ModelManager(ReadOnlyClinic clinic, ReadOnlyUserPrefs userPrefs,
            ReadOnlyUserMacros userMacros, ReadOnlyCommandHistory commandHistory) {
        super();
        requireAllNonNull(clinic, userPrefs, userMacros, commandHistory);

        logger.fine("Initializing with CLI-nic: " + clinic + ", with user prefs " + userPrefs
                + " with user macros " + userMacros + " and with " + commandHistory);

        this.clinic = new VersionedClinic(clinic);
        this.userPrefs = new UserPrefs(userPrefs);
        this.userMacros = new UserMacros(userMacros);
        this.commandHistory = new CommandHistory(commandHistory);
        filteredSuppliers = new FilteredList<>(this.clinic.getSupplierList());
        filteredWarehouses = new FilteredList<>(this.clinic.getWarehouseList());
        macroList = this.userMacros.getMacroList();
    }

    public ModelManager() {
        this(new Clinic(), new UserPrefs(), new UserMacros(), new CommandHistory());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getClinicFilePath() {
        return userPrefs.getClinicFilePath();
    }

    @Override
    public void setClinicFilePath(Path clinicFilePath) {
        requireNonNull(clinicFilePath);
        userPrefs.setClinicFilePath(clinicFilePath);
    }

    @Override
    public Path getUserMacrosFilePath() {
        return userPrefs.getUserMacrosFilePath();
    }

    @Override
    public void setUserMacrosFilePath(Path userMacrosFilePath) {
        requireNonNull(userMacrosFilePath);
        userPrefs.setUserMacrosFilePath(userMacrosFilePath);
    }

    //=========== UserMacros ==================================================================================

    @Override
    public void setUserMacros(ReadOnlyUserMacros userMacros) {
        this.userMacros.resetData(userMacros);
    }

    @Override
    public ReadOnlyUserMacros getUserMacros() {
        return userMacros;
    }

    @Override
    public boolean hasMacro(Macro macro) {
        requireNonNull(macro);
        return userMacros.hasMacro(macro);
    }

    @Override
    public Optional<Macro> getMacro(String aliasString) {
        requireNonNull(aliasString);
        return userMacros.getMacro(aliasString);
    }

    @Override
    public Optional<Macro> getMacro(Alias alias) {
        requireNonNull(alias);
        return userMacros.getMacro(alias);
    }

    @Override
    public void deleteMacro(Macro target) {
        userMacros.removeMacro(target);
    }

    @Override
    public void addMacro(Macro macro) {
        userMacros.addMacro(macro);
    }

    @Override
    public void setMacro(Macro target, Macro editedMacro) {
        requireAllNonNull(target, editedMacro);

        userMacros.setMacro(target, editedMacro);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Macro}
     */
    @Override
    public ObservableList<Macro> getMacroList() {
        return macroList;
    }

    //=========== Clinic ================================================================================

    @Override
    public void setClinic(ReadOnlyClinic clinic) {
        saveVersionedClinic();
        this.clinic.resetData(clinic);
    }

    @Override
    public ReadOnlyClinic getClinic() {
        return clinic.getCurrentClinic();
    }

    @Override
    public boolean hasSupplier(Supplier supplier) {
        requireNonNull(supplier);
        return clinic.hasSupplier(supplier);
    }

    @Override
    public boolean hasWarehouse(Warehouse warehouse) {
        requireNonNull(warehouse);
        return clinic.hasWarehouse(warehouse);
    }

    @Override
    public void deleteSupplier(Supplier target) {
        clinic.removeSupplier(target);
    }

    @Override
    public void deleteWarehouse(Warehouse target) {
        clinic.removeWarehouse(target);
    }

    @Override
    public void addSupplier(Supplier supplier) {
        clinic.addSupplier(supplier);
        updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
    }

    @Override
    public void addWarehouse(Warehouse warehouse) {
        clinic.addWarehouse(warehouse);
        updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);
    }

    @Override
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        requireAllNonNull(target, editedSupplier);

        clinic.setSupplier(target, editedSupplier);
    }

    @Override
    public void setWarehouse(Warehouse target, Warehouse editedWarehouse) {
        requireAllNonNull(target, editedWarehouse);

        clinic.setWarehouse(target, editedWarehouse);
    }

    //=========== Filtered Supplier List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Supplier} backed by the internal list of
     * {@code versionedClinic}
     */
    @Override
    public ObservableList<Supplier> getFilteredSupplierList() {
        return filteredSuppliers;
    }

    @Override
    public void updateFilteredSupplierList(Predicate<Supplier> predicate) {
        requireNonNull(predicate);
        filteredSuppliers.setPredicate(predicate);
    }

    //=========== Filtered Warehouse List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Warehouse} backed by the internal list of
     * {@code versionedCLI-nic}
     */
    @Override
    public ObservableList<Warehouse> getFilteredWarehouseList() {
        return filteredWarehouses;
    }


    @Override
    public void updateFilteredWarehouseList(Predicate<Warehouse> predicate) {
        requireNonNull(predicate);
        filteredWarehouses.setPredicate(predicate);
    }

    //=========== Redo/Undo  ====================================================================================

    @Override
    public boolean canUndoClinic() {
        return clinic.canUndo();
    }

    @Override
    public boolean canRedoClinic() {
        return clinic.canRedo();
    }

    @Override
    public void undoClinic() throws NoUndoableVersionException {
        clinic.undo();
        updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);
    }

    @Override
    public void redoClinic() throws NoRedoableVersionException {
        clinic.redo();
        updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);
    }

    @Override
    public void saveVersionedClinic() {
        clinic.save();
    }

    @Override
    public Path getCommandHistoryFilePath() {
        return userPrefs.getCommandHistoryFilePath();
    }

    @Override
    public ReadOnlyCommandHistory getCommandHistory() {
        return commandHistory;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return clinic.equals(other.clinic)
                && userPrefs.equals(other.userPrefs)
                && filteredSuppliers.equals(other.filteredSuppliers)
                && filteredWarehouses.equals(other.filteredWarehouses)
                && macroList.equals((other.macroList));
    }

}
