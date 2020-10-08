package seedu.address.model.attribute;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.warehouse.Warehouse;

/**
 * Tests that a {{@code Warehouse}}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicateForWarehouse implements Predicate<Warehouse> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicateForWarehouse(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Warehouse warehouse) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(warehouse.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicateForWarehouse // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicateForWarehouse) other).keywords)); // state check
    }

}
