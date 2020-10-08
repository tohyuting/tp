package seedu.clinic.model.warehouse.exceptions;

/**
 * Signals that the operation will result in duplicate Warehouses (Warehouses are considered duplicates if they have the
 * same identity).
 */
public class DuplicateWarehouseException extends RuntimeException {
    public DuplicateWarehouseException() {
        super("Operation would result in duplicate warehouses");
    }
}
