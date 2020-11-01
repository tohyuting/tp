package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_INVALID_TYPE_EDIT;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_INVALID_USAGE;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_NOT_EDITED;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_NO_INDEX;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_NO_PREFIX;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_NO_PREFIX_AND_INDEX;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_SUPPLIER_NO_ADDRESS;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_WAREHOUSE_NO_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_PREFIX;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.EditCommand;
import seedu.clinic.logic.commands.EditCommand.EditDescriptor;
import seedu.clinic.logic.commands.EditCommand.EditSupplierDescriptor;
import seedu.clinic.logic.commands.EditCommand.EditWarehouseDescriptor;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.Phone;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final String LOG_MESSAGE_TOKENIZE_SUCCESS = "Successfully tokenized user input.";
    private static final String LOG_MESSAGE_VALID_TYPE_PREFIX_SUPPLIER =
            "User input contains type prefix for supplier.";
    private static final String LOG_MESSAGE_CREATE_SUPPLIER_DESCRIPTOR_SUCCESS =
            "Successfully created an editSupplierDescriptor using the given user input.";
    private static final String INVALID_WAREHOUSE_PREFIX_ASSERTION =
            "The warehouse prefix should have been present.";
    private static final String INVALID_PHONE_PREFIX_ASSERTION = "The prefix here should be of Phone type!";

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_INDEX, PREFIX_NAME,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK);

        logger.log(Level.INFO, LOG_MESSAGE_TOKENIZE_SUCCESS);

        // Check if the prefix and arguments are valid
        if (!argMultimap.getValue(PREFIX_TYPE).isPresent() && !argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX_AND_INDEX, EditCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX, EditCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_NO_INDEX, EditCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_INDEX, argMultimap);
        }

        Type type;
        try {
            type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_TYPE, argMultimap);
        }

        if (type.equals(Type.WAREHOUSE_PRODUCT) || type.equals(Type.SUPPLIER_PRODUCT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_TYPE_EDIT, EditCommand.MESSAGE_USAGE));
        }

        // Parse into EditCommand
        if (type.equals(Type.SUPPLIER)) {
            return parseEditSupplier(argMultimap, index);
        } else {
            return parseEditWarehouse(argMultimap, index);
        }
    }

    private EditCommand parseEditSupplier(ArgumentMultimap argMultimap, Index index) throws ParseException {

        logger.log(Level.INFO, LOG_MESSAGE_VALID_TYPE_PREFIX_SUPPLIER);

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            throw new ParseException(String.format(MESSAGE_SUPPLIER_NO_ADDRESS,
                    EditCommand.MESSAGE_USAGE));
        }

        EditSupplierDescriptor editSupplierDescriptor = new EditSupplierDescriptor();
        editSupplierDescriptor = parseSupplierForEditing(editSupplierDescriptor, argMultimap);

        logger.log(Level.INFO, LOG_MESSAGE_CREATE_SUPPLIER_DESCRIPTOR_SUCCESS);


        if (!editSupplierDescriptor.isAnyFieldEdited()) {
            throw new ParseException(String.format(MESSAGE_NOT_EDITED,
                    EditCommand.MESSAGE_USAGE));
        }

        return new EditCommand(index, editSupplierDescriptor);
    }

    private EditCommand parseEditWarehouse(ArgumentMultimap argMultimap, Index index) throws ParseException {
        assert ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get()).equals(Type.WAREHOUSE)
                : INVALID_WAREHOUSE_PREFIX_ASSERTION;

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            throw new ParseException(String.format(MESSAGE_WAREHOUSE_NO_EMAIL,
                    EditCommand.MESSAGE_USAGE));
        }

        EditWarehouseDescriptor editWarehouseDescriptor = new EditWarehouseDescriptor();
        editWarehouseDescriptor = parseWarehouseForEditing(editWarehouseDescriptor, argMultimap);

        if (!editWarehouseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(String.format(MESSAGE_NOT_EDITED,
                    EditCommand.MESSAGE_USAGE));
        }

        return new EditCommand(index, editWarehouseDescriptor);
    }

    private EditWarehouseDescriptor parseWarehouseForEditing(EditWarehouseDescriptor editWarehouseDescriptor,
                                                    ArgumentMultimap argMultimap) throws ParseException {
        parseGeneralDetails(editWarehouseDescriptor, argMultimap);

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editWarehouseDescriptor.setAddress(
                    ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        return editWarehouseDescriptor;
    }

    private EditSupplierDescriptor parseSupplierForEditing(EditSupplierDescriptor editSupplierDescriptor,
                                                   ArgumentMultimap argMultimap) throws ParseException {
        parseGeneralDetails(editSupplierDescriptor, argMultimap);

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editSupplierDescriptor.setEmail(
                    ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        return editSupplierDescriptor;
    }


    private EditDescriptor parseGeneralDetails(EditDescriptor editDescriptor, ArgumentMultimap argMultimap)
            throws ParseException {

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDescriptor.setName(
                    ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            Phone phoneNumber;
            try {
                phoneNumber = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            } catch (ParseException pe) {
                throw checkInvalidArguments(PREFIX_PHONE, argMultimap);
            }
            editDescriptor.setPhone(phoneNumber);
        }

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editDescriptor.setRemark(
                    ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get()));
        }

        return editDescriptor;
    }

    private ParseException checkInvalidArguments(Prefix prefix, ArgumentMultimap argMultimap) {
        if (argMultimap.getValue(prefix).get().contains("/")) {
            return new ParseException(MESSAGE_INVALID_PREFIX + "\n" + EditCommand.MESSAGE_USAGE);
        }
        if (argMultimap.getValue(prefix).get().split("\\s+").length != 1) {
            return new ParseException(String.format(MESSAGE_INVALID_USAGE, EditCommand.MESSAGE_USAGE));
        }
        if (prefix.equals(PREFIX_TYPE)) {
            return new ParseException(String.format(MESSAGE_INVALID_TYPE_EDIT, EditCommand.MESSAGE_USAGE));
        } else if (prefix.equals(PREFIX_INDEX)) {
            return new ParseException(MESSAGE_INVALID_INDEX + "\n" + EditCommand.MESSAGE_USAGE);
        } else {
            assert prefix.equals(PREFIX_PHONE) : INVALID_PHONE_PREFIX_ASSERTION;
            return new ParseException(Phone.MESSAGE_CONSTRAINTS + "\n" + EditCommand.MESSAGE_USAGE);
        }
    }
}
