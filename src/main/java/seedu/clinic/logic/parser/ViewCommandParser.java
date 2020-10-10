package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.clinic.logic.commands.ViewCommand;
import static seedu.clinic.logic.commands.ViewCommand.ALLOWED_TYPES;
import static seedu.clinic.logic.commands.ViewCommand.NAME_IN_VIEW_COMMAND;
import static seedu.clinic.logic.commands.ViewCommand.NUMBER_OF_ARGUMENTS;
import static seedu.clinic.logic.commands.ViewCommand.TYPE_IN_VIEW_COMMAND;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForSupplier;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForWarehouse;

import java.util.Arrays;
import java.util.List;

public class ViewCommandParser implements Parser<ViewCommand> {

    @Override
    public ViewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        String[] userInputs = trimmedArgs.split("\\s+");
        if (userInputs.length < NUMBER_OF_ARGUMENTS) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_TOO_FEW_ARGUMENTS));
        }

        String type = userInputs[TYPE_IN_VIEW_COMMAND].toLowerCase();
        List<String> name = Arrays.asList(userInputs).subList(NAME_IN_VIEW_COMMAND, userInputs.length);

        if (!Arrays.asList(ALLOWED_TYPES).contains(type.toLowerCase())) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_INVALID_TYPE));
        }

        return new ViewCommand(type, name);
    }
}
