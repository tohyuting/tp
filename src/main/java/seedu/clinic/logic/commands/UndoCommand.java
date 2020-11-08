package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = "Undo Command Usage\n\nUndo the previous command"
            + " if the command was in effect and edited data\n\n"
            + "Example:\n"
            + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo successfully";
    public static final String MESSAGE_FAILURE = "Cannot undo: no earlier version of CLI-nic to recover!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoClinic()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoClinic();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
