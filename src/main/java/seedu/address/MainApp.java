package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonLoginBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.LoginBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        //Get current DateTime
        LocalDateTime currentDateTime = LocalDateTime.now();

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        LoginBookStorage loginBookStorage = new JsonLoginBookStorage(Path.of("data", "lastLogin.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage, loginBookStorage);
        model = initModelManager(storage, userPrefs);
        logic = new LogicManager(model, storage);
        ui = new UiManager(logic);
        Path filePath = loginBookStorage.getLoginBookFilePath();
        LocalDateTime lastLogin = getLastLogin(filePath);
        updateLastLogin(currentDateTime, filePath);
        checkSemEnded(lastLogin, currentDateTime);

    }

    /**
     * Retrieves Last Login
     * @param filePath
     * @return LocalDateTime
     * @throws DataLoadingException
     * @throws CommandException
     */
    public LocalDateTime getLastLogin(Path filePath) throws DataLoadingException, CommandException {
        LocalDateTime lastLogin = LocalDateTime.now();
        //Retrieve the last login date time object
        Optional<LocalDateTime> loginBookOptional = storage.readLoginBook(filePath);
        //if present, retrieve the last login date time
        if (loginBookOptional.isPresent()) {
            logger.info("Last login: " + loginBookOptional.get());
            lastLogin = loginBookOptional.get();
        } else {
            logger.info("No last login data found.");
        }
        return lastLogin;

    }

    /**
     * Update Last Login to json file
     * @param currentDateTime
     * @param filePath
     * @throws CommandException
     */
    public void updateLastLogin(LocalDateTime currentDateTime, Path filePath) throws CommandException {
        logic.saveLastLogin(currentDateTime, filePath);
    }

    /**
     * Checks if the current date time has past current semester end datetime
     * @param lastLogin
     * @param currentDateTime
     * @throws CommandException
     */
    public void checkSemEnded(LocalDateTime lastLogin, LocalDateTime currentDateTime) throws CommandException {
        int sem = 0;
        //Academic year is always fixed as the current year stated in user machine
        int year = currentDateTime.getYear();
        // Fix Sem 1 end date as 31st Dec, since the actual end of Semester 1 varies but is always around mid-December.
        // Course registration occurs in January, so clear courses before new registrations.
        LocalDateTime sem1End = LocalDateTime.of(year, 12, 31, 0, 0);
        LocalDateTime sem1Start = LocalDateTime.of(year, 8, 1, 0, 0);
        // Fix Sem 2 end date as 31st May, since the actual end of Semester 2 varies but is always around mid-May.
        // Course registration occurs in July, so clear courses before new registrations.
        LocalDateTime sem2End = LocalDateTime.of(year, 5, 31, 0, 0);
        LocalDateTime sem2Start = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endSemesterDateTime;
        LocalDateTime startSemesterDateTime;
        if (currentDateTime.isBefore(sem2End)) {
            // Currently in Semester 2
            endSemesterDateTime = sem2End;
            startSemesterDateTime = sem2Start;
            logger.info("Currently in Semester 2");
            sem = 2;
        } else if (currentDateTime.isAfter(sem2End) && currentDateTime.isBefore(sem1Start)) {
            // Semester Break (June - July) → Use Sem 2's end date
            endSemesterDateTime = sem2End;
            startSemesterDateTime = sem2Start;
            logger.info("Currently in Semester Break (June - July), using Sem 2 end date");
        } else if (currentDateTime.isBefore(sem1End)) {
            // Currently in Semester 1
            endSemesterDateTime = sem1End;
            startSemesterDateTime = sem1Start;
            logger.info("Currently in Semester 1");
            sem = 1;
        } else {
            // Semester Break (December - early January) → Use Sem 1's end date
            endSemesterDateTime = sem1End;
            startSemesterDateTime = sem1Start;
            logger.info("Currently in Semester Break (December - January), using Sem 1 end date");
        }

        checkEnded(currentDateTime, lastLogin, endSemesterDateTime, startSemesterDateTime, sem);
    }

    /**
     * Actual Implementation of the Semester End check
     * @param currentDateTime
     * @param lastLogin
     * @param endSemesterDateTime
     * @param startSemesterDateTime
     * @param sem
     * @throws CommandException
     */
    public void checkEnded(LocalDateTime currentDateTime, LocalDateTime lastLogin,
                           LocalDateTime endSemesterDateTime, LocalDateTime startSemesterDateTime,
                           int sem) throws CommandException {
        //if current Date is past the current sem end date, update courses
        //Possibility that user logins after 1 year e.g. last login was 3/3/2025, new login is 3/3/2026,
        //this will make the current sem end date time to be that of the current year e.g. 31/5/2026
        //however it will be inaccurate since the sem should have ended 31/5/2025
        //thus need check if the last login is before current start sem, if it is, need update courses
        if ((currentDateTime.isAfter(endSemesterDateTime) && lastLogin.isBefore(endSemesterDateTime))
                || (lastLogin.isBefore(startSemesterDateTime) && currentDateTime.isAfter(startSemesterDateTime))) {
            logger.info("Semester " + sem + " has ended!");
            logger.info("Last login: " + lastLogin);
            logger.info("Current date: " + currentDateTime);
            logic.updatePreviousCourses();
        } else {
            logger.info("Semester " + sem + " is still ongoing!");
        }
    }
    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getAddressBookFilePath()
                        + " populated with a sample AddressBook.");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty AddressBook.");
            initialData = new AddressBook();
        }

        return new ModelManager(initialData, userPrefs);
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
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
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
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
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
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping AddressBook ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
