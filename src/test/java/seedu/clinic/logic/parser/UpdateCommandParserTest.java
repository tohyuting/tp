package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_PRODUCT_A;
import static seedu.clinic.logic.commands.CommandTestUtil.INDEX_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.INDEX_DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_INDEX_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_PRODUCT_NAME_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_PRODUCT_QUANTITY_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_QUANTITY_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_QUANTITY_DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.TAG_DESC_FEVER;
import static seedu.clinic.logic.commands.CommandTestUtil.TYPE_DESC_SUPPLIER;
import static seedu.clinic.logic.commands.CommandTestUtil.TYPE_DESC_WAREHOUSE;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_NAME_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_A;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_QUANTITY;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_USAGE;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_WAREHOUSE;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.UpdateCommand;
import seedu.clinic.model.attribute.Name;

public class UpdateCommandParserTest {

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Name expectedWarehouseName = new Name(VALID_WAREHOUSE_NAME_A);
        Name expectedSupplierName = new Name(VALID_NAME_BOB);
        Name expectedProductName = new Name(VALID_PRODUCT_NAME_ASPIRIN);
        Index expectedIndex = Index.fromOneBased(1);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_WAREHOUSE + INDEX_DESC_A
                + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.WAREHOUSE, expectedIndex, expectedProductName, DESC_PRODUCT_A));

        // multiple indices - last index accepted
        assertParseSuccess(parser, TYPE_DESC_WAREHOUSE + INDEX_DESC_B + INDEX_DESC_A
                + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.WAREHOUSE, expectedIndex, expectedProductName, DESC_PRODUCT_A));

        // multiple product names - last product name accepted
        assertParseSuccess(parser, TYPE_DESC_WAREHOUSE + INDEX_DESC_A + PRODUCT_NAME_DESC_B
                + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.WAREHOUSE, expectedIndex, expectedProductName, DESC_PRODUCT_A));

        // multiple product quantities - last product quantity accepted
        assertParseSuccess(parser, TYPE_DESC_WAREHOUSE + INDEX_DESC_A + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_B + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.WAREHOUSE, expectedIndex, expectedProductName, DESC_PRODUCT_A));

        // multiple entity types - last type accepted
        assertParseSuccess(parser, TYPE_DESC_WAREHOUSE + TYPE_DESC_SUPPLIER + INDEX_DESC_A
                        + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.SUPPLIER, expectedIndex, expectedProductName, DESC_PRODUCT_A));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

        // missing type prefix
        assertParseFailure(parser, Type.WAREHOUSE + INDEX_DESC_A + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, expectedMessage);

        // missing index prefix
        assertParseFailure(parser, TYPE_DESC_WAREHOUSE + INDEX_FIRST_WAREHOUSE + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, String.format(MESSAGE_INVALID_TYPE, UpdateCommand.MESSAGE_USAGE));

        // missing product name prefix
        assertParseFailure(parser, TYPE_DESC_WAREHOUSE + INDEX_DESC_A + VALID_PRODUCT_NAME_ASPIRIN
                + PRODUCT_QUANTITY_DESC_A, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, Type.WAREHOUSE.toString() + INDEX_FIRST_WAREHOUSE + VALID_PRODUCT_NAME_ASPIRIN
                        + VALID_WAREHOUSE_PRODUCT_QUANTITY_A, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid type
        assertParseFailure(parser, INVALID_TYPE_DESC + INDEX_DESC_A + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, String.format(MESSAGE_INVALID_TYPE, UpdateCommand.MESSAGE_USAGE));

        // invalid index
        assertParseFailure(parser, TYPE_DESC_WAREHOUSE + INVALID_INDEX_DESC
                + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A,
                String.format(MESSAGE_INVALID_INDEX, UpdateCommand.MESSAGE_USAGE));

        // invalid product name
        assertParseFailure(parser, TYPE_DESC_SUPPLIER + INDEX_DESC_A + INVALID_PRODUCT_NAME_DESC
                + PRODUCT_QUANTITY_DESC_A, Name.MESSAGE_CONSTRAINTS
                + "\n\n" + UpdateCommand.MESSAGE_USAGE);

        // invalid product quantity
        assertParseFailure(parser, TYPE_DESC_SUPPLIER + INDEX_DESC_A + PRODUCT_NAME_DESC_A
                + INVALID_PRODUCT_QUANTITY_DESC, String.format(MESSAGE_INVALID_QUANTITY,
                        UpdateCommand.MESSAGE_USAGE));

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, TYPE_DESC_SUPPLIER + INVALID_INDEX_DESC + PRODUCT_NAME_DESC_A
                        + INVALID_PRODUCT_QUANTITY_DESC,
                        String.format(MESSAGE_INVALID_INDEX, UpdateCommand.MESSAGE_USAGE));

        assertParseFailure(parser, INVALID_TYPE_DESC + INDEX_DESC_A + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, String.format(MESSAGE_INVALID_TYPE, UpdateCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_SUPPLIER + INDEX_DESC_A
                        + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A,
                String.format(MESSAGE_INVALID_USAGE, UpdateCommand.MESSAGE_USAGE));
    }
}
