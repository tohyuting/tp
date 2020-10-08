package seedu.clinic.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.supplier.UniqueSupplierList;

/**
 * Wraps all data at the clinic level
 * Duplicates are not allowed (by .isSameSupplier comparison)
 */
public class Clinic implements ReadOnlyClinic {

    private final UniqueSupplierList suppliers;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        suppliers = new UniqueSupplierList();
    }

    public Clinic() {}

    /**
     * Creates a Clinic using the Suppliers in the {@code toBeCopied}
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
     * Resets the existing data of this {@code Clinic} with {@code newData}.
     */
    public void resetData(ReadOnlyClinic newData) {
        requireNonNull(newData);

        setSuppliers(newData.getSupplierList());
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
        return suppliers.asUnmodifiableObservableList().size() + " suppliers";
        // TODO: refine later
    }

    @Override
    public ObservableList<Supplier> getSupplierList() {
        return suppliers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Clinic // instanceof handles nulls
                && suppliers.equals(((Clinic) other).suppliers));
    }

    @Override
    public int hashCode() {
        return suppliers.hashCode();
    }
}
