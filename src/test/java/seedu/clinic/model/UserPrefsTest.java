package seedu.clinic.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setClinicFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setClinicFilePath(null));
    }

    @Test
    public void setUserMacrosFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setUserMacrosFilePath(null));
    }

    @Test
    public void equalsTest() {
        // null - false
        UserPrefs userPrefs = new UserPrefs();
        assertFalse(userPrefs.equals(null));

        // different type - false
        assertFalse(userPrefs.equals(1));
    }
}
