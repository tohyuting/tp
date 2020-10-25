package seedu.clinic.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.commons.exceptions.DataConversionException;
import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.commons.util.FileUtil;
import seedu.clinic.commons.util.JsonUtil;
import seedu.clinic.model.ReadOnlyUserMacros;

/**
 * A class to access UserMacros stored in the hard disk as a json file
 */
public class JsonUserMacrosStorage implements UserMacrosStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUserMacrosStorage.class);

    private Path filePath;

    public JsonUserMacrosStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserMacrosFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUserMacros> readUserMacros() throws DataConversionException {
        return readUserMacros(filePath);
    }

    /**
     * Similar to {@link #readUserMacros()}
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<ReadOnlyUserMacros> readUserMacros(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableUserMacros> jsonUserMacros = JsonUtil.readJsonFile(
                filePath, JsonSerializableUserMacros.class);
        if (jsonUserMacros.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonUserMacros.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUserMacros(ReadOnlyUserMacros userMacros) throws IOException {
        saveUserMacros(userMacros, filePath);
    }

    /**
     * Similar to {@link #saveUserMacros(ReadOnlyUserMacros)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveUserMacros(ReadOnlyUserMacros userMacros, Path filePath) throws IOException {
        requireNonNull(userMacros);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableUserMacros(userMacros), filePath);
    }
}
