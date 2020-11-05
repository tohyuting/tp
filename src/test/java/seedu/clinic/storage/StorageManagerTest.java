package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static seedu.clinic.testutil.TypicalCommandHistory.getTypicalCommandHistory;
import static seedu.clinic.testutil.TypicalMacro.getTypicalUserMacros;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalVersionedClinic;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.model.Clinic;
//import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.VersionedClinic;

public class StorageManagerTest {

    @TempDir
    public Path temporaryFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonClinicStorage clinicStorage = new JsonClinicStorage(getTempFilePath("clinic.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs.json"));
        JsonUserMacrosStorage userMacrosStorage = new JsonUserMacrosStorage(getTempFilePath("userMacros.json"));
        TextFileCommandHistoryStorage commandHistoryStorage = new TextFileCommandHistoryStorage(
                getTempFilePath("commandHistory.txt"));
        storageManager = new StorageManager(clinicStorage, userPrefsStorage, userMacrosStorage, commandHistoryStorage);
    }

    private Path getTempFilePath(String fileName) {
        return temporaryFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void clinicReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonClinicStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonClinicStorageTest} class.
         */
        VersionedClinic original = getTypicalVersionedClinic();
        storageManager.saveClinic(original);
        ReadOnlyClinic retrieved = storageManager.readClinic().get();
        assertEquals(original.getCurrentClinic(), new Clinic(retrieved));
    }

    @Test
    public void userMacrosReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserMacrosStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserMacrosStorageTest} class.
         */
        UserMacros original = getTypicalUserMacros();
        storageManager.saveUserMacros(original);
        ReadOnlyUserMacros retrieved = storageManager.readUserMacros().get();
        assertEquals(original, new UserMacros(retrieved));
    }

    //    @Test
    //    public void commandHistoryReadSave() throws Exception {
    //        CommandHistory original = getTypicalCommandHistory();
    //        storageManager.saveCommandHistory("First command history",
    //                storageManager.getCommandHistoryFilePath());
    //        storageManager.saveCommandHistory("Second command history",
    //                storageManager.getCommandHistoryFilePath());
    //
    //        CommandHistory newCommandHistory = new CommandHistory(storageManager.readCommandHistory().get());
    //        assertEquals(original.readPreviousHistory(), newCommandHistory.readPreviousHistory());
    //        assertEquals(original.readPreviousHistory(), newCommandHistory.readPreviousHistory());
    //        assertEquals(original.readNextHistory(), newCommandHistory.readNextHistory());
    //    }

    @Test
    public void getClinicFilePath() {
        assertNotNull(storageManager.getClinicFilePath());
    }

    @Test
    public void getUserMacrosFilePath() {
        assertNotNull(storageManager.getUserMacrosFilePath());
    }

    @Test
    public void getCommandHistoryFilePath() {
        assertNotNull(storageManager.getCommandHistoryFilePath());
    }

}
