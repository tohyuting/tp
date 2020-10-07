package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Remark;
import seedu.address.model.supplier.Supplier;

/**
 * Edits the details of an existing supplier in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the supplier identified "
            + "by the index number used in the displayed supplier list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_SUPPLIER_SUCCESS = "Edited Supplier: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SUPPLIER = "This supplier already exists in the remark book.";

    private final Index index;
    private final EditSupplierDescriptor editSupplierDescriptor;

    /**
     * @param index of the supplier in the filtered supplier list to edit
     * @param editSupplierDescriptor details to edit the supplier with
     */
    public EditCommand(Index index, EditSupplierDescriptor editSupplierDescriptor) {
        requireNonNull(index);
        requireNonNull(editSupplierDescriptor);

        this.index = index;
        this.editSupplierDescriptor = new EditSupplierDescriptor(editSupplierDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Supplier> lastShownList = model.getFilteredSupplierList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        Supplier supplierToEdit = lastShownList.get(index.getZeroBased());
        Supplier editedSupplier = createEditedSupplier(supplierToEdit, editSupplierDescriptor);

        if (!supplierToEdit.isSameSupplier(editedSupplier) && model.hasSupplier(editedSupplier)) {
            throw new CommandException(MESSAGE_DUPLICATE_SUPPLIER);
        }

        model.setSupplier(supplierToEdit, editedSupplier);
        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        return new CommandResult(String.format(MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier));
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
        Remark remark = supplierToEdit.getRemark();
        Set<Product> products = supplierToEdit.getProducts();

        return new Supplier(updatedName, updatedPhone, updatedEmail, remark, products);
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
        return index.equals(e.index)
                && editSupplierDescriptor.equals(e.editSupplierDescriptor);
    }

    /**
     * Stores the details to edit the supplier with. Each non-empty field value will replace the
     * corresponding field value of the supplier.
     */
    public static class EditSupplierDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Remark remark;
        private Set<Product> products;

        public EditSupplierDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code products} is used internally.
         */
        public EditSupplierDescriptor(EditSupplierDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setRemark(toCopy.remark);
            setProducts(toCopy.products);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, remark, products);
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

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
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
}
