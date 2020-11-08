package seedu.clinic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.commons.exceptions.DataConversionException;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.ReadOnlyCommandHistory;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.ReadOnlyUserPrefs;
import seedu.clinic.model.UserPrefs;

/**
 * Manages storage of CLI-nic data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ClinicStorage clinicStorage;
    private UserPrefsStorage userPrefsStorage;
    private UserMacrosStorage userMacrosStorage;
    private CommandHistoryStorage commandHistoryStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ClinicStorage}, {@code UserPrefStorage},
     * {@code UserMacrosStorage} and {@code CommandHistoryStorage}.
     */
    public StorageManager(ClinicStorage clinicStorage, UserPrefsStorage userPrefsStorage,
            UserMacrosStorage userMacrosStorage, CommandHistoryStorage commandHistoryStorage) {
        super();
        this.clinicStorage = clinicStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userMacrosStorage = userMacrosStorage;
        this.commandHistoryStorage = commandHistoryStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ UserMacros methods ==============================

    @Override
    public Path getUserMacrosFilePath() {
        return userMacrosStorage.getUserMacrosFilePath();
    }

    @Override
    public Optional<ReadOnlyUserMacros> readUserMacros() throws DataConversionException, IOException {
        return readUserMacros(userMacrosStorage.getUserMacrosFilePath());
    }

    @Override
    public Optional<ReadOnlyUserMacros> readUserMacros(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userMacrosStorage.readUserMacros(filePath);
    }

    @Override
    public void saveUserMacros(ReadOnlyUserMacros userMacros) throws IOException {
        saveUserMacros(userMacros, userMacrosStorage.getUserMacrosFilePath());
    }

    @Override
    public void saveUserMacros(ReadOnlyUserMacros userMacros, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userMacrosStorage.saveUserMacros(userMacros, filePath);
    }

    // ================ Clinic methods ==============================

    @Override
    public Path getClinicFilePath() {
        return clinicStorage.getClinicFilePath();
    }

    @Override
    public Optional<ReadOnlyClinic> readClinic() throws DataConversionException, IOException {
        return readClinic(clinicStorage.getClinicFilePath());
    }

    @Override
    public Optional<ReadOnlyClinic> readClinic(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return clinicStorage.readClinic(filePath);
    }

    @Override
    public void saveClinic(ReadOnlyClinic clinic) throws IOException {
        saveClinic(clinic, clinicStorage.getClinicFilePath());
    }

    @Override
    public void saveClinic(ReadOnlyClinic clinic, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        clinicStorage.saveClinic(clinic, filePath);
    }

    // ================ CommandHistory methods ==============================

    @Override
    public Path getCommandHistoryFilePath() {
        return commandHistoryStorage.getCommandHistoryFilePath();
    }

    @Override
    public Optional<ReadOnlyCommandHistory> readCommandHistory() throws IOException {
        return readCommandHistory(commandHistoryStorage.getCommandHistoryFilePath());
    }

    @Override
    public Optional<ReadOnlyCommandHistory> readCommandHistory(Path filePath) throws IOException {
        return commandHistoryStorage.readCommandHistory(filePath);
    }

    @Override
    public void saveCommandHistory(String commandHistory, Path filePath) throws IOException {
        commandHistoryStorage.saveCommandHistory(commandHistory, filePath);
    }

}
