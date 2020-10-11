package seedu.clinic.testutil;

import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_PANADOL;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_TAG_FEVER;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_TAG_PAINKILLER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.clinic.model.product.Product;

/**
 * A utility class containing a list of {@code Product} objects to be used in tests.
 */
public class TypicalProductSupplier {
    public static final Product DEFAULT_PRODUCT = new ProductBuilderSupplier().build();

    //Manually Added

    public static final Product SANITIZER_SUPPLIER = new ProductBuilderSupplier().withName("Dettol Sanitizer")
            .withTags("germsfree", "topchoice").build();
    public static final Product SURGICAL_MASK_SUPPLIER = new ProductBuilderSupplier().withName("Surgical mask")
            .withTags("cheap", "durable").build();
    public static final Product PLASTER_SUPPLIER = new ProductBuilderSupplier().withName("Plaster")
            .withTags("waterproof").build();
    public static final Product CHARCOAL_PILL_SUPPLIER = new ProductBuilderSupplier().withName("Charcoal Pills")
            .withTags("diarrhoea").build();
    public static final Product LATEX_GLOVES_SUPPLIER = new ProductBuilderSupplier().withName("Latex Gloves")
            .withTags("durable").build();
    public static final Product STETHOSCOPE_SUPPLIER = new ProductBuilderSupplier().withName("stethoscope")
            .withTags("professional").build();

    // Manually added - Product's details found in {@code CommandTestUtil}
    public static final Product ASPIRIN_SUPPLIER = new ProductBuilderSupplier().withName(VALID_PRODUCT_NAME_ASPIRIN)
            .withTags(VALID_TAG_PAINKILLER).build();
    public static final Product PANADOL_SUPPLIER = new ProductBuilderSupplier().withName(VALID_PRODUCT_NAME_PANADOL)
            .withTags(VALID_TAG_FEVER, VALID_TAG_PAINKILLER).build();

    private TypicalProductSupplier() {} // prevents instantiation

    public static List<Product> getTypicalProductSupplier() {
        return new ArrayList<>(Arrays.asList(SANITIZER_SUPPLIER, SURGICAL_MASK_SUPPLIER, PLASTER_SUPPLIER,
                CHARCOAL_PILL_SUPPLIER, LATEX_GLOVES_SUPPLIER, STETHOSCOPE_SUPPLIER,
                ASPIRIN_SUPPLIER, PANADOL_SUPPLIER));
    }

}
