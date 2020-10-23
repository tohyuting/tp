package seedu.clinic.model.attribute;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.testutil.WarehouseBuilder;

public class NameContainsKeywordsPredicateForWarehouseTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
        List<String> firstPredicateKeywordListWithSpaces = Collections.singletonList("first keywords");

        NameContainsKeywordsPredicateForWarehouse firstPredicate =
                new NameContainsKeywordsPredicateForWarehouse(firstPredicateKeywordList);
        NameContainsKeywordsPredicateForWarehouse secondPredicate =
                new NameContainsKeywordsPredicateForWarehouse(secondPredicateKeywordList);
        NameContainsKeywordsPredicateForWarehouse firstPredicateWithSpaces =
                new NameContainsKeywordsPredicateForWarehouse(firstPredicateKeywordListWithSpaces);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicateKeywordListWithSpaces.equals(firstPredicateKeywordListWithSpaces));

        // same values -> returns true
        NameContainsKeywordsPredicateForWarehouse firstPredicateCopy =
                new NameContainsKeywordsPredicateForWarehouse(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different supplier -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicateForWarehouse predicate =
                new NameContainsKeywordsPredicateForWarehouse(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new WarehouseBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicateForWarehouse(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new WarehouseBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicateForWarehouse(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new WarehouseBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicateForWarehouse(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new WarehouseBuilder().withName("Alice Bob").build()));

        // Non-alphanumeric keywords included
        predicate = new NameContainsKeywordsPredicateForWarehouse(Arrays.asList("Pte.Ltd", "&"));
        assertTrue(predicate.test(new WarehouseBuilder().withName("M & M Pte.Ltd").build()));

        // Keywords with spaces
        predicate = new NameContainsKeywordsPredicateForWarehouse(Arrays.asList("Alice Tan Warehouse"));
        assertTrue(predicate.test(new WarehouseBuilder().withName("Alice Tan Warehouse").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicateForWarehouse predicate =
                new NameContainsKeywordsPredicateForWarehouse(Collections.emptyList());
        assertFalse(predicate.test(new WarehouseBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicateForWarehouse(Arrays.asList("Carol"));
        assertFalse(predicate.test(new WarehouseBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicateForWarehouse(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new WarehouseBuilder().withName("Alice").withPhone("12345")
                .withAddress("alice address").withRemark("Main Street").build()));
    }
}
