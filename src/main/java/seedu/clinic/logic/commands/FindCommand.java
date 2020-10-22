package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Optional;

import seedu.clinic.commons.core.Messages;
import seedu.clinic.model.Model;
import seedu.clinic.model.supplier.SupplierProductsContainKeywordsPredicate;
import seedu.clinic.model.warehouse.WarehouseProductsContainKeywordsPredicate;

/**
 * Finds and lists all suppliers/warehouses in the CLI-nic app whose name, remark and/or products matches any of the
 * argument keywords.
 *
 * Prefixes provided can be in any order.
 * At least one of the name, remark or product prefixes must be provided.
 * Keyword matching is case insensitive.
 * Keyword only matches whole word e.g. alex will match "alex" but not "alexia".
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all suppliers/warehouses whose name, remark"
            + " and/or products matches any of the specified keywords (case-insensitive) and displays them as a list"
            + " with index numbers. Prefixes provided can be in any order. At least one of the name, remark or product"
            + " prefixes must be provided.\n\n"
            + "Parameters:\n"
            + PREFIX_TYPE + "TYPE "
            + "[" + PREFIX_NAME + "NAME...] "
            + "[" + PREFIX_PRODUCT_NAME + "PRODUCT_NAME...] "
            + "[" + PREFIX_REMARK + "REMARK...]\n\n"
            + "Example:\n"
            + "1) " + COMMAND_WORD + " ct/s pd/panadol\n"
            + "2) " + COMMAND_WORD + " ct/s pd/panadol face mask n/alex\n"
            + "3) " + COMMAND_WORD + " ct/w pd/panadol n/bernice r/biggest";

    public static final String MESSAGE_INVALID_TYPE = "Type must be either 's' or 'w'.\n\n"
            + "Example:\n"
            + "1) ct/s\n"
            + "2) ct/w";

    private final Optional<SupplierProductsContainKeywordsPredicate> supplierPredicate;
    private final Optional<WarehouseProductsContainKeywordsPredicate> warehousePredicate;

    /**
     * Constructs a new FindCommand object.
     */
    public FindCommand(SupplierProductsContainKeywordsPredicate supplierPredicate) {
        this.supplierPredicate = Optional.of(supplierPredicate);
        this.warehousePredicate = Optional.empty();
    }

    /**
     * Constructs a new FindCommand object.
     */
    public FindCommand(WarehouseProductsContainKeywordsPredicate warehousePredicate) {
        this.warehousePredicate = Optional.of(warehousePredicate);
        this.supplierPredicate = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (supplierPredicate.isPresent()) {
            model.updateFilteredSupplierList(supplierPredicate.get());
            return new CommandResult(
                    String.format(Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW, model.getFilteredSupplierList().size()));
        }

        model.updateFilteredWarehouseList(warehousePredicate.get());
        return new CommandResult(
                String.format(Messages.MESSAGE_WAREHOUSE_LISTED_OVERVIEW, model.getFilteredWarehouseList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && (supplierPredicate.isPresent()
                        ? supplierPredicate.equals(((FindCommand) other).supplierPredicate)
                        : warehousePredicate.equals(((FindCommand) other).warehousePredicate)));
    }
}
