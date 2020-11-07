package seedu.clinic.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Preference file Location : preferences.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));

        Config modifiedConfig = new Config();
        modifiedConfig.setLogLevel(Level.ALL);
        assertFalse(defaultConfig.equals(modifiedConfig));

        assertFalse(defaultConfig.equals("random string"));
    }

    @Test
    public void getLogLevel_rightLevelReturned() {
        Config defaultConfig = new Config();
        assertEquals(defaultConfig.getLogLevel(), Level.INFO);

        defaultConfig.setLogLevel(Level.WARNING);
        assertEquals(defaultConfig.getLogLevel(), Level.WARNING);
    }

    @Test
    public void getUserPrefsFilePath_correctPathReturned() {
        Config defaultConfig = new Config();
        assertEquals(defaultConfig.getUserPrefsFilePath(), Paths.get("preferences.json"));
    }

    @Test
    public void hashTest() {
        Config defaultConfig = new Config();
        assertEquals(Objects.hash(defaultConfig.getLogLevel(), defaultConfig.getUserPrefsFilePath()),
                defaultConfig.hashCode());
    }
}
