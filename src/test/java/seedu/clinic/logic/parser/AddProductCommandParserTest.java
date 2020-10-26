package seedu.clinic.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_PRODUCT_NAME_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_SUPPLIER_NAME_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.TAG_DESC_ANTIBIOTICS;
import static seedu.clinic.logic.commands.CommandTestUtil.TAG_DESC_ANTIBIOTICS_FEVER;
import static seedu.clinic.logic.commands.CommandTestUtil.TAG_DESC_FEVER;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_TAG_ANTIBIOTICS;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_TAG_FEVER;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.model.util.SampleDataUtil.getTagSet;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.AddProductCommand;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Tag;
import seedu.clinic.model.product.Product;

class AddProductCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProductCommand.MESSAGE_USAGE);

    private AddProductCommandParser parser = new AddProductCommandParser();

    @Test
    public void parse_nullArgs_throwsNullPointerException() throws Exception {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Name expectedName = new Name(VALID_NAME_AMY);
        Product expectedProduct = new Product(new Name(VALID_PRODUCT_NAME_ASPIRIN), getTagSet(VALID_TAG_ANTIBIOTICS));

        // whitespace only preamble - success
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + PRODUCT_NAME_DESC_AMY
                + TAG_DESC_ANTIBIOTICS, new AddProductCommand(expectedName, expectedProduct));

        // one tag - success
        assertParseSuccess(parser, NAME_DESC_AMY + PRODUCT_NAME_DESC_AMY
                + TAG_DESC_ANTIBIOTICS, new AddProductCommand(expectedName, expectedProduct));

        // multiple tags - success
        expectedProduct = new Product(new Name(VALID_PRODUCT_NAME_ASPIRIN),
                getTagSet(VALID_TAG_ANTIBIOTICS, VALID_TAG_FEVER));
        assertParseSuccess(parser, NAME_DESC_AMY + PRODUCT_NAME_DESC_AMY + TAG_DESC_ANTIBIOTICS_FEVER,
                new AddProductCommand(expectedName, expectedProduct));
    }

    @Test
    public void parser_optionalFieldMissing_success() {
        Name expectedName = new Name(VALID_NAME_AMY);
        Product expectedProduct = new Product(new Name(VALID_PRODUCT_NAME_ASPIRIN), new HashSet<>());

        // no tag - success
        assertParseSuccess(parser, NAME_DESC_AMY + PRODUCT_NAME_DESC_AMY,
                new AddProductCommand(expectedName, expectedProduct));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parse_invalidValue_failure() {
        // invalid supplier name
        assertParseFailure(parser, INVALID_SUPPLIER_NAME_DESC + PRODUCT_NAME_DESC_AMY, Name.MESSAGE_CONSTRAINTS);

        // invalid product name
        assertParseFailure(parser, NAME_DESC_AMY + INVALID_PRODUCT_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_AMY + PRODUCT_NAME_DESC_AMY + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        // empty tag
        assertParseFailure(parser, NAME_DESC_AMY + PRODUCT_NAME_DESC_AMY + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
    }

    // multiple repeated fields, last entry value accepted
    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Name expectedName = new Name(VALID_NAME_AMY);
        Product expectedProduct = new Product(new Name(VALID_PRODUCT_NAME_ASPIRIN), getTagSet(VALID_TAG_ANTIBIOTICS));

        String userInput = NAME_DESC_BOB + NAME_DESC_AMY + PRODUCT_NAME_DESC_BOB + PRODUCT_NAME_DESC_AMY
                + TAG_DESC_FEVER + TAG_DESC_ANTIBIOTICS;

        assertParseSuccess(parser, userInput, new AddProductCommand(expectedName, expectedProduct));
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // invalid name in the input
        Name expectedName = new Name(VALID_NAME_AMY);
        Product expectedProduct = new Product(new Name(VALID_PRODUCT_NAME_ASPIRIN), getTagSet(VALID_TAG_ANTIBIOTICS));

        String userInput = INVALID_SUPPLIER_NAME_DESC + NAME_DESC_AMY + PRODUCT_NAME_DESC_BOB + PRODUCT_NAME_DESC_AMY
                + TAG_DESC_FEVER + TAG_DESC_ANTIBIOTICS;

        assertParseSuccess(parser, userInput, new AddProductCommand(expectedName, expectedProduct));

        // invalid name in the input
        expectedProduct = new Product(new Name(VALID_PRODUCT_NAME_ASPIRIN), getTagSet(VALID_TAG_ANTIBIOTICS));
        userInput = NAME_DESC_AMY + INVALID_PRODUCT_NAME_DESC + PRODUCT_NAME_DESC_AMY
                + TAG_DESC_FEVER + TAG_DESC_ANTIBIOTICS;

        assertParseSuccess(parser, userInput, new AddProductCommand(expectedName, expectedProduct));
    }
}
