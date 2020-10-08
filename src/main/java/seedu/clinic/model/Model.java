package seedu.clinic.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
<<<<<<< HEAD:src/main/java/seedu/address/model/Model.java
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.warehouse.Warehouse;
=======
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.model.supplier.Supplier;
>>>>>>> upstream/master:src/main/java/seedu/clinic/model/Model.java

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

    //
    /**
     * Returns true if a warehouse with the same identity as {@code warehouse} exists in the address book.
     */
    boolean hasWarehouse(Warehouse warehouse);

    /**
     * Deletes the given warehouse.
     * The warehouse must exist in the address book.
     */
    void deleteWarehouse(Warehouse target);

    /**
     * Adds the given warehouse.
     * {@code warehouse} must not already exist in the address book.
     */
    void addWarehouse(Warehouse warehouse);

    /**
     * Replaces the given warehouse {@code target} with {@code editedWarehouse}.
     * {@code target} must exist in the address book.
     * The warehouse identity of {@code editedWarehouse} must not be the same as another existing warehouse
     * in the address book.
     */
    void setWarehouse(Warehouse target, Warehouse editedWarehouse);

    /** Returns an unmodifiable view of the filtered warehouse list */
    ObservableList<Warehouse> getFilteredWarehouseList();

    /**
     * Updates the filter of the filtered warehouse list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWarehouseList(Predicate<Warehouse> predicate);
}
