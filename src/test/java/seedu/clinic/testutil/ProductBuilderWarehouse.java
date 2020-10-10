package seedu.clinic.testutil;

import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.product.Product;

/**
 * A utility class to help with building Product objects for Warehouse.
 */
public class ProductBuilderWarehouse {
    public static final String DEFAULT_NAME = "Thermometer";
    public static final int DEFAULT_QUANTITY = 50;

    private Name productName;
    private int productQuantity;

    /**
     * Creates a {@code ProductBuilderWarehouse} with the default details.
     */
    public ProductBuilderWarehouse() {
        productName = new Name(DEFAULT_NAME);
        productQuantity = DEFAULT_QUANTITY;
    }

    /**
     * Initializes the ProductBuilderWarehouse with the data of {@code productToCopy}.
     */
    public ProductBuilderWarehouse(Product productToCopy) {
        productName = productToCopy.getProductName();
        productQuantity = productToCopy.getProductQuantity();
    }

    /**
     * Sets the {@code Name} of the {@code Product} that we are building.
     */
    public ProductBuilderWarehouse withName(String name) {
        this.productName = new Name(name);
        return this;
    }

    /**
     * Sets the quantity of the {@code Product} that we are building.
     */
    public ProductBuilderWarehouse withQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
        return this;
    }

    public Product build() {
        return new Product(productName, productQuantity);
    }

}
