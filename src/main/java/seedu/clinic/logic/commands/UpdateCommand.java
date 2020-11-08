package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_QUANTITY;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_WAREHOUSES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.clinic.commons.core.Messages;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.commons.util.CollectionUtil;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.Type;
import seedu.clinic.model.Model;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Tag;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Updates the quantity/tags for a product in a specific warehouse/supplier, or adds the product if it does not
 * already exist for that warehouse/supplier.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";
    public static final String COMPULSORY_UPDATE_SUPPLIER_COMMAND = "update ct/s i/ pd/";
    public static final String COMPULSORY_UPDATE_WAREHOUSE_COMMAND = "update ct/w i/ pd/";

    public static final String MESSAGE_USAGE = "Update Command Usage\n\nUpdates the quantity and/or tags of"
            + " the product with the specified"
            + " name for the supplier or warehouse at the index specified. If the product does not exist for"
            + " that supplier or warehouse, a new product will be created for that supplier or warehouse."
            + " TYPE specified should be either s for supplier or w for warehouse."
            + " If the PRODUCT_NAME already exists in the supplier or warehouse,"
            + " at least one optional argument has to be entered.\n\n"
            + "Parameters:\n"
            + PREFIX_TYPE + "TYPE "
            + PREFIX_INDEX + "INDEX (Must be a positive integer) "
            + PREFIX_PRODUCT_NAME + "PRODUCT_NAME "
            + "[" + PREFIX_PRODUCT_QUANTITY + "QUANTITY]"
            + "[" + PREFIX_TAG + "TAG...]\n\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TYPE + "w "
            + PREFIX_INDEX + "1 "
            + PREFIX_PRODUCT_NAME + "Panadol "
            + PREFIX_PRODUCT_QUANTITY + "350 "
            + PREFIX_TAG + "Fever Headache";

    public static final String MESSAGE_SUCCESS = "Product stock updated: \n\n%1$s\n\nin %2$s.";
    private static final String MESSAGE_INVALID_TYPE = "You used an invalid type! Type for this command "
            + "should be either ct/s or ct/w only.\n\n%1$s";
    private static final String MESSAGE_EMPTY_DESCRIPTOR = "Either the quantity or tags (or both) has to be "
            + "supplied to update an existing product.";

    private final Type entityType;
    private final Index index;
    private final Name productName;
    private final UpdateProductDescriptor updateProductDescriptor;

    /**
     * Creates an UpdateCommand to update the product with the specified {@code productName} for the
     * supplier/warehouse at the specified {@code index}
     */
    public UpdateCommand(Type entityType, Index index, Name productName,
            UpdateProductDescriptor updateProductDescriptor) {
        requireAllNonNull(entityType, index, productName, updateProductDescriptor);

        this.entityType = entityType;
        this.index = index;
        this.productName = productName;
        this.updateProductDescriptor = updateProductDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        switch (entityType) {
        case SUPPLIER:
            return updateProductForSupplier(model);
        case WAREHOUSE:
            return updateProductForWarehouse(model);
        default:
            throw new CommandException(String.format(MESSAGE_INVALID_TYPE, UpdateCommand.MESSAGE_USAGE));
        }
    }

    private CommandResult updateProductForWarehouse(Model model) throws CommandException {
        List<Warehouse> lastShownList = model.getFilteredWarehouseList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
        }

        Warehouse warehouseToUpdate = lastShownList.get(index.getZeroBased());
        Set<Product> updatedProductSet = new HashSet<>(warehouseToUpdate.getProducts());
        Product productToUpdate;

        if (warehouseToUpdate.hasProductWithName(productName)) {
            if (!updateProductDescriptor.isAnyFieldEdited()) {
                throw new CommandException(MESSAGE_EMPTY_DESCRIPTOR);
            }

            productToUpdate = warehouseToUpdate.getProductByName(productName);
            updatedProductSet.remove(productToUpdate); // removes the existing entry for the product
        } else {
            productToUpdate = new Product(productName, 0, new HashSet<>());
        }

        Product updatedProduct = createUpdatedProduct(productToUpdate, updateProductDescriptor);
        updatedProductSet.add(updatedProduct);
        Warehouse updatedWarehouse = new Warehouse(warehouseToUpdate.getName(), warehouseToUpdate.getPhone(),
                warehouseToUpdate.getAddress(), warehouseToUpdate.getRemark(), updatedProductSet);
        model.setWarehouse(warehouseToUpdate, updatedWarehouse);
        model.updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);
        model.saveVersionedClinic();
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedProduct.toStringWithTags().trim(),
                updatedWarehouse.getName().fullName));
    }

    private CommandResult updateProductForSupplier(Model model) throws CommandException {
        List<Supplier> lastShownList = model.getFilteredSupplierList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        Supplier supplierToUpdate = lastShownList.get(index.getZeroBased());
        Set<Product> updatedProductSet = new HashSet<>(supplierToUpdate.getProducts());
        Product productToUpdate;

        if (supplierToUpdate.hasProductWithName(productName)) {
            if (!updateProductDescriptor.isAnyFieldEdited()) {
                throw new CommandException(MESSAGE_EMPTY_DESCRIPTOR);
            }

            productToUpdate = supplierToUpdate.getProductByName(productName);
            updatedProductSet.remove(productToUpdate); // removes the existing entry for the product
        } else {
            productToUpdate = new Product(productName, 0, new HashSet<>());
        }

        Product updatedProduct = createUpdatedProduct(productToUpdate, updateProductDescriptor);
        updatedProductSet.add(updatedProduct);
        Supplier updatedSupplier = new Supplier(supplierToUpdate.getName(), supplierToUpdate.getPhone(),
                supplierToUpdate.getEmail(), supplierToUpdate.getRemark(), updatedProductSet);
        model.setSupplier(supplierToUpdate, updatedSupplier);
        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        model.saveVersionedClinic();
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedProduct.toStringWithTags().trim(),
                updatedSupplier.getName().fullName));
    }

    /**
     * Creates and returns a {@code Product} with the details of {@code productToUpdate}
     * updated with {@code updateProductDescriptor}.
     */
    private static Product createUpdatedProduct(Product productToUpdate,
            UpdateProductDescriptor updateProductDescriptor) {
        assert productToUpdate != null;

        int updatedQuantity = updateProductDescriptor.getQuantity().orElse(productToUpdate.getProductQuantity());
        Set<Tag> updatedTags = updateProductDescriptor.getTags().orElse(productToUpdate.getProductTags());

        return new Product(productToUpdate.getProductName(), updatedQuantity, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdateCommand // instanceof handles nulls
                && entityType.equals(((UpdateCommand) other).entityType)
                && index.equals(((UpdateCommand) other).index)
                && productName.equals(((UpdateCommand) other).productName)
                && updateProductDescriptor.equals(((UpdateCommand) other).updateProductDescriptor));
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityType, index, productName, updateProductDescriptor);
    }

    /**
     * Stores the details to edit the product with. Each non-empty field value will replace the
     * corresponding field value of the product.
     */
    public static class UpdateProductDescriptor {
        private Integer quantity;
        private Set<Tag> tags;

        public UpdateProductDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UpdateProductDescriptor(UpdateProductDescriptor toCopy) {
            setQuantity(toCopy.quantity);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(quantity, tags);
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Optional<Integer> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateProductDescriptor)) {
                return false;
            }

            // state check
            UpdateProductDescriptor e = (UpdateProductDescriptor) other;

            return getQuantity().equals(e.getQuantity())
                    && getTags().equals(e.getTags());
        }
    }

}
