package seedu.clinic.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.logic.commands.CommandResult;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.Model;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.ReadOnlyCommandHistory;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the Clinic.
     *
     * @see seedu.clinic.model.Model#getClinic()
     */
    ReadOnlyClinic getClinic();

    /** Returns an unmodifiable view of the filtered list of suppliers */
    ObservableList<Supplier> getFilteredSupplierList();

    /** Returns an unmodifiable view of the filtered list of warehouses */
    ObservableList<Warehouse> getFilteredWarehouseList();

    /**
     * Returns the user prefs' clinic file path.
     */
    Path getClinicFilePath();

    /**
     * Returns the User Macros.
     *
     * @see Model#getUserMacros()
     */
    ReadOnlyUserMacros getUserMacros();

    /** Returns an unmodifiable view of the list of macros */
    ObservableList<Macro> getMacroList();

    /**
     * Returns the user macros file path according to user prefs.
     */
    Path getUserMacrosFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    ReadOnlyCommandHistory getCommandHistory();
}
