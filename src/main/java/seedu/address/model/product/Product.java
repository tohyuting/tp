package seedu.address.model.product;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.supplier.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents Product to be sold by Suppliers and stored in Warehouse.
 * Guarantees: immutable;
 */
public class Product {

    private final Name productName;
    private final int productQuantity;
    private final Set<Tag> productTags = new HashSet<>();

    /**
     * Constructs a Product with a productName and the productQuantity associated with the product.
     * Product objects constructed will not have any product tags associated with it.
     *
     * @param productName
     * @param productQuantity
     */
    public Product(Name productName, int productQuantity) {
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    /**
     * Constructs a Product with a productName and the tags (if any) associated with the products.
     * Tags can be 0 or more than 1.
     *
     * @param productName
     * @param productTags
     */
    public Product(Name productName, Set<Tag> productTags) {
        this.productName = productName;
        this.productQuantity = 0;
        this.productTags.addAll(productTags);
    }

    /**
     * Constructs a Product with a productName, the product's productQuantity
     * and tags associated with the current product.
     *
     * @param productName
     * @param productQuantity
     * @param productTags
     */
    public Product(Name productName, int productQuantity, Set<Tag> productTags) {
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productTags.addAll(productTags);
    }

    public Name getProductName() {
        return this.productName;
    }

    public int getProductQuantity() {
        return this.productQuantity;
    }

    public Set<Tag> getProductTags() {
        return Collections.unmodifiableSet(this.productTags);
    }

    /**
     * Returns a new Product with the change in quantity reflected.
     *
     * @param updatedQuantity
     */
    public Product updateQuantityForWarehouse(int updatedQuantity) {
        return new Product(this.productName, updatedQuantity, this.productTags);
    }

    /**
     * Compares if two products are equal by checking if they have the same product name.
     *
     * @param other Object to compare with.
     * @return Is the other object equals to this object.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Product // instanceof handles nulls
                && productName.equals(((Product) other).productName)); // state check
    }

    @Override public int hashCode() {
        return Objects.hash(productName);
    }

    /**
     * Displays String representation of Product in Supplier.
     *
     * @return String representation of Product in Supplier.
     */
    public String toStringForSupplier() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getProductName())
                .append(" Tags: ");
        getProductTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Displays String representation of Product displayed under a warehouse.
     *
     * @return String representation of Product displayed under a warehouse.
     */
    public String toStringForWareHouse() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getProductName())
                .append(" - ")
                .append(getProductQuantity())
                .append(" left");
        return builder.toString();
    }
}
