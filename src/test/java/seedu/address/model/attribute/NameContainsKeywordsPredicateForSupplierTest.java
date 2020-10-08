package seedu.address.model.attribute;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SupplierBuilder;

public class NameContainsKeywordsPredicateForSupplierTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicateForSupplier firstPredicate =
                new NameContainsKeywordsPredicateForSupplier(firstPredicateKeywordList);
        NameContainsKeywordsPredicateForSupplier secondPredicate =
                new NameContainsKeywordsPredicateForSupplier(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicateForSupplier firstPredicateCopy =
                new NameContainsKeywordsPredicateForSupplier(firstPredicateKeywordList);
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
        NameContainsKeywordsPredicateForSupplier predicate =
                new NameContainsKeywordsPredicateForSupplier(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicateForSupplier(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicateForSupplier(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicateForSupplier(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));

        // Non-alphanumeric keywords included
        predicate = new NameContainsKeywordsPredicateForSupplier(Arrays.asList("Pte.Ltd", "&"));
        assertTrue(predicate.test(new SupplierBuilder().withName("M & M Pte.Ltd").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicateForSupplier predicate =
                new NameContainsKeywordsPredicateForSupplier(Collections.emptyList());
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicateForSupplier(Arrays.asList("Carol"));
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicateForSupplier(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withRemark("Main Street").build()));
    }
}
