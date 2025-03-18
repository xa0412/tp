package seedu.address.model.course;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's course in the address book
 * Guarantees: immutable
 */
public class Course {
    private final String value;

    /**
     * Constructs a {@code Course}
     *
     * @param course A string corresponding to a course code
     */
    public Course(String course) {
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
