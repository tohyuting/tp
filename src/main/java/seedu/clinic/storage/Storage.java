package seedu.clinic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.clinic.commons.exceptions.DataConversionException;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.ReadOnlyUserPrefs;
import seedu.clinic.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ClinicStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getClinicFilePath();

    @Override
    Optional<ReadOnlyClinic> readClinic() throws DataConversionException, IOException;

    @Override
    void saveClinic(ReadOnlyClinic clinic) throws IOException;

}
