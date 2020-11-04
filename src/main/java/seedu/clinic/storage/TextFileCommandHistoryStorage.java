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
    public Optional<CommandHistory> readCommandHistory() throws IOException {
        return readCommandHistoryHelper(filePath);
    }

    @Override
    public Optional<CommandHistory> readCommandHistory(Path filePath) throws IOException {
        return readCommandHistoryHelper(filePath);
    }

    private Optional<CommandHistory> readCommandHistoryHelper(Path filePath) throws IOException {
        ArrayList<String> commandHistory = new ArrayList<>();
        Scanner scanner = new Scanner(new File(String.valueOf(filePath)));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            commandHistory.add(line);
        }

        return Optional.ofNullable(new CommandHistory(commandHistory));
    }

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
