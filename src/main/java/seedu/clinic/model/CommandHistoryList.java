package seedu.clinic.model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinic.storage.TextFileCommandHistoryStorage;

public class CommandHistoryList {
    private final ObservableList<String> internalList = FXCollections.observableArrayList();
    private int commandHistoryIndex = internalList.size();

    public CommandHistoryList() {}

    /**
     * Stores the list of command history strings into a new ObservableList.
     */
    public CommandHistoryList(List<String> commandHistoryList) {
        commandHistoryList.forEach(x -> internalList.add(x));
        commandHistoryIndex = internalList.size();
    }

    public List<String> getCommandHistoryAsListOfStrings() {
        return internalList;
    }

    /**
     * Returns an earlier command previously entered by the user.
     */
    public String readPreviousHistory() {
        String prevHistory = "";
        if (commandHistoryIndex > 0) {
            prevHistory = internalList.get(commandHistoryIndex - 1);
            commandHistoryIndex = commandHistoryIndex - 1;
        }

        if (commandHistoryIndex == 0) {
            commandHistoryIndex -= 1;
        }

        return prevHistory;
    }

    /**
     * Returns a later command that was previously entered by the user.
     */
    public String readNextHistory() {
        String nextHistory = "";

        if (commandHistoryIndex < internalList.size() - 1) {
            nextHistory = internalList.get(commandHistoryIndex + 1);
            commandHistoryIndex = commandHistoryIndex + 1;
        }

        if (commandHistoryIndex == internalList.size() - 1) {
            commandHistoryIndex += 1;
        }

        return nextHistory;
    }

    /**
     * Updates the observable list of history commands with a new command just entered by the user.
     * Note that this function does not save the command directly to commandHistory.txt file.
     * This function merely updates the ObservableList stored in the CommandHistory model with the latest command.
     * The command just entered by the user will still be saved to commandHistory.txt but it will be handled by a
     * different function and class. Refer to {@link TextFileCommandHistoryStorage} for more details.
     *
     * @param newCommandHistory takes in the command just entered by the user.
     */
    public void updateHistory(String newCommandHistory) {
        internalList.add(newCommandHistory);
        commandHistoryIndex = internalList.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommandHistoryList // instanceof handles nulls
                && internalList.equals(((CommandHistoryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
