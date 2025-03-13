package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents a Person's friendship in the address book
 * Guarantees: immutable
 */
public class Friendship implements Comparable<Friendship> {

    public static final String MESSAGE_CONSTRAINTS =
            "Friendship should be \"ACQUAINTANCE\" or \"FRIEND\" or \"CLOSE_FRIEND\" and not be blank";

    /**
     * Represents a Person's friendship closeness level
     */
    public enum Level {
        ACQUAINTANCE, FRIEND, CLOSE_FRIEND
    }

    private final Level value;

    /**
     * Constructs a {@code Friendship}
     * @param level A measure of the friendship's closeness level as a string
     */
    public Friendship(String level) {
        checkArgument(isValidFriendship(level), MESSAGE_CONSTRAINTS);
        value = Level.valueOf(level);
    }

    /**
     * Constructs a {@code Friendship}
     * @param level A measure of the friendship's closeness level
     */
    public Friendship(Level level) {
        value = level;
    }


    /**
     * Returns true if a given string is a valid friendship closeness level.
     */
    public static boolean isValidFriendship(String test) {
        return Arrays.stream(Level.values()).anyMatch(level -> level.name().equals(test));
    }

    @Override
    public int compareTo(Friendship other) {
        return value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Friendship)) {
            return false;
        }

        Friendship otherFriendship = (Friendship) other;
        return value.equals(otherFriendship.value);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
