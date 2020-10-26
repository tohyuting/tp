package seedu.clinic.model.attribute;

import java.util.function.Predicate;

import seedu.clinic.model.warehouse.Warehouse;

/**
 * Tests that a {{@code Warehouse}}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicateForWarehouse implements Predicate<Warehouse> {
    private final String keywords;

    public NameContainsKeywordsPredicateForWarehouse(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Warehouse warehouse) {
        return keywords.equals(warehouse.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicateForWarehouse // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicateForWarehouse) other).keywords)); // state check
    }

}
