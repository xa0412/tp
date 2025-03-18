package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.course.Course;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Course> courses = new HashSet<>();
    private final Friendship friendship;
    private final Set<PreviousCourse> previousCourses = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
            Set<Course> courses, Friendship friendship) {
        requireAllNonNull(name, phone, email, address, tags, courses, friendship);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.courses.addAll(courses);
        // For testing purposes: To check whether the previous semester module is added
        // on instead of cleared and create
        // this.previousCourses.add(new PreviousCourse("2030S"));
        this.friendship = friendship;
    }

    // /**
    // * TODO: Remove this constructor
    // */
    // public Person(Name name, Phone phone, Email email, Address address, Set<Tag>
    // tags) {
    // this.name = name;
    // this.phone = phone;
    // this.email = email;
    // this.address = address;
    // this.tags.addAll(tags);
    // this.friendship = new Friendship(Friendship.Level.FRIEND);
    // }

    /**
     * Represents a new Constructor to update the previous courses
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param tags
     * @param courses
     * @param friendshipType
     * @param previousCourses
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
            Set<Course> courses, Friendship friendshipType, Set<PreviousCourse> previousCourses) {
        requireAllNonNull(name, phone, email, address, tags, courses, friendshipType);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.courses.addAll(courses);
        this.friendship = friendshipType;
        this.previousCourses.addAll(previousCourses);

    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Friendship getFriendship() {
        return friendship;
    }

    public Set<Course> getCourses() {
        return Collections.unmodifiableSet(courses);
    }

    public Set<PreviousCourse> getPreviousCourses() {
        return Collections.unmodifiableSet(previousCourses);
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
