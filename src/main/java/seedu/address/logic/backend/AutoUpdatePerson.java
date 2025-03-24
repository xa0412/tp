package seedu.address.logic.backend;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.course.Course;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Friendship;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PreviousCourse;
import seedu.address.model.tag.Tag;

/**
 * Represents the logic to automatically update a person
 */
public class AutoUpdatePerson {

    /**
     * Update the person's previous courses with current courses
     *
     * @param person
     * @return Person. A new updated person is created
     */
    public Person setPreviousCourses(Person person) {
        Set<Course> currentCourses = person.getCourses();
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();
        Set<Tag> tags = person.getTags();
        Friendship friendship = person.getFriendship();
        // Used for testing when courses should not be cleared.
        // Set<Course> emptyCourses = person.getCourses();
        //Used List as there is ordering so recent courses will appear first
        List<PreviousCourse> previousCourses = new LinkedList<>(person.getPreviousCourses());

        // Convert current courses and add them to the front
        previousCourses.addAll(0, currentCourses.stream()
                .map(course -> new PreviousCourse(course.toString()))
                .collect(Collectors.toList()));

        // Clear Current Courses
        Set<Course> emptyCourses = new HashSet<>();

        // create a new updated person
        return new Person(name, phone, email, address, tags, emptyCourses, friendship, previousCourses);
    }
    /**
     * Iterates through the person list and updates the person
     *
     * @param personList
     * @param model
     */
    public Model updatePreviousCourses(List<Person> personList, Model model) {
        for (Person person : personList) {
            // if person has current courses then updates previous courses
            if (!person.getCourses().isEmpty()) {
                Person updatedperson = setPreviousCourses(person);
                model.setPerson(person, updatedperson);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            }
        }
        return model;
    }
}
