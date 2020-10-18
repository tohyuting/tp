package seedu.clinic.logic.parser;

import static seedu.clinic.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_NAME_DESC_WAREHOUSE;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinic.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_NAME_A;
import static seedu.clinic.logic.commands.CommandTestUtil.WAREHOUSE_NAME_DESC_A;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_INPUT_BOTH_SUPPLIER_WAREHOUSE_PREFIX;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_NO_PREFIX;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_SUPPLIER_NO_ADDRESS;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_SUPPLIER_PREFIX_NOT_ALLOWED;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_WAREHOUSE_NO_EMAIL;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_WAREHOUSE_PREFIX_NOT_ALLOWED;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_WAREHOUSE;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_WAREHOUSE;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_THIRD_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_THIRD_WAREHOUSE;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.EditCommand;
import seedu.clinic.logic.commands.EditCommand.EditSupplierDescriptor;
import seedu.clinic.logic.commands.EditCommand.EditWarehouseDescriptor;
import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Email;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.testutil.EditSupplierDescriptorBuilder;
import seedu.clinic.testutil.EditWarehouseDescriptorBuilder;


public class EditCommandParserTest {

    private static final String REMARK_EMPTY = " " + PREFIX_REMARK;

    private static final String MESSAGE_WAREHOUSE_EMAIL_PREFIX =
            String.format(MESSAGE_WAREHOUSE_NO_EMAIL,
                    EditCommand.MESSAGE_USAGE);
    private static final String MESSAGE_SUPPLIER_ADDRESS_PREFIX =
            String.format(MESSAGE_SUPPLIER_NO_ADDRESS,
                    EditCommand.MESSAGE_USAGE);
    private static final String MESSAGE_NO_INDEX_INPUT = String.format(MESSAGE_NO_PREFIX,
            EditCommand.MESSAGE_USAGE);
    private static final String MESSAGE_TWO_INDEX_INPUT = String.format(
            MESSAGE_INPUT_BOTH_SUPPLIER_WAREHOUSE_PREFIX, EditCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_INDEX = String.format(
            EditCommand.MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INVALID_INDEX);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_NO_INDEX_INPUT);
        assertParseFailure(parser, VALID_PHONE_AMY, MESSAGE_NO_INDEX_INPUT);
        assertParseFailure(parser, VALID_REMARK_AMY, MESSAGE_NO_INDEX_INPUT);
        assertParseFailure(parser, VALID_ADDRESS_AMY, MESSAGE_NO_INDEX_INPUT);
        assertParseFailure(parser, VALID_EMAIL_AMY, MESSAGE_NO_INDEX_INPUT);

