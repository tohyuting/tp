package seedu.clinic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_TAG_ANTIBIOTICS;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_NAME_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_REMARK_B;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalSupplier.ALICE;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalSupplierOnlyClinic;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalVersionedClinic;
import static seedu.clinic.testutil.TypicalWarehouse.A;
import static seedu.clinic.testutil.TypicalWarehouse.getTypicalWarehouseOnlyClinic;

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
import seedu.clinic.model.warehouse.Warehouse;
import seedu.clinic.model.warehouse.exceptions.DuplicateWarehouseException;
import seedu.clinic.testutil.SupplierBuilder;
import seedu.clinic.testutil.WarehouseBuilder;


public class ClinicTest {

    private final VersionedClinic clinic = new VersionedClinic(new Clinic());

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clinic.getWarehouseList());
        assertEquals(Collections.emptyList(), clinic.getSupplierList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clinic.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyClinic_replacesData() {
        Clinic newSupplierData = getTypicalSupplierOnlyClinic();
        clinic.resetData(newSupplierData);
        assertEquals(newSupplierData, clinic);

        Clinic newWarehouseData = getTypicalWarehouseOnlyClinic();
        clinic.resetData(newWarehouseData);
        assertEquals(newWarehouseData, clinic);

        Clinic newData = getTypicalVersionedClinic();
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
        SupplierOnlyClinicStub newData = new SupplierOnlyClinicStub(newSuppliers);

        assertThrows(DuplicateSupplierException.class, () -> clinic.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateWarehouses_throwsDuplicateWarehouseException() {
        // Two warehouses with the same identity fields
        Warehouse editedA = new WarehouseBuilder(A).withRemark(VALID_WAREHOUSE_REMARK_B)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_B, VALID_WAREHOUSE_PRODUCT_QUANTITY_B))
                .build();
        List<Warehouse> newWarehouses = Arrays.asList(A, editedA);
        WarehouseOnlyClinicStub newData = new WarehouseOnlyClinicStub(newWarehouses);

        assertThrows(DuplicateWarehouseException.class, () -> clinic.resetData(newData));
    }

    @Test
    public void hasSupplier_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clinic.hasSupplier(null));
    }

    @Test
    public void hasWarehouse_nullWarehouse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clinic.hasWarehouse(null));
    }

    @Test
    public void hasSupplier_supplierNotInClinic_returnsFalse() {
        assertFalse(clinic.hasSupplier(ALICE));
    }

    @Test
    public void hasWarehouse_warehouseNotInClinic_returnsFalse() {
        assertFalse(clinic.hasWarehouse(A));
    }

    @Test
    public void hasSupplier_supplierInClinic_returnsTrue() {
        clinic.addSupplier(ALICE);
        assertTrue(clinic.hasSupplier(ALICE));
    }

    @Test
    public void hasWarehouse_warehouseInClinic_returnsTrue() {
        clinic.addWarehouse(A);
        assertTrue(clinic.hasWarehouse(A));
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
    public void hasWarehouse_warehouseWithSameIdentityFieldsInClinic_returnsTrue() {
        clinic.addWarehouse(A);
        Warehouse editedA = new WarehouseBuilder(A).withRemark(VALID_REMARK_BOB)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_B, VALID_WAREHOUSE_PRODUCT_QUANTITY_B))
                .build();
        assertTrue(clinic.hasWarehouse(editedA));
    }

    @Test
    public void getSupplierList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> clinic.getSupplierList().remove(0));
    }

    @Test
    public void getWarehouseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> clinic.getWarehouseList().remove(0));
    }

    /**
     * A stub ReadOnlyClinic whose suppliers list can violate interface constraints.
     */
    private static class SupplierOnlyClinicStub implements ReadOnlyClinic {
        private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        private final ObservableList<Warehouse> warehouses = FXCollections.observableArrayList();

        SupplierOnlyClinicStub(Collection<Supplier> suppliers) {
            this.suppliers.setAll(suppliers);
        }

        @Override
        public ObservableList<Supplier> getSupplierList() {
            return suppliers;
        }

        @Override
        public ObservableList<Warehouse> getWarehouseList() {
            return warehouses;
        }
    }

    /**
     * A stub ReadOnlyClinic whose warehouse list can violate interface constraints.
     */
    private static class WarehouseOnlyClinicStub implements ReadOnlyClinic {
        private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        private final ObservableList<Warehouse> warehouses = FXCollections.observableArrayList();

        WarehouseOnlyClinicStub(Collection<Warehouse> warehouses) {
            this.warehouses.setAll(warehouses);
        }

        @Override
        public ObservableList<Supplier> getSupplierList() {
            return suppliers;
        }

        @Override
        public ObservableList<Warehouse> getWarehouseList() {
            return warehouses;
        }
    }
}
