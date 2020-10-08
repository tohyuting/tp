package seedu.clinic.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Jackson-friendly version of {@link Warehouse}.
 */
class JsonAdaptedWarehouse {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Warehouse's %s field is missing!";

    private final String name;
    private final String phone;
    private final String address;
    private final String remark;
    private final List<JsonAdaptedProduct> products = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedWarehouse} with the given warehouse details.
     */
    @JsonCreator
    public JsonAdaptedWarehouse(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("address") String address, @JsonProperty("remark") String remark,
                               @JsonProperty("products") List<JsonAdaptedProduct> products) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.remark = remark;
        if (products != null) {
            this.products.addAll(products);
        }
    }

    /**
     * Converts a given {@code Warehouse} into this class for Jackson use.
     */
    public JsonAdaptedWarehouse(Warehouse source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        remark = source.getRemark().value;
        products.addAll(source.getProducts().stream()
                .map(JsonAdaptedProduct::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted warehouse object into the model's {@code Warehouse} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted warehouse.
     */
    public Warehouse toModelType() throws IllegalValueException {
        final List<Product> warehouseProducts = new ArrayList<>();
        for (JsonAdaptedProduct product : products) {
            warehouseProducts.add(product.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(remark);

        final Set<Product> modelProducts = new HashSet<>(warehouseProducts);
        return new Warehouse(modelName, modelPhone, modelAddress, modelRemark, modelProducts);
    }

}
