package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the previous command"
            + " if the command edited data\n\n"
            + " The list of undoable commands include "
            + "Example:\n"
            + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo successfully";
    public static final String MESSAGE_FAILURE = "Cannot undo: no earlier version of Clinic to recover!";

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
