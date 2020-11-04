package seedu.clinic.model.supplier;

import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.clinic.model.attribute.Email;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.product.exceptions.ProductNotFoundException;

/**
 * Represents a Supplier in the CLI-nic app.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Supplier {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Remark remark;
    private final Set<Product> products = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Supplier(Name name, Phone phone, Email email, Remark remark, Set<Product> products) {
        requireAllNonNull(name, phone, email, remark, products);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.remark = remark;
        this.products.addAll(products);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable product set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }

    /**
     * Returns a product with the {@code targetName} sold by the supplier.
     *
     * @return The product matching the target name.
     * @throws ProductNotFoundException if these is no product in the list matching the name asked.
     */
    public Product getProductByName(Name targetName) throws ProductNotFoundException {
        Product matchedProduct = products.stream().filter(p -> p.getProductName().equals(targetName))
                .findFirst().orElseThrow(ProductNotFoundException::new);

        return matchedProduct;
    }

    /**
     * Removes a product sold by the Supplier.
     *
     * @return a new Supplier with the {@code targetProduct} removed.
     */
    public Supplier removeProduct(Product targetProduct) {
        assert this.getProducts().contains(targetProduct) : "Target product not in list";

        Set<Product> updatedProductSet = new HashSet<>(this.getProducts());
        updatedProductSet.remove(targetProduct); // removes the matching product
        Supplier updatedSupplier = new Supplier(this.getName(), this.getPhone(),
                this.getEmail(), this.getRemark(), updatedProductSet);
        return updatedSupplier;
    }

    /**
     * Returns true if a product with the {@code targetName} is found in the supplier.
     */
    public boolean hasProductWithName(Name targetName) {
        return products.stream().anyMatch(p -> p.getProductName().equals(targetName));
    }

    /**
     * Returns true if both suppliers are equal, with name (case sensitive).
     * This defines a weaker notion of equality between two suppliers than equals.
     */
    public boolean equalsSupplierCaseSensitive(Supplier otherSupplier) {
        if (otherSupplier == this) {
            return true;
        }

        return otherSupplier != null
                && otherSupplier.getName().equalsCaseSensitive(getName())
                && otherSupplier.getPhone().equals(getPhone())
                && otherSupplier.getEmail().equals(getEmail())
                && otherSupplier.getRemark().equals(getRemark())
                && otherSupplier.getProducts().equals(getProducts());
    }

    /**
     * Returns true if both suppliers of the same name (case insensitive).
     * This defines a weaker notion of equality between two suppliers.
     */
    public boolean isSameSupplier(Supplier otherSupplier) {
        if (otherSupplier == this) {
            return true;
        }

        return otherSupplier != null
                && otherSupplier.getName().equals(getName());
    }

    /**
     * Returns true if both suppliers have the same identity and data fields.
     * This defines a stronger notion of equality between two suppliers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Supplier)) {
            return false;
        }

        Supplier otherSupplier = (Supplier) other;
        return otherSupplier.getName().equals(getName())
                && otherSupplier.getPhone().equals(getPhone())
                && otherSupplier.getEmail().equals(getEmail())
                && otherSupplier.getRemark().equals(getRemark())
                && otherSupplier.getProducts().equals(getProducts());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, remark, products);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Remarks: ")
                .append(getRemark())
                .append(" Products: ");
        getProducts().forEach(s -> builder.append(s.toString()).append(" "));
        return builder.toString();
    }
}
