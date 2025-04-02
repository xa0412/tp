package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's course in the address book
 * Guarantees: immutable
 */
public class PreviousCourse {
    public static final String MESSAGE_CONSTRAINTS = "Previous Course names should be alphanumeric and a maximum of "
            + "10 characters long. It must also follow a structured format: a department code (letters), "
            + "followed by a course number (digits), with an optional single-letter suffix. e.g. CS2030S, CFG1003";
    public static final String VALIDATION_REGEX = "^(?=.{1,10}$)[A-Za-z]+[0-9]+[A-Za-z]?$";


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
