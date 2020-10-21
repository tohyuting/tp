package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.AddCommand.MESSAGE_SUPPLIER_MISSING_PREFIX;
import static seedu.clinic.logic.commands.AddCommand.MESSAGE_TYPE_PREFIX_NOT_ALLOWED;
import static seedu.clinic.logic.commands.AddCommand.MESSAGE_WAREHOUSE_MISSING_PREFIX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_WAREHOUSE_NAME;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.logic.commands.AddCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Email;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        AddCommand addCommand;
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUPPLIER_NAME, PREFIX_WAREHOUSE_NAME,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK);
        logger.log(Level.INFO, "Successfully tokenized user input.");

        if (argMultimap.getValue(PREFIX_SUPPLIER_NAME).isEmpty()
                && argMultimap.getValue(PREFIX_WAREHOUSE_NAME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_TYPE_PREFIX_NOT_ALLOWED,
                    AddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_SUPPLIER_NAME).isPresent()
                && argMultimap.getValue(PREFIX_WAREHOUSE_NAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_TYPE_PREFIX_NOT_ALLOWED,
                    AddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_SUPPLIER_NAME).isPresent()) {
            logger.log(Level.INFO, "User input contains supplier name prefix.");

            if (!arePrefixesPresent(argMultimap, PREFIX_SUPPLIER_NAME, PREFIX_PHONE)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_SUPPLIER_MISSING_PREFIX, AddCommand.MESSAGE_USAGE));
            }

            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_SUPPLIER_NAME).get());
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse("Nil"));
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).orElse("Nil"));
            Set<Product> productList = new HashSet<>();

            Supplier supplier = new Supplier(name, phone, email, remark, productList);
            logger.log(Level.INFO, "Successfully created Supplier with given user input.");

            addCommand = new AddCommand(supplier);
        } else {
            assert argMultimap.getValue(PREFIX_WAREHOUSE_NAME).isPresent() : "The warehouse name prefix"
                    + " should be present here.";
            logger.log(Level.INFO, "User input contains warehouse name prefix.");

            if (!arePrefixesPresent(argMultimap, PREFIX_WAREHOUSE_NAME, PREFIX_PHONE, PREFIX_ADDRESS)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_WAREHOUSE_MISSING_PREFIX, AddCommand.MESSAGE_USAGE));
            }

            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_WAREHOUSE_NAME).get());
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).orElse("Nil"));
            Set<Product> productList = new HashSet<>();

            Warehouse warehouse = new Warehouse(name, phone, address, remark, productList);
            logger.log(Level.INFO, "Successfully created Warehouse with given user input.");

            addCommand = new AddCommand(warehouse);
        }
        return addCommand;
    }

    //TODO: Consideration: shall we put all these same method under the parent Command class?
    //      Will it violate the Liskov substitution principle?
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
