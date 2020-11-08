package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.AddCommand.MESSAGE_INVALID_SUPPLIER_ADDRESS_PREFIX;
import static seedu.clinic.logic.commands.AddCommand.MESSAGE_INVALID_WAREHOUSE_EMAIL_PREFIX;
import static seedu.clinic.logic.commands.AddCommand.MESSAGE_MISSING_TYPE_PREFIX;
import static seedu.clinic.logic.commands.AddCommand.MESSAGE_SUPPLIER_MISSING_PREFIX;
import static seedu.clinic.logic.commands.AddCommand.MESSAGE_WAREHOUSE_MISSING_PREFIX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.checkInvalidArguments;
import static seedu.clinic.logic.parser.Type.SUPPLIER;
import static seedu.clinic.logic.parser.Type.WAREHOUSE;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static final String LOG_MESSAGE_TOKENIZE_SUCCESS = "Successfully tokenized user input.";
    private static final String LOG_MESSAGE_VALID_TYPE_PREFIX_SUPPLIER =
            "User input contains type prefix for supplier.";
    private static final String LOG_MESSAGE_VALID_TYPE_PREFIX_WAREHOUSE =
            "User input contains type prefix for warehouse.";
    private static final String LOG_MESSAGE_PARSE_SUPPLIER_SUCCESS =
            "Successfully created Supplier with given user input.";
    private static final String LOG_MESSAGE_PARSE_WAREHOUSE_SUCCESS =
            "Successfully created Warehouse with given user input.";

    private static final String INVALID_WAREHOUSE_ASSERTION = "The type prefix for warehouse should be present here.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_NAME,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK);
        logger.log(Level.INFO, LOG_MESSAGE_TOKENIZE_SUCCESS);

        if (argMultimap.getValue(PREFIX_TYPE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Type type;
        try {
            type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_TYPE, argMultimap, AddCommand.MESSAGE_USAGE);
        }

        if (type.equals(SUPPLIER)) {
            return parseAddSupplier(argMultimap);
        } else if (type.equals(WAREHOUSE)) {
            return parseAddWarehouse(argMultimap, type);
        } else {
            throw new ParseException(String.format(MESSAGE_MISSING_TYPE_PREFIX,
                    AddCommand.MESSAGE_USAGE));
        }
    }

    private AddCommand parseAddSupplier(ArgumentMultimap argMultimap) throws ParseException {
        logger.log(Level.INFO, LOG_MESSAGE_VALID_TYPE_PREFIX_SUPPLIER);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_SUPPLIER_MISSING_PREFIX, AddCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_SUPPLIER_ADDRESS_PREFIX,
                    AddCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            ParserUtil.checkInvalidArgumentsInPreamble(argMultimap.getPreamble(), AddCommand.MESSAGE_USAGE);
        }

        //Current Prefix to Parse
        Prefix currentPrefix = PREFIX_NAME;

        Name name;
        Email email;
        Remark remark;
        Phone phone;
        try {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            currentPrefix = PREFIX_EMAIL;
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            currentPrefix = PREFIX_REMARK;
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).orElse(""));
            currentPrefix = PREFIX_PHONE;
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(currentPrefix, argMultimap, AddCommand.MESSAGE_USAGE);
        }

        Set<Product> productList = new HashSet<>();

        Supplier supplier = new Supplier(name, phone, email, remark, productList);
        logger.log(Level.INFO, LOG_MESSAGE_PARSE_SUPPLIER_SUCCESS);

        return new AddCommand(supplier);
    }

    private AddCommand parseAddWarehouse(ArgumentMultimap argMultimap, Type type) throws ParseException {
        assert type.equals(WAREHOUSE) : INVALID_WAREHOUSE_ASSERTION;
        logger.log(Level.INFO, LOG_MESSAGE_VALID_TYPE_PREFIX_WAREHOUSE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_WAREHOUSE_MISSING_PREFIX, AddCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_WAREHOUSE_EMAIL_PREFIX,
                    AddCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            ParserUtil.checkInvalidArgumentsInPreamble(argMultimap.getPreamble(), AddCommand.MESSAGE_USAGE);
        }

        Name name;
        Address address;
        Remark remark;
        Phone phone;

        //Current Prefix to Parse
        Prefix currentPrefix = PREFIX_NAME;
        try {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            currentPrefix = PREFIX_ADDRESS;
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            currentPrefix = PREFIX_REMARK;
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).orElse(""));
            currentPrefix = PREFIX_PHONE;
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(currentPrefix, argMultimap, AddCommand.MESSAGE_USAGE);
        }

        Set<Product> productList = new HashSet<>();

        Warehouse warehouse = new Warehouse(name, phone, address, remark, productList);
        logger.log(Level.INFO, LOG_MESSAGE_PARSE_WAREHOUSE_SUCCESS);

        return new AddCommand(warehouse);
    }
}
