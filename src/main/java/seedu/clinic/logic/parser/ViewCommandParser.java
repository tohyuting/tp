package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.clinic.commons.core.index.Index;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_INVALID_TYPE_VIEW;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_NO_PREFIX;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_MISSING_INDEX;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_MISSING_TYPE;

import java.util.Arrays;
import java.util.List;

import seedu.clinic.logic.commands.ViewCommand;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_SUPPLIER_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_WAREHOUSE_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_WAREHOUSE_NAME;
import seedu.clinic.logic.parser.exceptions.ParseException;

public class ViewCommandParser implements Parser<ViewCommand> {

    @Override
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_INDEX);

        if(!argMultimap.getValue(PREFIX_TYPE).isPresent()
                && !argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX, ViewCommand.MESSAGE_USAGE));
        };

        if(!argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_MISSING_TYPE, ViewCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_MISSING_INDEX, ViewCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        Type type;
        try {
            type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_TYPE_VIEW, ViewCommand.MESSAGE_USAGE));
        }

        if (type.equals(Type.SUPPLIER_PRODUCT) || type.equals(Type.WAREHOUSE_PRODUCT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_TYPE_VIEW, ViewCommand.MESSAGE_USAGE));
        }

        return new ViewCommand(type, index);
    }
}
