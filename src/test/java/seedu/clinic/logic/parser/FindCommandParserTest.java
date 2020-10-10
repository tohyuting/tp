package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.model.supplier.SupplierProductsContainKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new SupplierProductsContainKeywordsPredicate(Arrays.asList("supplier", "panadol")));
        assertParseSuccess(parser, "supplier panadol", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n supplier \n \t panadol  \t", expectedFindCommand);
    }

}
