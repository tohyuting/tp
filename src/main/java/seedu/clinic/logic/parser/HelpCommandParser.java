package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.HelpCommand.ALLOWED_ARGUMENTS;
import static seedu.clinic.logic.commands.HelpCommand.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.clinic.logic.commands.HelpCommand.MESSAGE_WRONG_ARGUMENT;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.logic.commands.HelpCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    private static final String LOG_HELP_GENERIC = "Received instructions to display generic help message.";
    private static final String LOG_HELP_SPECIFIC = "Received instructions to display specific help"
            + " message for %1$s command.";
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.log(Level.INFO, LOG_HELP_GENERIC);
            return new HelpCommand("all");
        }
        String[] helpArguments = trimmedArgs.split("\\s+");
        if (helpArguments.length > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_TOO_MANY_ARGUMENTS));
        }

        String helpArgument = helpArguments[0].toLowerCase();
        if (!Arrays.asList(ALLOWED_ARGUMENTS).contains(helpArgument)) {
            throw new ParseException(String.format(MESSAGE_WRONG_ARGUMENT, HelpCommand.MESSAGE_USAGE));
        }
        logger.log(Level.INFO, String.format(LOG_HELP_SPECIFIC, helpArgument));
        return new HelpCommand(helpArgument);
    }
}
