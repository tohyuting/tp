package seedu.clinic.model.attribute;

import java.util.List;
import java.util.function.Predicate;

import seedu.clinic.commons.util.StringUtil;
import seedu.clinic.model.warehouse.Warehouse;

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
        boolean testValue;
        try {
            testValue = keywords.stream()
                    .anyMatch(keyword ->
                            StringUtil.containsWordIgnoreCase(warehouse.getName().fullName, keyword));
        } catch (IllegalArgumentException ex) {
            testValue = keywords.stream()
                    .anyMatch(keyword -> warehouse.getName().fullName.equals(keyword));
        }
        return testValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicateForWarehouse // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicateForWarehouse) other).keywords)); // state check
    }

}
