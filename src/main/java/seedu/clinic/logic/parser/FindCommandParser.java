package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.checkInvalidArguments;
import static seedu.clinic.logic.parser.Type.SUPPLIER;
import static seedu.clinic.logic.parser.Type.WAREHOUSE;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
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
    private static final String LOG_MESSAGE_PARSE_FIND_SUPPLIER = "Returning FindCommand to find relevant suppliers.";
    private static final String LOG_MESSAGE_PARSE_FIND_WAREHOUSE = "Returning FindCommand to find relevant warehouses.";

    private static final String NULL_TYPE_ASSERTION = "type cannot be null";

    private final Logger logger = LogsCenter.getLogger(getClass());

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
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TYPE)
                || !ParserUtil.atLeastOnePrefixPresent(argMultimap, PREFIX_NAME, PREFIX_PRODUCT_NAME, PREFIX_REMARK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            ParserUtil.checkInvalidArgumentsInPreamble(argMultimap.getPreamble(), FindCommand.MESSAGE_USAGE);
        }

        Type type;
        try {
            type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
            assert type != null : NULL_TYPE_ASSERTION;
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_TYPE, argMultimap, FindCommand.MESSAGE_USAGE);
        }

        if (!type.equals(SUPPLIER) && !type.equals(WAREHOUSE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_TYPE,
                    FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = {};
        String[] productKeywords = {};
        String[] remarkKeywords = {};

        //Current Prefix to Parse
        Prefix currentPrefix = PREFIX_NAME;

        try {
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
                nameKeywords = name.fullName.split("\\s+");
            }

            if (argMultimap.getValue(PREFIX_PRODUCT_NAME).isPresent()) {
                currentPrefix = PREFIX_PRODUCT_NAME;
                Name productName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());
                productKeywords = productName.fullName.split("\\s+");
            }

            if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
                currentPrefix = PREFIX_REMARK;
                Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
                remarkKeywords = remark.value.split("\\s+");
            }
        } catch (ParseException pe) {
            throw checkInvalidArguments(currentPrefix, argMultimap, FindCommand.MESSAGE_USAGE);
        }

        if (type.equals(SUPPLIER)) {
            logger.log(Level.INFO, LOG_MESSAGE_PARSE_FIND_SUPPLIER);
            return new FindCommand(new SupplierPredicate(Arrays.asList(nameKeywords),
                    Arrays.asList(productKeywords), Arrays.asList(remarkKeywords)));
        }

        logger.log(Level.INFO, LOG_MESSAGE_PARSE_FIND_WAREHOUSE);
        return new FindCommand(new WarehousePredicate(Arrays.asList(nameKeywords),
                Arrays.asList(productKeywords), Arrays.asList(remarkKeywords)));
    }

}
