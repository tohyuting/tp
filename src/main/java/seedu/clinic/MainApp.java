package seedu.clinic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.clinic.commons.core.Config;
import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.commons.core.Version;
import seedu.clinic.commons.exceptions.DataConversionException;
import seedu.clinic.commons.util.ConfigUtil;
import seedu.clinic.commons.util.StringUtil;
import seedu.clinic.logic.Logic;
import seedu.clinic.logic.LogicManager;
import seedu.clinic.model.Clinic;
import seedu.clinic.model.CommandHistory;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.ReadOnlyClinic;
import seedu.clinic.model.ReadOnlyCommandHistory;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.ReadOnlyUserPrefs;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.util.SampleDataUtil;
import seedu.clinic.storage.ClinicStorage;
import seedu.clinic.storage.CommandHistoryStorage;
import seedu.clinic.storage.JsonClinicStorage;
import seedu.clinic.storage.JsonUserMacrosStorage;
import seedu.clinic.storage.JsonUserPrefsStorage;
import seedu.clinic.storage.Storage;
import seedu.clinic.storage.StorageManager;
import seedu.clinic.storage.TextFileCommandHistoryStorage;
import seedu.clinic.storage.UserMacrosStorage;
import seedu.clinic.storage.UserPrefsStorage;
import seedu.clinic.ui.Ui;
import seedu.clinic.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing CLI-nic ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ClinicStorage clinicStorage = new JsonClinicStorage(userPrefs.getClinicFilePath());
        UserMacrosStorage userMacrosStorage = new JsonUserMacrosStorage(userPrefs.getUserMacrosFilePath());
        CommandHistoryStorage commandHistoryStorage = new TextFileCommandHistoryStorage(
                userPrefs.getCommandHistoryFilePath());
        storage = new StorageManager(clinicStorage, userPrefsStorage, userMacrosStorage, commandHistoryStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s CLI-nic data, user macros, and
     * {@code userPrefs}. <br>
     * The data from the sample CLI-nic data will be used instead if {@code storage}'s clinic is not found,
     * or an empty clinic will be used instead if errors occur when reading {@code storage}'s clinic.
     * An empty User Macros model will be used if {@code storage}'s User Macros are not found or if errors
     * occur when reading {@code storage}'s User Macros.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyClinic> clinicOptional;
        ReadOnlyClinic initialClinicData;

        try {
            clinicOptional = storage.readClinic();
            if (clinicOptional.isEmpty()) {
                logger.info("CLI-nic data file not found. Will be starting with a sample CLI-nic");
            }
            initialClinicData = clinicOptional.orElseGet(SampleDataUtil::getSampleClinic);
        } catch (DataConversionException e) {
            logger.warning("CLI-nic data file not in the correct format. Will be starting with an empty CLI-nic");
            initialClinicData = new Clinic();
        } catch (IOException e) {
            logger.warning("Problem encountered while reading from the CLI-nic data file. Will be starting "
                    + "with an empty CLI-nic");
            initialClinicData = new Clinic();
        }

        Optional<ReadOnlyUserMacros> userMacrosOptional;
        ReadOnlyUserMacros initialUserMacrosData;
        try {
            userMacrosOptional = storage.readUserMacros();
            if (userMacrosOptional.isEmpty()) {
                logger.info("User Macros file not found. Will be starting with an empty User Macros model");
            }
            initialUserMacrosData = userMacrosOptional.orElseGet(UserMacros::new);
        } catch (DataConversionException e) {
            logger.warning("User Macros file not in the correct format. Will be starting with an empty "
                    + "User Macros model");
            initialUserMacrosData = new UserMacros();
        } catch (IOException e) {
            logger.warning("Problem encountered while reading from the User Macros file. Will be starting with an "
                    + "empty User Macros model");
            initialUserMacrosData = new UserMacros();
        }

        Optional<ReadOnlyCommandHistory> commandHistoryOptional;
        ReadOnlyCommandHistory initialCommandHistoryData;
        try {
            commandHistoryOptional = storage.readCommandHistory();
            initialCommandHistoryData = commandHistoryOptional.get();
        } catch (IOException e) {
            logger.warning("Problem encountered while reading from commandHistory.txt file. Will be starting with"
                    + " an empty Command History model");
            initialCommandHistoryData = new CommandHistory();
        }

        return new ModelManager(initialClinicData, userPrefs, initialUserMacrosData, initialCommandHistoryData);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem  encountered while reading from the file. Will be starting with "
                    + "default user prefs");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting CLI-nic " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping CLI-nic ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
