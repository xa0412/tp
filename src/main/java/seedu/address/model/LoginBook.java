package seedu.address.model;

import java.time.LocalDateTime;

/**
 * Represnt a Login Timestamp
 */
public class LoginBook {
    private final LocalDateTime lastLogin;

    public LoginBook(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
}
