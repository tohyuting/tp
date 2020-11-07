package seedu.clinic.logic.commands;

import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.logic.commands.HelpCommand.MESSAGE_FOR_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_genericHelp_success() {
        String aboutHelpCommand = "You can view the description, input format and sample commands for a particular"
                + " command using\n"
                + "help COMMAND.\n\n"
                + "Example:\n"
                + "help find\n\n"
                + "Alternatively, you may visit the User Guide on our website for more details."
                + " The URL can be accessed by pressing F1.";

        String aboutAddCommand = AddCommand.COMMAND_WORD + "\nAdds a supplier or warehouse to CLI-nic";
        String aboutAssignMacroCommand = AssignMacroCommand.COMMAND_WORD + "\nAssigns a macro to a command string";
        String aboutClearCommand = ClearCommand.COMMAND_WORD + "\nClears all suppliers and warehouses' entries in"
                + " CLI-nic";
        String aboutDeleteCommand = DeleteCommand.COMMAND_WORD + "\nDeletes a supplier, warehouse or a product"
                + " associated with the supplier/warehouse that is no longer needed";
        String aboutEditCommand = EditCommand.COMMAND_WORD + "\nEdits a specific supplier or warehouse";
        String aboutExitCommand = ExitCommand.COMMAND_WORD + "\nExits the application";
        String aboutFindCommand = FindCommand.COMMAND_WORD + "\nFinds relevant supplier(s) or warehouse(s)";
        String aboutListCommand = ListCommand.COMMAND_WORD + "\nLists all suppliers and warehouses";
        String aboutListMacroCommand = ListMacroCommand.COMMAND_WORD + "\nLists all presently saved macros";
        String aboutRemoveMacroCommand = RemoveMacroCommand.COMMAND_WORD + "\nRemoves the macro for an alias";
        String aboutUpdateCommand = UpdateCommand.COMMAND_WORD + "\nUpdates a product associated with a supplier or"
                + " warehouse";
        String aboutViewCommand = ViewCommand.COMMAND_WORD + "\nViews the in-depth information associated with a"
                + " specific supplier or warehouse";
        String aboutUndoCommand = UndoCommand.COMMAND_WORD + "\nUndo the previous editing on the data of CLI-nic";
        String aboutRedoCommand = RedoCommand.COMMAND_WORD + "\nRecovers the previous state of CLI-nic before"
                + " the undo command on the data of CLI-nic";

        String genericHelpMessage = aboutHelpCommand + "\n\n"
                + aboutAddCommand + "\n\n"
                + aboutAssignMacroCommand + "\n\n"
                + aboutClearCommand + "\n\n"
                + aboutDeleteCommand + "\n\n"
                + aboutEditCommand + "\n\n"
                + aboutExitCommand + "\n\n"
                + aboutFindCommand + "\n\n"
                + aboutListCommand + "\n\n"
                + aboutListMacroCommand + "\n\n"
                + aboutRedoCommand + "\n\n"
                + aboutRemoveMacroCommand + "\n\n"
                + aboutUndoCommand + "\n\n"
                + aboutUpdateCommand + "\n\n"
                + aboutViewCommand + "\n\n";
        CommandResult expectedCommandResult = new CommandResult(genericHelpMessage,
                false, false);
        assertCommandSuccess(new HelpCommand("all"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_addHelp_success() {
        String addHelpMessage = MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + AddCommand.MESSAGE_USAGE;

        CommandResult expectedCommandResult = new CommandResult(addHelpMessage, false, false);
        assertCommandSuccess(new HelpCommand("add"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_clearHelp_success() {
        CommandResult expectedCommandResult = new CommandResult(ClearCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand("clear"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_deleteHelp_success() {
        String deleteHelpMessage = MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + DeleteCommand.MESSAGE_USAGE;
        CommandResult expectedCommandResult = new CommandResult(deleteHelpMessage, false, false);
        assertCommandSuccess(new HelpCommand("delete"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_editHelp_success() {
        String editHelpMessage = MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + EditCommand.MESSAGE_USAGE;
        CommandResult expectedCommandResult = new CommandResult(editHelpMessage, false, false);
        assertCommandSuccess(new HelpCommand("edit"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_exitHelp_success() {
        CommandResult expectedCommandResult = new CommandResult(ExitCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand("exit"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_findHelp_success() {
        String findHelpMessage = MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + FindCommand.MESSAGE_USAGE;
        CommandResult expectedCommandResult = new CommandResult(findHelpMessage, false, false);
        assertCommandSuccess(new HelpCommand("find"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listHelp_success() {
        CommandResult expectedCommandResult = new CommandResult(ListCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand("list"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listMacroHelp_success() {
        CommandResult expectedCommandResult = new CommandResult(ListMacroCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand("listmacro"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_updateHelp_success() {
        String updateCommandHelpMessage = MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + UpdateCommand.MESSAGE_USAGE;
        CommandResult expectedCommandResult = new CommandResult(updateCommandHelpMessage,
                false, false);
        assertCommandSuccess(new HelpCommand("update"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_assignMacroHelp_success() {
        String assignMacroCommandHelpMessage = MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + AssignMacroCommand.MESSAGE_USAGE;
        CommandResult expectedCommandResult = new CommandResult(assignMacroCommandHelpMessage,
                false, false);
        assertCommandSuccess(new HelpCommand("assignmacro"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_removeMacroHelp_success() {
        String removeMacroCommandHelpMessage = MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + RemoveMacroCommand.MESSAGE_USAGE;
        CommandResult expectedCommandResult = new CommandResult(removeMacroCommandHelpMessage,
                false, false);
        assertCommandSuccess(new HelpCommand("removemacro"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_viewHelp_success() {
        String viewCommandHelpMessage = MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + ViewCommand.MESSAGE_USAGE;
        CommandResult expectedCommandResult = new CommandResult(viewCommandHelpMessage, false, false);
        assertCommandSuccess(new HelpCommand("view"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_undoHelp_success() {
        String undoCommandHelpMessage = MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + UndoCommand.MESSAGE_USAGE;
        CommandResult expectedCommandResult = new CommandResult(undoCommandHelpMessage, false, false);
        assertCommandSuccess(new HelpCommand("undo"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_redoHelp_success() {
        String redoCommandHelpMessage = MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + RedoCommand.MESSAGE_USAGE;
        CommandResult expectedCommandResult = new CommandResult(redoCommandHelpMessage, false, false);
        assertCommandSuccess(new HelpCommand("redo"), model,
                expectedCommandResult, expectedModel);
    }
}
