package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;

/**
 * Adds a product to a supplier in the CLI-nic app.
 */
public class AddProductCommand extends Command {
    public static final String COMMAND_WORD = "addp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a product to a supplier "
            + "Parameters: "
            + PREFIX_SUPPLIER_NAME + "SUPPLIER_NAME "
            + PREFIX_PRODUCT_NAME + "PRODUCT_NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_SUPPLIER_NAME + "Alice Pauline Ltd "
            + PREFIX_PRODUCT_NAME + "Panadol "
            + PREFIX_TAG + "primary largest \n";

    public static final String MESSAGE_SUCCESS = "New %2$s product added to supplier %1$s ";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This supplier already sells this product!";
    public static final String MESSAGE_SUPPLIER_NOT_FOUND = "This supplier does not exists!";

    private final Name targetSupplierName;
    private final Product productToAdd;

    /**
     * Creates an AddProductCommand to add the {@code Product} to the specified {@code Supplier}
     */
    public AddProductCommand(Name inputName, Product product) {
        requireNonNull(inputName);
        requireNonNull(product);
        targetSupplierName = inputName;
        productToAdd = product;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Supplier targetSupplier;

        try {
            targetSupplier = getSupplierByName(targetSupplierName, model);
        } catch (NoSuchElementException e) {
            throw new CommandException(MESSAGE_SUPPLIER_NOT_FOUND);
        }

        Set<Product> updatedProductSet = new HashSet<>(targetSupplier.getProducts());

        if (updatedProductSet.contains(productToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        updatedProductSet.add(productToAdd);

        Supplier updatedSupplier = new Supplier(targetSupplier.getName(), targetSupplier.getPhone(),
                targetSupplier.getEmail(), targetSupplier.getRemark(), updatedProductSet);

        model.setSupplier(targetSupplier, updatedSupplier);
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedSupplier.getName(),
                productToAdd.getProductName()));
    }

    public static Supplier getSupplierByName(Name supplierName, Model model) throws NoSuchElementException {
        return model.getClinic().getSupplierList().stream()
                .filter(supplier -> supplier.getName().equals(supplierName)).findFirst().orElseThrow();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddProductCommand // instanceof handles nulls
                && targetSupplierName.equals(((AddProductCommand) other).targetSupplierName)
                && productToAdd.equals(((AddProductCommand) other).productToAdd));
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetSupplierName, productToAdd);
    }
}
