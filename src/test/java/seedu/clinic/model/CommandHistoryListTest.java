package seedu.clinic.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class CommandHistoryListTest {
    private CommandHistoryList commandHistoryList = new CommandHistoryList(Arrays.asList("clear", "view"));

    @Test
    void readPreviousHistory_success() {
        String prevCommand = commandHistoryList.readPreviousHistory();
        assertEquals(prevCommand, "view");

        // empty commandHistoryList
        CommandHistoryList emptyCommandHistoryList = new CommandHistoryList(Arrays.asList());
        prevCommand = emptyCommandHistoryList.readPreviousHistory();
        assertEquals(prevCommand, "");
    }

    @Test
    void readNextHistory() {
        String prevCommand = commandHistoryList.readPreviousHistory();
        String nextCommand = commandHistoryList.readNextHistory();
        assertEquals(nextCommand, "");

        prevCommand = commandHistoryList.readPreviousHistory();
        prevCommand = commandHistoryList.readPreviousHistory();
        nextCommand = commandHistoryList.readNextHistory();
        assertEquals(nextCommand, "clear");
    }

    @Test
    void updateHistory() {
    }

    @Test
    void testEquals() {
    }
}