package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import seedu.clinic.commons.core.LogsCenter;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_INVALID_TYPE_VIEW;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_INVALID_USAGE;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_MISSING_INDEX;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_MISSING_TYPE;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_NO_PREFIX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.ViewCommand;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_PREFIX;
import seedu.clinic.logic.parser.exceptions.ParseException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewCommandParser implements Parser<ViewCommand> {

    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_INDEX);

        logger.log(Level.INFO, "Tokenised user inputs");

        if (!argMultimap.getValue(PREFIX_TYPE).isPresent()
                && !argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX, ViewCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_MISSING_TYPE, ViewCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_MISSING_INDEX, ViewCommand.MESSAGE_USAGE));
        }

        Type type;
        try {
            type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_TYPE, argMultimap);
        }

        logger.log(Level.INFO, "Successfully parsed command type of user input");

        if (type.equals(Type.SUPPLIER_PRODUCT) || type.equals(Type.WAREHOUSE_PRODUCT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_TYPE_VIEW, ViewCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_INDEX, argMultimap);
        }

        logger.log(Level.INFO, "Successfully parsed index of user input, creating new ViewCommand");

        return new ViewCommand(type, index);
    }

    private ParseException checkInvalidArguments(Prefix prefix, ArgumentMultimap argMultimap) {
        if (argMultimap.getValue(prefix).get().contains("/")) {
            return new ParseException(MESSAGE_INVALID_PREFIX + "\n" + ViewCommand.MESSAGE_USAGE);
        }
        if (argMultimap.getValue(prefix).get().split("\\s+").length != 1) {
            return new ParseException(String.format(MESSAGE_INVALID_USAGE, ViewCommand.MESSAGE_USAGE));
        }
        if (prefix.equals(PREFIX_TYPE)) {
            return new ParseException(String.format(MESSAGE_INVALID_TYPE_VIEW, ViewCommand.MESSAGE_USAGE));
        } else {
            assert prefix.equals(PREFIX_INDEX) : "The prefix here should be of Index type!";
            return new ParseException(MESSAGE_INVALID_INDEX + "\n" + ViewCommand.MESSAGE_USAGE);
        }
    }
}
