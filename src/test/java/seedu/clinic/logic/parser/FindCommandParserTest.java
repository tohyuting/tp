package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_TYPE;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.model.supplier.SupplierPredicate;
import seedu.clinic.model.warehouse.WarehousePredicate;

/**
 * Tests if {@code FindCommand} parses the arguments correctly for {@code Supplier} and {@code Warehouse}.
 */
public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArg_throwsParseException() {
        // Missing at least one of the name, product and remark prefix
        assertParseFailure(parser, " ct/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ct/w", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        // Missing the type prefix
        assertParseFailure(parser, " n/alex", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " pd/panadol", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " r/biggest", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/alex bernice pd/face", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/alex bernice r/biggest", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " pd/face mask r/biggest", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/alex bernice pd/face mask panadol r/biggest",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidType_throwsParseException() {
        assertParseFailure(parser, " ct/supplier pd/panadol",
                String.format(MESSAGE_INVALID_TYPE, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ct/warehouse pd/panadol",
                String.format(MESSAGE_INVALID_TYPE, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ct/pss pd/panadol",
                String.format(MESSAGE_INVALID_TYPE, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " ct/ps n/alex", String.format(MESSAGE_INVALID_TYPE,
                FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ct/pw pd/panadol",
                String.format(MESSAGE_INVALID_TYPE, FindCommand.MESSAGE_USAGE));
    }

    // Only the last prefix provided for type, name, product and remark will be accepted
    @Test
    public void parse_validArgsMultiplePrefixes_returnsFindCommandToFindSuppliers() {
        List<String> nameKeywords = Arrays.asList("alex", "bernice");
        List<String> productKeywords = Arrays.asList("panadol");
        List<String> remarkKeywords = Arrays.asList("cheap", "fast");

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new SupplierPredicate(nameKeywords, productKeywords, remarkKeywords));
        assertParseSuccess(parser, " ct/s n/roy n/alex bernice pd/face mask and needle pd/panadol"
                        + " r/test remark r/cheap fast",
                expectedFindCommand);
    }

    // Only the last prefix provided for type, name, product and remark will be accepted
    @Test
    public void parse_validArgsMultiplePrefixes_returnsFindCommandToFindWarehouses() {
        List<String> nameKeywords = Arrays.asList("alex", "bernice");
        List<String> productKeywords = Arrays.asList("panadol");
        List<String> remarkKeywords = Arrays.asList("biggest");

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new WarehousePredicate(nameKeywords, productKeywords, remarkKeywords));
        assertParseSuccess(parser, " ct/w n/roy n/alex bernice pd/face mask and needle pd/panadol"
                        + " r/smallest r/biggest",
                expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommandToFindSuppliers() {
        List<String> nameKeywords = Arrays.asList("alex", "bernice");
        List<String> productKeywords = Arrays.asList("panadol");
        List<String> remarkKeywords = Arrays.asList("cheap", "fast");

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new SupplierPredicate(nameKeywords, productKeywords, remarkKeywords));
        assertParseSuccess(parser, " ct/s n/alex bernice pd/panadol r/cheap fast",
                expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n ct/s \n \t n/alex bernice pd/panadol \t r/cheap fast",
                expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommandToFindWarehouses() {
        List<String> nameKeywords = Arrays.asList("alex", "bernice");
        List<String> productKeywords = Arrays.asList("panadol");
        List<String> remarkKeywords = Arrays.asList("biggest");

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new WarehousePredicate(nameKeywords, productKeywords, remarkKeywords));
        assertParseSuccess(parser, " ct/w n/alex bernice pd/panadol r/biggest",
                expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n ct/w \n \t n/alex \t\n bernice pd/panadol \t r/biggest \t",
                expectedFindCommand);
    }

}
