package seedu.clinic.model.supplier;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.clinic.commons.util.StringUtil;
import seedu.clinic.model.product.Product;

/**
 * Tests whether the {@code Name}, {@code Remark} or any of the {@code Product} sold by {@code Supplier} matches any
 * of the keywords given.
 */
public class SupplierPredicate implements Predicate<Supplier> {
    private Optional<List<String>> nameKeywords = Optional.empty();
    private Optional<List<String>> productKeywords = Optional.empty();
    private Optional<List<String>> remarkKeywords = Optional.empty();

    /**
     * Constructs a new {@code SupplierPredicate}.
     */
    public SupplierPredicate(List<String> nameKeywords, List<String> productKeywords,
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
    public boolean test(Supplier supplier) {
        if (nameKeywords.isPresent()) {
            boolean match = nameKeywords.get().stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(supplier.getName().fullName, keyword));

            if (match) {
                return true;
            }
        }

        if (productKeywords.isPresent()) {
            Product[] products = supplier.getProducts().toArray(Product[]::new);
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
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(supplier.getRemark().value, keyword));
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SupplierPredicate // instanceof handles nulls
                && nameKeywords.equals(((SupplierPredicate) other).nameKeywords)
                && productKeywords.equals(((SupplierPredicate) other).productKeywords)
                && remarkKeywords.equals(((SupplierPredicate) other).remarkKeywords)); // state check
    }

}
