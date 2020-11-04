package seedu.clinic.model;

import java.util.List;

public interface ReadOnlyCommandHistory {
    CommandHistoryList getCommandHistoryList();

    List<String> getCommandHistoryAsListOfStrings();

    String readPreviousHistory();

    String readNextHistory();
}
