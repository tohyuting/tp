package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.model.supplier.SupplierProductsContainKeywordsPredicate;
import seedu.clinic.model.warehouse.WarehouseProductsContainKeywordsPredicate;

/**
 * Tests if {@code FindCommand} parses the arguments correctly for {@code Supplier} and {@code Warehouse}.
 */
public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidType_throwsParseException() {
        assertParseFailure(parser, "s", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "w", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "suppliers", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "warehouses", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "suppliersqwerty panadol", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "warehouses panadol", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingKeyword_throwsParseException() {
        assertParseFailure(parser, "supplier", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "warehouse", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindSuppliersCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindSuppliersCommand =
                new FindCommand(new SupplierProductsContainKeywordsPredicate(Arrays.asList("supplier", "panadol")));
        assertParseSuccess(parser, "supplier panadol", expectedFindSuppliersCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n supplier \n \t panadol  \t", expectedFindSuppliersCommand);
    }

    @Test
    public void parse_validArgs_returnsFindWarehousesCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindWarehousesCommand =
                new FindCommand(new WarehouseProductsContainKeywordsPredicate(Arrays.asList("warehouse", "panadol")));
        assertParseSuccess(parser, "warehouse panadol", expectedFindWarehousesCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n warehouse \n \t panadol  \t", expectedFindWarehousesCommand);
    }

}
