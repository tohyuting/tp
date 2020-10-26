package seedu.clinic.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.clinic.logic.commands.UpdateCommand.UpdateProductDescriptor;
import seedu.clinic.model.attribute.Tag;
import seedu.clinic.model.product.Product;

/**
 * A utility class to help with building UpdateProductDescriptor objects.
 */
public class UpdateProductDescriptorBuilder {
    private UpdateProductDescriptor descriptor;

    public UpdateProductDescriptorBuilder() {
        descriptor = new UpdateProductDescriptor();
    }

    public UpdateProductDescriptorBuilder(UpdateProductDescriptor descriptor) {
        this.descriptor = new UpdateProductDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdateProductDescriptor} with fields containing {@code product}'s details
     */
    public UpdateProductDescriptorBuilder(Product product) {
        descriptor = new UpdateProductDescriptor();
        descriptor.setQuantity(product.getProductQuantity());
        descriptor.setTags(product.getProductTags());
    }

    /**
     * Sets the {@code quantity} of the {@code UpdateProductDescriptor} that we are building.
     */
    public UpdateProductDescriptorBuilder withQuantity(int quantity) {
        descriptor.setQuantity(quantity);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code UpdateProductDescriptor}
     * that we are building.
     */
    public UpdateProductDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public UpdateProductDescriptor build() {
        return descriptor;
    }
}
