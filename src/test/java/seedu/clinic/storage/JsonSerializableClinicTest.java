package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.commons.util.JsonUtil;
import seedu.clinic.model.Clinic;
import seedu.clinic.testutil.TypicalSupplier;

public class JsonSerializableClinicTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableClinicTest");
    private static final Path TYPICAL_SUPPLIERS_FILE = TEST_DATA_FOLDER.resolve("typicalSuppliersClinic.json");
    private static final Path INVALID_SUPPLIER_FILE = TEST_DATA_FOLDER.resolve("invalidSupplierClinic.json");
    private static final Path DUPLICATE_SUPPLIER_FILE = TEST_DATA_FOLDER.resolve("duplicateSupplierClinic.json");

    @Test
    public void toModelType_typicalSuppliersFile_success() throws Exception {
        JsonSerializableClinic dataFromFile = JsonUtil.readJsonFile(TYPICAL_SUPPLIERS_FILE,
                JsonSerializableClinic.class).get();
        Clinic clinicFromFile = dataFromFile.toModelType();
        Clinic typicalSuppliersClinic = TypicalSupplier.getTypicalClinic();
        assertEquals(clinicFromFile, typicalSuppliersClinic);
    }

    @Test
    public void toModelType_invalidSupplierFile_throwsIllegalValueException() throws Exception {
        JsonSerializableClinic dataFromFile = JsonUtil.readJsonFile(INVALID_SUPPLIER_FILE,
                JsonSerializableClinic.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateSuppliers_throwsIllegalValueException() throws Exception {
        JsonSerializableClinic dataFromFile = JsonUtil.readJsonFile(DUPLICATE_SUPPLIER_FILE,
                JsonSerializableClinic.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableClinic.MESSAGE_DUPLICATE_SUPPLIER,
                dataFromFile::toModelType);
    }

}
