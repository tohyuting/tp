package seedu.clinic.model.warehouse.exceptions;

/**
 * Signals that the operation is unable to find the specified warehouse.
 */
public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException() {
        super("The warehouse is not found");
    }
}
