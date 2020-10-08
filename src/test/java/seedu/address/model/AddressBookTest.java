package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ANTIBIOTICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_REMARK_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_B;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSupplier.ALICE;
import static seedu.address.testutil.TypicalSupplier.getTypicalAddressBook;
import static seedu.address.testutil.TypicalSupplier.getTypicalSupplierOnlyAddressBook;
import static seedu.address.testutil.TypicalWarehouse.A;
import static seedu.address.testutil.TypicalWarehouse.getTypicalWarehouseOnlyAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.exceptions.DuplicateSupplierException;
import seedu.address.model.warehouse.Warehouse;
import seedu.address.model.warehouse.exceptions.DuplicateWarehouseException;
import seedu.address.testutil.SupplierBuilder;
import seedu.address.testutil.WarehouseBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getWarehouseList());
        assertEquals(Collections.emptyList(), addressBook.getSupplierList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newSupplierData = getTypicalSupplierOnlyAddressBook();
        addressBook.resetData(newSupplierData);
        assertEquals(newSupplierData, addressBook);

        AddressBook newWarehouseData = getTypicalWarehouseOnlyAddressBook();
        addressBook.resetData(newWarehouseData);
        assertEquals(newWarehouseData, addressBook);

        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateSuppliers_throwsDuplicateSupplierException() {
        // Two suppliers with the same identity fields
        Supplier editedAlice = new SupplierBuilder(ALICE).withRemark(VALID_REMARK_BOB)
                .withProducts(Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS}))
                .build();
        List<Supplier> newSuppliers = Arrays.asList(ALICE, editedAlice);
        SupplierOnlyAddressBookStub newData = new SupplierOnlyAddressBookStub(newSuppliers);

        assertThrows(DuplicateSupplierException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateWarehouses_throwsDuplicateWarehouseException() {
        // Two suppliers with the same identity fields
        Warehouse editedA = new WarehouseBuilder(A).withRemark(VALID_WAREHOUSE_REMARK_B)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_B, VALID_WAREHOUSE_PRODUCT_QUANTITY_B))
                .build();
        List<Warehouse> newWarehouses = Arrays.asList(A, editedA);
        WarehouseOnlyAddressBookStub newData = new WarehouseOnlyAddressBookStub(newWarehouses);

        assertThrows(DuplicateWarehouseException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasSupplier_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasSupplier(null));
    }

    @Test
    public void hasWarehouse_nullWarehouse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasWarehouse(null));
    }

    @Test
    public void hasSupplier_supplierNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasSupplier(ALICE));
    }

    @Test
    public void hasWarehouse_warehouseNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasWarehouse(A));
    }

    @Test
    public void hasSupplier_supplierInAddressBook_returnsTrue() {
        addressBook.addSupplier(ALICE);
        assertTrue(addressBook.hasSupplier(ALICE));
    }

    @Test
    public void hasWarehouse_warehouseInAddressBook_returnsTrue() {
        addressBook.addWarehouse(A);
        assertTrue(addressBook.hasWarehouse(A));
    }

    @Test
    public void hasSupplier_supplierWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addSupplier(ALICE);
        Supplier editedAlice = new SupplierBuilder(ALICE).withRemark(VALID_REMARK_BOB)
                .withProducts(Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS}))
                .build();
        assertTrue(addressBook.hasSupplier(editedAlice));
    }

    @Test
    public void hasWarehouse_warehouseWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addWarehouse(A);
        Warehouse editedA = new WarehouseBuilder(A).withRemark(VALID_REMARK_BOB)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_B, VALID_WAREHOUSE_PRODUCT_QUANTITY_B))
                .build();
        assertTrue(addressBook.hasWarehouse(editedA));
    }

    @Test
    public void getSupplierList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getSupplierList().remove(0));
    }

    @Test
    public void getWarehouseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getWarehouseList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose suppliers list can violate interface constraints.
     */
    private static class SupplierOnlyAddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        private final ObservableList<Warehouse> warehouses = FXCollections.observableArrayList();

        SupplierOnlyAddressBookStub(Collection<Supplier> suppliers) {
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
     * A stub ReadOnlyAddressBook whose warehouse list can violate interface constraints.
     */
    private static class WarehouseOnlyAddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        private final ObservableList<Warehouse> warehouses = FXCollections.observableArrayList();

        WarehouseOnlyAddressBookStub(Collection<Warehouse> warehouses) {
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
