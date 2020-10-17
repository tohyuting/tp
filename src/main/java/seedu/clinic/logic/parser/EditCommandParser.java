package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_SUPPLIER_NO_ADDRESS;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_WAREHOUSE_NO_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_SUPPLIER_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.EditCommand;
import seedu.clinic.logic.commands.EditCommand.EditDescriptor;
import seedu.clinic.logic.commands.EditCommand.EditSupplierDescriptor;
import seedu.clinic.logic.commands.EditCommand.EditWarehouseDescriptor;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_WAREHOUSE_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_WAREHOUSE_NAME;
import seedu.clinic.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUPPLIER_INDEX, PREFIX_WAREHOUSE_INDEX,
                        PREFIX_SUPPLIER_NAME, PREFIX_WAREHOUSE_NAME,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK);

        if (argMultimap.getValue(PREFIX_SUPPLIER_INDEX).isPresent()
                && argMultimap.getValue(PREFIX_WAREHOUSE_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_SUPPLIER_INDEX).isPresent()
                && !argMultimap.getValue(PREFIX_WAREHOUSE_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE));
        }
        EditDescriptor editDescriptor;
        Index index;
        if (argMultimap.getValue(PREFIX_SUPPLIER_INDEX).isPresent()) {
            if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_SUPPLIER_NO_ADDRESS));
            }
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_SUPPLIER_INDEX).get());
            EditSupplierDescriptor editSupplierDescriptor = new EditSupplierDescriptor();
            editSupplierDescriptor = parseSupplierForEditing(editSupplierDescriptor, argMultimap);
            if (!editSupplierDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
            return new EditCommand(index, editSupplierDescriptor);
        } else {
            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_WAREHOUSE_NO_EMAIL));
            }
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_WAREHOUSE_INDEX).get());
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


    private EditDescriptor parseGeneralDetails(EditDescriptor editDescriptor, ArgumentMultimap argMultimap) throws ParseException {
        Prefix nameType;
        if (editDescriptor instanceof EditSupplierDescriptor) {
            nameType = PREFIX_SUPPLIER_NAME;
        } else {
            nameType = PREFIX_WAREHOUSE_NAME;
        }
        if (argMultimap.getValue(nameType).isPresent()) {
            editDescriptor.setName(
                    ParserUtil.parseName(argMultimap.getValue(nameType).get()));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editDescriptor.setPhone(
                    ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editDescriptor.setRemark(
                    ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get()));
        }
        return editDescriptor;
    }
}
