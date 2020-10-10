package seedu.clinic.model.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.TypicalProductSupplier.SANITIZER_SUPPLIER;
import static seedu.clinic.testutil.TypicalProductSupplier.SURGICAL_MASK_SUPPLIER;
import static seedu.clinic.testutil.TypicalProductWarehouse.SANITIZER_WAREHOUSE;
import static seedu.clinic.testutil.TypicalProductWarehouse.SURGICAL_MASK_WAREHOUSE;

import org.junit.jupiter.api.Test;

import seedu.clinic.testutil.ProductBuilderSupplier;
import seedu.clinic.testutil.ProductBuilderWarehouse;

public class ProductTest {
    @Test
    public void isSameProductSupplier() {
        //same object -> returns true
        assertTrue(SANITIZER_SUPPLIER.equals(SANITIZER_SUPPLIER));

        //null -> returns false
        assertFalse(SANITIZER_SUPPLIER.equals(null));

        // different name -> returns false
        Product editedSanitizer = new ProductBuilderSupplier(SANITIZER_SUPPLIER).withName("Lifebuoy").build();
        assertFalse(SANITIZER_SUPPLIER.equals(editedSanitizer));

        //different name and tag -> returns false
        Product editedMask = new ProductBuilderSupplier(SURGICAL_MASK_SUPPLIER).withName("housebrand")
                .withTags("protects").build();
        assertFalse(SURGICAL_MASK_SUPPLIER.equals(editedMask));

        //different tag, same name -> returns true
        Product editedMaskTags = new ProductBuilderSupplier(SURGICAL_MASK_SUPPLIER)
                .withTags("protects").build();
        assertTrue(SURGICAL_MASK_SUPPLIER.equals(editedMaskTags));
    }

    @Test
    public void isSameProductWarehouse() {
        //same object -> returns true
        assertTrue(SANITIZER_WAREHOUSE.equals(SANITIZER_WAREHOUSE));

        //null -> returns false
        assertFalse(SANITIZER_WAREHOUSE.equals(null));

        // different name -> returns false
        Product editedSanitizer = new ProductBuilderWarehouse(SANITIZER_WAREHOUSE)
                .withName("Lifebuoy").build();
        assertFalse(SANITIZER_WAREHOUSE.equals(editedSanitizer));

        //different name and quantity -> returns false
        Product editedMask = new ProductBuilderWarehouse(SURGICAL_MASK_WAREHOUSE).withName("housebrand")
                .withQuantity(100).build();
        assertFalse(SURGICAL_MASK_WAREHOUSE.equals(editedMask));

        //different quantity, same name -> returns true
        Product editedMaskTags = new ProductBuilderWarehouse(SURGICAL_MASK_WAREHOUSE)
                .withQuantity(100).build();
        assertTrue(SURGICAL_MASK_WAREHOUSE.equals(editedMaskTags));
    }

}
