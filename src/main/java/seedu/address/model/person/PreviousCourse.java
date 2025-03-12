package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's course in the address book
 * Guarantees: immutable
 */
public class PreviousCourse {
    private final String value;

    /**
     * Constructs a {@code Course}
     * @param course A string corresponding to a course code
     */
    public PreviousCourse(String course) {
        requireNonNull(course);
        value = course;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PreviousCourse)) {
            return false;
        }

        PreviousCourse otherPreviousCourse = (PreviousCourse) other;
        return value.equals(otherPreviousCourse.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
