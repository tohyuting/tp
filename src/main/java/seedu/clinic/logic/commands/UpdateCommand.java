package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_QUANTITY;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_WAREHOUSES;

import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

    public static final String MESSAGE_USAGE = "Update Command Usage\n\nUpdates the quantity and/or tags of"
            + " the product with the specified"
            + " name in the specified supplier or warehouse. If the product does not exist for that supplier or"
            + " warehouse, a new product will be created for that supplier or warehouse."
            + " TYPE specified should be either s for supplier or w for warehouse. QUANTITY should"
            + " be a non-negative unsigned integer. If the PRODUCT_NAME already exists in the supplier or warehouse,"
            + " at least one optional argument has to be entered.\n\n"
            + "Parameters:\n"
            + PREFIX_TYPE + "TYPE "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRODUCT_NAME + "PRODUCT_NAME "
            + "[" + PREFIX_PRODUCT_QUANTITY + "QUANTITY]"
            + "[" + PREFIX_TAG + "TAG]\n\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TYPE + "w "
            + PREFIX_NAME + "Alex Yeoh warehouse "
            + PREFIX_PRODUCT_NAME + "Panadol "
            + PREFIX_PRODUCT_QUANTITY + "350 "
            + PREFIX_TAG + "Fever";

    public static final String MESSAGE_SUCCESS = "Product stock updated: %1$s in %2$s.";
    private static final String MESSAGE_NO_SUCH_ENTITY = "The specified warehouse/supplier cannot be found.";
    private static final String MESSAGE_INVALID_TYPE = "Invalid Type.";
    private static final String MESSAGE_EMPTY_DESCRIPTOR = "Either the quantity or tags (or both) has to be "
            + "supplied to update an existing product.";

    private final Type entityType;
    private final Name entityName;
    private final Name productName;
    private final UpdateProductDescriptor updateProductDescriptor;

    /**
     * Creates an UpdateCommand to update the product with the specified {@code productName} for the
     * supplier/warehouse with the specified {@code entityName}
     */
    public UpdateCommand(Type entityType, Name entityName, Name productName,
            UpdateProductDescriptor updateProductDescriptor) {
        requireAllNonNull(entityType, entityName, productName, updateProductDescriptor);

        this.entityName = entityName;
        this.productName = productName;
        this.entityType = entityType;
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
            throw new CommandException(MESSAGE_INVALID_TYPE);
        }
    }

    private CommandResult updateProductForWarehouse(Model model) throws CommandException {
        Warehouse warehouseToUpdate;

        try {
            warehouseToUpdate = getWarehouseByName(entityName, model);
        } catch (NoSuchElementException e) {
            throw new CommandException(MESSAGE_NO_SUCH_ENTITY);
        }

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
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedProduct.toString(),
                updatedWarehouse.getName().fullName));
    }

    private CommandResult updateProductForSupplier(Model model) throws CommandException {
        Supplier supplierToUpdate;

        try {
            supplierToUpdate = getSupplierByName(entityName, model);
        } catch (NoSuchElementException e) {
            throw new CommandException(MESSAGE_NO_SUCH_ENTITY);
        }

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
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedProduct.toString(),
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

    public static Warehouse getWarehouseByName(Name warehouseName, Model model) throws NoSuchElementException {
        return model.getClinic().getWarehouseList().stream()
                .filter(warehouse -> warehouse.getName().equals(warehouseName)).findFirst().orElseThrow();
    }

    public static Supplier getSupplierByName(Name supplierName, Model model) throws NoSuchElementException {
        return model.getClinic().getSupplierList().stream()
                .filter(supplier -> supplier.getName().equals(supplierName)).findFirst().orElseThrow();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdateCommand // instanceof handles nulls
                && entityType.equals(((UpdateCommand) other).entityType)
                && entityName.equals(((UpdateCommand) other).entityName)
                && productName.equals(((UpdateCommand) other).productName)
                && updateProductDescriptor.equals(((UpdateCommand) other).updateProductDescriptor));
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityType, entityName, productName, updateProductDescriptor);
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
