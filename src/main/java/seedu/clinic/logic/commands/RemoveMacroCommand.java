package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.Macro;

/**
 * Removes a macro identified using it's alias in the UserMacros model.
 */
public class RemoveMacroCommand extends Command {
    public static final String COMMAND_WORD = "removemacro";
    public static final String COMPLETE_REMOVE_MACRO_COMMAND = "removemacro";
    public static final String MESSAGE_USAGE = "Remove Macro Usage\n\nRemoves the macro identified by the"
            + " alias used in the macro list.\n\n"
            + "Parameters:\n"
            + "ALIAS (has to be an existing macro)\n\n"
            + "Example:\n"
            + COMMAND_WORD + " uwp";

    public static final String MESSAGE_REMOVE_MACRO_SUCCESS = "Removed Macro: %1$s.";
    public static final String MESSAGE_MACRO_DOES_NOT_EXIST = "Macro does not exist. You can check which"
            + " macros are presently saved by using the listmacro command.";

    private final Alias targetAlias;

    /**
     * Creates a RemoveMacroCommand to remove the macro with the specified {@code targetAlias}
     */
    public RemoveMacroCommand(Alias targetAlias) {
        requireNonNull(targetAlias);
        this.targetAlias = targetAlias;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Optional<Macro> optionalMacro = model.getMacro(targetAlias);

        if (optionalMacro.isEmpty()) {
            throw new CommandException(MESSAGE_MACRO_DOES_NOT_EXIST);
        }

        Macro macroToRemove = optionalMacro.get();
        model.deleteMacro(macroToRemove);
        return new CommandResult(String.format(MESSAGE_REMOVE_MACRO_SUCCESS, macroToRemove.getAlias()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveMacroCommand // instanceof handles nulls
                && targetAlias.equals(((RemoveMacroCommand) other).targetAlias)); // state check
    }

}
