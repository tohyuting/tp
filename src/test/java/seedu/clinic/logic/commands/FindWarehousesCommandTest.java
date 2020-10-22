package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.commons.core.Messages.MESSAGE_WAREHOUSE_LISTED_OVERVIEW;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.testutil.TypicalWarehouse.CARL;
import static seedu.clinic.testutil.TypicalWarehouse.ELLE;
import static seedu.clinic.testutil.TypicalWarehouse.FIONA;
import static seedu.clinic.testutil.TypicalWarehouse.getTypicalWarehouseOnlyClinic;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.warehouse.WarehousePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindWarehousesCommandTest {
    private Model model = new ModelManager(getTypicalWarehouseOnlyClinic(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWarehouseOnlyClinic(), new UserPrefs());

    @Test
    public void equals() {
        WarehousePredicate firstPredicate =
                new WarehousePredicate(Collections.singletonList("first"));
        WarehousePredicate secondPredicate =
                new WarehousePredicate(Collections.singletonList("second"));

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

        // different warehouse -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noWarehouseFound() {
        String expectedMessage = String.format(MESSAGE_WAREHOUSE_LISTED_OVERVIEW, 0);
        WarehousePredicate warehousePredicate = preparePredicate(" ");
        FindCommand command = new FindCommand(warehousePredicate);
        expectedModel.updateFilteredWarehouseList(warehousePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredWarehouseList());
    }

    @Test
    public void execute_multipleKeywords_multipleSuppliersFound() {
        String expectedMessage = String.format(MESSAGE_WAREHOUSE_LISTED_OVERVIEW, 3);
        WarehousePredicate warehousePredicate = preparePredicate("warehouse syrup");
        FindCommand command = new FindCommand(warehousePredicate);
        expectedModel.updateFilteredWarehouseList(warehousePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredWarehouseList());
    }

    /**
     * Parses {@code userInput} into a {@code WarehousePredicate}.
     */
    private WarehousePredicate preparePredicate(String userInput) {
        return new WarehousePredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
