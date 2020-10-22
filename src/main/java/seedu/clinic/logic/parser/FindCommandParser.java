package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.Type.SUPPLIER;
import static seedu.clinic.logic.parser.Type.WAREHOUSE;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.supplier.SupplierPredicate;
import seedu.clinic.model.warehouse.WarehousePredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_NAME, PREFIX_PRODUCT_NAME, PREFIX_REMARK);

        // Ensures that the type prefix is present and at least one of the name, product or remark prefix is present
        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE)
                || !atLeastOnePrefixPresent(argMultimap, PREFIX_NAME, PREFIX_PRODUCT_NAME, PREFIX_REMARK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());

        if (!type.equals(SUPPLIER) && !type.equals(WAREHOUSE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_INVALID_TYPE));
        }

        String[] nameKeywords = {};
        String[] productKeywords = {};
        String[] remarkKeywords = {};

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            nameKeywords = name.fullName.split("\\s+");
        }

        if (argMultimap.getValue(PREFIX_PRODUCT_NAME).isPresent()) {
            Name productName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());
            productKeywords = productName.fullName.split("\\s+");
        }

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
            remarkKeywords = remark.value.split("\\s+");
        }

        if (type.toString().equals("s")) {
            return new FindCommand(new SupplierPredicate(Arrays.asList(nameKeywords),
                    Arrays.asList(productKeywords), Arrays.asList(remarkKeywords)));
        }

        return new FindCommand(new WarehousePredicate(Arrays.asList(nameKeywords),
                Arrays.asList(productKeywords), Arrays.asList(remarkKeywords)));
    }

    /**
     * Returns true if none of the prefixes contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if at least one of the prefixes does not have empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean atLeastOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
