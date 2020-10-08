package seedu.clinic.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.clinic.model.Clinic;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Email;
import seedu.clinic.model.supplier.Name;
import seedu.clinic.model.supplier.Phone;
import seedu.clinic.model.supplier.Remark;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.tag.Tag;

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

    public static ReadOnlyClinic getSampleClinic() {
        Clinic sampleAb = new Clinic();
        for (Supplier sampleSupplier : getSampleSuppliers()) {
            sampleAb.addSupplier(sampleSupplier);
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
