package seedu.clinic.logic.commands;

import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.testutil.TypicalSupplier.getTypicalVersionedClinic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;
import seedu.clinic.testutil.SupplierBuilder;
import seedu.clinic.testutil.WarehouseBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalVersionedClinic(), new UserPrefs(), new UserMacros(), new CommandHistory());
    }

    @Test
    public void execute_newSupplier_success() {
        Supplier validSupplier = new SupplierBuilder().build();

        Model expectedModel = new ModelManager(model.getClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        expectedModel.addSupplier(validSupplier);
        expectedModel.saveVersionedClinic();
        assertCommandSuccess(new AddCommand(validSupplier), model,
                String.format(AddCommand.MESSAGE_SUPPLIER_SUCCESS, validSupplier), expectedModel);
    }

    @Test
    public void execute_duplicateSupplier_throwsCommandException() {
        Supplier supplierInList = model.getClinic().getSupplierList().get(0);
        assertCommandFailure(new AddCommand(supplierInList), model, AddCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

    @Test
    public void execute_newWarehouse_success() {
        Warehouse validWarehouse = new WarehouseBuilder().build();

        Model expectedModel = new ModelManager(model.getClinic(), new UserPrefs(), new UserMacros(),
                new CommandHistory());
        expectedModel.addWarehouse(validWarehouse);
        expectedModel.saveVersionedClinic();
        assertCommandSuccess(new AddCommand(validWarehouse), model,
                String.format(AddCommand.MESSAGE_WAREHOUSE_SUCCESS, validWarehouse), expectedModel);
    }

    @Test
    public void execute_duplicateWarehouse_throwsCommandException() {
        Warehouse warehouseInList = model.getClinic().getWarehouseList().get(0);
        assertCommandFailure(new AddCommand(warehouseInList), model, AddCommand.MESSAGE_DUPLICATE_WAREHOUSE);
    }

}
