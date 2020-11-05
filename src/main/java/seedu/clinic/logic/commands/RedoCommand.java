package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = "Redo Command Usage\n\nRedo the previously undone command"
            + " if undo was successfully executed\n\n"
            + "Example:\n"
            + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redo successfully";
    public static final String MESSAGE_FAILURE = "Cannot redo: no earlier undone version to restore!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoClinic()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoClinic();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
