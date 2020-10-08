package seedu.clinic.model.warehouse;

import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.clinic.testutil.WarehouseBuilder;

class WarehouseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Warehouse warehouse = new WarehouseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> warehouse.getProducts().remove(0));
    }

    @Test
    void getName() {
    }

    @Test
    void getRemark() {
    }

    @Test
    void getPhone() {
    }

    @Test
    void getAddress() {
    }

    @Test
    void getProducts() {
    }

    @Test
    void isSameWarehouse() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testToString() {
    }
}
