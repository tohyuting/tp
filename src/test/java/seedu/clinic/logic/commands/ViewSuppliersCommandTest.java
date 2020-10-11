package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalClinic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.Messages;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForSupplier;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommand}.
 */
public class ViewSuppliersCommandTest {
    private final String type = "supplier";

    private Model model = new ModelManager(getTypicalClinic(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClinic(), new UserPrefs());

    @Test
    public void equals() {
        List<String> firstName = new ArrayList<>();
        firstName.add("first supplier");
        List<String> secondName = new ArrayList<>();
        secondName.add("second supplier");

        ViewCommand viewFirstCommand = new ViewCommand(type, firstName);
        ViewCommand viewSecondCommand = new ViewCommand(type, secondName);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(type, firstName);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different supplier -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void execute_oneWordInName_success() {
        String supplierName = "Fiona";
        String expectedMessage = String.format(Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 1);
        ViewCommand viewFirstCommand = new ViewCommand(type, Arrays.asList(supplierName));
        NameContainsKeywordsPredicateForSupplier supplierPredicate =
                new NameContainsKeywordsPredicateForSupplier(Arrays.asList(supplierName));
        model.updateFilteredSupplierList(supplierPredicate);
        expectedModel.updateFilteredSupplierList(supplierPredicate);
        assertCommandSuccess(viewFirstCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleWordInName_success() {
        String supplierName = "Fiona DANIEL";
        String[] supplierNames = supplierName.trim().split("\\s+");
        String expectedMessage = String.format(Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 2);
        ViewCommand viewFirstCommand = new ViewCommand(type, Arrays.asList(supplierNames));
        NameContainsKeywordsPredicateForSupplier supplierPredicate =
                new NameContainsKeywordsPredicateForSupplier(Arrays.asList(supplierNames));
        model.updateFilteredSupplierList(supplierPredicate);
        expectedModel.updateFilteredSupplierList(supplierPredicate);
        assertCommandSuccess(viewFirstCommand, model, expectedMessage, expectedModel);
    }

}
