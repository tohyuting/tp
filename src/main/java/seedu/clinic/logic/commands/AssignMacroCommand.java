package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_COMMAND_STRING;

import java.util.Objects;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;
import seedu.clinic.model.macro.Macro;

/**
 * Assigns a toAssign to a command string in the CLI-nic app.
 */
public class AssignMacroCommand extends Command {

    public static final String COMMAND_WORD = "assignmacro";
    public static final String COMPLETE_ASSIGN_MACRO_COMMAND = "assignmacro a/ cs/";

    public static final String MESSAGE_USAGE = "Assign Macro Usage\n\n"
            + "Assigns a macro to a command string. ALIAS should only consist "
            + "of alphanumeric characters and/or underscores. ALIAS cannot be an existing command word and cannot "
            + "be used in an existing macro. COMMAND_STRING can consist of any number of prefixes, but the first "
            + "word has to be a pre-defined command word. All arguments are case sensitive.\n\n"
            + "Parameters:\n"
            + PREFIX_ALIAS + "ALIAS "
            + PREFIX_COMMAND_STRING + "COMMAND_STRING\n\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_ALIAS + "uwp "
            + PREFIX_COMMAND_STRING + "update ct/w pd/Panadol";

    public static final String MESSAGE_SUCCESS = "Macro assigned: %1$s";
    public static final String MESSAGE_DUPLICATE_MACRO = "This macro already exists in the CLI-nic app.";

    private final Macro toAssign;

    /**
     * Creates an AssignMacroCommand to assign the specified {@code Macro}
     */
    public AssignMacroCommand(Macro macro) {
        requireNonNull(macro);
        this.toAssign = macro;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMacro(toAssign)) {
            throw new CommandException(MESSAGE_DUPLICATE_MACRO);
        }

        model.addMacro(toAssign);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAssign));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignMacroCommand // instanceof handles nulls
                && toAssign.equals(((AssignMacroCommand) other).toAssign));
    }

    @Override
    public int hashCode() {
        return Objects.hash(toAssign);
    }

}
