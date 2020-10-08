package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.warehouse.Warehouse;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withSupplier("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Supplier} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withSupplier(Supplier supplier) {
        addressBook.addSupplier(supplier);
        return this;
    }

    /**
     * Adds a new {@code Warehouse} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withWarehouse(Warehouse warehouse) {
        addressBook.addWarehouse(warehouse);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
