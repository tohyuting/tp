package seedu.clinic.testutil;

import seedu.clinic.model.CommandHistory;

public class TypicalCommandHistory {
    private TypicalCommandHistory() {} // prevents instantiation

    /**
     * Returns a {@code CommandHistory} with some existing command history.
     */
    public static CommandHistory getTypicalCommandHistory() {
        CommandHistory commandHistory = new CommandHistory();
        commandHistory.updateHistory("First command history");
        commandHistory.updateHistory("Second command history");

        return commandHistory;
    }
}
