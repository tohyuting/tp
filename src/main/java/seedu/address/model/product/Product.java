package seedu.address.model.product;

import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents Product to be sold by Suppliers and stored in Warehouse.
 * Guarantees: immutable;
 */
public class Product {

    private final Name productName;
    private int productQuantity;
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
     * Constructs a Product with a productName, the product's updatedQuantity
     * and tags associated with the current product.
     *
     * @param productName
     * @param updatedQuantity
     * @param productTags
     */
    public Product(Name productName, int updatedQuantity, Set<Tag> productTags) {
        this.productName = productName;
        this.productQuantity = updatedQuantity;
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
     * Creates a new Product with the change in quantity reflected.
     *
     * @param updatedQuantity
     * @return
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

    /**
     * Displays String representation of Product in Supplier.
     *
     * @return String representation of Product in Supplier.
     */
    @Override
    public String toString() {
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
    public String toStringWareHouse() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getProductName())
                .append(" - ")
                .append(getProductQuantity())
                .append(" left");
        return builder.toString();
    }
}
