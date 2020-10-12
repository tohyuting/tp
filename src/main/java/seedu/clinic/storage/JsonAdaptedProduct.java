package seedu.clinic.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Tag;
import seedu.clinic.model.product.Product;

public class JsonAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";
    private final String name;
    private int quantity;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProduct} with the given product details.
     */
    @JsonCreator
    public JsonAdaptedProduct(@JsonProperty("name") String name,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                              @JsonProperty("quantity") int quantity) {
        this.name = name;
        this.quantity = quantity;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Product} into this class for Jackson use.
     */
    public JsonAdaptedProduct(Product source) {
        name = source.getProductName().fullName;
        quantity = source.getProductQuantity();
        tagged.addAll(source.getProductTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted product object into the model's {@code Product} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product.
     */
    public Product toModelType() throws IllegalValueException {
        final List<Tag> productTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            productTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        if (!Product.isValidQuantity(quantity)) {
            throw new IllegalValueException(String.format(Product.MESSAGE_CONSTRAINTS));
        }
        final Name modelName = new Name(name);

        final Set<Tag> modelTags = new HashSet<>(productTags);
        return new Product(modelName, quantity, modelTags);
    }
}
