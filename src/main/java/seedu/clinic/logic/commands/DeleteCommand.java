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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
 * Deletes a supplier identified using it's displayed index from the CLI-nic app.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "\nUsage 1 - Deletes the supplier/warehouse identified by the index number used in the displayed lists."
            + "\nParameters:"
            + PREFIX_TYPE + "TYPE "
            + PREFIX_INDEX + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "s "
            + PREFIX_INDEX + "1 \n"
            + "\nUsage 2 - Deletes product the supplier/warehouse identified by the index number used "
            + "in the displayed lists.\n"
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_PRODUCT_NAME + " PRODUCT_NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "s "
            + PREFIX_INDEX + "2 "
            + PREFIX_PRODUCT_NAME + "new product \n";

    public static final String MESSAGE_DELETE_SUPPLIER_SUCCESS = "Deleted Supplier: %1$s";
    public static final String MESSAGE_DELETE_WAREHOUSE_SUCCESS = "Deleted Warehouse: %1$s";
    public static final String MESSAGE_DELETE_PRODUCT_IN_WAREHOUSE_SUCCESS =
            "Deleted Product: %1$s from Warehouse: %2$s";
    public static final String MESSAGE_DELETE_PRODUCT_IN_SUPPLIER_SUCCESS =
            "Deleted Product: %1$s from Supplier: %2$s";

    private final Type targetType;
    private final Index targetIndex;
    private final Optional<Name> targetProductName;

    /**
     * Creates an DeleteCommand to delete the warehouse/supplier at {@code targetIndex} of the displayed list.
     */
    public DeleteCommand(Type targetType, Index targetIndex) {
        this.targetType = targetType;
        this.targetIndex = targetIndex;
        this.targetProductName = Optional.empty();
    }

    /**
     * Creates an DeleteCommand to delete the specified {@code targetProductName} in the warehouse/supplier
     * at {@code targetIndex} of the displayed list.
     */
    public DeleteCommand(Type targetType, Index targetIndex, Name targetProductName) {
        this.targetType = targetType;
        this.targetIndex = targetIndex;
        this.targetProductName = Optional.of(targetProductName);
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

    private CommandResult executeWarehouseRelatedDeletion(Model model) throws CommandException {
        List<Warehouse> lastShownList = model.getFilteredWarehouseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
        }

        if (targetType.equals(WAREHOUSE)) {
            Warehouse warehouseToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteWarehouse(warehouseToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_WAREHOUSE_SUCCESS, warehouseToDelete));
        }

        Warehouse warehouseToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Set<Product> productSetToUpdate = new HashSet<>(warehouseToUpdate.getProducts());
        Product matchedProduct;
        try {
            matchedProduct = warehouseToUpdate.getProductByName(targetProductName.get());
            productSetToUpdate.remove(matchedProduct); // removes the matching product
            Warehouse updatedWarehouse = new Warehouse(warehouseToUpdate.getName(), warehouseToUpdate.getPhone(),
                    warehouseToUpdate.getAddress(), warehouseToUpdate.getRemark(), productSetToUpdate);
            model.setWarehouse(warehouseToUpdate, updatedWarehouse);
            model.updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);
            return new CommandResult(String.format(MESSAGE_DELETE_PRODUCT_IN_WAREHOUSE_SUCCESS,
                    matchedProduct.getProductName(), updatedWarehouse.getName()));
        } catch (ProductNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PRODUCT_NAME_IN_WAREHOUSE,
                    targetProductName.get(), warehouseToUpdate.getName()));
        }
    }

    private CommandResult executeSupplierRelatedDeletion(Model model) throws CommandException {
        List<Supplier> lastShownList = model.getFilteredSupplierList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        if (targetType.equals(SUPPLIER)) {
            Supplier supplierToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteSupplier(supplierToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierToDelete));
        }

        Supplier supplierToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Set<Product> updatedProductSet = new HashSet<>(supplierToUpdate.getProducts());
        Product matchedProduct;
        try {
            matchedProduct = supplierToUpdate.getProductByName(targetProductName.get());
            updatedProductSet.remove(matchedProduct); // removes the matching product
            Supplier updatedSupplier = new Supplier(supplierToUpdate.getName(), supplierToUpdate.getPhone(),
                    supplierToUpdate.getEmail(), supplierToUpdate.getRemark(), updatedProductSet);
            model.setSupplier(supplierToUpdate, updatedSupplier);
            model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
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
