package seedu.clinic.testutil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.util.SampleDataUtil;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * A utility class to help with building Warehouse objects.
 */
public class WarehouseBuilder {
    public static final String DEFAULT_NAME = "Jurong East Branch";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "Headquarter branch";

    private Name name;
    private Phone phone;
    private Address address;
    private Remark remark;
    private Set<Product> products;

    /**
     * Creates a {@code WarehouseBuilder} with the default details.
     */
    public WarehouseBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
        products = new HashSet<>();
    }

    /**
     * Initializes the WarehouseBuilder with the data of {@code warehouseToCopy}.
     */
    public WarehouseBuilder(Warehouse warehouseToCopy) {
        name = warehouseToCopy.getName();
        phone = warehouseToCopy.getPhone();
        address = warehouseToCopy.getAddress();
        remark = warehouseToCopy.getRemark();
        products = new HashSet<>();
    }

    /**
     * Sets the {@code Name} of the {@code Warehouse} that we are building.
     */
    public WarehouseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code products} into a {@code Set<Product>} and set it to the {@code Warehouse} that we are building.
     */
    public WarehouseBuilder withProducts(Map<String, Integer> productMap) {
        this.products = SampleDataUtil.getProductSetForWarehouse(products, productMap);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Warehouse} that we are building.
     */
    public WarehouseBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Warehouse} that we are building.
     */
    public WarehouseBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Warehouse} that we are building.
     */
    public WarehouseBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Warehouse build() {
        return new Warehouse(name, phone, address, remark, products);
    }
}
