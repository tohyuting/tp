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

    public CommandHistory(List<String> commandHistory) {
        this.commandHistory = commandHistory;
        commandHistoryIndex = commandHistory.size();
    }

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

    public void updateHistory(String newCommandHistory) {
        commandHistory.add(newCommandHistory);
        commandHistoryIndex = commandHistory.size();
    }
}
