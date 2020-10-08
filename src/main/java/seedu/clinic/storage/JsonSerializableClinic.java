package seedu.clinic.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

<<<<<<< HEAD:src/main/java/seedu/address/storage/JsonSerializableAddressBook.java
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.warehouse.Warehouse;
=======
import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.supplier.Supplier;
>>>>>>> upstream/master:src/main/java/seedu/clinic/storage/JsonSerializableClinic.java

/**
 * An Immutable Clinic that is serializable to JSON format.
 */
@JsonRootName(value = "clinic")
class JsonSerializableClinic {

    public static final String MESSAGE_DUPLICATE_SUPPLIER = "Suppliers list contains duplicate supplier(s).";
    public static final String MESSAGE_DUPLICATE_WAREHOUSE = "Warehouses list contains duplicate warehouse(s).";

    private final List<JsonAdaptedSupplier> suppliers = new ArrayList<>();
    private final List<JsonAdaptedWarehouse> warehouses = new ArrayList<>();

    /**
<<<<<<< HEAD:src/main/java/seedu/address/storage/JsonSerializableAddressBook.java
     * Constructs a {@code JsonSerializableAddressBook} with the given suppliers and warehouses.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("suppliers") List<JsonAdaptedSupplier> suppliers,
                                       @JsonProperty("warehouses") List<JsonAdaptedWarehouse> warehouses) {
=======
     * Constructs a {@code JsonSerializableClinic} with the given suppliers.
     */
    @JsonCreator
    public JsonSerializableClinic(@JsonProperty("suppliers") List<JsonAdaptedSupplier> suppliers) {
>>>>>>> upstream/master:src/main/java/seedu/clinic/storage/JsonSerializableClinic.java
        this.suppliers.addAll(suppliers);
        this.warehouses.addAll(warehouses);
    }

    /**
     * Converts a given {@code ReadOnlyClinic} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableClinic}.
     */
<<<<<<< HEAD:src/main/java/seedu/address/storage/JsonSerializableAddressBook.java
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        suppliers.addAll(source.getSupplierList().stream().map(JsonAdaptedSupplier::new)
                .collect(Collectors.toList()));
        warehouses.addAll(source.getWarehouseList().stream().map(JsonAdaptedWarehouse::new)
                .collect(Collectors.toList()));
=======
    public JsonSerializableClinic(ReadOnlyClinic source) {
        suppliers.addAll(source.getSupplierList().stream().map(JsonAdaptedSupplier::new).collect(Collectors.toList()));
>>>>>>> upstream/master:src/main/java/seedu/clinic/storage/JsonSerializableClinic.java
    }

    /**
     * Converts this clinic into the model's {@code Clinic} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Clinic toModelType() throws IllegalValueException {
        Clinic clinic = new Clinic();
        for (JsonAdaptedSupplier jsonAdaptedSupplier : suppliers) {
            Supplier supplier = jsonAdaptedSupplier.toModelType();
            if (clinic.hasSupplier(supplier)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SUPPLIER);
            }
            clinic.addSupplier(supplier);
        }
<<<<<<< HEAD:src/main/java/seedu/address/storage/JsonSerializableAddressBook.java

        for (JsonAdaptedWarehouse jsonAdaptedWarehouse : warehouses) {
            Warehouse warehouse = jsonAdaptedWarehouse.toModelType();
            if (addressBook.hasWarehouse(warehouse)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WAREHOUSE);
            }
            addressBook.addWarehouse(warehouse);
        }

        return addressBook;
=======
        return clinic;
>>>>>>> upstream/master:src/main/java/seedu/clinic/storage/JsonSerializableClinic.java
    }

}
