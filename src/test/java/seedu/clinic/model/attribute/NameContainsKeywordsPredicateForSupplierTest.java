package seedu.clinic.model.attribute;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.clinic.testutil.SupplierBuilder;

public class NameContainsKeywordsPredicateForSupplierTest {

    @Test
    public void equals() {
        String firstPredicateKeywordWithSpaces = "first keywords";
        String secondPredicateKeywordWithSpaces = "second keywords";

        NameContainsKeywordsPredicateForSupplier firstPredicateWithSpaces =
                new NameContainsKeywordsPredicateForSupplier(firstPredicateKeywordWithSpaces);
        NameContainsKeywordsPredicateForSupplier secondPredicateWithSpaces =
                new NameContainsKeywordsPredicateForSupplier(secondPredicateKeywordWithSpaces);

        // same object -> returns true
        assertTrue(firstPredicateWithSpaces.equals(firstPredicateWithSpaces));

        // same values -> returns true
        NameContainsKeywordsPredicateForSupplier firstPredicateCopy =
                new NameContainsKeywordsPredicateForSupplier(firstPredicateKeywordWithSpaces);
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
        NameContainsKeywordsPredicateForSupplier predicate =
                new NameContainsKeywordsPredicateForSupplier("Alice");
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice").build()));

        // Non-alphanumeric keywords included
        predicate = new NameContainsKeywordsPredicateForSupplier("Pte.Ltd &");
        assertTrue(predicate.test(new SupplierBuilder().withName("Pte.Ltd &").build()));

        // keywords containing spaces
        predicate = new NameContainsKeywordsPredicateForSupplier("Bernice Yu Pte Ltd");
        assertTrue(predicate.test(new SupplierBuilder().withName("Bernice Yu Pte Ltd").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicateForSupplier predicate =
                new NameContainsKeywordsPredicateForSupplier("");
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicateForSupplier("Carol");
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));
    }
}
