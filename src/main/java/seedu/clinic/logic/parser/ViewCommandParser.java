package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.checkInvalidArguments;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.ViewCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;

public class ViewCommandParser implements Parser<ViewCommand> {

    private static final String LOG_MESSAGE_TOKENIZE_SUCCESS = "Successfully tokenized user input.";
    private static final String LOG_MESSAGE_PARSE_INPUT_SUCCESS = "Successfully parsed command type of user input.";
    private static final String LOG_MESSAGE_CREATE_VIEW_COMMAND =
            "Successfully parsed index of user input, creating new ViewCommand.";
    private static final String LOG_MESSAGE_PARSE_INPUT_TYPE_SUPPLIER_OR_WAREHOUSE =
            "Type used in user input for View is a valid type (either ct/s or ct/w).";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_INDEX);

        logger.log(Level.INFO, LOG_MESSAGE_TOKENIZE_SUCCESS);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            ParserUtil.checkInvalidArgumentsInPreamble(argMultimap.getPreamble(), ViewCommand.MESSAGE_USAGE);
        }

        Type type;
        try {
            type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_TYPE, argMultimap, ViewCommand.MESSAGE_USAGE);
        }

        logger.log(Level.INFO, LOG_MESSAGE_PARSE_INPUT_SUCCESS);

        if (type.equals(Type.SUPPLIER_PRODUCT) || type.equals(Type.WAREHOUSE_PRODUCT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_TYPE, ViewCommand.MESSAGE_USAGE));
        }

        logger.log(Level.INFO, LOG_MESSAGE_PARSE_INPUT_TYPE_SUPPLIER_OR_WAREHOUSE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_INDEX, argMultimap, ViewCommand.MESSAGE_USAGE);
        }

        logger.log(Level.INFO, LOG_MESSAGE_CREATE_VIEW_COMMAND);

        return new ViewCommand(type, index);
    }

}
