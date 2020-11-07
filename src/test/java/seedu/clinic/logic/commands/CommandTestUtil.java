package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_QUANTITY;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_WAREHOUSE_NAME;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.EditCommand.EditSupplierDescriptor;
import seedu.clinic.logic.commands.EditCommand.EditWarehouseDescriptor;
import seedu.clinic.logic.commands.UpdateCommand.UpdateProductDescriptor;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.Type;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.Model;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForSupplier;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForWarehouse;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;
import seedu.clinic.testutil.EditSupplierDescriptorBuilder;
import seedu.clinic.testutil.EditWarehouseDescriptorBuilder;
import seedu.clinic.testutil.UpdateProductDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    // Warehouse test samples
    public static final String VALID_WAREHOUSE_NAME_A = "Warehouse A";
    public static final String VALID_WAREHOUSE_NAME_B = "Warehouse B";
    public static final String VALID_WAREHOUSE_PHONE_A = "11111111";
    public static final String VALID_WAREHOUSE_PHONE_B = "22222222";
    public static final String VALID_WAREHOUSE_ADDRESS_A = "Block 312, Amy Street 1";
    public static final String VALID_WAREHOUSE_ADDRESS_B = "Block 123, Bobby Street 3";
    public static final String VALID_WAREHOUSE_REMARK_A = "Big";
    public static final String VALID_WAREHOUSE_REMARK_B = "Small";
    public static final String VALID_WAREHOUSE_PRODUCT_NAME_A = "Panadol";
    public static final String VALID_WAREHOUSE_PRODUCT_NAME_B = "Aspirin";
    public static final int VALID_WAREHOUSE_PRODUCT_QUANTITY_A = 10;
    public static final int VALID_WAREHOUSE_PRODUCT_QUANTITY_B = 20;
    public static final int VALID_WAREHOUSE_PRODUCT_QUANTITY_50 = 50;
    public static final String VALID_WAREHOUSE_PRODUCT_TAG_FEVER = "fever";
    public static final String VALID_WAREHOUSE_PRODUCT_TAG_HEADACHE = "headache";

    // Product test samples
    public static final String VALID_PRODUCT_NAME_PANADOL = "Panadol";
    public static final String VALID_PRODUCT_NAME_ASPIRIN = "Aspirin";
    public static final int VALID_PRODUCT_QUANTITY_A = 90;
    public static final int VALID_PRODUCT_QUANTITY_B = 600;

    // general test samples
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_GEORGE = "George Best Ltd";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_GEORGE = "9482442";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_GEORGE = "anna@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_FEVER = "fever";
    public static final String VALID_TAG_ANTIBIOTICS = "antibiotics";
    public static final String VALID_TAG_PAINKILLER = "painkiller";
    public static final String VALID_TYPE_SUPPLIER = "s";
    public static final String VALID_TYPE_WAREHOUSE = "w";
    public static final String VALID_REMARK_AMY = "Sells a diverse range of products";
    public static final String VALID_REMARK_BOB = "Long term partner";
    public static final String VALID_REMARK_GEORGE = "frequent discount";

    public static final String NAME_DESC_AMY = " " + PREFIX_SUPPLIER_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_AMY2 = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_SUPPLIER_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_BOB2 = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_WAREHOUSE_A = " " + PREFIX_WAREHOUSE_NAME + VALID_WAREHOUSE_NAME_A;
    public static final String NAME_DESC_WAREHOUSE_B = " " + PREFIX_WAREHOUSE_NAME + VALID_WAREHOUSE_NAME_B;
    public static final String NAME_DESC_WAREHOUSE_A2 = " " + PREFIX_NAME + VALID_WAREHOUSE_NAME_A;
    public static final String NAME_DESC_WAREHOUSE_B2 = " " + PREFIX_NAME + VALID_WAREHOUSE_NAME_B;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String PHONE_DESC_WAREHOUSE_A = " " + PREFIX_PHONE + VALID_WAREHOUSE_PHONE_A;
    public static final String PHONE_DESC_WAREHOUSE_B = " " + PREFIX_PHONE + VALID_WAREHOUSE_PHONE_B;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    // Todo remove address for suppliers
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ADDRESS_DESC_WAREHOUSE_A = " " + PREFIX_ADDRESS + VALID_WAREHOUSE_ADDRESS_A;
    public static final String ADDRESS_DESC_WAREHOUSE_B = " " + PREFIX_ADDRESS + VALID_WAREHOUSE_ADDRESS_B;
    public static final String PRODUCT_NAME_DESC_AMY = " " + PREFIX_PRODUCT_NAME + VALID_PRODUCT_NAME_ASPIRIN;
    public static final String PRODUCT_NAME_DESC_BOB = " " + PREFIX_PRODUCT_NAME + VALID_PRODUCT_NAME_PANADOL;
    public static final String TAG_DESC_FEVER = " " + PREFIX_TAG + VALID_TAG_FEVER;
    public static final String TAG_DESC_ANTIBIOTICS = " " + PREFIX_TAG + VALID_TAG_ANTIBIOTICS;
    public static final String TAG_DESC_ANTIBIOTICS_FEVER = " " + PREFIX_TAG + VALID_TAG_ANTIBIOTICS + VALID_TAG_FEVER;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String REMARK_DESC_WAREHOUSE_A = " " + PREFIX_REMARK + VALID_WAREHOUSE_REMARK_A;
    public static final String REMARK_DESC_WAREHOUSE_B = " " + PREFIX_REMARK + VALID_WAREHOUSE_REMARK_B;
    public static final String TYPE_DESC_SUPPLIER = " " + PREFIX_TYPE + VALID_TYPE_SUPPLIER;
    public static final String TYPE_DESC_WAREHOUSE = " " + PREFIX_TYPE + VALID_TYPE_WAREHOUSE;
    public static final String TYPE_DESC_SUPPLIER_PRODUCT = " " + PREFIX_TYPE + Type.SUPPLIER_PRODUCT;
    public static final String TYPE_DESC_WAREHOUSE_PRODUCT = " " + PREFIX_TYPE + Type.WAREHOUSE_PRODUCT;
    public static final String INDEX_DESC_A = " " + PREFIX_INDEX + INDEX_FIRST_SUPPLIER.getOneBased();
    public static final String INDEX_DESC_B = " " + PREFIX_INDEX + INDEX_SECOND_SUPPLIER.getOneBased();

    public static final String WAREHOUSE_NAME_DESC_C = " " + PREFIX_NAME + VALID_WAREHOUSE_NAME_A;
    public static final String SUPPLIER_NAME_DESC_C = " " + PREFIX_NAME + VALID_NAME_BOB;
    // Todo, keeping for backward compatibility. Standardize naming convention as above
    public static final String WAREHOUSE_NAME_DESC_A = " " + PREFIX_WAREHOUSE_NAME + VALID_WAREHOUSE_NAME_A;
    public static final String WAREHOUSE_NAME_DESC_A2 = " " + PREFIX_NAME + VALID_WAREHOUSE_NAME_A;
    public static final String WAREHOUSE_NAME_DESC_B = " " + PREFIX_WAREHOUSE_NAME + VALID_WAREHOUSE_NAME_B;
    public static final String PRODUCT_NAME_DESC_A = " " + PREFIX_PRODUCT_NAME + VALID_PRODUCT_NAME_ASPIRIN;
    public static final String PRODUCT_NAME_DESC_B = " " + PREFIX_PRODUCT_NAME + VALID_PRODUCT_NAME_PANADOL;
    public static final String PRODUCT_QUANTITY_DESC_A = " " + PREFIX_PRODUCT_QUANTITY
            + VALID_WAREHOUSE_PRODUCT_QUANTITY_A;
    public static final String PRODUCT_QUANTITY_DESC_B = " " + PREFIX_PRODUCT_QUANTITY
            + VALID_WAREHOUSE_PRODUCT_QUANTITY_B;

    // invalid test samples
    public static final String INVALID_TYPE_DESC = " " + PREFIX_TYPE + "pp"; // Type can only be one of 's',
    // 'w', 'ps', 'pw'
    public static final String INVALID_NAME_DESC = " " + PREFIX_SUPPLIER_NAME + "&James"; // names cannot
    // start with '&'
    public static final String INVALID_NAME_DESC2 = " " + PREFIX_NAME + "&James"; // names cannot
    // start with '&'
    public static final String INVALID_NAME_DESC_WAREHOUSE = " " + PREFIX_WAREHOUSE_NAME + "&John";
    public static final String INVALID_NAME_DESC_WAREHOUSE2 = " " + PREFIX_NAME + "&John";
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK + "iiiiiiiiiiiiiiiiiiiiiiii"
            + "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"; // more than
    // 100 characters not allowed for remarks
    public static final String INVALID_SUPPLIER_NAME_DESC = " " + PREFIX_SUPPLIER_NAME
            + "&Amy"; // names cannot start with '&'
    public static final String INVALID_WAREHOUSE_NAME_DESC = " " + PREFIX_WAREHOUSE_NAME
            + "&Amy"; // names cannot start with '&'
    public static final String INVALID_ENTITY_NAME_DESC = " " + PREFIX_NAME
            + "&Amy"; // names cannot start with '&'
    public static final String INVALID_PRODUCT_NAME_DESC = " " + PREFIX_PRODUCT_NAME
            + "&Amy"; // names cannot start with '&'
    public static final String INVALID_WAREHOUSE_ADDRESS_DESC = " "
            + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_PRODUCT_QUANTITY_DESC = " " + PREFIX_PRODUCT_QUANTITY
            + "+20"; // Signed integers are not allowed
    public static final String INVALID_INDEX_DESC = " " + PREFIX_INDEX
            + "+20"; // Signed integers are not allowed


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditSupplierDescriptor DESC_AMY;
    public static final EditSupplierDescriptor DESC_BOB;

    public static final EditWarehouseDescriptor DESC_A;
    public static final EditWarehouseDescriptor DESC_B;

    public static final UpdateProductDescriptor DESC_PRODUCT_A;
    public static final UpdateProductDescriptor DESC_PRODUCT_B;
    public static final UpdateProductDescriptor DESC_PRODUCT_C;


    static {
        DESC_AMY = new EditSupplierDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withRemark(VALID_REMARK_AMY)
                .withProducts(Map.of(VALID_PRODUCT_NAME_PANADOL, new String[]{VALID_TAG_FEVER})).build();
        DESC_BOB = new EditSupplierDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withRemark(VALID_REMARK_BOB)
                .withProducts(Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS})).build();
        DESC_A = new EditWarehouseDescriptorBuilder().withName(VALID_WAREHOUSE_NAME_A)
                .withPhone(VALID_WAREHOUSE_PHONE_A).withAddress(VALID_WAREHOUSE_ADDRESS_A)
                .withRemark(VALID_WAREHOUSE_REMARK_A)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_WAREHOUSE_PRODUCT_QUANTITY_A)).build();
        DESC_B = new EditWarehouseDescriptorBuilder().withName(VALID_WAREHOUSE_NAME_B)
                .withPhone(VALID_WAREHOUSE_PHONE_B).withAddress(VALID_WAREHOUSE_ADDRESS_B)
                .withRemark(VALID_WAREHOUSE_REMARK_B)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_B, VALID_WAREHOUSE_PRODUCT_QUANTITY_A)).build();
        DESC_PRODUCT_A = new UpdateProductDescriptorBuilder().withQuantity(VALID_WAREHOUSE_PRODUCT_QUANTITY_A)
                .withTags(VALID_TAG_FEVER).build();
        DESC_PRODUCT_B = new UpdateProductDescriptorBuilder().withQuantity(VALID_PRODUCT_QUANTITY_B).build();
        DESC_PRODUCT_C = new UpdateProductDescriptorBuilder().withTags(VALID_TAG_ANTIBIOTICS).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the clinic, filtered supplier list and selected supplier,
     * - filtered warehouse list and selected warehouse in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Clinic expectedClinic = new Clinic(actualModel.getClinic());
        List<Supplier> expectedFilteredSupplierList = new ArrayList<>(actualModel.getFilteredSupplierList());
        List<Warehouse> expectedFilteredWarehouseList = new ArrayList<>(actualModel.getFilteredWarehouseList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedClinic, actualModel.getClinic());
        assertEquals(expectedFilteredSupplierList, actualModel.getFilteredSupplierList());
        assertEquals(expectedFilteredWarehouseList, actualModel.getFilteredWarehouseList());
    }

    /**
     * Updates {@code model}'s filtered supplier list to show only the supplier at the given {@code targetIndex} in the
     * {@code model}'s clinic.
     */
    public static void showSupplierAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredSupplierList().size());

        Supplier supplier = model.getFilteredSupplierList().get(targetIndex.getZeroBased());
        model.updateFilteredSupplierList(new NameContainsKeywordsPredicateForSupplier(
                supplier.getName().fullName));

        assertEquals(1, model.getFilteredSupplierList().size());
    }

    /**
     * Updates {@code model}'s filtered warehouse list to show only the warehouse at
     * the given {@code targetIndex} in the {@code model}'s clinic.
     */
    public static void showWarehouseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredWarehouseList().size());
        Warehouse warehouse = model.getFilteredWarehouseList().get(targetIndex.getZeroBased());
        model.updateFilteredWarehouseList(new NameContainsKeywordsPredicateForWarehouse(
                warehouse.getName().fullName));
        assertEquals(1, model.getFilteredWarehouseList().size());
    }
}
