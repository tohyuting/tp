package seedu.clinic.model.attribute;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.clinic.testutil.WarehouseBuilder;

public class NameContainsKeywordsPredicateForWarehouseTest {

    @Test
    public void equals() {
        String firstPredicateKeywordWithSpaces = "first keywords";
        String secondPredicateKeywordWithSpaces = "second keywords";

        NameContainsKeywordsPredicateForWarehouse firstPredicateWithSpaces =
                new NameContainsKeywordsPredicateForWarehouse(firstPredicateKeywordWithSpaces);

        NameContainsKeywordsPredicateForWarehouse secondPredicateWithSpaces =
                new NameContainsKeywordsPredicateForWarehouse(secondPredicateKeywordWithSpaces);

        // same object -> returns true
        assertTrue(firstPredicateWithSpaces.equals(firstPredicateWithSpaces));
        assertTrue(firstPredicateKeywordWithSpaces.equals(firstPredicateKeywordWithSpaces));

        // same values -> returns true
        NameContainsKeywordsPredicateForWarehouse firstPredicateCopy =
                new NameContainsKeywordsPredicateForWarehouse(firstPredicateKeywordWithSpaces);
        assertTrue(firstPredicateWithSpaces.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicateWithSpaces.equals(1));

        // null -> returns false
        assertFalse(firstPredicateWithSpaces.equals(null));

        // different supplier -> returns false
        assertFalse(firstPredicateWithSpaces.equals(secondPredicateWithSpaces));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicateForWarehouse predicate =
                new NameContainsKeywordsPredicateForWarehouse("Alice");
        assertTrue(predicate.test(new WarehouseBuilder().withName("Alice").build()));

        // keywords with spaces
        predicate = new NameContainsKeywordsPredicateForWarehouse("Alice Bob");
        assertTrue(predicate.test(new WarehouseBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicateForWarehouse predicate =
                new NameContainsKeywordsPredicateForWarehouse("");
        assertFalse(predicate.test(new WarehouseBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicateForWarehouse("Carol");
        assertFalse(predicate.test(new WarehouseBuilder().withName("Alice Bob").build()));

    }
}
