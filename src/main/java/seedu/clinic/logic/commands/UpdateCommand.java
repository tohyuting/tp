package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_QUANTITY;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_WAREHOUSE_NAME;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_WAREHOUSES;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Update the stock for a product in a specific warehouse.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates a stock of a product in a specific warehouse. "
            + "Parameters: "
            + PREFIX_WAREHOUSE_NAME + "WAREHOUSE_NAME "
            + PREFIX_PRODUCT_NAME + "PRODUCT_NAME"
            + PREFIX_PRODUCT_QUANTITY + "PRODUCT_QUANTITY"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WAREHOUSE_NAME + "Jurong Warehouse "
            + PREFIX_PRODUCT_NAME + "Panadol "
            + PREFIX_PRODUCT_QUANTITY + "350";

    public static final String MESSAGE_SUCCESS = "Product stock updated: %1$s";
    private static final String MESSAGE_NO_SUCH_WAREHOUSE = "The specified warehouse cannot be found";

    private final Warehouse warehouseToUpdate;
    private final Product updatedProduct;

    /**
     * Creates an UpdateCommand to update the specified {@code Product} for the specified {@code Warehouse}
     */
    public UpdateCommand(Warehouse warehouse, Product product) {
        requireNonNull(warehouse);
        requireNonNull(product);
        warehouseToUpdate = warehouse;
        updatedProduct = product;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasWarehouse(warehouseToUpdate)) {
            throw new CommandException(MESSAGE_NO_SUCH_WAREHOUSE);
        }

        Set<Product> updatedProductSet = new HashSet<>(warehouseToUpdate.getProducts());

        if (updatedProductSet.contains(updatedProduct)) {
            updatedProductSet.remove(updatedProduct);
        }

        updatedProductSet.add(updatedProduct); // adds the updated product
        Warehouse updatedWarehouse = new Warehouse(warehouseToUpdate.getName(), warehouseToUpdate.getPhone(),
                warehouseToUpdate.getAddress(), warehouseToUpdate.getRemark(), updatedProductSet);
        model.setWarehouse(warehouseToUpdate, updatedWarehouse);
        model.updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedWarehouse));
    }

    @Override public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdateCommand // instanceof handles nulls
                && warehouseToUpdate.equals(((UpdateCommand) other).warehouseToUpdate)
                && updatedProduct.equals(((UpdateCommand) other).updatedProduct));
    }

    @Override public int hashCode() {
        return Objects.hash(warehouseToUpdate, updatedProduct);
    }
}
