package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.parser.CliSyntax.TYPE_SUPPLIER;
import static seedu.clinic.logic.parser.CliSyntax.TYPE_WAREHOUSE;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_WAREHOUSE;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.DeleteCommand;
import seedu.clinic.model.attribute.Name;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {
    private static final String VALID_INDEX_DESC = " " + INDEX_FIRST_SUPPLIER.getOneBased();

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommandWithoutProduct() {
        assertParseSuccess(parser, TYPE_SUPPLIER + VALID_INDEX_DESC,
                new DeleteCommand(TYPE_SUPPLIER, INDEX_FIRST_SUPPLIER));
        assertParseSuccess(parser, TYPE_WAREHOUSE+ VALID_INDEX_DESC,
                new DeleteCommand(TYPE_WAREHOUSE, INDEX_FIRST_WAREHOUSE));

        //whitespace + valid argument
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + TYPE_SUPPLIER + VALID_INDEX_DESC,
                new DeleteCommand(TYPE_SUPPLIER, INDEX_FIRST_SUPPLIER));
    }

    @Test
    public void parse_validArgs_returnsDeleteCommandWithProduct() {
        assertParseSuccess(parser,
                TYPE_SUPPLIER + VALID_INDEX_DESC + PRODUCT_NAME_DESC_A,
                new DeleteCommand(TYPE_SUPPLIER, INDEX_FIRST_SUPPLIER, new Name(VALID_PRODUCT_NAME_ASPIRIN)));

        //whitespace + valid argument
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + TYPE_SUPPLIER + VALID_INDEX_DESC + PRODUCT_NAME_DESC_A + PREAMBLE_WHITESPACE,
                new DeleteCommand(TYPE_SUPPLIER, INDEX_FIRST_SUPPLIER, new Name(VALID_PRODUCT_NAME_ASPIRIN)));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // whitespace only
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //invalid type keyword
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        //less than 2 arguments
        assertParseFailure(parser, "supplier",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        //invalid list index
        assertParseFailure(parser, "supplier ab",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "warehouse ab",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_deleteCommandWithProductInvalidPrefix_throwsParseException() {
        //wrong prefix
        assertParseFailure(parser, TYPE_SUPPLIER + VALID_INDEX_DESC + " p/abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        //prefix missing
        assertParseFailure(parser, TYPE_SUPPLIER + VALID_INDEX_DESC + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_deleteCommandWithProductInvalidProductName_throwsParseException() {
        //invalid product name
        assertParseFailure(parser, TYPE_SUPPLIER + VALID_INDEX_DESC + " pd/*abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
