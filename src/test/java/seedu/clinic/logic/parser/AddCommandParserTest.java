package seedu.clinic.logic.parser;
/*
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinic.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.clinic.testutil.TypicalSupplier.BOB;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.testutil.TypicalSupplier.AMY;

import static seedu.clinic.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import java.util.Map;

import org.junit.jupiter.api.Test;

<<<<<<< HEAD:src/test/java/seedu/address/logic/parser/AddCommandParserTest.java
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.Remark;
import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.SupplierBuilder;
=======
import seedu.clinic.logic.commands.AddCommand;
import seedu.clinic.model.supplier.Email;
import seedu.clinic.model.supplier.Name;
import seedu.clinic.model.supplier.Phone;
import seedu.clinic.model.supplier.Remark;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.testutil.SupplierBuilder;
>>>>>>> upstream/master:src/test/java/seedu/clinic/logic/parser/AddCommandParserTest.java
*/
public class AddCommandParserTest {
/*
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Supplier expectedSupplier = new SupplierBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + REMARK_DESC_BOB, new AddCommand(expectedSupplier));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + REMARK_DESC_BOB, new AddCommand(expectedSupplier));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + REMARK_DESC_BOB, new AddCommand(expectedSupplier));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + REMARK_DESC_BOB, new AddCommand(expectedSupplier));

        // multiple remarks - last remark accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + REMARK_DESC_AMY
                + REMARK_DESC_BOB, new AddCommand(expectedSupplier));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Supplier expectedSupplier = new SupplierBuilder(AMY).withProducts(Map.of()).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedSupplier));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + REMARK_DESC_BOB,
                expectedMessage);

        // missing remark prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_REMARK_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_REMARK_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + REMARK_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + REMARK_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + REMARK_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_REMARK_DESC,
                Remark.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_REMARK_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + REMARK_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
 */
}
