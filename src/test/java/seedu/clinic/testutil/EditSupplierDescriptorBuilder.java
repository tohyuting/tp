package seedu.clinic.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.clinic.logic.commands.EditCommand.EditSupplierDescriptor;
import seedu.clinic.model.attribute.Email;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.attribute.Tag;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;

/**
 * A utility class to help with building EditSupplierDescriptor objects.
 */
public class EditSupplierDescriptorBuilder {

    private EditSupplierDescriptor descriptor;

    public EditSupplierDescriptorBuilder() {
        descriptor = new EditSupplierDescriptor();
    }

    public EditSupplierDescriptorBuilder(EditSupplierDescriptor descriptor) {
        this.descriptor = new EditSupplierDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSupplierDescriptor} with fields containing {@code Supplier}'s details
     */
    public EditSupplierDescriptorBuilder(Supplier supplier) {
        descriptor = new EditSupplierDescriptor();
        descriptor.setName(supplier.getName());
        descriptor.setPhone(supplier.getPhone());
        descriptor.setEmail(supplier.getEmail());
        descriptor.setRemark(supplier.getRemark());
        descriptor.setProducts(supplier.getProducts());
    }

    /**
     * Sets the {@code Name} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Parses the {@code productMap} into a {@code Set<Product>} and set it to the {@code EditSupplierDescriptor}
     * that we are building.
     * @param productMap
     */
    public EditSupplierDescriptorBuilder withProducts(Map<String, String[]> productMap) {
        Set<Product> productSet = new HashSet<>();
        for (String productName:productMap.keySet()) {
            Set<Tag> productTags = Arrays.stream(productMap.get(productName))
                    .map(Tag::new)
                    .collect(Collectors.toSet());
            Product product = new Product(new Name(productName), productTags);
            productSet.add(product);
        }
        descriptor.setProducts(productSet);
        return this;
    }

    public EditSupplierDescriptor build() {
        return descriptor;
    }
}
