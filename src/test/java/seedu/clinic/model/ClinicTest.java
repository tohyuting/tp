package seedu.clinic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_TAG_ANTIBIOTICS;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalSupplier.ALICE;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalClinic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.supplier.exceptions.DuplicateSupplierException;
import seedu.clinic.testutil.SupplierBuilder;

public class ClinicTest {

    private final Clinic clinic = new Clinic();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clinic.getSupplierList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clinic.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyClinic_replacesData() {
        Clinic newData = getTypicalClinic();
        clinic.resetData(newData);
        assertEquals(newData, clinic);
    }

    @Test
    public void resetData_withDuplicateSuppliers_throwsDuplicateSupplierException() {
        // Two suppliers with the same identity fields
        Supplier editedAlice = new SupplierBuilder(ALICE).withRemark(VALID_REMARK_BOB)
                .withProducts(Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS}))
                .build();
        List<Supplier> newSuppliers = Arrays.asList(ALICE, editedAlice);
        ClinicStub newData = new ClinicStub(newSuppliers);

        assertThrows(DuplicateSupplierException.class, () -> clinic.resetData(newData));
    }

    @Test
    public void hasSupplier_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clinic.hasSupplier(null));
    }

    @Test
    public void hasSupplier_supplierNotInClinic_returnsFalse() {
        assertFalse(clinic.hasSupplier(ALICE));
    }

    @Test
    public void hasSupplier_supplierInClinic_returnsTrue() {
        clinic.addSupplier(ALICE);
        assertTrue(clinic.hasSupplier(ALICE));
    }

    @Test
    public void hasSupplier_supplierWithSameIdentityFieldsInClinic_returnsTrue() {
        clinic.addSupplier(ALICE);
        Supplier editedAlice = new SupplierBuilder(ALICE).withRemark(VALID_REMARK_BOB)
                .withProducts(Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS}))
                .build();
        assertTrue(clinic.hasSupplier(editedAlice));
    }

    @Test
    public void getSupplierList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> clinic.getSupplierList().remove(0));
    }

    /**
     * A stub ReadOnlyClinic whose suppliers list can violate interface constraints.
     */
    private static class ClinicStub implements ReadOnlyClinic {
        private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

        ClinicStub(Collection<Supplier> suppliers) {
            this.suppliers.setAll(suppliers);
        }

        @Override
        public ObservableList<Supplier> getSupplierList() {
            return suppliers;
        }
    }

}
