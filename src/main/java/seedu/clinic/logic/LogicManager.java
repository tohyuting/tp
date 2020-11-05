package seedu.clinic.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.logic.commands.Command;
import seedu.clinic.logic.commands.CommandResult;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.ClinicParser;
import seedu.clinic.logic.parser.MacroParser;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.Model;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.ReadOnlyCommandHistory;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;
import seedu.clinic.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ClinicParser clinicParser;
    private final MacroParser macroParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        clinicParser = new ClinicParser();
        macroParser = new MacroParser();
    }

    @Override
    public CommandResult execute(String inputString) throws CommandException, ParseException {
        logger.info("----------------[USER INPUT][" + inputString + "]");

        CommandResult commandResult;
        String commandString = macroParser.parseMacro(model, inputString);

        logger.info("----------------[USER COMMAND][" + commandString + "]");

        Command command = clinicParser.parseCommand(commandString);
        commandResult = command.execute(model);

        try {
            storage.saveClinic(model.getClinic());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        try {
            storage.saveUserMacros(model.getUserMacros());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        try {
            storage.saveCommandHistory(commandString, model.getCommandHistoryFilePath());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        logger.info("----------------[CLI-nic data saved]");

        return commandResult;
    }

    @Override
    public ReadOnlyClinic getClinic() {
        return model.getClinic();
    }

    @Override
    public ObservableList<Supplier> getFilteredSupplierList() {
        return model.getFilteredSupplierList();
    }

    @Override
    public ObservableList<Warehouse> getFilteredWarehouseList() {
        return model.getFilteredWarehouseList();
    }

    @Override
    public Path getClinicFilePath() {
        return model.getClinicFilePath();
    }

    @Override
    public ReadOnlyUserMacros getUserMacros() {
        return model.getUserMacros();
    }

    @Override
    public ObservableList<Macro> getMacroList() {
        return model.getMacroList();
    }

    @Override
    public Path getUserMacrosFilePath() {
        return model.getUserMacrosFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyCommandHistory getCommandHistory() {
        return model.getCommandHistory();
    }
}
