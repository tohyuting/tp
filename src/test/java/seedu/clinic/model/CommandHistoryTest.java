package seedu.clinic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class CommandHistoryTest {
    private CommandHistoryList commandHistoryList = new CommandHistoryList(Arrays.asList("clear", "view"));
    private ReadOnlyCommandHistory readOnlyCommandHistoryStub = new CommandHistory(commandHistoryList);

    @Test
    public void getCommandHistoryList_success() {
        CommandHistory commandHistory = new CommandHistory(readOnlyCommandHistoryStub);
        assertEquals(commandHistory.getCommandHistoryList(), commandHistoryList);
    }

    @Test
    public void getCommandHistoryAsListOfStrings_success() {
        CommandHistory commandHistory = new CommandHistory(readOnlyCommandHistoryStub);
        assertEquals(commandHistory.getCommandHistoryAsListOfStrings(), Arrays.asList("clear", "view"));
    }

    @Test
    void readPreviousHistory() {
        CommandHistory commandHistory = new CommandHistory(readOnlyCommandHistoryStub);
        String prevCommand = commandHistory.readPreviousHistory();
        assertEquals(prevCommand, "view");

        // empty commandHistoryList
        CommandHistory emptyCommandHistoryList = new CommandHistory(new CommandHistoryList());
        prevCommand = emptyCommandHistoryList.readPreviousHistory();
        assertEquals(prevCommand, "");
    }

    @Test
    void readNextHistory() {
        CommandHistory commandHistory = new CommandHistory(readOnlyCommandHistoryStub);
        String prevCommand = commandHistory.readPreviousHistory();
        String nextCommand = commandHistory.readNextHistory();
        assertEquals(nextCommand, "");

        prevCommand = commandHistory.readPreviousHistory();
        prevCommand = commandHistory.readPreviousHistory();
        nextCommand = commandHistory.readNextHistory();
        assertEquals(nextCommand, "clear");
    }
}
