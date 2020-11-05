package seedu.clinic.model.warehouse;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.warehouse.exceptions.DuplicateWarehouseException;
import seedu.clinic.model.warehouse.exceptions.WarehouseNotFoundException;

/**
 * A list of warehouses that enforces uniqueness between its elements and does not allow nulls.
 * A warehouse is considered unique by comparing using {@code Warehouse#isSameWarehouse(Warehouse)}. As such, adding
 * and updating of warehouses uses Warehouse#isSameWarehouse(Warehouse) for equality so as to ensure that the warehouse
 * being added or updated is unique in terms of identity in the UniqueWarehouseList.
 * However, the removal of a warehouse uses Warehouse#equals(Object) so as to ensure that the warehouse with
 * exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Warehouse#isSameWarehouse(Warehouse)
 */
public class UniqueWarehouseList implements Iterable<Warehouse> {
    private final ObservableList<Warehouse> internalList = FXCollections.observableArrayList();
    private final ObservableList<Warehouse> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent warehouses as the given argument.
     */
    public boolean contains(Warehouse toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameWarehouse);
    }

    /**
     * Adds a warehouse to the list.
     * The warehouse must not already exist in the list.
     */
    public void add(Warehouse warehouseToAdd) {
        requireNonNull(warehouseToAdd);
        if (contains(warehouseToAdd)) {
            throw new DuplicateWarehouseException();
        }
        internalList.add(warehouseToAdd);
    }

    /**
     * Returns the warehouse corresponding to the name specified in an optional wrapper if it exists,
     * and an empty optional otherwise.
     */
    public Optional<Warehouse> getWarehouse(Name warehouseName) {
        requireNonNull(warehouseName);
        return internalList.stream().filter((Warehouse warehouse)->warehouse.getName()
                .equals(warehouseName)).findFirst();
    }


    /**
     * Replaces the warehouse {@code target} in the list with {@code editedWarehouse}.
     * {@code target} must exist in the list.
     * The warehouse identity of {@code editedWarehouse} must not be the same as another existing warehouse in the list.
     */
    public void setWarehouse(Warehouse target, Warehouse editedWarehouse) {
        requireAllNonNull(target, editedWarehouse);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new WarehouseNotFoundException();
        }

        if (!target.isSameWarehouse(editedWarehouse) && contains(editedWarehouse)) {
            throw new DuplicateWarehouseException();
        }

        internalList.set(index, editedWarehouse);
    }

    /**
     * Removes the equivalent warehouse from the list.
     * The warehouse must exist in the list.
     */
    public void remove(Warehouse toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new WarehouseNotFoundException();
        }
    }

    /**
     * Replaces the current list of warehouses in {@code internalList} with a new {@code replacement}.
     */
    public void setWarehouses(UniqueWarehouseList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code warehouses}.
     * {@code warehouses} must not contain duplicate warehouses.
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        requireAllNonNull(warehouses);
        if (!warehousesAreUnique(warehouses)) {
            throw new DuplicateWarehouseException();
        }

        internalList.setAll(warehouses);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Warehouse> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Warehouse> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueWarehouseList // instanceof handles nulls
                    && internalList.equals(((UniqueWarehouseList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code warehouses} contains only unique warehouses.
     */
    private boolean warehousesAreUnique(List<Warehouse> warehouses) {
        for (int i = 0; i < warehouses.size() - 1; i++) {
            for (int j = i + 1; j < warehouses.size(); j++) {
                if (warehouses.get(i).isSameWarehouse(warehouses.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
