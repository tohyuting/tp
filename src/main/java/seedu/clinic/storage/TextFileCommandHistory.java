package seedu.clinic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.commons.util.FileUtil;

public class TextFileCommandHistory implements CommandHistoryStorage {
    private static final Logger logger = LogsCenter.getLogger(TextFileCommandHistory.class);

    private Path filePath;

    public TextFileCommandHistory(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getCommandHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<String> readCommandHistory(Path filePath) throws IOException {
        return Optional.of("String"); // Todo
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
