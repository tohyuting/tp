package seedu.clinic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_WAREHOUSES;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalSupplier.ALICE;
import static seedu.clinic.testutil.TypicalSupplier.BENSON;
import static seedu.clinic.testutil.TypicalWarehouse.A;
import static seedu.clinic.testutil.TypicalWarehouse.B;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForSupplier;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForWarehouse;
import seedu.clinic.testutil.ClinicBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Clinic(), new Clinic(modelManager.getClinic()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setClinicFilePath(Paths.get("clinic/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setClinicFilePath(Paths.get("new/clinic/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setClinicFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setClinicFilePath(null));
    }

    @Test
    public void setClinicFilePath_validPath_setsClinicFilePath() {
        Path path = Paths.get("clinic/book/file/path");
        modelManager.setClinicFilePath(path);
        assertEquals(path, modelManager.getClinicFilePath());
    }

    @Test
    public void hasSupplier_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSupplier(null));
    }

    @Test
    public void hasWarehouse_nullWarehouse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasWarehouse(null));
    }

    @Test
    public void hasSupplierByName_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSupplierByName(null));
    }

    @Test
    public void hasSupplier_supplierNotInClinic_returnsFalse() {
        assertFalse(modelManager.hasSupplier(ALICE));
    }

    @Test
    public void hasWarehouse_warehouseNotInClinic_returnsFalse() {
        assertFalse(modelManager.hasWarehouse(A));
    }

    @Test
    public void hasSupplierByName_supplierNotInClinic_returnsFalse() {
        assertFalse(modelManager.hasSupplierByName(ALICE));
    }

    @Test
    public void hasSupplier_supplierInClinic_returnsTrue() {
        modelManager.addSupplier(ALICE);
        assertTrue(modelManager.hasSupplier(ALICE));
    }

    @Test
    public void hasWarehouse_warehouseInClinic_returnsTrue() {
        modelManager.addWarehouse(A);
        assertTrue(modelManager.hasWarehouse(A));
    }

    @Test
    public void hasSupplierByName_supplierInClinic_returnsTrue() {
        modelManager.addSupplier(ALICE);
        assertTrue(modelManager.hasSupplierByName(ALICE));
    }

    @Test
    public void getFilteredSupplierList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredSupplierList().remove(0));
    }

    @Test
    public void getFilteredWarehouseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> modelManager.getFilteredWarehouseList().remove(0));
    }

    @Test
    public void equals() {
        Clinic clinic = new ClinicBuilder()
                .withSupplier(ALICE)
                .withSupplier(BENSON)
                .withWarehouse(A)
                .withWarehouse(B)
                .build();
        Clinic differentClinic = new Clinic();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(clinic, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(clinic, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different clinic -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentClinic, userPrefs)));

        // different filteredSupplierList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        String[] query = Arrays.copyOfRange(keywords, 0, 2);
        modelManager.updateFilteredSupplierList(new NameContainsKeywordsPredicateForSupplier(Arrays.asList(query)));
        assertFalse(modelManager.equals(new ModelManager(clinic, userPrefs)));

        // different filteredWarehouseList -> returns false
        keywords = A.getName().fullName.split("\\s+");
        query = Arrays.copyOfRange(keywords, 0, 2);
        modelManager.updateFilteredWarehouseList(new NameContainsKeywordsPredicateForWarehouse(Arrays.asList(query)));
        assertFalse(modelManager.equals(new ModelManager(clinic, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        modelManager.updateFilteredWarehouseList(PREDICATE_SHOW_ALL_WAREHOUSES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setClinicFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(clinic, differentUserPrefs)));
    }
}
