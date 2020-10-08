package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.EditCommand.EditSupplierDescriptor;
import seedu.address.logic.commands.EditCommand.EditWarehouseDescriptor;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.Remark;
import seedu.address.model.attribute.Tag;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.warehouse.Warehouse;

/**
 * A utility class to help with building EditWarehouseDescriptor objects.
 */
public class EditWarehouseDescriptionBuilder {

    private EditWarehouseDescriptor descriptor;

    public EditWarehouseDescriptionBuilder() {
        descriptor = new EditWarehouseDescriptor();
    }

    public EditWarehouseDescriptionBuilder(EditWarehouseDescriptor descriptor) {
        this.descriptor = new EditWarehouseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditWarehouseDescriptor} with fields containing {@code Warehouse}'s details
     */
    public EditWarehouseDescriptionBuilder(Warehouse warehouse) {
        descriptor = new EditWarehouseDescriptor();
        descriptor.setName(warehouse.getName());
        descriptor.setPhone(warehouse.getPhone());
        descriptor.setAddress(warehouse.getAddress());
        descriptor.setRemark(warehouse.getRemark());
        descriptor.setProducts(warehouse.getProducts());
    }

    /**
     * Sets the {@code Name} of the {@code EditWarehouseDescriptor} that we are building.
     */
    public EditWarehouseDescriptionBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditWarehouseDescriptor} that we are building.
     */
    public EditWarehouseDescriptionBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditWarehouseDescriptor} that we are building.
     */
    public EditWarehouseDescriptionBuilder withEmail(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditWarehouseDescriptor} that we are building.
     */
    public EditWarehouseDescriptionBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Parses the {@code productMap} into a {@code Set<Product>} and set it to the {@code EditWarehouseDescriptor}
     * that we are building.
     * @param productMap
     */
    public EditWarehouseDescriptionBuilder withProducts(Map<String, Integer> productMap) {
        Set<Product> productSet = new HashSet<>();
        for (String productName: productMap.keySet()) {
            Product product = new Product(new Name(productName), productMap.get(productName));
            productSet.add(product);
        }
        descriptor.setProducts(productSet);
        return this;
    }

    public EditWarehouseDescriptor build() {
        return descriptor;
    }
}

