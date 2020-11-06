package seedu.clinic.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.supplier.UniqueSupplierList;
import seedu.clinic.model.warehouse.UniqueWarehouseList;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Wraps all data at the clinic level
 * Duplicates are not allowed (by .isSameSupplier and isSameWarehouse comparison)
 */
public class Clinic implements ReadOnlyClinic {

    private final UniqueSupplierList suppliers;
    private final UniqueWarehouseList warehouses;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        suppliers = new UniqueSupplierList();
        warehouses = new UniqueWarehouseList();
    }

    public Clinic() {}

    /**
     * Creates a Clinic using the Suppliers & Warehouses in the {@code toBeCopied}
     */
    public Clinic(ReadOnlyClinic toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the supplier list with {@code suppliers}.
     * {@code suppliers} must not contain duplicate suppliers.
     */
    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers.setSuppliers(suppliers);
    }

    /**
     * Replaces the contents of the warehouse list with {@code warehouses}.
     * {@code warehouses} must not contain duplicate warehouses.
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses.setWarehouses(warehouses);
    }

    /**
     * Resets the existing data of this {@code Clinic} with {@code newData}.
     */
    public void resetData(ReadOnlyClinic newData) {
        requireNonNull(newData);

        setSuppliers(newData.getSupplierList());
        setWarehouses(newData.getWarehouseList());
    }

    //// warehouse-level operations

    /**
     * Returns true if a warehouse with the same identity as {@code warehouse} exists in the clinic.
     */
    public boolean hasWarehouse(Warehouse warehouse) {
        requireNonNull(warehouse);
        return warehouses.contains(warehouse);
    }

    /**
     * Returns the warehouse corresponding to the name specified in an optional wrapper if it exists,
     * and an empty optional otherwise
     */
    public Optional<Warehouse> getWarehouse(Name warehouseName) {
        requireNonNull(warehouseName);
        return warehouses.getWarehouse(warehouseName);
    }

    /**
     * Adds a warehouse to the clinic.
     * The warehouse must not already exist in the clinic.
     */
    public void addWarehouse(Warehouse p) {
        warehouses.add(p);
    }

    /**
     * Replaces the given warehouse {@code target} in the list with {@code editedWarehouse}.
     * {@code target} must exist in the clinic.
     * The warehouse identity of {@code editedWarehouse} must not be the same as another existing warehouse
     * in the clinic.
     */
    public void setWarehouse(Warehouse target, Warehouse editedWarehouse) {
        requireNonNull(editedWarehouse);

        warehouses.setWarehouse(target, editedWarehouse);
    }

    /**
     * Removes {@code key} from this {@code Clinic}.
     * {@code key} must exist in the clinic.
     */
    public void removeWarehouse(Warehouse key) {
        warehouses.remove(key);
    }

    //// supplier-level operations

    /**
     * Returns true if a supplier with the same identity as {@code supplier} exists in the CLI-nic app.
     */
    public boolean hasSupplier(Supplier supplier) {
        requireNonNull(supplier);
        return suppliers.contains(supplier);
    }

    /**
     * Returns the supplier corresponding to the name specified in an optional wrapper if it exists,
     * and an empty optional otherwise
     */
    public Optional<Supplier> getSupplier(Name supplierName) {
        requireNonNull(supplierName);
        return suppliers.getSupplier(supplierName);
    }

    /**
     * Adds a supplier to the CLI-nic app.
     * The supplier must not already exist in clinic.
     */
    public void addSupplier(Supplier p) {
        suppliers.add(p);
    }

    /**
     * Replaces the given supplier {@code target} in the list with {@code editedSupplier}.
     * {@code target} must exist in clinic.
     * The supplier identity of {@code editedSupplier} must not be the same as another existing supplier
     * in the CLI-nic app.
     */
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        requireNonNull(editedSupplier);

        suppliers.setSupplier(target, editedSupplier);
    }

    /**
     * Removes {@code key} from this {@code Clinic}.
     * {@code key} must exist in clinic.
     */
    public void removeSupplier(Supplier key) {
        suppliers.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return suppliers.asUnmodifiableObservableList().size() + " suppliers\n"
                + warehouses.asUnmodifiableObservableList().size() + " warehouses";
        // TODO: refine later
    }

    @Override
    public ObservableList<Supplier> getSupplierList() {
        return suppliers.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Warehouse> getWarehouseList() {
        return warehouses.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Clinic // instanceof handles nulls
                && warehouses.equals(((Clinic) other).warehouses)
                && suppliers.equals(((Clinic) other).suppliers));
    }

    @Override
    public int hashCode() {
        return Objects.hash(suppliers, warehouses);
    }
}
