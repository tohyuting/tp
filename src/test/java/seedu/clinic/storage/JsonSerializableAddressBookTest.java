package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.commons.util.JsonUtil;
import seedu.clinic.model.AddressBook;
import seedu.clinic.testutil.TypicalSupplier;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_SUPPLIERS_FILE = TEST_DATA_FOLDER.resolve("typicalSuppliersAddressBook.json");
    private static final Path INVALID_SUPPLIER_FILE = TEST_DATA_FOLDER.resolve("invalidSupplierAddressBook.json");
    private static final Path DUPLICATE_SUPPLIER_FILE = TEST_DATA_FOLDER.resolve("duplicateSupplierAddressBook.json");

    @Test
    public void toModelType_typicalSuppliersFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_SUPPLIERS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalSuppliersAddressBook = TypicalSupplier.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalSuppliersAddressBook);
    }

    @Test
    public void toModelType_invalidSupplierFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_SUPPLIER_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateSuppliers_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_SUPPLIER_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_SUPPLIER,
                dataFromFile::toModelType);
    }

}
