package seedu.clinic.model;

import java.util.List;

/**
 * Wraps data from commandHistory.txt in a {@code CommandHistoryList}.
 */
public class CommandHistory implements ReadOnlyCommandHistory {
    private CommandHistoryList commandHistoryList = new CommandHistoryList();

    public CommandHistory() {}

    public CommandHistory(ReadOnlyCommandHistory readOnlyCommandHistory) {
        commandHistoryList = new CommandHistoryList(readOnlyCommandHistory.getCommandHistoryAsListOfStrings());
    }

    /**
     * Constructs a new CommandHistory wrapper that wraps data read from commandHistory.txt.
     * @param commandHistoryList takes in a list of command history.
     */
    public CommandHistory(CommandHistoryList commandHistoryList) {
        this.commandHistoryList = commandHistoryList;
    }

    @Override
    public List<String> getCommandHistoryAsListOfStrings() {
        return commandHistoryList.getCommandHistoryAsListOfStrings();
    }

    @Override
    public CommandHistoryList getCommandHistoryList() {
        return commandHistoryList;
    }

    public String readPreviousHistory() {
        return commandHistoryList.readPreviousHistory();
    }

    @Override
    public String readNextHistory() {
        return commandHistoryList.readNextHistory();
    }
}
