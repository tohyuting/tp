package seedu.clinic.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Supplier> PREDICATE_SHOW_ALL_SUPPLIERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Warehouse> PREDICATE_SHOW_ALL_WAREHOUSES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user macros file path as stated in user prefs.
     */
    Path getUserMacrosFilePath();

    /**
     * Returns the user macros.
     */
    ReadOnlyUserMacros getUserMacros();

    /**
     * Sets the user prefs' clinic file path.
     */
    void setUserMacrosFilePath(Path clinicFilePath);

    /**
     * Replaces userMacros data with the data in {@code userMacros}.
     */
    void setUserMacros(ReadOnlyUserMacros userMacros);

    /**
     * Returns true if a macro with the same alias as {@code macro} exists in the UserMacros model.
     */
    boolean hasMacro(Macro macro);

    /**
     * Returns the macro corresponding to the alias string in an optional wrapper if it exists,
     * and an empty optional otherwise
     */
    Optional<Macro> getMacro(String aliasString);

    /**
     * Returns the macro corresponding to the alias in an optional wrapper if it exists,
     * and an empty optional otherwise
     */
    Optional<Macro> getMacro(Alias alias);

    /**
     * Deletes the given macro.
     * The macro must exist in the UserMacros model.
     */
    void deleteMacro(Macro target);

    /**
     * Adds the given macro.
     * {@code macro} must not already exist in the UserMacros model.
     */
    void addMacro(Macro macro);

    /**
     * Replaces the given macro {@code target} with {@code editedMacro}.
     * {@code target} must exist in the UserMacros model.
     * The alias of {@code editedMacro} must not be the same as another existing macro
     * in the UserMacros model.
     */
    void setMacro(Macro target, Macro editedMacro);

    /** Returns an unmodifiable view of the macro list */
    ObservableList<Macro> getMacroList();

    /**
     * Returns the user prefs' clinic file path.
     */
    Path getClinicFilePath();

    /**
     * Sets the user prefs' clinic file path.
     */
    void setClinicFilePath(Path clinicFilePath);

    /**
     * Replaces clinic data with the data in {@code clinic}.
     */
    void setClinic(ReadOnlyClinic clinic);

    /** Returns the Clinic */
    ReadOnlyClinic getClinic();

    /**
     * Returns true if a supplier with the same identity as {@code supplier} exists in the CLI-nic app.
     */
    boolean hasSupplier(Supplier supplier);

    /**
     * Deletes the given supplier.
     * The supplier must exist in the CLI-nic app.
     */
    void deleteSupplier(Supplier target);

    /**
     * Adds the given supplier.
     * {@code supplier} must not already exist in the CLI-nic app.
     */
    void addSupplier(Supplier supplier);

    /**
     * Replaces the given supplier {@code target} with {@code editedSupplier}.
     * {@code target} must exist in the clinic.
     * The supplier identity of {@code editedSupplier} must not be the same as another existing supplier
     * in the CLI-nic app.
     */
    void setSupplier(Supplier target, Supplier editedSupplier);

    /** Returns an unmodifiable view of the filtered supplier list */
    ObservableList<Supplier> getFilteredSupplierList();

    /**
     * Updates the filter of the filtered supplier list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSupplierList(Predicate<Supplier> predicate);

    /**
     * Returns true if a warehouse with the same identity as {@code warehouse} exists in the clinic app.
     */
    boolean hasWarehouse(Warehouse warehouse);

    /**
     * Deletes the given warehouse.
     * The warehouse must exist in the clinic app.
     */
    void deleteWarehouse(Warehouse target);

    /**
     * Adds the given warehouse.
     * {@code warehouse} must not already exist in the clinic app.
     */
    void addWarehouse(Warehouse warehouse);

    /**
     * Replaces the given warehouse {@code target} with {@code editedWarehouse}.
     * {@code target} must exist in the clinic app.
     * The warehouse identity of {@code editedWarehouse} must not be the same as another existing warehouse
     * in the clinic app.
     */
    void setWarehouse(Warehouse target, Warehouse editedWarehouse);

    /** Returns an unmodifiable view of the filtered warehouse list */
    ObservableList<Warehouse> getFilteredWarehouseList();

    /**
     * Updates the filter of the filtered warehouse list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWarehouseList(Predicate<Warehouse> predicate);

    /**
     * Returns true if the model has earlier Clinic version to recover.
     */
    boolean canUndoClinic();

    /**
     * Returns true if the model has earlier undone Clinic version to restore.
     */
    boolean canRedoClinic();

    /**
     * Recovers the Clinic to its previous version.
     */
    void undoClinic();

    /**
     * Restores the Clinic to its previously undone version.
     */
    void redoClinic();

    /**
     * Saves the current Clinic version.
     */
    void saveVersionedClinic();

    /**
     * Returns the user prefs' command history file path.
     */
    Path getCommandHistoryFilePath();

    /**
     * Returns the command history.
     */
    ReadOnlyCommandHistory getCommandHistory();
}
