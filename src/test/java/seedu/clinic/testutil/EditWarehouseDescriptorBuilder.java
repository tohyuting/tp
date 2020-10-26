package seedu.clinic.testutil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.clinic.logic.commands.EditCommand.EditWarehouseDescriptor;
import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * A utility class to help with building EditWarehouseDescriptor objects.
 */
public class EditWarehouseDescriptorBuilder {

    private EditWarehouseDescriptor descriptor;

    public EditWarehouseDescriptorBuilder() {
        descriptor = new EditWarehouseDescriptor();
    }

    public EditWarehouseDescriptorBuilder(EditWarehouseDescriptor descriptor) {
        this.descriptor = new EditWarehouseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditWarehouseDescriptor} with fields containing {@code Warehouse}'s details
     */
    public EditWarehouseDescriptorBuilder(Warehouse warehouse) {
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
    public EditWarehouseDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditWarehouseDescriptor} that we are building.
     */
    public EditWarehouseDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditWarehouseDescriptor} that we are building.
     */
    public EditWarehouseDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditWarehouseDescriptor} that we are building.
     */
    public EditWarehouseDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Parses the {@code productMap} into a {@code Set<Product>} and set it to the {@code EditWarehouseDescriptor}
     * that we are building.
     * @param productMap
     */
    public EditWarehouseDescriptorBuilder withProducts(Map<String, Integer> productMap) {
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

