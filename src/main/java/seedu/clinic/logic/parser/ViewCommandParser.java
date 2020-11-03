package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_INVALID_TYPE_VIEW;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_INVALID_USAGE;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_MISSING_INDEX;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_MISSING_TYPE;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_NO_PREFIX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_PREFIX;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.ViewCommand;
import static seedu.clinic.logic.parser.ParserUtil.checkInvalidArguments;
import seedu.clinic.logic.parser.exceptions.ParseException;

public class ViewCommandParser implements Parser<ViewCommand> {

    private static final String LOG_MESSAGE_TOKENIZE_SUCCESS = "Successfully tokenized user input.";
    private static final String LOG_MESSAGE_PARSE_INPUT_SUCCESS = "Successfully parsed command type of user input.";
    private static final String LOG_MESSAGE_CREATE_VIEW_COMMAND =
            "Successfully parsed index of user input, creating new ViewCommand.";
    private static final String INVALID_INDEX_PREFIX_ASSERTION = "The prefix here should be of Index type!";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_INDEX);

        logger.log(Level.INFO, LOG_MESSAGE_TOKENIZE_SUCCESS);

        if (!argMultimap.getValue(PREFIX_TYPE).isPresent() && !argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX, ViewCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_MISSING_TYPE, ViewCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_MISSING_INDEX, ViewCommand.MESSAGE_USAGE));
        }

        Type type;
        try {
            type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_TYPE, argMultimap, ViewCommand.MESSAGE_USAGE);
        }

        logger.log(Level.INFO, LOG_MESSAGE_PARSE_INPUT_SUCCESS);

        if (type.equals(Type.SUPPLIER_PRODUCT) || type.equals(Type.WAREHOUSE_PRODUCT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_TYPE_VIEW, ViewCommand.MESSAGE_USAGE));
        }

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
