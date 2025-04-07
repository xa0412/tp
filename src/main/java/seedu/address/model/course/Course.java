package seedu.address.model.course;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's course in the address book
 * Guarantees: immutable
 */
public class Course {
    public static final String MESSAGE_CONSTRAINTS = "Course names should be alphanumeric and a maximum of "
            + "10 characters long. It must also follow a structured format: a department code (letters),"
            + " followed by a course number (digits), with an optional single-letter suffix. e.g. CS2030S, CFG1003";
    public static final String VALIDATION_REGEX = "^(?=.{1,10}$)[A-Za-z]+[0-9]+[A-Za-z]?$";

    private final String value;

    /**
     * Constructs a {@code Course}
     *
     * @param course A string corresponding to a course code
     */
    public Course(String course) {
        requireNonNull(course);
        course = course.toUpperCase();
        checkArgument(isValidCourseName(course), MESSAGE_CONSTRAINTS);
        value = course;
    }

    /**
     * Returns true if a given string is a valid course name.
     */
    public static boolean isValidCourseName(String test) {
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

        if (!(other instanceof Course)) {
            return false;
        }

        Course otherCourse = (Course) other;
        return value.equals(otherCourse.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
