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
        Product editedSanitizerSupplier = new ProductBuilderSupplier(SANITIZER_SUPPLIER)
                .withName("Lifebuoy").build();
        assertFalse(SANITIZER_SUPPLIER.equals(editedSanitizerSupplier));

        //different name and tag -> returns false
        Product editedMaskSupplier = new ProductBuilderSupplier(SURGICAL_MASK_SUPPLIER).withName("housebrand")
                .withTags("protects").build();
        assertFalse(SURGICAL_MASK_SUPPLIER.equals(editedMaskSupplier));

        //different tag, same name -> returns true
        Product editedMaskTagsSupplier = new ProductBuilderSupplier(SURGICAL_MASK_SUPPLIER)
                .withTags("protects").build();
        assertTrue(SURGICAL_MASK_SUPPLIER.equals(editedMaskTagsSupplier));
    }

    @Test
    public void isSameProductWarehouse() {
        //same object -> returns true
        assertTrue(SANITIZER_WAREHOUSE.equals(SANITIZER_WAREHOUSE));

        //null -> returns false
        assertFalse(SANITIZER_WAREHOUSE.equals(null));

        // different name -> returns false
        Product editedSanitizerWarehouse = new ProductBuilderWarehouse(SANITIZER_WAREHOUSE)
                .withName("Lifebuoy").build();
        assertFalse(SANITIZER_WAREHOUSE.equals(editedSanitizerWarehouse));

        //different name and quantity -> returns false
        Product editedMaskWarehouse = new ProductBuilderWarehouse(SURGICAL_MASK_WAREHOUSE)
                .withName("housebrand").withQuantity(100).build();
        assertFalse(SURGICAL_MASK_WAREHOUSE.equals(editedMaskWarehouse));

        //different quantity, same name -> returns true
        Product editedMaskTagsWarehouse = new ProductBuilderWarehouse(SURGICAL_MASK_WAREHOUSE)
                .withQuantity(100).build();
        assertTrue(SURGICAL_MASK_WAREHOUSE.equals(editedMaskTagsWarehouse));
    }

}
