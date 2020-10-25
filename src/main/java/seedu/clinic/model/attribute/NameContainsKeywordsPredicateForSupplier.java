package seedu.clinic.model.attribute;

import java.util.function.Predicate;

import seedu.clinic.model.supplier.Supplier;

/**
 * Tests that a {@code Supplier}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicateForSupplier implements Predicate<Supplier> {
    private final String keywords;

    public NameContainsKeywordsPredicateForSupplier(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Supplier supplier) {
        return keywords.equals(supplier.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicateForSupplier // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicateForSupplier) other).keywords)); // state check
    }

}
