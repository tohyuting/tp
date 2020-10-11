package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_PRODUCT_NAME_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_PRODUCT_QUANTITY_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_WAREHOUSE_NAME_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_QUANTITY_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_QUANTITY_DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_NAME_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_A;
import static seedu.clinic.logic.commands.CommandTestUtil.WAREHOUSE_NAME_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.WAREHOUSE_NAME_DESC_B;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_QUANTITY;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.UpdateCommand;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.product.Product;

public class UpdateCommandParserTest {

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Name expectedName = new Name(VALID_WAREHOUSE_NAME_A);
        Product expectedProduct = new Product(new Name(VALID_PRODUCT_NAME_ASPIRIN), VALID_WAREHOUSE_PRODUCT_QUANTITY_A);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + WAREHOUSE_NAME_DESC_A + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, new UpdateCommand(expectedName, expectedProduct));

        // multiple warehouse names - last warehouse name accepted
        assertParseSuccess(parser, WAREHOUSE_NAME_DESC_B + WAREHOUSE_NAME_DESC_A + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, new UpdateCommand(expectedName, expectedProduct));

        // multiple product names - last product name accepted
        assertParseSuccess(parser, WAREHOUSE_NAME_DESC_A + PRODUCT_NAME_DESC_B + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, new UpdateCommand(expectedName, expectedProduct));

        // multiple product quantities - last product quantity accepted
        assertParseSuccess(parser, WAREHOUSE_NAME_DESC_A + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_B
                + PRODUCT_QUANTITY_DESC_A, new UpdateCommand(expectedName, expectedProduct));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

        // missing warehouse name prefix
        assertParseFailure(parser, VALID_WAREHOUSE_NAME_A + PRODUCT_NAME_DESC_A
                + PRODUCT_QUANTITY_DESC_A, expectedMessage);

        // missing product name prefix
        assertParseFailure(parser, WAREHOUSE_NAME_DESC_A + VALID_PRODUCT_NAME_ASPIRIN
                + PRODUCT_QUANTITY_DESC_A, expectedMessage);

        // missing product quantity prefix
        assertParseFailure(parser, WAREHOUSE_NAME_DESC_A + PRODUCT_NAME_DESC_A
                        + VALID_WAREHOUSE_PRODUCT_QUANTITY_A, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_WAREHOUSE_NAME_A + VALID_PRODUCT_NAME_ASPIRIN
                        + VALID_WAREHOUSE_PRODUCT_QUANTITY_A, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid warehouse name
        assertParseFailure(parser, INVALID_WAREHOUSE_NAME_DESC + PRODUCT_NAME_DESC_A + PRODUCT_QUANTITY_DESC_A,
                Name.MESSAGE_CONSTRAINTS);

        // invalid product name
        assertParseFailure(parser, WAREHOUSE_NAME_DESC_A + INVALID_PRODUCT_NAME_DESC + PRODUCT_QUANTITY_DESC_A,
                Name.MESSAGE_CONSTRAINTS);

        // invalid product quantity
        assertParseFailure(parser, WAREHOUSE_NAME_DESC_A + PRODUCT_NAME_DESC_A + INVALID_PRODUCT_QUANTITY_DESC,
                MESSAGE_INVALID_QUANTITY);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_WAREHOUSE_NAME_DESC + PRODUCT_NAME_DESC_A + INVALID_PRODUCT_QUANTITY_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + WAREHOUSE_NAME_DESC_A + PRODUCT_NAME_DESC_A
                        + PRODUCT_QUANTITY_DESC_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }
}
