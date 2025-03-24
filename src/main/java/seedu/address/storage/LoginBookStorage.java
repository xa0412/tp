package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;

/**
 * Represents a storage for {@link seedu.address.model.LoginBook}.
 */
public interface LoginBookStorage {
    void saveLoginBook(LocalDateTime lastLogin, Path filePath) throws IOException;

    Optional<LocalDateTime> readLoginBook(Path filePath) throws DataLoadingException;

    Path getLoginBookFilePath();
}
