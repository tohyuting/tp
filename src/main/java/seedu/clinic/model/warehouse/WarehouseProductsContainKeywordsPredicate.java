package seedu.clinic.model.warehouse;

import java.util.List;
import java.util.function.Predicate;

import seedu.clinic.commons.util.StringUtil;
import seedu.clinic.model.product.Product;

/**
 * Tests that any of the {@code Product} stored in a {@code Warehouse} matches any of the keywords given.
 */
public class WarehouseProductsContainKeywordsPredicate implements Predicate<Warehouse> {
    private final List<String> keywords;

    public WarehouseProductsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Warehouse warehouse) {
        Product[] products = warehouse.getProducts().toArray(Product[]::new);
        for (int i = 0; i < products.length; i++) {
            String productName = products[i].getProductName().fullName;
            boolean match = keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(productName, keyword));

            if (match) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WarehouseProductsContainKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((WarehouseProductsContainKeywordsPredicate) other).keywords)); // state check
    }

}
