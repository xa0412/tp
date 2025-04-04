package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's course in the address book
 * Guarantees: immutable
 */
public class PreviousCourse {
    public static final String MESSAGE_CONSTRAINTS = "Previouse Courses names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";


    private final String value;

    /**
     * Constructs a {@code Course}
     * @param course A string corresponding to a course code
     */
    public PreviousCourse(String course) {
        requireNonNull(course);
        value = course;
    }

    public static boolean isValidPreviousCourseName(String test) {
        return test.matches(VALIDATION_REGEX);
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
