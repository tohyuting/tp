package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_WAREHOUSES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.clinic.commons.core.Messages;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.commons.util.CollectionUtil;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;
import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Email;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;


/**
 * Edits the details of an existing supplier/warehouse in the CLI-nic app.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the supplier/warehouse"
            + " identified " + "by the index number used in the displayed supplier/warehouse list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: [si/INDEX] [wi/INDEX] "
            + "[" + PREFIX_SUPPLIER_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_REMARK + "REMARK] \n"
            + "Example: " + COMMAND_WORD + " si/1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "Note that either si or wi (not both) has to be provided, where si is the supplier list index"
            + " and wi is the warehouse list index. In addition, a supplier should not have an address"
            + " prefix entered while a warehouse should not have an email prefix entered. ";

    public static final String MESSAGE_EDIT_SUPPLIER_SUCCESS = "Edited Supplier: %1$s";
    public static final String MESSAGE_EDIT_WAREHOUSE_SUCCESS = "Edited Warehouse: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SUPPLIER = "This supplier already"
            + " exists in the remark book.";
    public static final String MESSAGE_DUPLICATE_WAREHOUSE = "This warehouse already"
            + " exists in the remark book.";
    public static final String MESSAGE_SUPPLIER_NO_ADDRESS = "Supplier do not have address!";
    public static final String MESSAGE_WAREHOUSE_NO_EMAIL = "Warehouse do not have email!";
    public static final String MESSAGE_INPUT_BOTH_SUPPLIER_WAREHOUSE_PREFIX = "Please only enter one type of"
            + " index, i.e. either wi/INDEX or si/INDEX";
    public static final String MESSAGE_NO_PREFIX = "Please enter at least one type of"
            + " index, i.e. either wi/INDEX or si/INDEX";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! %1$s \n"
            + MESSAGE_USAGE;
    public static final String MESSAGE_INVALID_PREFIX = "You used an invalid prefix!";
    public static final String MESSAGE_SUPPLIER_PREFIX_NOT_ALLOWED = "Supplier prefix (s/) not allowed "
            + "when editing warehouses.";
    public static final String MESSAGE_WAREHOUSE_PREFIX_NOT_ALLOWED = "Warehouse prefix (w/) not allowed "
            + "when editing suppliers.";

    private final Index index;
    private final EditDescriptor editDescriptor;

    /**
     * @param index of the supplier or warehouse in the filtered supplier or warehouse list to edit
     * @param editDescriptor details to edit the supplier with
     */
    public EditCommand(Index index, EditDescriptor editDescriptor) {
        requireNonNull(index);
        requireNonNull(editDescriptor);

        this.index = index;
        if (editDescriptor instanceof EditSupplierDescriptor) {
            this.editDescriptor = new EditSupplierDescriptor((EditSupplierDescriptor) editDescriptor);
        } else {
            this.editDescriptor = new EditWarehouseDescriptor((EditWarehouseDescriptor) editDescriptor);
        }

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Supplier> lastShownSupplierList = model.getFilteredSupplierList();
        List<Warehouse> lastShownWarehouseList = model.getFilteredWarehouseList();
        CommandResult commandResult;

        if (this.editDescriptor instanceof EditSupplierDescriptor) {
            if (index.getZeroBased() >= lastShownSupplierList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
            }

            Supplier supplierToEdit = lastShownSupplierList.get(index.getZeroBased());
            Supplier editedSupplier = createEditedSupplier(supplierToEdit,
                    (EditSupplierDescriptor) editDescriptor);

            if (!supplierToEdit.isSameSupplier(editedSupplier) && model.hasSupplier(editedSupplier)) {
                throw new CommandException(MESSAGE_DUPLICATE_SUPPLIER);
            }

            model.setSupplier(supplierToEdit, editedSupplier);
            model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
            commandResult = new CommandResult(String.format(MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier));
        } else {
            if (index.getZeroBased() >= lastShownWarehouseList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
            }
            Warehouse warehouseToEdit = lastShownWarehouseList.get(index.getZeroBased());
            Warehouse editedWarehouse = createEditedWarehouse(warehouseToEdit,
                    (EditWarehouseDescriptor) editDescriptor);

            if (!warehouseToEdit.isSameWarehouse(editedWarehouse) && model.hasWarehouse(editedWarehouse)) {
                throw new CommandException(MESSAGE_DUPLICATE_WAREHOUSE);
            }

            model.setWarehouse(warehouseToEdit, editedWarehouse);
            model.updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);
            commandResult = new CommandResult(String.format(MESSAGE_EDIT_WAREHOUSE_SUCCESS, editedWarehouse));
        }
        return commandResult;
    }

    /**
     * Creates and returns a {@code Supplier} with the details of {@code supplierToEdit}
     * edited with {@code editSupplierDescriptor}.
     */
    private static Supplier createEditedSupplier(Supplier supplierToEdit,
            EditSupplierDescriptor editSupplierDescriptor) {
        assert supplierToEdit != null;

        Name updatedName = editSupplierDescriptor.getName().orElse(supplierToEdit.getName());
        Phone updatedPhone = editSupplierDescriptor.getPhone().orElse(supplierToEdit.getPhone());
        Email updatedEmail = editSupplierDescriptor.getEmail().orElse(supplierToEdit.getEmail());
        Remark remark = editSupplierDescriptor.getRemark().orElse(supplierToEdit.getRemark());
        Set<Product> products = supplierToEdit.getProducts();

        return new Supplier(updatedName, updatedPhone, updatedEmail, remark, products);
    }

    /**
     * Creates and returns a {@code Warehouse} with the details of {@code warehouseToEdit}
     * edited with {@code editWarehouseDescriptor}.
     */
    private static Warehouse createEditedWarehouse(Warehouse warehouseToEdit,
                                                   EditWarehouseDescriptor editWarehouseDescriptor) {
        assert warehouseToEdit != null;

        Name updatedName = editWarehouseDescriptor.getName().orElse(warehouseToEdit.getName());
        Phone updatedPhone = editWarehouseDescriptor.getPhone().orElse(warehouseToEdit.getPhone());
        Address updatedAddress = editWarehouseDescriptor.getAddress().orElse(warehouseToEdit.getAddress());
        Remark remark = editWarehouseDescriptor.getRemark().orElse(warehouseToEdit.getRemark());
        Set<Product> products = warehouseToEdit.getProducts();

        return new Warehouse(updatedName, updatedPhone, updatedAddress, remark, products);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        if ((e.editDescriptor instanceof EditSupplierDescriptor)
                && (editDescriptor instanceof EditWarehouseDescriptor)) {
            return false;
        } else if ((e.editDescriptor instanceof EditWarehouseDescriptor)
                && (editDescriptor instanceof EditSupplierDescriptor)) {
            return false;
        }
        return index.equals(e.index)
                && editDescriptor.equals(e.editDescriptor);
    }

    /**
     * Stores the details to edit the general details of a supplier/warehouse with. Each non-empty field
     * value will replace the corresponding field value of the supplier/warehouse to be edited.
     */
    public static class EditDescriptor {
        private Name name;
        private Phone phone;
        private Remark remark;
        private Set<Product> products;

        public EditDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code products} is used internally.
         */
        public EditDescriptor (EditDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setRemark(toCopy.remark);
            setProducts(toCopy.products);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, remark, products);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        /**
         * Sets {@code products} to this object's {@code products}.
         * A defensive copy of {@code products} is used internally.
         */
        public void setProducts(Set<Product> products) {
            this.products = (products != null) ? new HashSet<>(products) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code products} is null.
         */
        public Optional<Set<Product>> getProducts() {
            return (products != null) ? Optional.of(Collections.unmodifiableSet(products)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDescriptor)) {
                return false;
            }

            // state check
            EditDescriptor e = (EditDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getRemark().equals(e.getRemark())
                    && getProducts().equals(e.getProducts());
        }
    }

    /**
     * Stores the details to edit the supplier with. Each non-empty field value will replace the
     * corresponding field value of the supplier.
     */
    public static class EditSupplierDescriptor extends EditDescriptor {
        private Email email;

        public EditSupplierDescriptor() {
            super();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code products} is used internally.
         */
        public EditSupplierDescriptor(EditSupplierDescriptor toCopy) {
            super(toCopy);
            setEmail(toCopy.email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            boolean generalDetails = super.isAnyFieldEdited();
            return CollectionUtil.isAnyNonNull(email) || generalDetails;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSupplierDescriptor)) {
                return false;
            }

            // state check
            EditSupplierDescriptor e = (EditSupplierDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getRemark().equals(e.getRemark())
                    && getProducts().equals(e.getProducts());
        }
    }

    /**
     * Stores the details to edit the warehouse with. Each non-empty field value will replace the
     * corresponding field value of the warehouse.
     */
    public static class EditWarehouseDescriptor extends EditDescriptor {
        private Address address;

        public EditWarehouseDescriptor() {
            super();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code products} is used internally.
         */
        public EditWarehouseDescriptor(EditWarehouseDescriptor toCopy) {
            super(toCopy);
            setAddress(toCopy.address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            boolean generalDetails = super.isAnyFieldEdited();
            return CollectionUtil.isAnyNonNull(address) || generalDetails;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditWarehouseDescriptor)) {
                return false;
            }

            // state check
            EditWarehouseDescriptor e = (EditWarehouseDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getAddress().equals(e.getAddress())
                    && getRemark().equals(e.getRemark())
                    && getProducts().equals(e.getProducts());
        }
    }
}
