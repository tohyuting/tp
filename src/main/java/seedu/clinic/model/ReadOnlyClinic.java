package seedu.clinic.model;

import javafx.collections.ObservableList;
import seedu.clinic.model.supplier.Supplier;

/**
 * Unmodifiable view of a clinic
 */
public interface ReadOnlyClinic {

    /**
     * Returns an unmodifiable view of the suppliers list.
     * This list will not contain any duplicate suppliers.
     */
    ObservableList<Supplier> getSupplierList();

}
