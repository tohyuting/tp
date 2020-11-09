package seedu.clinic.logic.commands;

import java.util.Arrays;

import seedu.clinic.model.Model;

/**
 * Displays a list of available commands and their utility descriptions.
 * If user specifies a specific command, the detailed description, input format and sample commands for that particular
 * command will be displayed instead.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = "Help Command Usage\n\nDisplays a list of available commands"
            + " and their utility descriptions\n"
            + "Example:\n\n"
            + "1) " + COMMAND_WORD + "\n"
            + "2) " + COMMAND_WORD + " find";

    public static final String MESSAGE_TOO_MANY_ARGUMENTS = "Only one command can be entered at any time\n\n"
            + MESSAGE_USAGE;

    public static final String[] ALLOWED_ARGUMENTS = new String[] {
        AddCommand.COMMAND_WORD, AssignMacroCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, ListMacroCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
        RemoveMacroCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
        UpdateCommand.COMMAND_WORD, ViewCommand.COMMAND_WORD
    };

    public static final String ALLOWED_KEYWORDS = Arrays.stream(ALLOWED_ARGUMENTS)
            .reduce("", (x, y) -> x.equals("") ? y : x + ", " + y);

    public static final String MESSAGE_WRONG_ARGUMENT = "You can only enter command from one of the "
            + "following keywords:\n"
            + ALLOWED_KEYWORDS + "\n\n%1$s";

    public static final String MESSAGE_FOR_COMMAND_FORMAT = "How to interpret command format?\n"
            + "1) Words in UPPER_CASE are parameters to be supplied by the user.\n"
            + "2) Items in square brackets are optional.\n"
            + "3) Items with ... can be used multiple times.\n"
            + "4) Prefixes specified can be in any order.";

    private final String commandArgument;

    /**
     * Constructs a new HelpCommand object.
     *
     * @param commandArgument takes in the command argument (if any) specified for help command.
     */
    public HelpCommand(String commandArgument) {
        this.commandArgument = commandArgument;
    }

    @Override
    public CommandResult execute(Model model) {
        String helpMessage = "";
        switch(commandArgument) {
        case AddCommand.COMMAND_WORD:
            helpMessage = generateHelpAddMessage();
            break;
        case AssignMacroCommand.COMMAND_WORD:
            helpMessage = generateHelpAssignMacroMessage();
            break;
        case ClearCommand.COMMAND_WORD:
            helpMessage = generateHelpClearMessage();
            break;
        case DeleteCommand.COMMAND_WORD:
            helpMessage = generateHelpDeleteMessage();
            break;
        case EditCommand.COMMAND_WORD:
            helpMessage = generateHelpEditMessage();
            break;
        case ExitCommand.COMMAND_WORD:
            helpMessage = generateHelpExitMessage();
            break;
        case FindCommand.COMMAND_WORD:
            helpMessage = generateHelpFindMessage();
            break;
        case ListCommand.COMMAND_WORD:
            helpMessage = generateHelpListMessage();
            break;
        case ListMacroCommand.COMMAND_WORD:
            helpMessage = generateHelpListMacroMessage();
            break;
        case RemoveMacroCommand.COMMAND_WORD:
            helpMessage = generateHelpRemoveMacroMessage();
            break;
        case UpdateCommand.COMMAND_WORD:
            helpMessage = generateHelpUpdateMessage();
            break;
        case ViewCommand.COMMAND_WORD:
            helpMessage = generateHelpViewMessage();
            break;
        case UndoCommand.COMMAND_WORD:
            helpMessage = generateHelpUndoMessage();
            break;
        case RedoCommand.COMMAND_WORD:
            helpMessage = generateHelpRedoMessage();
            break;
        default:
            assert commandArgument.equals("all") : "commandArgument should be all here!";
            helpMessage = generateHelpGenericMessage();
        }
        return new CommandResult(helpMessage, false, false);
    }

    private String generateHelpGenericMessage() {
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
        return genericHelpMessage;
    }

    private String generateHelpAddMessage() {
        return MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + AddCommand.MESSAGE_USAGE;
    }

    private String generateHelpAssignMacroMessage() {
        return MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + AssignMacroCommand.MESSAGE_USAGE;
    }

    private String generateHelpClearMessage() {
        return ClearCommand.MESSAGE_USAGE;
    }

    private String generateHelpDeleteMessage() {
        return MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + DeleteCommand.MESSAGE_USAGE;
    }

    private String generateHelpEditMessage() {
        return MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + EditCommand.MESSAGE_USAGE;
    }

    private String generateHelpExitMessage() {
        return ExitCommand.MESSAGE_USAGE;
    }

    private String generateHelpFindMessage() {
        return MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + FindCommand.MESSAGE_USAGE;
    }

    private String generateHelpListMessage() {
        return ListCommand.MESSAGE_USAGE;
    }

    private String generateHelpListMacroMessage() {
        return ListMacroCommand.MESSAGE_USAGE;
    }

    private String generateHelpRedoMessage() {
        return MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + RedoCommand.MESSAGE_USAGE;
    }

    private String generateHelpRemoveMacroMessage() {
        return MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + RemoveMacroCommand.MESSAGE_USAGE;
    }

    private String generateHelpUndoMessage() {
        return MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + UndoCommand.MESSAGE_USAGE;
    }

    private String generateHelpUpdateMessage() {
        return MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + UpdateCommand.MESSAGE_USAGE;
    }

    private String generateHelpViewMessage() {
        return MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + ViewCommand.MESSAGE_USAGE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand // instanceof handles nulls
                && commandArgument.equals(((HelpCommand) other).commandArgument)); // state check
    }
}
