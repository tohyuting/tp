package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.commons.core.Messages;
import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.Type;
import seedu.clinic.model.Model;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForSupplier;
import seedu.clinic.model.attribute.NameContainsKeywordsPredicateForWarehouse;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Displays specific supplier(s) or warehouse(s) with name that matches any of keywords input by user.
 * Keyword matching is case insensitive.
 * Keyword only matches whole word e.g. bernice will match "bernice yeoh" but not "berniceyeoh".
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = "View Command Usage\n\nViews information related to a"
            + " particular supplier or warehouse."
            + " TYPE specified should be either s for supplier or w for warehouse."
            + " INDEX must be a positive integer, not exceeding the total length of the supplier/warehouse list.\n\n"
            + "Parameters:\n"
            + "view " + PREFIX_TYPE + "TYPE " + PREFIX_INDEX + "INDEX\n\n"
            + "Example:\n"
            + "1) " + COMMAND_WORD + " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "2\n"
            + "2) " + COMMAND_WORD + " " + PREFIX_TYPE + "w " + PREFIX_INDEX + "5";
    public static final String MESSAGE_MISSING_INDEX = "Index has to be present!\n%1$s";
    public static final String MESSAGE_MISSING_TYPE = "A type, supplier (ct/s) or warehouse (ct/s)"
            + " has to be present!\n%1$s";
    public static final String MESSAGE_NO_PREFIX = "Please specify type and index using "
            + "ct/ and i/ prefixes \n%1$s";
    public static final String MESSAGE_INVALID_TYPE_VIEW = "Please specify a correct type,"
            + " either ct/s or ct/w\n%1$s";
    public static final String MESSAGE_INVALID_USAGE = "The input contains unnecessary arguments. Please "
            + "ensure that you only include prefixes specified in the User Guide.\n%1$s";

    private static final String LOG_MESSAGE_VIEW_SUPPLIER = "View Command wants to view a supplier.";
    private static final String LOG_MESSAGE_VIEW_WAREHOUSE = "View Command wants to view a warehouse.";
    private static final String LOG_MESSAGE_SUPPLIER_RETRIEVED = "Retrieved supplier to be viewed from supplier list.";
    private static final String LOG_MESSAGE_WAREHOUSE_RETRIEVED =
            "Retrieved warehouse to be viewed from warehouse list.";
    private static final String LOG_MESSAGE_MODEL_SHOW_SUPPLIER = "Updated model to show supplier to be viewed.";
    private static final String LOG_MESSAGE_MODEL_SHOW_WAREHOUSE = "Updated model to show warehouse to be viewed.";

    private static final String INVALID_WAREHOUSE_ASSERTION = "The command type should be warehouse here!";

    private final Type type;
    private final Index index;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates a new ViewCommand object.
     *
     * @param type takes in type of the viewCommand object, either supplier or warehouse.
     * @param index takes in name(s) as keywords to find warehouse(s) or supplier(s).
     */
    public ViewCommand(Type type, Index index) {
        this.type = type;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (type.equals(Type.SUPPLIER)) {
            return viewSupplier(model);
        } else {
            return viewWarehouse(model);
        }
    }

    private CommandResult viewSupplier(Model model) throws CommandException {
        List<Supplier> supplierList = model.getFilteredSupplierList();

        logger.log(Level.INFO, LOG_MESSAGE_VIEW_SUPPLIER);

        if (index.getZeroBased() >= supplierList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        Supplier supplierToView = supplierList.get(index.getZeroBased());

        logger.log(Level.INFO, LOG_MESSAGE_SUPPLIER_RETRIEVED);

        NameContainsKeywordsPredicateForSupplier supplierPredicate =
                new NameContainsKeywordsPredicateForSupplier(supplierToView.getName().fullName);
        model.updateFilteredSupplierList(supplierPredicate);

        logger.log(Level.INFO, LOG_MESSAGE_MODEL_SHOW_SUPPLIER);
        model.saveVersionedClinic();
        return new CommandResult(String.format(Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW,
                        model.getFilteredSupplierList().size()));
    }

    private CommandResult viewWarehouse(Model model) throws CommandException {
        List<Warehouse> warehouseList = model.getFilteredWarehouseList();

        assert type.equals(Type.WAREHOUSE) : INVALID_WAREHOUSE_ASSERTION;

        logger.log(Level.INFO, LOG_MESSAGE_VIEW_WAREHOUSE);

        if (index.getZeroBased() >= warehouseList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WAREHOUSE_DISPLAYED_INDEX);
        }
        Warehouse warehouseToView = warehouseList.get(index.getZeroBased());

        logger.log(Level.INFO, LOG_MESSAGE_WAREHOUSE_RETRIEVED);

        NameContainsKeywordsPredicateForWarehouse warehousePredicate =
                new NameContainsKeywordsPredicateForWarehouse(warehouseToView.getName().fullName);

        model.updateFilteredWarehouseList(warehousePredicate);
        logger.log(Level.INFO, LOG_MESSAGE_MODEL_SHOW_WAREHOUSE);
        model.saveVersionedClinic();
        return new CommandResult(String.format(Messages.MESSAGE_WAREHOUSE_LISTED_OVERVIEW,
                        model.getFilteredWarehouseList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && type.equals(((ViewCommand) other).type)
                && index.equals(((ViewCommand) other).index));
    }
}
