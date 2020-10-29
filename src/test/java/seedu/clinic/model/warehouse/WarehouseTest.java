package seedu.clinic.model.warehouse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_ADDRESS_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_NAME_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PHONE_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_NAME_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_NAME_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_REMARK_B;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalWarehouse.A;
import static seedu.clinic.testutil.TypicalWarehouse.B;

import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.clinic.testutil.WarehouseBuilder;

class WarehouseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Warehouse warehouse = new WarehouseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> warehouse.getProducts().remove(0));
    }

    @Test
    public void isSameWarehouse() {
        // same object -> returns true
        assertTrue(A.isSameWarehouse(A));

        // null -> returns false
        assertFalse(A.isSameWarehouse(null));

        // different phone and address -> returns true
        Warehouse editedA = new WarehouseBuilder(A).withPhone(VALID_WAREHOUSE_PHONE_B)
                .withAddress(VALID_WAREHOUSE_ADDRESS_B).build();
        assertTrue(A.isSameWarehouse(editedA));

        // different name -> returns false
        editedA = new WarehouseBuilder(A).withName(VALID_WAREHOUSE_NAME_B).build();
        assertFalse(A.isSameWarehouse(editedA));

        // same name, same phone, different attributes -> returns true
        editedA = new WarehouseBuilder(A).withAddress(VALID_WAREHOUSE_ADDRESS_B).withRemark(VALID_WAREHOUSE_REMARK_B)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_B, VALID_WAREHOUSE_PRODUCT_QUANTITY_B)).build();
        assertTrue(A.isSameWarehouse(editedA));

        // same name, same email, different attributes -> returns true
        editedA = new WarehouseBuilder(A).withPhone(VALID_WAREHOUSE_PHONE_B).withRemark(VALID_WAREHOUSE_REMARK_B)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_B, VALID_WAREHOUSE_PRODUCT_QUANTITY_B)).build();
        assertTrue(A.isSameWarehouse(editedA));

        // same name, same phone, same email, different attributes -> returns true
        editedA = new WarehouseBuilder(A).withRemark(VALID_REMARK_BOB)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_B, VALID_WAREHOUSE_PRODUCT_QUANTITY_B)).build();
        assertTrue(A.isSameWarehouse(editedA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Warehouse aCopy = new WarehouseBuilder(A)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_A, VALID_WAREHOUSE_PRODUCT_QUANTITY_A)).build();
        assertTrue(A.equals(aCopy));

        // same object -> returns true
        assertTrue(A.equals(A));

        // null -> returns false
        assertFalse(A.equals(null));

        // different type -> returns false
        assertFalse(A.equals(5));

        // different warehouse -> returns false
        assertFalse(A.equals(B));

        // different name -> returns false
        Warehouse editedA = new WarehouseBuilder(A).withName(VALID_WAREHOUSE_NAME_B).build();
        assertFalse(A.equals(editedA));

        // different phone -> returns false
        editedA = new WarehouseBuilder(A).withPhone(VALID_WAREHOUSE_PHONE_B).build();
        assertFalse(A.equals(editedA));

        // different address -> returns false
        editedA = new WarehouseBuilder(A).withAddress(VALID_WAREHOUSE_ADDRESS_B).build();
        assertFalse(A.equals(editedA));

        // different remark -> returns false
        editedA = new WarehouseBuilder(A).withRemark(VALID_WAREHOUSE_REMARK_B).build();
        assertFalse(A.equals(editedA));

        // different product -> returns false
        editedA = new WarehouseBuilder(A)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_B, VALID_WAREHOUSE_PRODUCT_QUANTITY_B)).build();
        assertFalse(A.equals(editedA));
    }
}
