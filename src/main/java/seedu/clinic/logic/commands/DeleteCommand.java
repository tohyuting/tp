package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.Type.SUPPLIER;
import static seedu.clinic.logic.parser.Type.SUPPLIER_PRODUCT;
import static seedu.clinic.logic.parser.Type.WAREHOUSE;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_WAREHOUSES;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.commons.core.Messages;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.Type;
import seedu.clinic.model.Model;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.product.exceptions.ProductNotFoundException;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Deletes a supplier identified using its displayed index from the CLI-nic app.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMPLETE_DELETE_SUPPLIER_COMMAND = "delete ct/s i/";
    public static final String COMPLETE_DELETE_WAREHOUSE_COMMAND = "delete ct/w i/";
    public static final String COMPLETE_DELETE_SUPPLIER_PRODUCT_COMMAND = "delete ct/ps i/ pd/";
    public static final String COMPLETE_DELETE_WAREHOUSE_PRODUCT_COMMAND = "delete ct/pw i/ pd/";

    public static final String MESSAGE_USAGE =
            "Delete Command Usage\n\nUsage 1 - Deletes the supplier or warehouse identified by the index"
            + " number used in the displayed lists."
            + " INDEX must be a positive integer, not exceeding the total length of the displayed list.\n\n"
            + "Parameters:\n"
            + PREFIX_TYPE + "TYPE "
            + PREFIX_INDEX + "INDEX\n\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TYPE + "s "
            + PREFIX_INDEX + "1\n\n"
            + "Usage 2 - Deletes a product associated with a specific supplier or warehouse identified by the index"
            + " number used in the displayed lists.\n\n"
            + "Parameters:\n"
            + PREFIX_TYPE + "TYPE "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_PRODUCT_NAME + " PRODUCT_NAME\n\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TYPE + "ps "
            + PREFIX_INDEX + "2 "
            + PREFIX_PRODUCT_NAME + "panadol";

    public static final String MESSAGE_DELETE_SUPPLIER_SUCCESS = "Deleted Supplier: %1$s";
    public static final String MESSAGE_DELETE_WAREHOUSE_SUCCESS = "Deleted Warehouse: %1$s";
    public static final String MESSAGE_DELETE_PRODUCT_IN_WAREHOUSE_SUCCESS =
            "Deleted Product: %1$s from Warehouse: %2$s";
    public static final String MESSAGE_DELETE_PRODUCT_IN_SUPPLIER_SUCCESS =
            "Deleted Product: %1$s from Supplier: %2$s";

    private static final String LOG_MESSAGE_NORMAL_DELETION =
            "Received information to delete a supplier or a warehouse.";
    private static final String LOG_MESSAGE_PRODUCT_DELETION =
            "Received information to delete a product from a supplier or a warehouse.";

    private final Type targetType;
    private final Index targetIndex;
    private final Optional<Name> targetProductName;
    private final Logger logger = LogsCenter.getLogger(getClass());

    //@@author criss-wang
    /**
     * Creates a DeleteCommand to delete the warehouse/supplier at {@code targetIndex} of the displayed list.
     */
    public DeleteCommand(Type targetType, Index targetIndex) {
        this.targetType = targetType;
        this.targetIndex = targetIndex;
        this.targetProductName = Optional.empty();
        logger.log(Level.INFO, LOG_MESSAGE_NORMAL_DELETION);
    }

    //@@author criss-wang
    /**
     * Creates a DeleteCommand to delete the specified {@code targetProductName} in the warehouse/supplier
     * at {@code targetIndex} of the displayed list.
     */
    public DeleteCommand(Type targetType, Index targetIndex, Name targetProductName) {
        this.targetType = targetType;
        this.targetIndex = targetIndex;
        this.targetProductName = Optional.of(targetProductName);
        logger.log(Level.INFO, LOG_MESSAGE_PRODUCT_DELETION);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetType.equals(SUPPLIER) || targetType.equals(SUPPLIER_PRODUCT)) {
            return executeSupplierRelatedDeletion(model);
        } else {
            return executeWarehouseRelatedDeletion(model);
        }
    }

    //@@author criss-wang
    private CommandResult executeWarehouseRelatedDeletion(Model model) throws CommandException {
        List<Warehouse> lastShownList = model.getFilteredWarehouseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
        }

        if (targetType.equals(WAREHOUSE)) {
            Warehouse warehouseToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteWarehouse(warehouseToDelete);
            model.saveVersionedClinic();
            return new CommandResult(String.format(MESSAGE_DELETE_WAREHOUSE_SUCCESS, warehouseToDelete));
        }

        Warehouse warehouseToUpdate = lastShownList.get(targetIndex.getZeroBased());
        try {
            // Finds the product with name matching the targetProductName
            Product matchedProduct = warehouseToUpdate.getProductByName(targetProductName.get());

            // Removes the matchedProduct from the target warehouse
            Warehouse updatedWarehouse = warehouseToUpdate.removeProduct(matchedProduct);

            // Updates the warehouse list in the model
            model.setWarehouse(warehouseToUpdate, updatedWarehouse);
            model.updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);
            model.saveVersionedClinic();
            return new CommandResult(String.format(MESSAGE_DELETE_PRODUCT_IN_WAREHOUSE_SUCCESS,
                    matchedProduct.getProductName(), updatedWarehouse.getName()));
        } catch (ProductNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PRODUCT_NAME_IN_WAREHOUSE,
                    targetProductName.get(), warehouseToUpdate.getName()));
        }
    }

    //@@author criss-wang
    private CommandResult executeSupplierRelatedDeletion(Model model) throws CommandException {
        List<Supplier> lastShownList = model.getFilteredSupplierList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        if (targetType.equals(SUPPLIER)) {
            Supplier supplierToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteSupplier(supplierToDelete);
            model.saveVersionedClinic();
            return new CommandResult(String.format(MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierToDelete));
        }

        Supplier supplierToUpdate = lastShownList.get(targetIndex.getZeroBased());
        try {
            Product matchedProduct = supplierToUpdate.getProductByName(targetProductName.get());
            Supplier updatedSupplier = supplierToUpdate.removeProduct(matchedProduct);
            model.setSupplier(supplierToUpdate, updatedSupplier);
            model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
            model.saveVersionedClinic();
            return new CommandResult(String.format(MESSAGE_DELETE_PRODUCT_IN_SUPPLIER_SUCCESS,
                    matchedProduct.getProductName(), updatedSupplier.getName()));
        } catch (ProductNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PRODUCT_NAME_IN_SUPPLIER,
                    targetProductName.get(), supplierToUpdate.getName()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetType.equals(((DeleteCommand) other).targetType)
                && targetIndex.equals(((DeleteCommand) other).targetIndex)
                && targetProductName.equals(((DeleteCommand) other).targetProductName)); // state check
    }
}
