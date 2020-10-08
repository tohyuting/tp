<<<<<<< HEAD:src/main/java/seedu/clinic/model/attribute/NameContainsKeywordsPredicateForSupplier.java
package seedu.address.model.attribute;
=======
package seedu.clinic.model.supplier;
>>>>>>> upstream/master:src/main/java/seedu/clinic/model/supplier/NameContainsKeywordsPredicate.java

import java.util.List;
import java.util.function.Predicate;

<<<<<<< HEAD:src/main/java/seedu/clinic/model/attribute/NameContainsKeywordsPredicateForSupplier.java
import seedu.address.commons.util.StringUtil;
import seedu.address.model.supplier.Supplier;
=======
import seedu.clinic.commons.util.StringUtil;
>>>>>>> upstream/master:src/main/java/seedu/clinic/model/supplier/NameContainsKeywordsPredicate.java

/**
 * Tests that a {@code Supplier}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicateForSupplier implements Predicate<Supplier> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicateForSupplier(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Supplier supplier) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(supplier.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicateForSupplier // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicateForSupplier) other).keywords)); // state check
    }

}
