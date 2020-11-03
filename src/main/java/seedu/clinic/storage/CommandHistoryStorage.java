package seedu.clinic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface CommandHistoryStorage {
    /**
     * Returns the file path of the command history data file.
     */
    Path getCommandHistoryFilePath();

    /**
     * Returns CommandHistory data as a {@link String}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws IOException if there was any problem when reading from the storage.
     */
    // Optional<String> readCommandHistory() throws IOException;

    Optional<String> readCommandHistory(Path filePath) throws IOException;


    /**
     * Saves the given {@link String} to the storage.
     * @param commandHistory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    // void saveCommandHistory(String commandHistory) throws IOException;

    void saveCommandHistory(String commandHistory, Path filePath) throws IOException;

}
