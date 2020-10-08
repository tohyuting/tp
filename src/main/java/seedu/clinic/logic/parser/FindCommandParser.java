package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

<<<<<<< HEAD:src/main/java/seedu/address/logic/parser/FindCommandParser.java
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.NameContainsKeywordsPredicateForSupplier;
=======
import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.supplier.NameContainsKeywordsPredicate;
>>>>>>> upstream/master:src/main/java/seedu/clinic/logic/parser/FindCommandParser.java

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicateForSupplier(Arrays.asList(nameKeywords)));
    }

}
