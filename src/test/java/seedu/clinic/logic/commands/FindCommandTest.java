package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.commons.core.Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.testutil.TypicalSupplier.CARL;
import static seedu.clinic.testutil.TypicalSupplier.ELLE;
import static seedu.clinic.testutil.TypicalSupplier.FIONA;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalClinic;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.supplier.ProductsContainKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalClinic(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClinic(), new UserPrefs());

    @Test
    public void equals() {
        ProductsContainKeywordsPredicate firstPredicate =
                new ProductsContainKeywordsPredicate(Collections.singletonList("first"));
        ProductsContainKeywordsPredicate secondPredicate =
                new ProductsContainKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different supplier -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noSupplierFound() {
        String expectedMessage = String.format(MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 0);
        ProductsContainKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredSupplierList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSupplierList());
    }

    @Test
    public void execute_multipleKeywords_multipleSuppliersFound() {
        String expectedMessage = String.format(MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 3);
        ProductsContainKeywordsPredicate predicate = preparePredicate("supplier mask");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredSupplierList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredSupplierList());
    }

    /**
     * Parses {@code userInput} into a {@code ProductsContainKeywordsPredicate}.
     */
    private ProductsContainKeywordsPredicate preparePredicate(String userInput) {
        return new ProductsContainKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
