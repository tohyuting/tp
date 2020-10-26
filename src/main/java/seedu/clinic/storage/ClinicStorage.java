package seedu.clinic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.clinic.commons.exceptions.DataConversionException;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.ReadOnlyClinic;

/**
 * Represents a storage for {@link Clinic}.
 */
public interface ClinicStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getClinicFilePath();

    /**
     * Returns Clinic data as a {@link ReadOnlyClinic}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyClinic> readClinic() throws DataConversionException, IOException;

    /**
     * @see #getClinicFilePath()
     */
    Optional<ReadOnlyClinic> readClinic(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyClinic} to the storage.
     * @param clinic cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveClinic(ReadOnlyClinic clinic) throws IOException;

    /**
     * @see #saveClinic(ReadOnlyClinic)
     */
    void saveClinic(ReadOnlyClinic clinic, Path filePath) throws IOException;

}
