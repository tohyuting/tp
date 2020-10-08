package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.attribute.Address;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.UniqueSupplierList;
import seedu.address.model.warehouse.UniqueWarehouseList;
import seedu.address.model.warehouse.Warehouse;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameSupplier and isSameWarehouse comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

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

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Suppliers & Warehouses in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setSuppliers(newData.getSupplierList());
        setWarehouses(newData.getWarehouseList());
    }

    //// warehouse-level operations

    /**
     * Returns true if a warehouse with the same identity as {@code warehouse} exists in the address book.
     */
    public boolean hasWarehouse(Warehouse warehouse) {
        requireNonNull(warehouse);
        return warehouses.contains(warehouse);
    }

    /**
     * Adds a warehouse to the address book.
     * The warehouse must not already exist in the address book.
     */
    public void addWarehouse(Warehouse p) {
        warehouses.add(p);
    }

    /**
     * Replaces the given warehouse {@code target} in the list with {@code editedWarehouse}.
     * {@code target} must exist in the address book.
     * The warehouse identity of {@code editedWarehouse} must not be the same as another existing warehouse
     * in the address book.
     */
    public void setWarehouse(Warehouse target, Warehouse editedWarehouse) {
        requireNonNull(editedWarehouse);

        warehouses.setWarehouse(target, editedWarehouse);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeWarehouse(Warehouse key) {
        warehouses.remove(key);
    }

    //// supplier-level operations

    /**
     * Returns true if a supplier with the same identity as {@code supplier} exists in the address book.
     */
    public boolean hasSupplier(Supplier supplier) {
        requireNonNull(supplier);
        return suppliers.contains(supplier);
    }

    /**
     * Adds a supplier to the address book.
     * The supplier must not already exist in the address book.
     */
    public void addSupplier(Supplier p) {
        suppliers.add(p);
    }

    /**
     * Replaces the given supplier {@code target} in the list with {@code editedSupplier}.
     * {@code target} must exist in the address book.
     * The supplier identity of {@code editedSupplier} must not be the same as another existing supplier
     * in the address book.
     */
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        requireNonNull(editedSupplier);

        suppliers.setSupplier(target, editedSupplier);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
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
                || (other instanceof AddressBook // instanceof handles nulls
                && warehouses.equals(((AddressBook) other).warehouses)
                && suppliers.equals(((AddressBook) other).suppliers));
    }

    @Override
    public int hashCode() {
        return Objects.hash(suppliers, warehouses);
    }
}
