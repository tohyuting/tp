package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.TYPE_SUPPLIER;

import java.util.List;

import seedu.clinic.commons.core.Messages;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.Model;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Deletes a supplier identified using it's displayed index from the CLI-nic app.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the supplier/warehouse identified by the index number used in the displayed lists.\n"
            + "Parameters: TYPE INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " supplier 1\n"
            + COMMAND_WORD + " warehouse 1";

    public static final String MESSAGE_DELETE_SUPPLIER_SUCCESS = "Deleted Supplier: %1$s";
    public static final String MESSAGE_DELETE_WAREHOUSE_SUCCESS = "Deleted Warehouse: %1$s";

    private final String targetType;
    private final Index targetIndex;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand(String targetType, Index targetIndex) {
        this.targetType = targetType;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetType.equals(TYPE_SUPPLIER)) {
            List<Supplier> lastShownList = model.getFilteredSupplierList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
            }

            Supplier supplierToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteSupplier(supplierToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierToDelete));
        } else {
            List<Warehouse> lastShownList = model.getFilteredWarehouseList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
            }

            Warehouse warehouseToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteWarehouse(warehouseToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_WAREHOUSE_SUCCESS, warehouseToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetType.equals(((DeleteCommand) other).targetType)
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
