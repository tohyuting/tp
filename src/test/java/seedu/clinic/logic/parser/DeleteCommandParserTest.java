package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.CommandTestUtil.INDEX_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.TYPE_DESC_SUPPLIER;
import static seedu.clinic.logic.commands.CommandTestUtil.TYPE_DESC_SUPPLIER_PRODUCT;
import static seedu.clinic.logic.commands.CommandTestUtil.TYPE_DESC_WAREHOUSE;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_PREFIX;
import static seedu.clinic.logic.parser.Type.SUPPLIER;
import static seedu.clinic.logic.parser.Type.SUPPLIER_PRODUCT;
import static seedu.clinic.logic.parser.Type.WAREHOUSE;
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
    private static final Name VALID_NAME_DESC = new Name(VALID_PRODUCT_NAME_ASPIRIN);

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommandWithoutProduct() {
        assertParseSuccess(parser, TYPE_DESC_SUPPLIER + INDEX_DESC_A,
                new DeleteCommand(SUPPLIER, INDEX_FIRST_SUPPLIER));
        assertParseSuccess(parser, TYPE_DESC_WAREHOUSE + INDEX_DESC_A,
                new DeleteCommand(WAREHOUSE, INDEX_FIRST_WAREHOUSE));

        // whitespace + valid argument
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + TYPE_DESC_SUPPLIER + INDEX_DESC_A,
                new DeleteCommand(SUPPLIER, INDEX_FIRST_SUPPLIER));
    }

    @Test
    public void parse_validArgsWithDifferentOrder_returnsDeleteCommandWithoutProduct() {
        // reordered argument entry & product name entered for supplier deletion - name ignored
        assertParseSuccess(parser, TYPE_DESC_SUPPLIER + PRODUCT_NAME_DESC_A + INDEX_DESC_A,
                new DeleteCommand(SUPPLIER, INDEX_FIRST_SUPPLIER));

        // reordered argument entry & product name entered for warehouse deletion - name ignored
        assertParseSuccess(parser, INDEX_DESC_A + PRODUCT_NAME_DESC_A + TYPE_DESC_WAREHOUSE,
                new DeleteCommand(WAREHOUSE, INDEX_FIRST_WAREHOUSE));
    }

    @Test
    public void parse_validArgs_returnsDeleteCommandWithProduct() {
        assertParseSuccess(parser, TYPE_DESC_SUPPLIER_PRODUCT + INDEX_DESC_A + PRODUCT_NAME_DESC_A,
                new DeleteCommand(SUPPLIER_PRODUCT, INDEX_FIRST_SUPPLIER, VALID_NAME_DESC));

        // whitespace + valid argument
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_SUPPLIER_PRODUCT
                        + INDEX_DESC_A + PRODUCT_NAME_DESC_A + PREAMBLE_WHITESPACE,
                new DeleteCommand(SUPPLIER_PRODUCT, INDEX_FIRST_SUPPLIER, VALID_NAME_DESC));
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
    public void parse_invalidPrefix_throwsParseException() {
        // invalid type prefix
        assertParseFailure(parser, "t/p" + INDEX_DESC_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // invalid index prefix
        assertParseFailure(parser, TYPE_DESC_SUPPLIER_PRODUCT + "is/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // invalid productName prefix
        assertParseFailure(parser, TYPE_DESC_SUPPLIER_PRODUCT + INDEX_DESC_A + " pdf/A",
                String.format(MESSAGE_INVALID_PREFIX, DeleteCommand.MESSAGE_USAGE));

        // missing index prefix
        assertParseFailure(parser, TYPE_DESC_SUPPLIER_PRODUCT + PRODUCT_NAME_DESC_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // missing productName prefix
        assertParseFailure(parser, TYPE_DESC_SUPPLIER_PRODUCT + INDEX_DESC_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid type argument
        assertParseFailure(parser, "ct/pe" + INDEX_DESC_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // invalid index argument
        assertParseFailure(parser, TYPE_DESC_SUPPLIER_PRODUCT + " i/0",
                String.format(MESSAGE_INVALID_INDEX, DeleteCommand.MESSAGE_USAGE));

        // invalid product name argument
        assertParseFailure(parser, TYPE_DESC_SUPPLIER_PRODUCT + INDEX_DESC_A + " pd/*name",
                Name.MESSAGE_CONSTRAINTS + "\n\n" + DeleteCommand.MESSAGE_USAGE);

        // missing index argument
        assertParseFailure(parser, TYPE_DESC_SUPPLIER + " i/" ,
                String.format(MESSAGE_INVALID_INDEX, DeleteCommand.MESSAGE_USAGE));

        // missing name argument
        assertParseFailure(parser, TYPE_DESC_SUPPLIER_PRODUCT + INDEX_DESC_A + " pd/",
                Name.MESSAGE_CONSTRAINTS + "\n\n" + DeleteCommand.MESSAGE_USAGE);
    }
}
