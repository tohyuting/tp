package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinic.commons.core.Messages;
import seedu.clinic.model.Model;
import seedu.clinic.model.supplier.ProductsContainKeywordsPredicate;

/**
 * Finds and lists all suppliers in the CLI-nic app that sell products matching any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all suppliers that sell products matching "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TYPE KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " supplier panadol";

    private final ProductsContainKeywordsPredicate predicate;

    public FindCommand(ProductsContainKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSupplierList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW, model.getFilteredSupplierList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
