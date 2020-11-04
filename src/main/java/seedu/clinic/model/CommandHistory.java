package seedu.clinic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps data from commandHistory.txt in a list.
 */
public class CommandHistory {
    private List<String> commandHistory = new ArrayList<>();
    private int commandHistoryIndex = 0;

    public CommandHistory() {};

    /**
     * Constructs a new CommandHistory wrapper that wraps data read from commandHistory.txt.
     * @param commandHistory takes in a list of command history strings.
     */
    public CommandHistory(List<String> commandHistory) {
        this.commandHistory = commandHistory;
        commandHistoryIndex = commandHistory.size();
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }

    /**
     * Returns the command previously entered by the user.
     */
    public String readPreviousHistory() {
        String prevHistory = "";
        if (commandHistoryIndex > 0) {
            prevHistory = commandHistory.get(commandHistoryIndex - 1);
            commandHistoryIndex = commandHistoryIndex - 1;
        }

        if (commandHistoryIndex == 0) {
            commandHistoryIndex -= 1;
        }

        return prevHistory;
    }

    /**
     * Returns an earlier command that was previously entered by the user.
     */
    public String readNextHistory() {
        String nextHistory = "";

        if (commandHistoryIndex < commandHistory.size() - 1) {
            nextHistory = commandHistory.get(commandHistoryIndex + 1);
            commandHistoryIndex = commandHistoryIndex + 1;
        }

        if (commandHistoryIndex == commandHistory.size() - 1) {
            commandHistoryIndex += 1;
        }

        return nextHistory;
    }

    /**
     * Updates the list of history commands with a new command just entered by the user.
     * Note that this function does not save the command directly to commandHistory.txt file.
     * This function merely updates the CommandHistory model with the latest command.
     * The command just entered by the user will still be saved to commandHistory.txt but it will be handled by a
     * different function and class.
     *
     * @param newCommandHistory takes in the command just entered by the user.
     */
    public void updateHistory(String newCommandHistory) {
        commandHistory.add(newCommandHistory);
        commandHistoryIndex = commandHistory.size();
    }
}
