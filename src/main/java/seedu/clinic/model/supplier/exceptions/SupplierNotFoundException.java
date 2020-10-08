package seedu.clinic.model.supplier.exceptions;

/**
 * Signals that the operation is unable to find the specified supplier.
 */
public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException() {
        super("The supplier is not found");
    }
}
