package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSupplier.ALICE;
import static seedu.address.testutil.TypicalSupplier.HOON;
import static seedu.address.testutil.TypicalSupplier.IDA;
import static seedu.address.testutil.TypicalWarehouse.BENSON;
import static seedu.address.testutil.TypicalWarehouse.HENRY;
import static seedu.address.testutil.TypicalWarehouse.IRVIN;
import static seedu.address.testutil.TypicalSupplier.getTypicalSupplierOnlyAddressBook;
import static seedu.address.testutil.TypicalWarehouse.getTypicalWarehouseOnlyAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
        return new JsonAddressBookStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidSupplierAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidSupplierAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidWarehouseAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidWarehouseAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidSupplierAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class,
                () -> readAddressBook("invalidAndValidSupplierAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidWarehouseAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class,
                () -> readAddressBook("invalidAndValidWarehouseAddressBook.json"));
    }

    @Test
    public void readAndSaveSupplierOnlyAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AddressBook originalSupplierOnly = getTypicalSupplierOnlyAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(originalSupplierOnly, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(originalSupplierOnly, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        originalSupplierOnly.addSupplier(HOON);
        originalSupplierOnly.removeSupplier(ALICE);
        jsonAddressBookStorage.saveAddressBook(originalSupplierOnly, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(originalSupplierOnly, new AddressBook(readBack));

        // Save and read without specifying file path
        originalSupplierOnly.addSupplier(IDA);
        jsonAddressBookStorage.saveAddressBook(originalSupplierOnly); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(originalSupplierOnly, new AddressBook(readBack));

    }

    @Test
    public void readAndSaveWarehouseOnlyAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AddressBook originalWarehouseOnly = getTypicalWarehouseOnlyAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(originalWarehouseOnly, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(originalWarehouseOnly, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        originalWarehouseOnly.addWarehouse(HENRY);
        originalWarehouseOnly.removeWarehouse(BENSON);
        jsonAddressBookStorage.saveAddressBook(originalWarehouseOnly, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(originalWarehouseOnly, new AddressBook(readBack));

        // Save and read without specifying file path
        originalWarehouseOnly.addWarehouse(IRVIN);
        jsonAddressBookStorage.saveAddressBook(originalWarehouseOnly); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(originalWarehouseOnly, new AddressBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new JsonAddressBookStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null));
    }
}
