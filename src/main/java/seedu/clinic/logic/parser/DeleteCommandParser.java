package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.checkInvalidArguments;
import static seedu.clinic.logic.parser.Type.SUPPLIER;
import static seedu.clinic.logic.parser.Type.WAREHOUSE;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.DeleteCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final String INVALID_INDEX_ASSERTION = "The index is less than 1!";
    private static final String MESSAGE_INVALID_PRODUCT_PREFIX = "The product name prefix should not"
            + " be present when you are deleting a supplier or warehouse!\n\n%1$s";

    //@@author criss-wang
    /**
     * Parses the given {@code String} of arguments, identifies the correct type of DeleteCommand via argument length
     * and returns the DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_TYPE, PREFIX_INDEX, PREFIX_PRODUCT_NAME);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            ParserUtil.checkInvalidArgumentsInPreamble(argMultimap.getPreamble(), DeleteCommand.MESSAGE_USAGE);
        }

        Type type;
        Index index;

        //Current Prefix to Parse
        Prefix currentPrefix = PREFIX_TYPE;

        try {
            type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
            currentPrefix = PREFIX_INDEX;
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(currentPrefix, argMultimap, DeleteCommand.MESSAGE_USAGE);
        }

        assert index.getOneBased() >= 1 : INVALID_INDEX_ASSERTION;

        if (type.equals(SUPPLIER) || type.equals(WAREHOUSE)) {
            if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PRODUCT_NAME)) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_PRODUCT_PREFIX, DeleteCommand.MESSAGE_USAGE));
            }
            return new DeleteCommand(type, index);
        }

        // The product deletion must have product name prefix
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PRODUCT_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        Name productName;
        try {
            productName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_PRODUCT_NAME, argMultimap, DeleteCommand.MESSAGE_USAGE);
        }

        System.out.println(productName);
        return new DeleteCommand(type, index, productName);
    }
}
