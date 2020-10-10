package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.clinic.commons.core.Messages;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForSupplier;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForWarehouse;

import java.util.Collections;
import java.util.List;

public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":View information related to a"
            + " particular supplier or warehouse.\n"
            + "Parameters:\nview TYPE NAME\n"
            + "Example:\nview warehouse warehouseA\nview supplier supplierA\n";
    public static final String MESSAGE_TOO_FEW_ARGUMENTS = "You should key in at least 2 arguments.\n"
            + MESSAGE_USAGE;
    public static final String MESSAGE_INVALID_TYPE = "Your type can only be supplier or warehouse.\n"
            + MESSAGE_USAGE;
    public static final int NUMBER_OF_ARGUMENTS = 2;
    public static final int TYPE_IN_VIEW_COMMAND = 0;
    public static final int NAME_IN_VIEW_COMMAND = 1;
    public static final String[] ALLOWED_TYPES = new String[]{"supplier", "warehouse"};

    private final String type;
    private final List<String> name;

    public ViewCommand(String type, List<String> name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        CommandResult commandResult;
        if (type.equals("supplier")) {
            NameContainsKeywordsPredicateForSupplier supplierPredicate =
                    new NameContainsKeywordsPredicateForSupplier(name);
            model.updateFilteredSupplierList(supplierPredicate);
            commandResult = new CommandResult(
                    String.format(Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW,
                            model.getFilteredSupplierList().size()));
        } else {
            NameContainsKeywordsPredicateForWarehouse warehousePredicate =
                    new NameContainsKeywordsPredicateForWarehouse(name);
            model.updateFilteredWarehouseList(warehousePredicate);
            commandResult = new CommandResult(
                    String.format(Messages.MESSAGE_WAREHOUSE_LISTED_OVERVIEW,
                            model.getFilteredWarehouseList().size()));
        }
        return commandResult;
    }
}