        // no field specified
        assertParseFailure(parser, " si/1", EditCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, " wi/1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_NO_INDEX_INPUT);
        assertParseFailure(parser, PREAMBLE_WHITESPACE, MESSAGE_NO_INDEX_INPUT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, " si/-5 " + NAME_DESC_AMY, MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, " wi/-5 " + WAREHOUSE_NAME_DESC_A, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, " si/0 " + NAME_DESC_AMY, MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, " wi/0 " + WAREHOUSE_NAME_DESC_A, MESSAGE_INVALID_INDEX);

        //two index
        assertParseFailure(parser, " si/0 " + NAME_DESC_AMY + " wi/0" + WAREHOUSE_NAME_DESC_A,
                MESSAGE_TWO_INDEX_INPUT);

        // invalid arguments being parsed as index
        assertParseFailure(parser, " si/1 some random string "
                + NAME_DESC_AMY, MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, " wi/1 some random string "
                + WAREHOUSE_NAME_DESC_A, MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as index
        assertParseFailure(parser, " si/1 i/ string", String.format(
                EditCommand.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_INVALID_PREFIX));

        assertParseFailure(parser, " wi/1 i/ string", String.format(
                EditCommand.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_INVALID_PREFIX));
    }

    @Test
    public void parse_invalidValue_failure() {

        assertParseFailure(parser, " si/1 " + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " si/1 " + INVALID_PHONE_DESC,
                String.format(EditCommand.MESSAGE_INVALID_COMMAND_FORMAT,
                        Phone.MESSAGE_CONSTRAINTS)); // invalid phone
        assertParseFailure(parser, " si/1 " + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, " si/1 " + INVALID_ADDRESS_DESC,
                MESSAGE_SUPPLIER_ADDRESS_PREFIX); // address not allowed
        assertParseFailure(parser, " si/1 " + INVALID_REMARK_DESC,
                Remark.MESSAGE_CONSTRAINTS); // invalid tag

        assertParseFailure(parser, " wi/1 " + INVALID_NAME_DESC_WAREHOUSE,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " wi/1 " + INVALID_PHONE_DESC,
                String.format(EditCommand.MESSAGE_INVALID_COMMAND_FORMAT,
                        Phone.MESSAGE_CONSTRAINTS)); // invalid phone
        assertParseFailure(parser, " wi/1 " + INVALID_EMAIL_DESC,
                MESSAGE_WAREHOUSE_EMAIL_PREFIX); // email not allowed
        assertParseFailure(parser, " wi/1 " + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, " wi/1 " + INVALID_REMARK_DESC,
                Remark.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, " si/1 " + INVALID_PHONE_DESC + EMAIL_DESC_AMY,
                String.format(EditCommand.MESSAGE_INVALID_COMMAND_FORMAT, Phone.MESSAGE_CONSTRAINTS));

        //invalid phone followed by valid address
        assertParseFailure(parser, " wi/1 " + INVALID_PHONE_DESC + ADDRESS_DESC_AMY,
                String.format(EditCommand.MESSAGE_INVALID_COMMAND_FORMAT, Phone.MESSAGE_CONSTRAINTS));

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, " si/1 " + PHONE_DESC_BOB + INVALID_PHONE_DESC,
                String.format(EditCommand.MESSAGE_INVALID_COMMAND_FORMAT, Phone.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " wi/1 " + PHONE_DESC_BOB + INVALID_PHONE_DESC,
                String.format(EditCommand.MESSAGE_INVALID_COMMAND_FORMAT, Phone.MESSAGE_CONSTRAINTS));

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " si/1 " + INVALID_NAME_DESC + INVALID_EMAIL_DESC
                + PHONE_DESC_AMY, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " wi/1 " + INVALID_NAME_DESC_WAREHOUSE
                + INVALID_ADDRESS_DESC + PHONE_DESC_AMY, Name.MESSAGE_CONSTRAINTS);

        //using warehouse prefix (/w) when editing suppliers
        assertParseFailure(parser, " si/1 " + INVALID_NAME_DESC_WAREHOUSE + INVALID_EMAIL_DESC
                + PHONE_DESC_AMY, String.format(EditCommand.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_WAREHOUSE_PREFIX_NOT_ALLOWED));

        //using supplier prefix (/s) when editing warehouses
        assertParseFailure(parser, " wi/1 " + INVALID_NAME_DESC
                + INVALID_ADDRESS_DESC + PHONE_DESC_AMY, String.format(EditCommand.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_SUPPLIER_PREFIX_NOT_ALLOWED));

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        //all fields for suppliers
        Index targetIndexSupplier = INDEX_SECOND_SUPPLIER;
        String userInputSupplier = " si/" + targetIndexSupplier.getOneBased() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + NAME_DESC_AMY + REMARK_DESC_AMY;

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withRemark(VALID_REMARK_AMY).build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //all fields for warehouses
        Index targetIndexWarehouse = INDEX_SECOND_WAREHOUSE;
        String userInputWarehouse = " wi/" + targetIndexWarehouse.getOneBased() + PHONE_DESC_BOB
                + ADDRESS_DESC_AMY + WAREHOUSE_NAME_DESC_A + REMARK_DESC_AMY;

        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder()
                .withName(VALID_WAREHOUSE_NAME_A).withPhone(VALID_PHONE_BOB)
                .withAddress(VALID_ADDRESS_AMY).withRemark(VALID_REMARK_AMY).build();
        EditCommand expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        //some fields for suppliers
        Index targetIndexSupplier = INDEX_SECOND_SUPPLIER;
        String userInputSupplier = " si/" + targetIndexSupplier.getOneBased() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY;

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //some fields for warehouses
        Index targetIndexWarehouse = INDEX_SECOND_WAREHOUSE;
        String userInputWarehouse = " wi/" + targetIndexWarehouse.getOneBased() + PHONE_DESC_BOB
                + ADDRESS_DESC_AMY;

        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_AMY).build();
        EditCommand expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);
    }


    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        //supplier
        Index targetIndexSupplier = INDEX_SECOND_SUPPLIER;
        String userInputSupplier = " si/" + targetIndexSupplier.getOneBased() + NAME_DESC_AMY;

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        Index targetIndexWarehouse = INDEX_SECOND_WAREHOUSE;
        String userInputWarehouse = " wi/" + targetIndexWarehouse.getOneBased() + WAREHOUSE_NAME_DESC_A;

        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder()
                .withName(VALID_WAREHOUSE_NAME_A).build();
        EditCommand expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);

        // phone
        //supplier
        userInputSupplier = " si/" + targetIndexSupplier.getOneBased() + PHONE_DESC_AMY;
        descriptorSupplier = new EditSupplierDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        userInputWarehouse = " wi/" + targetIndexWarehouse.getOneBased() + PHONE_DESC_AMY;
        descriptorWarehouse = new EditWarehouseDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);

        // remark
        //supplier
        userInputSupplier = " si/" + targetIndexSupplier.getOneBased() + REMARK_DESC_AMY;

        descriptorSupplier = new EditSupplierDescriptorBuilder().withRemark(VALID_REMARK_AMY).build();
        expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        userInputWarehouse = " wi/" + targetIndexWarehouse.getOneBased() + REMARK_DESC_AMY;
        descriptorWarehouse = new EditWarehouseDescriptorBuilder().withRemark(VALID_REMARK_AMY).build();
        expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);

        // email
        //supplier only
        userInputSupplier = " si/" + targetIndexSupplier.getOneBased() + EMAIL_DESC_AMY;
        descriptorSupplier = new EditSupplierDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);
        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        // address
        //warehouse only
        userInputWarehouse = " wi/" + targetIndexWarehouse.getOneBased() + ADDRESS_DESC_AMY;
        descriptorWarehouse = new EditWarehouseDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);
        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);

    }


    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        //supplier
        Index targetIndexSupplier = INDEX_FIRST_SUPPLIER;
        String userInputSupplier = " si/" + targetIndexSupplier.getOneBased() + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB;

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        Index targetIndexWarehouse = INDEX_FIRST_WAREHOUSE;
        String userInputWarehouse = " wi/" + targetIndexWarehouse.getOneBased() + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + PHONE_DESC_BOB + ADDRESS_DESC_BOB;

        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB).build();
        EditCommand expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        //supplier
        Index targetIndexSupplier = INDEX_FIRST_SUPPLIER;
        String userInputSupplier = targetIndexSupplier.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);
        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        Index targetIndexWarehouse = INDEX_FIRST_WAREHOUSE;
        String userInputWarehouse = targetIndexWarehouse.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);
        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);

        // other valid values specified
        //supplier
        userInputSupplier = targetIndexSupplier.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC
                + REMARK_DESC_BOB + PHONE_DESC_BOB;
        descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withRemark(VALID_REMARK_BOB).build();
        expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);
        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        userInputWarehouse = targetIndexWarehouse.getOneBased() + ADDRESS_DESC_BOB + INVALID_PHONE_DESC
                + REMARK_DESC_BOB + PHONE_DESC_BOB;
        descriptorWarehouse = new EditWarehouseDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withRemark(VALID_REMARK_BOB).build();
        expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);
        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);
    }

    @Test
    public void parse_resetRemarks_success() {
        //supplier
        Index targetIndexSupplier = INDEX_THIRD_SUPPLIER;
        String userInputSupplier = " si/" + targetIndexSupplier.getOneBased() + REMARK_EMPTY;

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder().withRemark("").build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        Index targetIndexWarehouse = INDEX_THIRD_WAREHOUSE;
        String userInputWarehouse = " wi/" + targetIndexWarehouse.getOneBased() + REMARK_EMPTY;

        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder().withRemark("").build();
        EditCommand expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);
    }

}
