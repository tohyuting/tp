package seedu.clinic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.clinic.model.ReadOnlyCommandHistory;

public interface CommandHistoryStorage {
    /**
     * Returns the file path of the command history data file.
     */
    Path getCommandHistoryFilePath();

    /**
     * Returns CommandHistory data as a {@link ReadOnlyCommandHistory}.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCommandHistory> readCommandHistory() throws IOException;

    Optional<ReadOnlyCommandHistory> readCommandHistory(Path filePath) throws IOException;

    void saveCommandHistory(String commandHistory, Path filePath) throws IOException;

}
