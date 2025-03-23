package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.model.LoginBook;

/**
 * A serializable class to store last login information in JSON format.
 */
@JsonRootName(value = "loginbook")
public class JsonSerializableLoginBook {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final String lastLogin; // Stored as a String for JSON compatibility

    /**
     * Constructs a {@code JsonSerializableLoginBook} with the given last login.
     */
    @JsonCreator
    public JsonSerializableLoginBook(@JsonProperty("lastLogin") LocalDateTime lastLogin) {
        this.lastLogin = lastLogin != null ? lastLogin.format(FORMATTER) : LocalDateTime.now().format(FORMATTER);
    }

    /**
     * Converts this object to a {@code LocalDateTime}.
     */
    public LoginBook toModelType() {
        return new LoginBook(LocalDateTime.parse(lastLogin, FORMATTER)); // Wrap in a LoginBook object
    }


    public String getLastLoginString() {
        return lastLogin;
    }
}
