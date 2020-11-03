package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;
import seedu.clinic.model.macro.Macro;

public class ListMacroCommand extends Command {
    public static final String COMMAND_WORD = "listmacro";
    public static final String MESSAGE_SUCCESS = "Currently saved Macros:\n%1$s";
    public static final String MESSAGE_EMPTY_LIST = "There are no macros currently saved.";
    private String listFormatting = "%1$s. ";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Macro> macroList = model.getMacroList();

        if (macroList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        StringBuilder resultStringBuilder = new StringBuilder();
        int counter = 1;
        for (Macro macro : macroList) {
            resultStringBuilder.append(String.format(listFormatting, counter)).append(macro).append("\n\n");
            counter++;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, resultStringBuilder.toString()));
    }
}
