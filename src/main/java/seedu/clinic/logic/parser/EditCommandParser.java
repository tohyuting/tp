package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_NO_INDEX;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_NO_PREFIX;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_SUPPLIER_NO_ADDRESS;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_WAREHOUSE_NO_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;

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

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_INDEX, PREFIX_NAME,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK);

        logger.log(Level.INFO, "Successfully tokenized user input.");

        if (!argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX,
                    EditCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_NO_INDEX,
                    EditCommand.MESSAGE_USAGE));
        }

        String typeOfCommand = argMultimap.getValue(PREFIX_TYPE).get();
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (ParseException pe) {
            String indexValue = argMultimap.getValue(PREFIX_INDEX).get();
            if (indexValue.contains("/")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EditCommand.MESSAGE_INVALID_PREFIX));
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, pe.getMessage()));
        }

        if (typeOfCommand.equals("s")) {

            logger.log(Level.INFO, "User input contains supplier prefix.");

            if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                throw new ParseException(String.format(MESSAGE_SUPPLIER_NO_ADDRESS,
                        EditCommand.MESSAGE_USAGE));
            }

            EditSupplierDescriptor editSupplierDescriptor = new EditSupplierDescriptor();
            editSupplierDescriptor = parseSupplierForEditing(editSupplierDescriptor, argMultimap);

            logger.log(Level.INFO, "Successfully created an editSupplierDescriptor using the given"
                    + " user input.");


            if (!editSupplierDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }

            return new EditCommand(index, editSupplierDescriptor);
        } else {
            assert argMultimap.getValue(PREFIX_TYPE).get().equals("w") : "The warehouse prefix "
                    + " should have been present.";

            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                throw new ParseException(String.format(MESSAGE_WAREHOUSE_NO_EMAIL,
                        EditCommand.MESSAGE_USAGE));
            }

            EditWarehouseDescriptor editWarehouseDescriptor = new EditWarehouseDescriptor();
            editWarehouseDescriptor = parseWarehouseForEditing(editWarehouseDescriptor, argMultimap);

            if (!editWarehouseDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }

            return new EditCommand(index, editWarehouseDescriptor);
        }
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
                String invalidPhoneNumber = argMultimap.getValue(PREFIX_PHONE).get();
                if (invalidPhoneNumber.contains("/")) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            EditCommand.MESSAGE_INVALID_PREFIX));
                }
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, pe.getMessage()));
            }
            editDescriptor.setPhone(phoneNumber);
        }

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editDescriptor.setRemark(
                    ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get()));
        }

        return editDescriptor;
    }
}
