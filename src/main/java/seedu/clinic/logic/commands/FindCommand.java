package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

<<<<<<< HEAD:src/main/java/seedu/address/logic/commands/FindCommand.java
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.attribute.NameContainsKeywordsPredicateForSupplier;
=======
import seedu.clinic.commons.core.Messages;
import seedu.clinic.model.Model;
import seedu.clinic.model.supplier.NameContainsKeywordsPredicate;
>>>>>>> upstream/master:src/main/java/seedu/clinic/logic/commands/FindCommand.java

/**
 * Finds and lists all suppliers in the CLI-nic app whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all suppliers whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicateForSupplier predicate;

    public FindCommand(NameContainsKeywordsPredicateForSupplier predicate) {
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
