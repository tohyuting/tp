package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_NAME_DESC2;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_NAME_DESC_WAREHOUSE2;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.clinic.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.clinic.logic.commands.CommandTestUtil.NAME_DESC_AMY2;
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
import static seedu.clinic.logic.commands.CommandTestUtil.WAREHOUSE_NAME_DESC_A2;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_INPUT_BOTH_SUPPLIER_WAREHOUSE_PREFIX;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_NOT_EDITED;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_NO_PREFIX;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_SUPPLIER_NO_ADDRESS;
import static seedu.clinic.logic.commands.EditCommand.MESSAGE_WAREHOUSE_NO_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_USAGE;
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

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + VALID_NAME_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + VALID_PHONE_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + VALID_REMARK_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + VALID_ADDRESS_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + VALID_EMAIL_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // no field specified
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 ",
                String.format(MESSAGE_NOT_EDITED, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "2 ",
                String.format(MESSAGE_NOT_EDITED, EditCommand.MESSAGE_USAGE));

        // no type and no field specified
        assertParseFailure(parser, " " + PREFIX_INDEX + "1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_INDEX + "2 " + PREAMBLE_WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        //no type specified
        assertParseFailure(parser, " " + PREFIX_INDEX + "1 " + VALID_NAME_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_INDEX + "1 " + VALID_PHONE_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_INDEX + "1 " + VALID_REMARK_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_INDEX + "1 " + VALID_ADDRESS_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_INDEX + "1 " + VALID_EMAIL_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "-5 "
                + NAME_DESC_AMY2, String.format(MESSAGE_INVALID_INDEX, EditCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "-5 "
                + WAREHOUSE_NAME_DESC_A2, String.format(MESSAGE_INVALID_INDEX, EditCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "0 "
                + NAME_DESC_AMY2, String.format(MESSAGE_INVALID_INDEX, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "0 "
                + WAREHOUSE_NAME_DESC_A2, String.format(MESSAGE_INVALID_INDEX, EditCommand.MESSAGE_USAGE));

        // invalid arguments being parsed as index
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 testing"
                + NAME_DESC_AMY2, String.format(MESSAGE_INVALID_USAGE, EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "1 testing"
                + WAREHOUSE_NAME_DESC_A2, String.format(MESSAGE_INVALID_USAGE, EditCommand.MESSAGE_USAGE));

        // invalid prefix being parsed as index
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + " z/testing",
                String.format(MESSAGE_INVALID_INDEX, EditCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + " z/testing",
                String.format(MESSAGE_INVALID_INDEX, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidType_failure() {
        Index targetIndexSupplier = INDEX_SECOND_SUPPLIER;
        String userInputSupplier = " " + PREFIX_TYPE + "p " + PREFIX_INDEX
                + targetIndexSupplier.getOneBased() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + NAME_DESC_AMY + REMARK_DESC_AMY;

        assertParseFailure(parser, userInputSupplier,
                String.format(MESSAGE_INVALID_TYPE, EditCommand.MESSAGE_USAGE));

        userInputSupplier = " " + PREFIX_TYPE + "ps " + PREFIX_INDEX
                + targetIndexSupplier.getOneBased() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + NAME_DESC_AMY + REMARK_DESC_AMY;

        assertParseFailure(parser, userInputSupplier,
                String.format(MESSAGE_INVALID_TYPE, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {

        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 "
                        + INVALID_NAME_DESC2, Name.MESSAGE_CONSTRAINTS
                        + "\n\n" + EditCommand.MESSAGE_USAGE); // invalid name
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 "
                        + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS
                        + "\n\n" + EditCommand.MESSAGE_USAGE); // invalid phone
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 "
                        + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS
                        + "\n\n" + EditCommand.MESSAGE_USAGE); // invalid email
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 "
                        + INVALID_ADDRESS_DESC, MESSAGE_SUPPLIER_ADDRESS_PREFIX); // address not allowed
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 "
                        + INVALID_REMARK_DESC, Remark.MESSAGE_CONSTRAINTS
                        + "\n\n" + EditCommand.MESSAGE_USAGE); // invalid tag

        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "1 "
                        + INVALID_NAME_DESC_WAREHOUSE2, Name.MESSAGE_CONSTRAINTS
                        + "\n\n" + EditCommand.MESSAGE_USAGE); // invalid name
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "1 "
                        + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS
                        + "\n\n" + EditCommand.MESSAGE_USAGE); // invalid phone
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "1 "
                        + INVALID_EMAIL_DESC, MESSAGE_WAREHOUSE_EMAIL_PREFIX); // email not allowed
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "1 "
                        + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS
                        + "\n\n" + EditCommand.MESSAGE_USAGE); // invalid address
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "1 "
                        + INVALID_REMARK_DESC, Remark.MESSAGE_CONSTRAINTS
                        + "\n\n" + EditCommand.MESSAGE_USAGE); // invalid remark

        // invalid phone followed by valid email
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 "
                        + INVALID_PHONE_DESC + EMAIL_DESC_AMY,
                Phone.MESSAGE_CONSTRAINTS + "\n\n" + EditCommand.MESSAGE_USAGE);

        //invalid phone followed by valid address
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "1 "
                        + INVALID_PHONE_DESC + ADDRESS_DESC_AMY, Phone.MESSAGE_CONSTRAINTS
                + "\n\n" + EditCommand.MESSAGE_USAGE);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 "
                        + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS
                + "\n\n" + EditCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "1 "
                        + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS
                        + "\n\n" + EditCommand.MESSAGE_USAGE);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1 "
                + INVALID_NAME_DESC2 + INVALID_EMAIL_DESC
                + PHONE_DESC_AMY, Name.MESSAGE_CONSTRAINTS
                + "\n\n" + EditCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "1 "
                + INVALID_NAME_DESC_WAREHOUSE2
                + INVALID_ADDRESS_DESC + PHONE_DESC_AMY, Name.MESSAGE_CONSTRAINTS
                + "\n\n" + EditCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        //all fields for suppliers
        Index targetIndexSupplier = INDEX_SECOND_SUPPLIER;
        String userInputSupplier = " " + PREFIX_TYPE + "s " + PREFIX_INDEX
                + targetIndexSupplier.getOneBased() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + NAME_DESC_AMY + REMARK_DESC_AMY;

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withRemark(VALID_REMARK_AMY).build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //all fields for warehouses
        Index targetIndexWarehouse = INDEX_SECOND_WAREHOUSE;
        String userInputWarehouse = " " + PREFIX_TYPE + "w " + PREFIX_INDEX
                + targetIndexWarehouse.getOneBased() + PHONE_DESC_BOB
                + ADDRESS_DESC_AMY + WAREHOUSE_NAME_DESC_A2 + REMARK_DESC_AMY;

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
        String userInputSupplier = " " + PREFIX_TYPE + "s " + PREFIX_INDEX
                + targetIndexSupplier.getOneBased() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY;

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //some fields for warehouses
        Index targetIndexWarehouse = INDEX_SECOND_WAREHOUSE;
        String userInputWarehouse = " " + PREFIX_TYPE + "w " + PREFIX_INDEX
                + targetIndexWarehouse.getOneBased() + PHONE_DESC_BOB
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
        String userInputSupplier = " " + PREFIX_TYPE + "s " + PREFIX_INDEX
                + targetIndexSupplier.getOneBased() + NAME_DESC_AMY;

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        Index targetIndexWarehouse = INDEX_SECOND_WAREHOUSE;
        String userInputWarehouse = " " + PREFIX_TYPE + "w " + PREFIX_INDEX
                + targetIndexWarehouse.getOneBased() + WAREHOUSE_NAME_DESC_A;

        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder()
                .withName(VALID_WAREHOUSE_NAME_A).build();
        EditCommand expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);

        // phone
        //supplier
        userInputSupplier = " " + PREFIX_TYPE + "s " + PREFIX_INDEX
                + targetIndexSupplier.getOneBased() + PHONE_DESC_AMY;
        descriptorSupplier = new EditSupplierDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        userInputWarehouse = " " + PREFIX_TYPE + "w " + PREFIX_INDEX
                + targetIndexWarehouse.getOneBased() + PHONE_DESC_AMY;
        descriptorWarehouse = new EditWarehouseDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);

        // remark
        //supplier
        userInputSupplier = " " + PREFIX_TYPE + "s " + PREFIX_INDEX
                + targetIndexSupplier.getOneBased() + REMARK_DESC_AMY;

        descriptorSupplier = new EditSupplierDescriptorBuilder().withRemark(VALID_REMARK_AMY).build();
        expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        userInputWarehouse = " " + PREFIX_TYPE + "w " + PREFIX_INDEX
                + targetIndexWarehouse.getOneBased() + REMARK_DESC_AMY;
        descriptorWarehouse = new EditWarehouseDescriptorBuilder().withRemark(VALID_REMARK_AMY).build();
        expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);

        // email
        //supplier only
        userInputSupplier = " " + PREFIX_TYPE + "s " + PREFIX_INDEX
                + targetIndexSupplier.getOneBased() + EMAIL_DESC_AMY;
        descriptorSupplier = new EditSupplierDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);
        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        // address
        //warehouse only
        userInputWarehouse = " " + PREFIX_TYPE + "w " + PREFIX_INDEX
                + targetIndexWarehouse.getOneBased() + ADDRESS_DESC_AMY;
        descriptorWarehouse = new EditWarehouseDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);
        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);

    }


    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        //supplier
        Index targetIndexSupplier = INDEX_FIRST_SUPPLIER;
        String userInputSupplier = " " + PREFIX_TYPE + "s " + PREFIX_INDEX
                + targetIndexSupplier.getOneBased() + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB;

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        Index targetIndexWarehouse = INDEX_FIRST_WAREHOUSE;
        String userInputWarehouse = " " + PREFIX_TYPE + "w " + PREFIX_INDEX
                + targetIndexWarehouse.getOneBased() + PHONE_DESC_AMY
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
        String userInputSupplier = " " + PREFIX_TYPE + "s " + PREFIX_INDEX
                + targetIndexSupplier.getOneBased() + REMARK_EMPTY;

        EditSupplierDescriptor descriptorSupplier = new EditSupplierDescriptorBuilder()
                .withRemark("").build();
        EditCommand expectedCommandSupplier = new EditCommand(targetIndexSupplier, descriptorSupplier);

        assertParseSuccess(parser, userInputSupplier, expectedCommandSupplier);

        //warehouse
        Index targetIndexWarehouse = INDEX_THIRD_WAREHOUSE;
        String userInputWarehouse = " " + PREFIX_TYPE + "w " + PREFIX_INDEX
                + targetIndexWarehouse.getOneBased() + REMARK_EMPTY;

        EditWarehouseDescriptor descriptorWarehouse = new EditWarehouseDescriptorBuilder()
                .withRemark("").build();
        EditCommand expectedCommandWarehouse = new EditCommand(targetIndexWarehouse, descriptorWarehouse);

        assertParseSuccess(parser, userInputWarehouse, expectedCommandWarehouse);
    }

}
