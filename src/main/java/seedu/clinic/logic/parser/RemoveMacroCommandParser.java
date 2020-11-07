package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_ALIAS;

import seedu.clinic.logic.commands.RemoveMacroCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.macro.Alias;

public class RemoveMacroCommandParser implements Parser<RemoveMacroCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveMacroCommand
     * and returns a RemoveMacroCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RemoveMacroCommand parse(String args) throws ParseException {
        try {
            Alias alias = ParserUtil.parseAlias(args);
            return new RemoveMacroCommand(alias);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_ALIAS, RemoveMacroCommand.MESSAGE_USAGE), pe);
        }
    }
}
