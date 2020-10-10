package seedu.clinic.model.warehouse;


import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.product.Product;

/**
 * Represents a Warehouse in the CLI-nic app.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Warehouse {

    // Identity fields
    // Each Warehouse identified by its unique name
    private final Name name;

    // Data fields
    private final Phone phone;
    private final Address address;
    private final Remark remark;
    private final Set<Product> products = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Warehouse(Name name, Phone phone, Address address, Remark remark) {
        requireAllNonNull(name, phone, address, remark);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.remark = remark;
    }

    /**
     * Updated warehouse with new product information.
     * Every field must be present and not null.
     */
    public Warehouse(Name name, Phone phone, Address address, Remark remark, Set<Product> products) {
        requireAllNonNull(name, phone, address, remark, products);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.remark = remark;
        this.products.addAll(products);
    }

    public Name getName() {
        return name;
    }

    public Remark getRemark() {
        return remark;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable product set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     *
     * @return A set of products stored in the warehouse.
     */
    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }

    /**
     * Returns true if both warehouses of the same name.
     * This defines a weaker notion of equality between two warehouses.
     */
    public boolean isSameWarehouse(Warehouse otherWarehouse) {
        if (otherWarehouse == this) {
            return true;
        }

        return otherWarehouse != null
                && otherWarehouse.getName().equals(getName());
    }

    /**
     * Returns true if both warehouses have the same identity and data fields.
     * This defines a stronger notion of equality between two warehouses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Warehouse)) {
            return false;
        }

        Warehouse otherWarehouse = (Warehouse) other;
        return otherWarehouse.getName().equals(getName())
                && otherWarehouse.getPhone().equals(getPhone())
                && otherWarehouse.getAddress().equals(getAddress())
                && otherWarehouse.getProducts().equals(getProducts())
                && otherWarehouse.getRemark().equals(getRemark());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, remark, products);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Address: ")
                .append(getAddress())
                .append(" Remark: ")
                .append(getRemark());
        builder.append(" Products: ");
        getProducts().forEach(s -> builder.append(s.toStringForWareHouse()));
        return builder.toString();
    }
}
