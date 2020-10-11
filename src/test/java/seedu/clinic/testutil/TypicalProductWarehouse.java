package seedu.clinic.testutil;

import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_PANADOL;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_QUANTITY_A;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_QUANTITY_B;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.clinic.model.product.Product;

/**
 * A utility class containing a list of {@code Product} objects to be used in tests.
 */
public class TypicalProductWarehouse {
    public static final Product DEFAULT_PRODUCT = new ProductBuilderWarehouse().build();

    //Manually Added

    public static final Product SANITIZER_WAREHOUSE = new ProductBuilderWarehouse().withName("Dettol Sanitizer")
            .withQuantity(50).build();
    public static final Product SURGICAL_MASK_WAREHOUSE = new ProductBuilderWarehouse().withName("Surgical mask")
            .withQuantity(50).build();
    public static final Product PLASTER_WAREHOUSE = new ProductBuilderWarehouse().withName("Plaster")
            .withQuantity(200).build();
    public static final Product CHARCOAL_PILL_WAREHOUSE = new ProductBuilderWarehouse().withName("Charcoal Pills")
            .withQuantity(500).build();
    public static final Product LATEX_GLOVES_WAREHOUSE = new ProductBuilderWarehouse().withName("Latex Gloves")
            .withQuantity(30).build();
    public static final Product STETHOSCOPE_WAREHOUSE = new ProductBuilderWarehouse().withName("stethoscope")
            .withQuantity(400).build();

    // Manually added - Product's details found in {@code CommandTestUtil}
    public static final Product ASPIRIN_WAREHOUSE = new ProductBuilderWarehouse().withName(VALID_PRODUCT_NAME_ASPIRIN)
            .withQuantity(VALID_PRODUCT_QUANTITY_A).build();
    public static final Product PANADOL_WAREHOUSE = new ProductBuilderWarehouse().withName(VALID_PRODUCT_NAME_PANADOL)
            .withQuantity(VALID_PRODUCT_QUANTITY_B).build();

    private TypicalProductWarehouse() {} // prevents instantiation

    public static List<Product> getTypicalProductSupplier() {
        return new ArrayList<>(Arrays.asList(SANITIZER_WAREHOUSE, SURGICAL_MASK_WAREHOUSE, PLASTER_WAREHOUSE,
                CHARCOAL_PILL_WAREHOUSE, LATEX_GLOVES_WAREHOUSE, STETHOSCOPE_WAREHOUSE,
                ASPIRIN_WAREHOUSE, PANADOL_WAREHOUSE));
    }

}
