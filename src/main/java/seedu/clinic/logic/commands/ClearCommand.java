package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.Model;

/**
 * Clears the CLI-nic app.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = "Clear Command Usage\n\nClears all suppliers and warehouses'"
            + " entries in";
    public static final String MESSAGE_SUCCESS = "CLI-nic has been cleared!";
    public static final String MESSAGE_EMPTY_CLINIC = "The CLI-nic is already empty, not data to clear";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getClinic().equals(new Clinic())) {
            throw new CommandException(MESSAGE_EMPTY_CLINIC);
        }
        model.setClinic(new Clinic());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
