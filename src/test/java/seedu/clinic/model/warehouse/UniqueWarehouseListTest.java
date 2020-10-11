package seedu.clinic.model.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_TAG_HEADACHE;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_REMARK_B;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalWarehouse.A;
import static seedu.clinic.testutil.TypicalWarehouse.B;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.warehouse.exceptions.DuplicateWarehouseException;
import seedu.clinic.model.warehouse.exceptions.WarehouseNotFoundException;
import seedu.clinic.testutil.WarehouseBuilder;

public class UniqueWarehouseListTest {

    private final UniqueWarehouseList uniqueWarehouseList = new UniqueWarehouseList();

    @Test
    public void contains_nullWarehouse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWarehouseList.contains(null));
    }

    @Test
    public void contains_warehouseNotInList_returnsFalse() {
        assertFalse(uniqueWarehouseList.contains(A));
    }

    @Test
    public void contains_warehouseInList_returnsTrue() {
        uniqueWarehouseList.add(A);
        assertTrue(uniqueWarehouseList.contains(A));
    }

    @Test
    public void contains_warehouseWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWarehouseList.add(A);
        Warehouse editedA = new WarehouseBuilder(A).withRemark(VALID_WAREHOUSE_REMARK_B)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_TAG_HEADACHE, VALID_WAREHOUSE_PRODUCT_QUANTITY_B))
                .build();
        assertTrue(uniqueWarehouseList.contains(editedA));
    }

    @Test
    public void add_nullWarehouse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWarehouseList.add(null));
    }

    @Test
    public void add_duplicateWarehouse_throwsDuplicateWarehouseException() {
        uniqueWarehouseList.add(A);
        assertThrows(DuplicateWarehouseException.class, () -> uniqueWarehouseList.add(A));
    }

    @Test
    public void setWarehouse_nullTargetSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWarehouseList.setWarehouse(null, A));
    }

    @Test
    public void setWarehouse_nullEditedWarehouse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWarehouseList.setWarehouse(A, null));
    }

    @Test
    public void setWarehouse_targetWarehouseNotInList_throwsWarehouseNotFoundException() {
        assertThrows(WarehouseNotFoundException.class, () -> uniqueWarehouseList.setWarehouse(A, A));
    }

    @Test
    public void setWarehouse_editedWarehouseIsSameWarehouse_success() {
        uniqueWarehouseList.add(A);
        uniqueWarehouseList.setWarehouse(A, A);
        UniqueWarehouseList expectedUniqueWarehouseList = new UniqueWarehouseList();
        expectedUniqueWarehouseList.add(A);
        assertEquals(expectedUniqueWarehouseList, uniqueWarehouseList);
    }

    @Test
    public void setWarehouse_editedWarehouseHasSameIdentity_success() {
        uniqueWarehouseList.add(A);
        Warehouse editedA = new WarehouseBuilder(A).withRemark(VALID_WAREHOUSE_REMARK_B)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_TAG_HEADACHE, VALID_WAREHOUSE_PRODUCT_QUANTITY_B))
                .build();
        uniqueWarehouseList.setWarehouse(A, editedA);
        UniqueWarehouseList expectedUniqueWarehouseList = new UniqueWarehouseList();
        expectedUniqueWarehouseList.add(editedA);
        assertEquals(expectedUniqueWarehouseList, uniqueWarehouseList);
    }

    @Test
    public void setWarehouse_editedWarehouseHasDifferentIdentity_success() {
        uniqueWarehouseList.add(A);
        uniqueWarehouseList.setWarehouse(A, B);
        UniqueWarehouseList expectedUniqueWarehouseList = new UniqueWarehouseList();
        expectedUniqueWarehouseList.add(B);
        assertEquals(expectedUniqueWarehouseList, uniqueWarehouseList);
    }

    @Test
    public void setWarehouse_editedWarehouseHasNonUniqueIdentity_throwsDuplicateWarehouseException() {
        uniqueWarehouseList.add(A);
        uniqueWarehouseList.add(B);
        assertThrows(DuplicateWarehouseException.class, () -> uniqueWarehouseList.setWarehouse(A, B));
    }

    @Test
    public void remove_nullWarehouse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWarehouseList.remove(null));
    }

    @Test
    public void remove_warehouseDoesNotExist_throwsWarehouseNotFoundException() {
        assertThrows(WarehouseNotFoundException.class, () -> uniqueWarehouseList.remove(A));
    }

    @Test
    public void remove_existingWarehouse_removesWarehouse() {
        uniqueWarehouseList.add(A);
        uniqueWarehouseList.remove(A);
        UniqueWarehouseList expectedUniqueWarehouseList = new UniqueWarehouseList();
        assertEquals(expectedUniqueWarehouseList, uniqueWarehouseList);
    }

    @Test
    public void setWarehouses_nullUniqueWarehouseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWarehouseList.setWarehouses((UniqueWarehouseList) null));
    }

    @Test
    public void setWarehouses_uniqueWarehouseList_replacesOwnListWithProvidedUniqueWarehouseList() {
        uniqueWarehouseList.add(A);
        UniqueWarehouseList expectedUniqueWarehouseList = new UniqueWarehouseList();
        expectedUniqueWarehouseList.add(B);
        uniqueWarehouseList.setWarehouses(expectedUniqueWarehouseList);
        assertEquals(expectedUniqueWarehouseList, uniqueWarehouseList);
    }

    @Test
    public void setWarehouses_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWarehouseList.setWarehouses((List<Warehouse>) null));
    }

    @Test
    public void setWarehouses_list_replacesOwnListWithProvidedList() {
        uniqueWarehouseList.add(A);
        List<Warehouse> supplierList = Collections.singletonList(B);
        uniqueWarehouseList.setWarehouses(supplierList);
        UniqueWarehouseList expectedUniqueWarehouseList = new UniqueWarehouseList();
        expectedUniqueWarehouseList.add(B);
        assertEquals(expectedUniqueWarehouseList, uniqueWarehouseList);
    }

    @Test
    public void setWarehouses_listWithDuplicateWarehouses_throwsDuplicateWarehouseException() {
        List<Warehouse> listWithDuplicateWarehouses = Arrays.asList(A, A);
        assertThrows(DuplicateWarehouseException.class, ()
            -> uniqueWarehouseList.setWarehouses(listWithDuplicateWarehouses));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueWarehouseList.asUnmodifiableObservableList().remove(0));
    }
}
