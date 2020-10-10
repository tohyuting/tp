package seedu.clinic.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Tag;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.util.SampleDataUtil;

/**
 * A utility class to help with building Product objects for Supplier.
 */
public class ProductBuilderSupplier {
    public static final String DEFAULT_NAME = "Thermometer";
    public static final Set<Tag> DEFAULT_TAGS = new HashSet<>();

    private Name productName;
    private Set<Tag> productTags;

    /**
     * Creates a {@code ProductBuilderSupplier} with the default details.
     */
    public ProductBuilderSupplier() {
        productName = new Name(DEFAULT_NAME);
        productTags = DEFAULT_TAGS;
    }

    /**
     * Initializes the ProductBuilderSupplier with the data of {@code productToCopy}.
     */
    public ProductBuilderSupplier(Product productToCopy) {
        productName = productToCopy.getProductName();
        productTags = productToCopy.getProductTags();
    }

    /**
     * Sets the {@code Name} of the {@code Product} that we are building.
     */
    public ProductBuilderSupplier withName(String name) {
        this.productName = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Product} that we are building.
     */
    public ProductBuilderSupplier withTags(String... tags) {
        this.productTags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Product build() {
        return new Product(productName, productTags);
    }

}
