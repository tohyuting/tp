package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.commons.core.Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW;
import static seedu.clinic.commons.core.Messages.MESSAGE_WAREHOUSE_LISTED_OVERVIEW;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.testutil.TypicalSupplier.CARL;
import static seedu.clinic.testutil.TypicalSupplier.ELLE;
import static seedu.clinic.testutil.TypicalSupplier.FIONA;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalVersionedClinic;
import static seedu.clinic.testutil.TypicalWarehouse.ALICE;
import static seedu.clinic.testutil.TypicalWarehouse.BENSON;
import static seedu.clinic.testutil.TypicalWarehouse.getTypicalWarehouseOnlyClinic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.supplier.SupplierPredicate;
import seedu.clinic.model.warehouse.WarehousePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model modelForSupplier = new ModelManager(getTypicalVersionedClinic(), new UserPrefs(), new UserMacros(),
            new CommandHistory());
    private Model expectedModelForSupplier = new ModelManager(getTypicalVersionedClinic(),
            new UserPrefs(), new UserMacros(), new CommandHistory());
    private Model modelForWarehouse = new ModelManager(getTypicalWarehouseOnlyClinic(), new UserPrefs(),
            new UserMacros(), new CommandHistory());
    private Model expectedModelForWarehouse = new ModelManager(getTypicalWarehouseOnlyClinic(), new UserPrefs(),
            new UserMacros(), new CommandHistory());

    @Test
    public void equals() {
        // Supplier
        SupplierPredicate firstSupplierPredicate = new SupplierPredicate(Collections.singletonList("first supplier"),
                Collections.singletonList("first supplier"),
                Collections.singletonList("first supplier"));
        SupplierPredicate secondSupplierPredicate = new SupplierPredicate(Collections.singletonList("second supplier"),
                Collections.singletonList("second supplier"),
                Collections.singletonList("second supplier"));
        // Warehouse
        WarehousePredicate firstWarehousePredicate =
                new WarehousePredicate(Collections.singletonList("first warehouse"),
                Collections.singletonList("first warehouse"),
                Collections.singletonList("first warehouse"));
        WarehousePredicate secondWarehousePredicate =
                new WarehousePredicate(Collections.singletonList("second warehouse"),
                Collections.singletonList("second warehouse"),
                Collections.singletonList("second warehouse"));


        FindCommand firstFindCommandForSupplier = new FindCommand(firstSupplierPredicate);
        FindCommand secondFindCommandForSupplier = new FindCommand(secondSupplierPredicate);
        FindCommand firstFindCommandForWarehouse = new FindCommand(firstWarehousePredicate);
        FindCommand secondFindCommandForWarehouse = new FindCommand(secondWarehousePredicate);

        // same object -> returns true
        assertTrue(firstFindCommandForSupplier.equals(firstFindCommandForSupplier));
        assertTrue(secondFindCommandForSupplier.equals(secondFindCommandForSupplier));
        assertTrue(firstFindCommandForWarehouse.equals(firstFindCommandForWarehouse));
        assertTrue(secondFindCommandForWarehouse.equals(secondFindCommandForWarehouse));

        // same values -> returns true
        FindCommand firstFindCommandForSupplierCopy = new FindCommand(firstSupplierPredicate);
        assertTrue(firstFindCommandForSupplier.equals(firstFindCommandForSupplierCopy));
        FindCommand secondFindCommandForSupplierCopy = new FindCommand(secondSupplierPredicate);
        assertTrue(secondFindCommandForSupplier.equals(secondFindCommandForSupplierCopy));
        FindCommand firstFindCommandForWarehouseCopy = new FindCommand(firstWarehousePredicate);
        assertTrue(firstFindCommandForWarehouse.equals(firstFindCommandForWarehouseCopy));
        FindCommand secondFindCommandForWarehouseCopy = new FindCommand(secondWarehousePredicate);
        assertTrue(secondFindCommandForWarehouse.equals(secondFindCommandForWarehouseCopy));

        // different types -> returns false
        assertFalse(firstFindCommandForSupplier.equals(1));
        assertFalse(secondFindCommandForSupplier.equals(1));
        assertFalse(firstFindCommandForWarehouse.equals(1));
        assertFalse(secondFindCommandForWarehouse.equals(1));

        // null -> returns false
        assertFalse(firstFindCommandForSupplier.equals(null));
        assertFalse(secondFindCommandForSupplier.equals(null));
        assertFalse(firstFindCommandForWarehouse.equals(null));
        assertFalse(secondFindCommandForWarehouse.equals(null));

        // different supplier -> returns false
        assertFalse(firstFindCommandForSupplier.equals(secondFindCommandForSupplier));

        // different warehouse -> return false
        assertFalse(firstFindCommandForWarehouse.equals(secondFindCommandForWarehouse));
    }

    @Test
    public void execute_zeroKeywords_noSupplierFound() {
        List<String> nameKeywords = Arrays.asList(new String[]{});
        List<String> productKeywords = Arrays.asList(new String[]{});
        List<String> remarkKeywords = Arrays.asList(new String[]{});

        String expectedMessage = String.format(MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 0);
        SupplierPredicate supplierPredicate = prepareSupplierPredicate(nameKeywords, productKeywords, remarkKeywords);
        FindCommand command = new FindCommand(supplierPredicate);
        expectedModelForSupplier.updateFilteredSupplierList(supplierPredicate);
        assertCommandSuccess(command, modelForSupplier, expectedMessage, expectedModelForSupplier);
        assertEquals(Collections.emptyList(), modelForSupplier.getFilteredSupplierList());
    }

    @Test
    public void execute_zeroKeywords_noWarehouseFound() {
        List<String> nameKeywords = Arrays.asList(new String[]{});
        List<String> productKeywords = Arrays.asList(new String[]{});
        List<String> remarkKeywords = Arrays.asList(new String[]{});

        String expectedMessage = String.format(MESSAGE_WAREHOUSE_LISTED_OVERVIEW, 0);
        WarehousePredicate warehousePredicate = prepareWarehousePredicate(nameKeywords, productKeywords,
                remarkKeywords);
        FindCommand command = new FindCommand(warehousePredicate);
        expectedModelForWarehouse.updateFilteredWarehouseList(warehousePredicate);
        assertCommandSuccess(command, modelForWarehouse, expectedMessage, expectedModelForWarehouse);
        assertEquals(Collections.emptyList(), modelForWarehouse.getFilteredWarehouseList());
    }

    @Test
    public void execute_multipleKeywords_multipleSuppliersFound() {
        List<String> nameKeywords = Arrays.asList(new String[]{});
        List<String> productKeywords = Arrays.asList("mask");
        List<String> remarkKeywords = Arrays.asList("cheap", "fast", "industry", "leader");

        String expectedMessage = String.format(MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 3);
        SupplierPredicate supplierPredicate = prepareSupplierPredicate(nameKeywords, productKeywords, remarkKeywords);
        FindCommand command = new FindCommand(supplierPredicate);
        expectedModelForSupplier.updateFilteredSupplierList(supplierPredicate);
        assertCommandSuccess(command, modelForSupplier, expectedMessage, expectedModelForSupplier);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), modelForSupplier.getFilteredSupplierList());
    }

    @Test
    public void execute_multipleKeywords_noSuppliersFound() {
        List<String> nameKeywords = Arrays.asList("name1", "name2");
        List<String> productKeywords = Arrays.asList("product1");
        List<String> remarkKeywords = Arrays.asList("remark1", "remark2");

        String expectedMessage = String.format(MESSAGE_SUPPLIERS_LISTED_OVERVIEW, 0);
        SupplierPredicate supplierPredicate = prepareSupplierPredicate(nameKeywords, productKeywords, remarkKeywords);
        FindCommand command = new FindCommand(supplierPredicate);
        expectedModelForSupplier.updateFilteredSupplierList(supplierPredicate);
        assertCommandSuccess(command, modelForSupplier, expectedMessage, expectedModelForSupplier);
        assertEquals(Collections.EMPTY_LIST, modelForSupplier.getFilteredSupplierList());
    }

    @Test
    public void execute_multipleKeywords_multipleWarehousesFound() {
        List<String> nameKeywords = Arrays.asList("alice", "benson");
        List<String> productKeywords = Arrays.asList("gauze", "towel");
        List<String> remarkKeywords = Arrays.asList("biggest", "near", "central");

        String expectedMessage = String.format(MESSAGE_WAREHOUSE_LISTED_OVERVIEW, 2);
        WarehousePredicate warehousePredicate = prepareWarehousePredicate(nameKeywords, productKeywords,
                remarkKeywords);
        FindCommand command = new FindCommand(warehousePredicate);
        expectedModelForWarehouse.updateFilteredWarehouseList(warehousePredicate);
        assertCommandSuccess(command, modelForWarehouse, expectedMessage, expectedModelForWarehouse);
        assertEquals(Arrays.asList(ALICE, BENSON), modelForWarehouse.getFilteredWarehouseList());
    }

    @Test
    public void execute_multipleKeywords_noWarehousesFound() {
        List<String> nameKeywords = Arrays.asList("name1", "name2");
        List<String> productKeywords = Arrays.asList("product1");
        List<String> remarkKeywords = Arrays.asList("remark1", "remark2");

        String expectedMessage = String.format(MESSAGE_WAREHOUSE_LISTED_OVERVIEW, 0);
        WarehousePredicate warehousePredicate = prepareWarehousePredicate(nameKeywords, productKeywords,
                remarkKeywords);
        FindCommand command = new FindCommand(warehousePredicate);
        expectedModelForWarehouse.updateFilteredWarehouseList(warehousePredicate);
        assertCommandSuccess(command, modelForWarehouse, expectedMessage, expectedModelForWarehouse);
        assertEquals(Collections.EMPTY_LIST, modelForWarehouse.getFilteredWarehouseList());
    }

    /**
     * Parses {@code userInput} into a {@code SupplierPredicate}.
     */
    private SupplierPredicate prepareSupplierPredicate(List<String> nameKeywords, List<String> productKeywords,
            List<String> remarkKeywords) {
        return new SupplierPredicate(nameKeywords, productKeywords, remarkKeywords);
    }

    /**
     * Parses {@code userInput} into a {@code WarehousePredicate}.
     */
    private WarehousePredicate prepareWarehousePredicate(List<String> nameKeywords, List<String> productKeywords,
            List<String> remarkKeywords) {
        return new WarehousePredicate(nameKeywords, productKeywords, remarkKeywords);
    }
}
