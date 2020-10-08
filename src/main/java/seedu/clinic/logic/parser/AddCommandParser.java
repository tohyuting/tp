package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

<<<<<<< HEAD:src/main/java/seedu/address/logic/parser/AddCommandParser.java
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.Product;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.Remark;
import seedu.address.model.supplier.Supplier;
=======
import seedu.clinic.logic.commands.AddCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Email;
import seedu.clinic.model.supplier.Name;
import seedu.clinic.model.supplier.Phone;
import seedu.clinic.model.supplier.Remark;
import seedu.clinic.model.supplier.Supplier;
>>>>>>> upstream/master:src/main/java/seedu/clinic/logic/parser/AddCommandParser.java

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
        Set<Product> productList = new HashSet<>();

        Supplier supplier = new Supplier(name, phone, email, remark, productList);

        return new AddCommand(supplier);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
