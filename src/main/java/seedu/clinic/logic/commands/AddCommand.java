package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Adds a supplier/warehouse to the CLI-nic app.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String COMPULSORY_ADD_SUPPLIER_COMMAND = "add ct/s n/ p/ e/";
    public static final String COMPULSORY_ADD_WAREHOUSE_COMMAND = "add ct/w n/ p/ addr/";

    public static final String MESSAGE_USAGE = "Add Command Usage\n\n"
            + "Adds a supplier or warehouse to CLI-nic.\n\n"
            + "Usage 1 - Adds a supplier into CLI-nic.\n\n"
            + "Parameters:\n"
            + PREFIX_TYPE + "TYPE "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL ["
            + PREFIX_REMARK + "REMARK]\n\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TYPE + "s "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_REMARK + "Largest contractor \n\n"
            + "Usage 2 - Adds a warehouse into CLI-nic\n\n"
            + "Parameters:\n"
            + PREFIX_TYPE + "TYPE "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_ADDRESS + "ADDRESS ["
            + PREFIX_REMARK + "REMARK]\n\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TYPE + "w "
            + PREFIX_NAME + "NUS South "
            + PREFIX_PHONE + "91234562 "
            + PREFIX_ADDRESS + "21 Lower Kent Ridge Rd, Singapore 119077 "
            + PREFIX_REMARK + "Mainly deal with schools ";

    public static final String MESSAGE_SUPPLIER_SUCCESS = "New supplier added: %1$s";
    public static final String MESSAGE_WAREHOUSE_SUCCESS = "New warehouse added: %1$s";
    public static final String MESSAGE_DUPLICATE_SUPPLIER = "This supplier already exists in CLI-nic";
    public static final String MESSAGE_DUPLICATE_WAREHOUSE = "This warehouse already exists in CLI-nic";
    public static final String MESSAGE_MISSING_TYPE_PREFIX = "You used an invalid type!"
            + " Type for this command should be either ct/s or ct/w only.\n\n%1$s";
    public static final String MESSAGE_SUPPLIER_MISSING_PREFIX = "There are missing prefixes, ensure"
            + " that you at least include: ct/s n/SUPPLIER_NAME, p/PHONE and e/EMAIL_ADDRESS\n\n%1$s";
    public static final String MESSAGE_WAREHOUSE_MISSING_PREFIX = "There are missing prefixes, ensure"
            + " that you at least include: ct/w n/WAREHOUSE_NAME, p/PHONE and addr/ADDRESS\n\n%1$s";
    public static final String MESSAGE_INVALID_SUPPLIER_ADDRESS_PREFIX = "Additional address prefix"
            + " detected, a supplier should not have the address prefix (addr/)\n\n%1$s";
    public static final String MESSAGE_INVALID_WAREHOUSE_EMAIL_PREFIX = "Additional email prefix"
            + " detected, a warehouse should not have the email prefix (e/)\n\n%1$s";

    private static final String LOG_MESSAGE_RECEIVE_SUPPLIER = "Received information to add supplier.";
    private static final String LOG_MESSAGE_RECEIVE_WAREHOUSE = "Received information to add warehouse.";
    private static final String LOG_MESSAGE_ADD_SUPPLIER_SUCCESS = "Supplier with given information has been added"
            + " and supplier list is updated on UI.";
    private static final String LOG_MESSAGE_ADD_WAREHOUSE_SUCCESS = "Warehouse with given information has been added"
            + " and warehouse list is updated on UI.";
    private static final String INVALID_WAREHOUSE_ASSERTION = "warehouseToAdd specified"
            + " should be of Warehouse type and not null here.";

    private final Supplier supplierToAdd;
    private final Warehouse warehouseToAdd;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an AddCommand to add the specified {@code Supplier}
     */
    public AddCommand(Supplier supplier) {
        requireNonNull(supplier);
        this.supplierToAdd = supplier;
        this.warehouseToAdd = null;
        logger.log(Level.INFO, LOG_MESSAGE_RECEIVE_SUPPLIER);
    }

    /**
     * Creates an AddCommand to add the specified {@code Warehouse}
     */
    public AddCommand(Warehouse warehouse) {
        requireNonNull(warehouse);
        this.supplierToAdd = null;
        this.warehouseToAdd = warehouse;
        logger.log(Level.INFO, LOG_MESSAGE_RECEIVE_WAREHOUSE);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        CommandResult commandResult;

        if (supplierToAdd != null) {
            if (model.hasSupplier(supplierToAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_SUPPLIER);
            }

            model.addSupplier(supplierToAdd);
            model.saveVersionedClinic();
            logger.log(Level.INFO, LOG_MESSAGE_ADD_SUPPLIER_SUCCESS);

            commandResult = new CommandResult(String.format(MESSAGE_SUPPLIER_SUCCESS, supplierToAdd));
        } else {
            assert this.warehouseToAdd != null : INVALID_WAREHOUSE_ASSERTION;
            if (model.hasWarehouse(warehouseToAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_WAREHOUSE);
            }

            model.addWarehouse(warehouseToAdd);
            model.saveVersionedClinic();
            logger.log(Level.INFO, LOG_MESSAGE_ADD_WAREHOUSE_SUCCESS);

            commandResult = new CommandResult(String.format(MESSAGE_WAREHOUSE_SUCCESS, warehouseToAdd));
        }
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && (supplierToAdd != null
                ? supplierToAdd.equals(((AddCommand) other).supplierToAdd)
                : warehouseToAdd.equals(((AddCommand) other).warehouseToAdd)));
    }
}
