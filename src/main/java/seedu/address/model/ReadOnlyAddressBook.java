package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.warehouse.Warehouse;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the suppliers list.
     * This list will not contain any duplicate suppliers.
     */
    ObservableList<Supplier> getSupplierList();

    /**
     * Returns an unmodifiable view of the warehouses list.
     * This list will not contain any duplicate warehouses.
     */
    ObservableList<Warehouse> getWarehouseList();
}
