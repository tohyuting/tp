package seedu.clinic.model.warehouse;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.clinic.commons.util.StringUtil;
import seedu.clinic.model.product.Product;

/**
 * Tests whether the {@code Name}, {@code Remark} or any of the {@code Product} stored in {@code Warehouse} matches any
 * of the keywords given.
 */
public class WarehousePredicate implements Predicate<Warehouse> {
    private Optional<List<String>> nameKeywords = Optional.empty();
    private Optional<List<String>> productKeywords = Optional.empty();
    private Optional<List<String>> remarkKeywords = Optional.empty();

    /**
     * Constructs a new {@code WarehousePredicate}.
     */
    public WarehousePredicate(List<String> nameKeywords, List<String> productKeywords,
            List<String> remarkKeywords) {
        if (nameKeywords.size() != 0) {
            this.nameKeywords = Optional.ofNullable(nameKeywords);
        }

        if (productKeywords.size() != 0) {
            this.productKeywords = Optional.ofNullable(productKeywords);
        }

        if (remarkKeywords.size() != 0) {
            this.remarkKeywords = Optional.ofNullable(remarkKeywords);
        }
    }

    @Override
    public boolean test(Warehouse warehouse) {
        if (nameKeywords.isPresent()) {
            boolean match = nameKeywords.get().stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(warehouse.getName().fullName, keyword));

            if (match) {
                return true;
            }
        }

        if (productKeywords.isPresent()) {
            Product[] products = warehouse.getProducts().toArray(Product[]::new);
            for (int i = 0; i < products.length; i++) {
                String productName = products[i].getProductName().fullName;
                boolean match = productKeywords.get().stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(productName, keyword));

                if (match) {
                    return true;
                }
            }
        }

        if (remarkKeywords.isPresent()) {
            return remarkKeywords.get().stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(warehouse.getRemark().value, keyword));
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WarehousePredicate // instanceof handles nulls
                && nameKeywords.equals(((WarehousePredicate) other).nameKeywords)
                && productKeywords.equals(((WarehousePredicate) other).productKeywords)
                && remarkKeywords.equals(((WarehousePredicate) other).remarkKeywords)); // state check
    }

}
