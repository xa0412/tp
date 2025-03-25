package seedu.address.logic.backend;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.course.Course;
import seedu.address.model.person.Person;

/**
 * Compares two {@code Person} by how similar their {@code courses} are to the provided {@code courses}
 */
public class PersonCoursesComparator implements Comparator<Person> {

    private final Set<Course> courses;

    public PersonCoursesComparator(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public int compare(Person o1, Person o2) {
        Set<Course> m1 = new HashSet<>(courses);
        Set<Course> m2 = new HashSet<>(courses);

        m1.retainAll(o1.getCourses());
        m2.retainAll(o2.getCourses());

        if (m1.size() == m2.size()) {
            return 0;
        }
        if (m1.size() < m2.size()) {
            return 1;
        }
        return -1;
    }


}
