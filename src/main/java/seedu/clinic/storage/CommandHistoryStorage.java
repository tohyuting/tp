package seedu.clinic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.clinic.model.CommandHistory;

public interface CommandHistoryStorage {
    /**
     * Returns the file path of the command history data file.
     */
    Path getCommandHistoryFilePath();

    /**
     * Returns CommandHistory data as a {@link CommandHistory}.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<CommandHistory> readCommandHistory() throws IOException;

    Optional<CommandHistory> readCommandHistory(Path filePath) throws IOException;

    void saveCommandHistory(String commandHistory, Path filePath) throws IOException;

}
