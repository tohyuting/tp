package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.logic.commands.HelpCommand;
import static seedu.clinic.logic.commands.HelpCommand.ALLOWED_ARGUMENT;
import static seedu.clinic.logic.commands.HelpCommand.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.clinic.logic.commands.HelpCommand.MESSAGE_WRONG_ARGUMENT;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForSupplier;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new HelpCommand("all");
        }
        String[] helpKeyword = trimmedArgs.split("\\s+");
        if (helpKeyword.length > 1) {
            throw new ParseException(MESSAGE_TOO_MANY_ARGUMENTS);
        }
        String helpArgument = helpKeyword[0].toLowerCase();
        if (!Arrays.asList(ALLOWED_ARGUMENT).contains(helpArgument)) {
            throw new ParseException(MESSAGE_WRONG_ARGUMENT);
        }
        return new HelpCommand(helpArgument);
    }
}
