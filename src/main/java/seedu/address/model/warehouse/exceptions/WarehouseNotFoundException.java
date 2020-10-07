package seedu.address.model.warehouse.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException() {
        super("The warehouse is not found");
    }
}
