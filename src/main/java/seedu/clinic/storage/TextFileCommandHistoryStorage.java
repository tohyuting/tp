package seedu.clinic.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.commons.util.FileUtil;
import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.CommandHistoryList;
import seedu.clinic.model.ReadOnlyCommandHistory;

/**
 * A class to access command history data stored as a txt file on the hard disk.
 */
public class TextFileCommandHistoryStorage implements CommandHistoryStorage {
    private static final Logger logger = LogsCenter.getLogger(TextFileCommandHistoryStorage.class);

    private Path filePath;

    public TextFileCommandHistoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getCommandHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCommandHistory> readCommandHistory() throws IOException {
        return readCommandHistoryHelper(filePath);
    }

    /**
     * Similar to {@link #readCommandHistory()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws IOException if the file cannot be read.
     */
    @Override
    public Optional<ReadOnlyCommandHistory> readCommandHistory(Path filePath) throws IOException {
        return readCommandHistoryHelper(filePath);
    }

    /**
     * Helper method for reading commandHistory.txt file stored on the hard disk.
     */
    private Optional<ReadOnlyCommandHistory> readCommandHistoryHelper(Path filePath) throws IOException {
        ArrayList<String> commandHistory = new ArrayList<>();
        Scanner scanner = new Scanner(new File(String.valueOf(filePath)));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            commandHistory.add(line);
        }
        CommandHistoryList commandHistoryList = new CommandHistoryList(commandHistory);
        return Optional.ofNullable(new CommandHistory(commandHistoryList));
    }

    /**
     * @param commandHistory takes in the valid command just entered by the user in the GUI.
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveCommandHistory(String commandHistory, Path filePath) throws IOException {
        if (FileUtil.isFileExists(filePath)) {
            logger.info("Appending command history to " + filePath);
            FileUtil.appendToFile(filePath, commandHistory + "\n");
        } else {
            logger.info("commandHistory.txt file not found. Creating a new file and writing to " + filePath);
            FileUtil.writeToFile(filePath, commandHistory + "\n");
        }
    }
}
