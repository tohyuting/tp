package seedu.clinic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.clinic.commons.exceptions.DataConversionException;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.UserMacros;

/**
 * Represents a storage for {@link UserMacros}.
 */
public interface UserMacrosStorage {
    /**
     * Returns the file path of the UserMacros data file.
     */
    Path getUserMacrosFilePath();

    /**
     * Returns user macro data as a {@link ReadOnlyUserMacros}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUserMacros> readUserMacros() throws DataConversionException, IOException;

    /**
     * @see #getUserMacrosFilePath()
     */
    Optional<ReadOnlyUserMacros> readUserMacros(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.clinic.model.ReadOnlyUserMacros} to the storage.
     * @param userMacros cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserMacros(ReadOnlyUserMacros userMacros) throws IOException;

    /**
     * @see #saveUserMacros(ReadOnlyUserMacros)
     */
    void saveUserMacros(ReadOnlyUserMacros userMacros, Path filePath) throws IOException;
}
