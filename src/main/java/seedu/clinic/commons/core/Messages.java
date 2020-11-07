package seedu.clinic.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n\n%1$s";
    public static final String MESSAGE_INVALID_ALIAS = "Invalid alias! \n\n%1$s";
    public static final String MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX =
            "The supplier index provided is larger than the displayed list size";
    public static final String MESSAGE_SUPPLIERS_LISTED_OVERVIEW = "%1$d supplier(s) listed!";
    public static final String MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX =
            "The warehouse index provided is larger than the displayed list size";
    public static final String MESSAGE_INVALID_PRODUCT_NAME_IN_SUPPLIER =
            "The product: %1$s is not sold by supplier: %2$s";
    public static final String MESSAGE_INVALID_PRODUCT_NAME_IN_WAREHOUSE =
            "The product: %1$s is not stored by warehouse: %2$s";
    public static final String MESSAGE_WAREHOUSE_LISTED_OVERVIEW = "%1$d warehouse(s) listed!";

}
