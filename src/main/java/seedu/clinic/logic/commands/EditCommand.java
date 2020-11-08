package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_WAREHOUSES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
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
    public static final String COMPULSORY_EDIT_SUPPLIER_COMMAND = "edit ct/s i/";
    public static final String COMPULSORY_EDIT_WAREHOUSE_COMMAND = "edit ct/w i/";

    public static final String MESSAGE_USAGE = "Edit Command Usage\n\nEdits a supplier or warehouse at the"
            + " specified index."
            + " For suppliers, only its name, phone, email and remarks can be edited."
            + " For warehouses, only its name, phone, address and remark can be edited."
            + " Note that EMAIL can only be used for suppliers while ADDRESS can only be used for warehouses."
            + " TYPE specified should be either s for supplier or w for warehouse.\n\n"
            + "Parameters:\n"
            + "ct/TYPE i/INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_REMARK + "REMARK]\n\n"
            + "Examples:\n"
            + "1) " + COMMAND_WORD + " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 " + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "2) " + COMMAND_WORD + " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "5 " + PREFIX_PHONE + "99876544 "
            + PREFIX_ADDRESS + "21 Lower Kent Ridge Road";

    public static final String MESSAGE_EDIT_SUPPLIER_SUCCESS = "Edited Supplier: %1$s";
    public static final String MESSAGE_EDIT_WAREHOUSE_SUCCESS = "Edited Warehouse: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.\n\n%1$s";
    public static final String MESSAGE_DUPLICATE_SUPPLIER = "A supplier with the same name already"
            + " exists in CLI-nic.";
    public static final String MESSAGE_DUPLICATE_WAREHOUSE = "A warehouse with the same name already"
            + " exists in CLI-nic.";
    public static final String MESSAGE_SUPPLIER_NO_ADDRESS = "Supplier do not have address!\n\n%1$s";
    public static final String MESSAGE_WAREHOUSE_NO_EMAIL = "Warehouse do not have email!\n\n%1$s";
    public static final String MESSAGE_INPUT_BOTH_SUPPLIER_WAREHOUSE_PREFIX = "Please only enter one type of"
            + " index, i.e. either wi/INDEX or si/INDEX";
    public static final String MESSAGE_NO_PREFIX = "Please enter at least one type of"
            + " command under ct (i.e. either ct/s or ct/w)\n%1$s";
    public static final String MESSAGE_SUPPLIER_UNCHANGED = "The edited field(s) will results in no change to"
            + " supplier selected. Please check your arguments again and re-enter your edit command.";
    public static final String MESSAGE_WAREHOUSE_UNCHANGED = "The edited field(s) will results in no change"
            + " to warehouse selected. Please check your arguments again and re-enter your edit command.";

    private static final String LOG_MESSAGE_RECEIVE_EDIT_SUPPLIER = "Received instructions to edit supplier.";
    private static final String LOG_MESSAGE_RECEIVE_EDIT_WAREHOUSE = "Received instructions to edit warehouse.";
    private static final String LOG_MESSAGE_SUPPLIER_RETRIEVED = "Supplier to edit is retrieved from supplier list.";
    private static final String LOG_MESSAGE_WAREHOUSE_RETRIEVED = "Warehouse to edit is retrieved from warehouse list.";
    private static final String LOG_MESSAGE_SUPPLIER_EDITED = "Supplier with edited information has been created.";
    private static final String LOG_MESSAGE_WAREHOUSE_EDITED = "Warehouse with edited information has been created.";
    private static final String LOG_MESSAGE_SUPPLIER_REPLACED_IN_MODEL = "Replaced supplier in supplier list.";
    private static final String LOG_MESSAGE_WAREHOUSE_REPLACED_IN_MODEL = "Replaced warehouse in warehouse list.";
    private static final String LOG_SUPPLIER_UPDATED_IN_UI = "Updated supplier in UI.";
    private static final String LOG_WAREHOUSE_UPDATED_IN_UI = "Updated warehouse in UI.";

    private static final String INVALID_EDIT_SUPPLIER_DESCRIPTOR_ASSERTION = "editDescriptor supplied should be "
            + "of EditSupplierDescriptor type here.";
    private static final String INVALID_EDIT_WAREHOUSE_DESCRIPTOR_ASSERTION = "editDescriptor supplied"
            + " should be of EditWarehouseDescriptor type here.";
    private static final String INVALID_OBJECT_IN_COMPARISON_ASSERTION =
            "Both editDescriptors should be of editDescriptor type.";

    private final Index index;
    private final EditDescriptor editDescriptor;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an EditCommand to edit a supplier/warehouse at {@code index} of the displayed list.
     *
     * @param index of the supplier or warehouse in the filtered supplier or warehouse list to edit
     * @param editDescriptor details to edit the supplier/warehouse with
     */
    public EditCommand(Index index, EditDescriptor editDescriptor) {
        requireNonNull(index);
        requireNonNull(editDescriptor);

        this.index = index;
        if (editDescriptor instanceof EditSupplierDescriptor) {
            this.editDescriptor = new EditSupplierDescriptor((EditSupplierDescriptor) editDescriptor);
            logger.log(Level.INFO, LOG_MESSAGE_RECEIVE_EDIT_SUPPLIER);
        } else {
            assert editDescriptor instanceof EditWarehouseDescriptor : INVALID_EDIT_SUPPLIER_DESCRIPTOR_ASSERTION;
            this.editDescriptor = new EditWarehouseDescriptor((EditWarehouseDescriptor) editDescriptor);
            logger.log(Level.INFO, LOG_MESSAGE_RECEIVE_EDIT_WAREHOUSE);
        }

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.editDescriptor instanceof EditSupplierDescriptor) {
            return executeSupplierEditing(model);
        } else {
            assert this.editDescriptor instanceof EditWarehouseDescriptor
                    : INVALID_EDIT_WAREHOUSE_DESCRIPTOR_ASSERTION;
            return executeWarehouseEditing(model);
        }
    }

    private CommandResult executeSupplierEditing(Model model) throws CommandException {
        List<Supplier> lastShownSupplierList = model.getFilteredSupplierList();

        if (index.getZeroBased() >= lastShownSupplierList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        Supplier supplierToEdit = lastShownSupplierList.get(index.getZeroBased());

        logger.log(Level.INFO, LOG_MESSAGE_SUPPLIER_RETRIEVED);

        Supplier editedSupplier = createEditedSupplier(supplierToEdit, (EditSupplierDescriptor) editDescriptor);

        logger.log(Level.INFO, LOG_MESSAGE_SUPPLIER_EDITED);

        if (supplierToEdit.equalsSupplierCaseSensitive(editedSupplier)) {
            throw new CommandException(MESSAGE_SUPPLIER_UNCHANGED);
        }

        if (!supplierToEdit.isSameSupplier(editedSupplier) && model.hasSupplier(editedSupplier)) {
            throw new CommandException(MESSAGE_DUPLICATE_SUPPLIER);
        }

        model.setSupplier(supplierToEdit, editedSupplier);

        logger.log(Level.INFO, LOG_MESSAGE_SUPPLIER_REPLACED_IN_MODEL);

        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);

        logger.log(Level.INFO, LOG_SUPPLIER_UPDATED_IN_UI);

        model.saveVersionedClinic();

        return new CommandResult(String.format(MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier));
    }

    private CommandResult executeWarehouseEditing(Model model) throws CommandException {
        List<Warehouse> lastShownWarehouseList = model.getFilteredWarehouseList();

        if (index.getZeroBased() >= lastShownWarehouseList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
        }
        Warehouse warehouseToEdit = lastShownWarehouseList.get(index.getZeroBased());

        logger.log(Level.INFO, LOG_MESSAGE_WAREHOUSE_RETRIEVED);

        Warehouse editedWarehouse = createEditedWarehouse(warehouseToEdit,
                (EditWarehouseDescriptor) editDescriptor);

        logger.log(Level.INFO, LOG_MESSAGE_WAREHOUSE_EDITED);

        if (warehouseToEdit.equalsWarehouseCaseSensitive(editedWarehouse)
                && warehouseToEdit.isSameWarehouse(editedWarehouse)) {
            throw new CommandException(MESSAGE_WAREHOUSE_UNCHANGED);
        }

        if (!warehouseToEdit.isSameWarehouse(editedWarehouse) && model.hasWarehouse(editedWarehouse)) {
            throw new CommandException(MESSAGE_DUPLICATE_WAREHOUSE);
        }

        model.setWarehouse(warehouseToEdit, editedWarehouse);

        logger.log(Level.INFO, LOG_MESSAGE_WAREHOUSE_REPLACED_IN_MODEL);

        model.updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);

        logger.log(Level.INFO, LOG_WAREHOUSE_UPDATED_IN_UI);
        model.saveVersionedClinic();

        return new CommandResult(String.format(MESSAGE_EDIT_WAREHOUSE_SUCCESS, editedWarehouse));
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

        assert (this.editDescriptor instanceof EditDescriptor && e.editDescriptor instanceof EditDescriptor)
                : INVALID_OBJECT_IN_COMPARISON_ASSERTION;

        return index.equals(e.index) && editDescriptor.equals(e.editDescriptor);
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
        public EditDescriptor(EditDescriptor toCopy) {
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
            if (products == null) {
                this.products = null;
            } else if (products.isEmpty()) {
                this.products = null;
            } else {
                this.products = new HashSet<>(products);
            }
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
         * Copy constructor to make a copy of {@code EditSupplierDescriptor}.
         * @param toCopy editSupplierDescriptor object to be copied from.
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
        @Override
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
         * Copy constructor to make a copy of {@code EditWarehouseDescriptor}.
         * @param toCopy editWarehouseDescriptor object to be copied from.
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
        @Override
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
