package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_PRODUCT_A;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_ENTITY_NAME_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_PRODUCT_NAME_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_PRODUCT_QUANTITY_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_QUANTITY_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_QUANTITY_DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.SUPPLIER_NAME_DESC_C;
import static seedu.clinic.logic.commands.CommandTestUtil.TAG_DESC_FEVER;
import static seedu.clinic.logic.commands.CommandTestUtil.TYPE_DESC_SUPPLIER;
import static seedu.clinic.logic.commands.CommandTestUtil.TYPE_DESC_WAREHOUSE;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_NAME_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_A;
import static seedu.clinic.logic.commands.CommandTestUtil.WAREHOUSE_NAME_DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.WAREHOUSE_NAME_DESC_C;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_QUANTITY;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_TYPE;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.UpdateCommand;
import seedu.clinic.model.attribute.Name;

public class UpdateCommandParserTest {

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Name expectedWarehouseName = new Name(VALID_WAREHOUSE_NAME_A);
        Name expectedSupplierName = new Name(VALID_NAME_BOB);
        Name expectedProductName = new Name(VALID_PRODUCT_NAME_ASPIRIN);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_WAREHOUSE + WAREHOUSE_NAME_DESC_C
                + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.WAREHOUSE, expectedWarehouseName, expectedProductName, DESC_PRODUCT_A));

        // multiple warehouse names - last warehouse name accepted
        assertParseSuccess(parser, TYPE_DESC_WAREHOUSE + WAREHOUSE_NAME_DESC_B + WAREHOUSE_NAME_DESC_C
                + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.WAREHOUSE, expectedWarehouseName, expectedProductName, DESC_PRODUCT_A));

        // multiple supplier names - last supplier name accepted
        assertParseSuccess(parser, SUPPLIER_NAME_DESC_C + NAME_DESC_AMY + NAME_DESC_BOB
                        + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.SUPPLIER, expectedSupplierName, expectedProductName, DESC_PRODUCT_A));

        // multiple product names - last product name accepted
        assertParseSuccess(parser, TYPE_DESC_WAREHOUSE + WAREHOUSE_NAME_DESC_C + PRODUCT_NAME_DESC_B
                + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.WAREHOUSE, expectedWarehouseName, expectedProductName, DESC_PRODUCT_A));

        // multiple product quantities - last product quantity accepted
        assertParseSuccess(parser, TYPE_DESC_WAREHOUSE + WAREHOUSE_NAME_DESC_C + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_B + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.WAREHOUSE, expectedWarehouseName, expectedProductName, DESC_PRODUCT_A));

        // multiple entity types - last type accepted
        assertParseSuccess(parser, WAREHOUSE_NAME_DESC_C + SUPPLIER_NAME_DESC_C + NAME_DESC_AMY + NAME_DESC_BOB
                        + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A + TAG_DESC_FEVER,
                new UpdateCommand(Type.SUPPLIER, expectedSupplierName, expectedProductName, DESC_PRODUCT_A));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

        // missing type prefix
        assertParseFailure(parser, Type.WAREHOUSE + WAREHOUSE_NAME_DESC_C + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, expectedMessage);

        // missing entity name prefix
        assertParseFailure(parser, TYPE_DESC_WAREHOUSE + VALID_WAREHOUSE_NAME_A + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, expectedMessage);

        // missing product name prefix
        assertParseFailure(parser, TYPE_DESC_WAREHOUSE + WAREHOUSE_NAME_DESC_C + VALID_PRODUCT_NAME_ASPIRIN
                + PRODUCT_QUANTITY_DESC_A, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, TYPE_DESC_WAREHOUSE + WAREHOUSE_NAME_DESC_C + VALID_PRODUCT_NAME_ASPIRIN
                        + VALID_WAREHOUSE_PRODUCT_QUANTITY_A, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid type
        assertParseFailure(parser, INVALID_TYPE_DESC + WAREHOUSE_NAME_DESC_C + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, MESSAGE_INVALID_TYPE);

        // invalid warehouse name
        assertParseFailure(parser, TYPE_DESC_WAREHOUSE + INVALID_ENTITY_NAME_DESC + PRODUCT_NAME_DESC_A
                        + PRODUCT_QUANTITY_DESC_A, Name.MESSAGE_CONSTRAINTS);

        // invalid supplier name
        assertParseFailure(parser, TYPE_DESC_SUPPLIER + INVALID_ENTITY_NAME_DESC + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, Name.MESSAGE_CONSTRAINTS);

        // invalid product name
        assertParseFailure(parser, TYPE_DESC_SUPPLIER + WAREHOUSE_NAME_DESC_C + INVALID_PRODUCT_NAME_DESC
                        + PRODUCT_QUANTITY_DESC_A, Name.MESSAGE_CONSTRAINTS);

        // invalid product quantity
        assertParseFailure(parser, TYPE_DESC_SUPPLIER + WAREHOUSE_NAME_DESC_C + PRODUCT_NAME_DESC_A
                        + INVALID_PRODUCT_QUANTITY_DESC, MESSAGE_INVALID_QUANTITY);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, TYPE_DESC_SUPPLIER + INVALID_ENTITY_NAME_DESC + PRODUCT_NAME_DESC_A
                        + INVALID_PRODUCT_QUANTITY_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_SUPPLIER + WAREHOUSE_NAME_DESC_C
                        + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }
}
