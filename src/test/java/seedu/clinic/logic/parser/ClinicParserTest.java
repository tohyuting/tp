package seedu.clinic.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_PRODUCT_A;
import static seedu.clinic.logic.commands.CommandTestUtil.INDEX_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_NAME_DESC_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.PRODUCT_QUANTITY_DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.TAG_DESC_FEVER;
import static seedu.clinic.logic.commands.CommandTestUtil.TYPE_DESC_SUPPLIER;
import static seedu.clinic.logic.commands.CommandTestUtil.TYPE_DESC_SUPPLIER_PRODUCT;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_PANADOL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_COMMAND_STRING;
import static seedu.clinic.logic.parser.Type.SUPPLIER;
import static seedu.clinic.logic.parser.Type.SUPPLIER_PRODUCT;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.AddCommand;
import seedu.clinic.logic.commands.AssignMacroCommand;
import seedu.clinic.logic.commands.ClearCommand;
import seedu.clinic.logic.commands.DeleteCommand;
import seedu.clinic.logic.commands.EditCommand;
import seedu.clinic.logic.commands.ExitCommand;
import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.logic.commands.HelpCommand;
import seedu.clinic.logic.commands.ListCommand;
import seedu.clinic.logic.commands.ListMacroCommand;
import seedu.clinic.logic.commands.RedoCommand;
import seedu.clinic.logic.commands.RemoveMacroCommand;
import seedu.clinic.logic.commands.UndoCommand;
import seedu.clinic.logic.commands.UpdateCommand;
import seedu.clinic.logic.commands.ViewCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.supplier.SupplierPredicate;
import seedu.clinic.model.warehouse.WarehousePredicate;
import seedu.clinic.testutil.EditSupplierDescriptorBuilder;
import seedu.clinic.testutil.MacroBuilder;
import seedu.clinic.testutil.ProductBuilderSupplier;
import seedu.clinic.testutil.SupplierBuilder;
import seedu.clinic.testutil.SupplierUtil;

public class ClinicParserTest {

    private static final Name VALID_NAME_DESC = new Name(VALID_PRODUCT_NAME_PANADOL);
    private final ClinicParser parser = new ClinicParser();


    @Test
    public void parseCommand_add() throws Exception {
        Supplier supplier = new SupplierBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(SupplierUtil.getAddCommand(supplier));
        assertEquals(new AddCommand(supplier), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + TYPE_DESC_SUPPLIER + INDEX_DESC_A);
        assertEquals(new DeleteCommand(SUPPLIER, INDEX_FIRST_SUPPLIER), command);

        command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + TYPE_DESC_SUPPLIER_PRODUCT
                        + INDEX_DESC_A + PRODUCT_NAME_DESC_BOB);
        assertEquals(new DeleteCommand(SUPPLIER_PRODUCT, INDEX_FIRST_SUPPLIER, VALID_NAME_DESC), command);
    }


    @Test
    public void parseCommand_edit() throws Exception {
        Supplier supplier = new SupplierBuilder().build();
        EditCommand.EditSupplierDescriptor descriptor = new EditSupplierDescriptorBuilder(supplier).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD
                + TYPE_DESC_SUPPLIER
                + INDEX_DESC_A + " "
                + SupplierUtil.getEditSupplierDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_SUPPLIER, descriptor), command);
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(ViewCommand.COMMAND_WORD
                + TYPE_DESC_SUPPLIER + INDEX_DESC_A);
        assertEquals(new ViewCommand(SUPPLIER, INDEX_FIRST_SUPPLIER), command);
    }

    @Test
    public void parseCommand_assignMacro() throws Exception {
        Macro macro = new MacroBuilder().withAlias("alpha").build();
        Alias alias = new Alias("alpha");
        AssignMacroCommand command = (AssignMacroCommand) parser.parseCommand(AssignMacroCommand.COMMAND_WORD
                + " " + PREFIX_ALIAS + "alpha " + PREFIX_COMMAND_STRING + "update");
        assertEquals(new AssignMacroCommand(macro), command);
    }

    @Test
    public void parseCommand_removeMacro() throws Exception {
        Alias alias = new Alias("alpha");
        RemoveMacroCommand command = (RemoveMacroCommand) parser.parseCommand(RemoveMacroCommand.COMMAND_WORD
                + " alpha ");
        assertEquals(new RemoveMacroCommand(alias), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 3") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_findSuppliers() throws Exception {
        List<String> nameKeywords = Arrays.asList("alex", "bernice");
        List<String> productKeywords = Arrays.asList("panadol");
        List<String> remarkKeywords = Arrays.asList("cheap", "fast");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " ct/s n/alex bernice pd/panadol r/cheap fast");
        assertEquals(new FindCommand(new SupplierPredicate(nameKeywords, productKeywords, remarkKeywords)), command);
    }

    @Test
    public void parseCommand_findWarehouses() throws Exception {
        List<String> nameKeywords = Arrays.asList("alex", "bernice");
        List<String> productKeywords = Arrays.asList("panadol");
        List<String> remarkKeywords = Arrays.asList("biggest");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " ct/w n/alex bernice pd/panadol r/biggest");
        assertEquals(new FindCommand(new WarehousePredicate(nameKeywords, productKeywords, remarkKeywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listMacro() throws Exception {
        assertTrue(parser.parseCommand(ListMacroCommand.COMMAND_WORD) instanceof ListMacroCommand);
        assertTrue(parser.parseCommand(ListMacroCommand.COMMAND_WORD + " 3") instanceof ListMacroCommand);
    }

    @Test
    public void parseCommand_update() throws Exception {
        Product product = new ProductBuilderSupplier().withName(VALID_PRODUCT_NAME_PANADOL).build();
        UpdateCommand.UpdateProductDescriptor descriptor = DESC_PRODUCT_A;
        UpdateCommand command = (UpdateCommand) parser.parseCommand(UpdateCommand.COMMAND_WORD
                + TYPE_DESC_SUPPLIER
                + INDEX_DESC_A + " "
                + PRODUCT_NAME_DESC_B
                + PRODUCT_QUANTITY_DESC_A
                + TAG_DESC_FEVER);
        assertEquals(new UpdateCommand(Type.SUPPLIER, INDEX_FIRST_SUPPLIER, product.getProductName(), descriptor),
                command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
