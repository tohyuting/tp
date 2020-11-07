package seedu.clinic.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class GuiSettingsTest {

    private GuiSettings defaultGuiSettings = new GuiSettings();

    @Test
    void getWindowWidth_widthReturned() {
        assertEquals(defaultGuiSettings.getWindowWidth(), 740);
    }

    @Test
    void getWindowHeight_heightReturned() {
        assertEquals(defaultGuiSettings.getWindowHeight(), 600);
    }

    @Test
    void getWindowCoordinates_coordinateReturned() {
        assertEquals(null, defaultGuiSettings.getWindowCoordinates());
    }

    @Test
    void testEquals_invalidInstance() {
        assertFalse(defaultGuiSettings.equals("random string"));
    }

    @Test
    void testHashCode() {
        int defaultHash = Objects.hash(defaultGuiSettings.getWindowWidth(),
                defaultGuiSettings.getWindowHeight(), defaultGuiSettings.getWindowCoordinates());
        assertEquals(defaultHash, defaultGuiSettings.hashCode());
    }
}
