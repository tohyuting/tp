package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;

import java.util.stream.Stream;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.DeleteCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments, identifies the correct type of DeleteCommand via argument length
     * and returns the DeleteCommand object for execution.
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

            if (trimmedKeywordArray.length == 2) {
                return parseDeleteWithoutProduct(trimmedKeywordArray);
            } else {
                return parseDeleteWithProduct(trimmedKeywordArray);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the given {@code trimmedKeywordArray} in the context of the DeleteCommand
     * and returns a DeleteCommand object for deletion of supplier/warehouse.
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteCommand parseDeleteWithoutProduct(String[] trimmedKeywordArray) throws ParseException {
        String typeKeyword = ParserUtil.parseType(trimmedKeywordArray[0]);
        Index index = ParserUtil.parseIndex(trimmedKeywordArray[1]);

        return new DeleteCommand(typeKeyword, index);
    }

    /**
     * Parses the given {@code trimmedKeywordArray} in the context of the DeleteCommand
     * and returns a DeleteCommand object for deletion of a product in a particular supplier/warehouse.
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteCommand parseDeleteWithProduct(String[] trimmedKeywordArray) throws ParseException {
        String typeKeyword = ParserUtil.parseType(trimmedKeywordArray[0]);
        Index index = ParserUtil.parseIndex(trimmedKeywordArray[1]);
        StringBuilder productArg = new StringBuilder();

        for (int i = 2; i < trimmedKeywordArray.length; i++) {
            productArg.append(" ");
            productArg.append(trimmedKeywordArray[i]);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(productArg.toString(), PREFIX_PRODUCT_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_PRODUCT_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        Name productName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());

        return new DeleteCommand(typeKeyword, index, productName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
