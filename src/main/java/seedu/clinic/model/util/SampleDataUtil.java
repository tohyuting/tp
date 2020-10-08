package seedu.clinic.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

<<<<<<< HEAD:src/main/java/seedu/address/model/util/SampleDataUtil.java
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.Remark;
import seedu.address.model.attribute.Tag;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.warehouse.Warehouse;
=======
import seedu.clinic.model.Clinic;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Email;
import seedu.clinic.model.supplier.Name;
import seedu.clinic.model.supplier.Phone;
import seedu.clinic.model.supplier.Remark;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.tag.Tag;
>>>>>>> upstream/master:src/main/java/seedu/clinic/model/util/SampleDataUtil.java

/**
 * Contains utility methods for populating {@code Clinic} with sample data.
 */
public class SampleDataUtil {
    public static Supplier[] getSampleSuppliers() {
        return new Supplier[] {
            new Supplier(new Name("Alex Yeoh Ltd"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Remark("long term partner"),
                getProductSet(Map.of("Panadol", new String[]{"fever"}))),
            new Supplier(new Name("Bernice Yu Pte Ltd"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Remark("long term partner"),
                getProductSet(Map.of("Panadol", new String[]{"fever"}))),
            new Supplier(new Name("Charlotte Oliveiro Ltd"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Remark("long term partner"),
                getProductSet(Map.of("Panadol", new String[]{"fever"}))),
            new Supplier(new Name("David Li Pte Ltd"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Remark("long term partner"),
                getProductSet(Map.of("Panadol", new String[]{"fever"}))),
            new Supplier(new Name("Irfan Ibrahim Pte Ltd"), new Phone("92492021"), new Email("irfan@example.com"),
                new Remark("long term partner"),
                getProductSet(Map.of("Panadol", new String[]{"fever"}))),
            new Supplier(new Name("Roy Balakrishnan Pte Ltd"), new Phone("92624417"), new Email("royb@example.com"),
                new Remark("long term partner"),
                getProductSet(Map.of("Panadol", new String[]{"fever"}))),
        };
    }

<<<<<<< HEAD:src/main/java/seedu/address/model/util/SampleDataUtil.java
    public static Warehouse[] getSampleWarehouses() {
        return new Warehouse[] {
                new Warehouse(new Name("Alex Yeoh warehouse"), new Phone("87438807"),
                        new Address("21 Lower Kent Ridge Rd, Singapore 119077"), new Remark("long term partner"),
                        getProductSetForWarehouse(Map.of("Panadol", 10))),
                new Warehouse(new Name("Bernice Yu warehouse"), new Phone("99272758"),
                        new Address("21 Lower Kent Ridge Rd, Singapore 119077"), new Remark("long term partner"),
                        getProductSetForWarehouse(Map.of("Panadol", 20))),
                new Warehouse(new Name("Charlotte Oliveiro warehouse"), new Phone("93210283"),
                        new Address("21 Lower Kent Ridge Rd, Singapore 119077"), new Remark("long term partner"),
                        getProductSetForWarehouse(Map.of("Panadol", 30))),
                new Warehouse(new Name("David Li warehouse"), new Phone("91031282"),
                        new Address("21 Lower Kent Ridge Rd, Singapore 119077"), new Remark("long term partner"),
                        getProductSetForWarehouse(Map.of("Panadol", 100))),
                new Warehouse(new Name("Irfan Ibrahim warehouse"), new Phone("92492021"),
                        new Address("21 Lower Kent Ridge Rd, Singapore 119077"),  new Remark("long term partner"),
                        getProductSetForWarehouse(Map.of("Panadol", 50))),
                new Warehouse(new Name("Roy Balakrishnan warehouse"), new Phone("92624417"),
                        new Address("21 Lower Kent Ridge Rd, Singapore 119077"),  new Remark("long term partner"),
                        getProductSetForWarehouse(Map.of("Panadol", 70))),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
=======
    public static ReadOnlyClinic getSampleClinic() {
        Clinic sampleAb = new Clinic();
>>>>>>> upstream/master:src/main/java/seedu/clinic/model/util/SampleDataUtil.java
        for (Supplier sampleSupplier : getSampleSuppliers()) {
            sampleAb.addSupplier(sampleSupplier);
        }

        for (Warehouse sampleWarehouse : getSampleWarehouses()) {
            sampleAb.addWarehouse(sampleWarehouse);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a product set containing the hashmap of strings given.
     */
    public static Set<Product> getProductSet(Map<String, String[]> productMap) {
        Set<Product> productSet = new HashSet<>();
        for (String productName:productMap.keySet()) {
            Set<Tag> productTags = getTagSet(productMap.get(productName));
            Product product = new Product(new Name(productName), productTags);
            productSet.add(product);
        }
        return productSet;
    }

    /**
     * Returns a product set containing the hashmap of strings given.
     */
    public static Set<Product> getProductSetForWarehouse(Map<String, Integer> productMap) {
        Set<Product> productSet = new HashSet<>();
        for (String productName:productMap.keySet()) {
            Product product = new Product(new Name(productName), productMap.get(productName));
            productSet.add(product);
        }
        return productSet;
    }
}
