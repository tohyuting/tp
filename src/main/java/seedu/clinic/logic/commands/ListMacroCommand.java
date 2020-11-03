package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinic.model.Model;
import seedu.clinic.model.macro.Macro;

public class ListMacroCommand extends Command {
    public static final String COMMAND_WORD = "listmacro";
    public static final String MESSAGE_SUCCESS = "Currently saved Macros:\n%1$s";
    private String listFormatting = "%1$s. ";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        StringBuilder resultStringBuilder = new StringBuilder();
        int counter = 1;
        for (Macro macro : model.getMacroList()) {
            resultStringBuilder.append(String.format(listFormatting, counter)).append(macro).append("\n\n");
            counter++;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, resultStringBuilder.toString()));
    }
}
