package seedu.clinic.model;

import javafx.collections.ObservableList;
<<<<<<< HEAD:src/main/java/seedu/address/model/ReadOnlyAddressBook.java
import seedu.address.model.supplier.Supplier;
import seedu.address.model.warehouse.Warehouse;
=======
import seedu.clinic.model.supplier.Supplier;
>>>>>>> upstream/master:src/main/java/seedu/clinic/model/ReadOnlyClinic.java

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
