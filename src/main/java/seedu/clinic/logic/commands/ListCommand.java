package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

import seedu.clinic.model.Model;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_WAREHOUSES;

/**
 * Lists all suppliers in the CLI-nic app to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all suppliers";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        model.updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
