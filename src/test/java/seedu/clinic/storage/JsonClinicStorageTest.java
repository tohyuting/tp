package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalSupplier.ALICE;
import static seedu.clinic.testutil.TypicalSupplier.HOON;
import static seedu.clinic.testutil.TypicalSupplier.IDA;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalClinic;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.clinic.commons.exceptions.DataConversionException;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.ReadOnlyClinic;

public class JsonClinicStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonClinicStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readClinic_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readClinic(null));
    }

    private java.util.Optional<ReadOnlyClinic> readClinic(String filePath) throws Exception {
        return new JsonClinicStorage(Paths.get(filePath)).readClinic(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readClinic("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readClinic("notJsonFormatClinic.json"));
    }

    @Test
    public void readClinic_invalidSupplierClinic_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readClinic("invalidSupplierClinic.json"));
    }

    @Test
    public void readClinic_invalidAndValidSupplierClinic_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readClinic("invalidAndValidSupplierClinic.json"));
    }

    @Test
    public void readAndSaveClinic_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempClinic.json");
        Clinic original = getTypicalClinic();
        JsonClinicStorage jsonClinicStorage = new JsonClinicStorage(filePath);

        // Save in new file and read back
        jsonClinicStorage.saveClinic(original, filePath);
        ReadOnlyClinic readBack = jsonClinicStorage.readClinic(filePath).get();
        assertEquals(original, new Clinic(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addSupplier(HOON);
        original.removeSupplier(ALICE);
        jsonClinicStorage.saveClinic(original, filePath);
        readBack = jsonClinicStorage.readClinic(filePath).get();
        assertEquals(original, new Clinic(readBack));

        // Save and read without specifying file path
        original.addSupplier(IDA);
        jsonClinicStorage.saveClinic(original); // file path not specified
        readBack = jsonClinicStorage.readClinic().get(); // file path not specified
        assertEquals(original, new Clinic(readBack));

    }

    @Test
    public void saveClinic_nullClinic_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClinic(null, "SomeFile.json"));
    }

    /**
     * Saves {@code clinic} at the specified {@code filePath}.
     */
    private void saveClinic(ReadOnlyClinic clinic, String filePath) {
        try {
            new JsonClinicStorage(Paths.get(filePath))
                    .saveClinic(clinic, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveClinic_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClinic(new Clinic(), null));
    }
}
