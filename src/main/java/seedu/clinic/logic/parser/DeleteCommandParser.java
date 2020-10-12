package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.DeleteCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] trimmedKeywordArray = trimmedArgs.split("\\s+");

            if (trimmedArgs.isEmpty() || trimmedKeywordArray.length < 2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            String typeKeyword = ParserUtil.parseType(trimmedKeywordArray[0]);
            Index index = ParserUtil.parseIndex(trimmedKeywordArray[1]);

            return new DeleteCommand(typeKeyword, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
