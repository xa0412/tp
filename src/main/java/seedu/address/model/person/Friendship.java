package seedu.address.model.person;

/**
 * Represents a Person's friendship in the address book
 * Guarantees: immutable
 */
public class Friendship implements Comparable<Friendship> {

    /**
     * Represents a Person's friendship closeness level
     */
    public enum Level {
        ACQUAINTANCE, FRIEND, CLOSE_FRIEND
    }

    private final Level value;

    /**
     * Constructs a {@code Friendship}
     * @param level A measure of the friendship's closeness level
     */
    public Friendship(Level level) {
        value = level;
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
