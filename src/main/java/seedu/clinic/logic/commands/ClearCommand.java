package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.Model;

/**
 * Clears the clinic.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = "Clears all suppliers and warehouses' entries in"
            + " CLI-nic.\n\n"
            + "Example:\n"
            + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "CLI-nic has been cleared!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getClinic().equals(new Clinic())) {
            throw new CommandException("CLI-nic already empty, nothing to clear");
        }
        model.setClinic(new Clinic());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
