package seedu.clinic.testutil;

import seedu.clinic.model.CommandHistory;

public class TypicalCommandHistory {
    private TypicalCommandHistory() {} // prevents instantiation

    /**
     * Returns a {@code CommandHistory} with some existing command history.
     */
    public static CommandHistory getTypicalCommandHistory() {
        CommandHistory commandHistory = new CommandHistory();
        commandHistory.getCommandHistoryList().updateHistory("First command history");
        commandHistory.getCommandHistoryList().updateHistory("Second command history");

        return commandHistory;
    }
}
