package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;

/**
 * A class to access LoginBook data stored as a json file on the hard disk.
 */
public class JsonLoginBookStorage implements LoginBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonLoginBookStorage.class);

    private Path filePath;

    public JsonLoginBookStorage() {}

    public JsonLoginBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Save the user last login with the default path set above
     * @param lastLogin
     * @throws IOException
     */
    public void saveLoginBook(LocalDateTime lastLogin) throws IOException {
        saveLoginBook(lastLogin, this.filePath);
    }

    /**
     * Saves the user last login into a json file
     * @param lastLogin
     * @param filePath
     * @throws IOException
     */
    public void saveLoginBook(LocalDateTime lastLogin, Path filePath) throws IOException {
        requireNonNull(lastLogin);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLoginBook(lastLogin), filePath);
    }

    public Optional<LocalDateTime> readLoginBook() throws DataLoadingException {
        return readLoginBook(filePath);
    }

    /**
     * Reads last login time from the specified file path.
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage fails.
     */
    public Optional<LocalDateTime> readLoginBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableLoginBook> jsonLoginBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableLoginBook.class);

        if (!jsonLoginBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonLoginBook.get().toModelType().getLastLogin());
        } catch (Exception e) {
            logger.info("Failed to parse lastLogin data: " + e.getMessage());
            throw new DataLoadingException(e);
        }
    }

    /**
     * Return filePath
     * @return Path
     */
    public Path getLoginBookFilePath() {
        return filePath;
    }

}
