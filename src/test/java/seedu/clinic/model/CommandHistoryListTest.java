package seedu.clinic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void updateHistory_success() {
        String commandString = "clear";
        commandHistoryList.updateHistory(commandString);
        CommandHistoryList updatedCommandHistoryList = new CommandHistoryList(Arrays.asList("clear", "view", "clear"));
        assertEquals(updatedCommandHistoryList, commandHistoryList);
    }

    @Test
    void testEquals() {
        CommandHistoryList updatedCommandHistoryList = new CommandHistoryList(Arrays.asList("clear", "view", "clear"));
        // null - false
        assertFalse(commandHistoryList.equals(null));

        // different type - false
        assertFalse(commandHistoryList.equals(1));

        // different content - false
        assertFalse(commandHistoryList.equals(updatedCommandHistoryList));

        // same object - true
        assertTrue(commandHistoryList.equals(commandHistoryList));

        // same content - true
        CommandHistoryList commandHistoryListCopy = new CommandHistoryList(Arrays.asList("clear", "view"));
        assertTrue(commandHistoryList.equals(commandHistoryListCopy));
    }
}
