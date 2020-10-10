package seedu.clinic.model;

import javafx.collections.ObservableList;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Unmodifiable view of a clinic
 */
public interface ReadOnlyClinic {

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
