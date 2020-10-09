package seedu.clinic.model.supplier;

import java.util.List;
import java.util.function.Predicate;

import seedu.clinic.commons.util.StringUtil;
import seedu.clinic.model.product.Product;

/**
 * Tests that any of the {@code Product} sold by {@code Supplier} matches any of the keywords given.
 */
public class ProductsContainKeywordsPredicate implements Predicate<Supplier> {
    private final List<String> keywords;

    public ProductsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Supplier supplier) {
        Product[] products = supplier.getProducts().toArray(Product[]::new);
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
                || (other instanceof ProductsContainKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ProductsContainKeywordsPredicate) other).keywords)); // state check
    }

}
