package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} or {@code Course} matches any of the keywords given.
 */
public class NameOrCourseContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> courseKeywords;

    /**
     * Constructs a {@code NameOrCourseContainsKeywordsPredicate} with the given keywords for name and course filtering.
     *
     * @param nameKeywords A list of keywords used to filter contacts by name. If empty, no name-based filtering is
     *        applied.
     * @param courseKeywords A list of keywords used to filter contacts by course. If empty, no course-based filtering
     *        is applied.
     */
    public NameOrCourseContainsKeywordsPredicate(List<String> nameKeywords, List<String> courseKeywords) {
        this.nameKeywords = nameKeywords;
        this.courseKeywords = courseKeywords;
    }

    public List<String> getNameKeywords() {
        return nameKeywords;
    }

    public List<String> getCourseKeywords() {
        return courseKeywords;
    }

    @Override
    public boolean test(Person person) {
        boolean matchesName = nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        boolean matchesCourse = courseKeywords.stream()
                .anyMatch(keyword -> person.getCourses().stream()
                        .anyMatch(course -> StringUtil.containsWordIgnoreCase(course.toString(), keyword)));

        boolean matchesPreviousCourse = courseKeywords.stream()
                .anyMatch(keyword -> person.getPreviousCourses().stream()
                        .anyMatch(previousCourse -> StringUtil.containsWordIgnoreCase(previousCourse.toString(),
                                keyword)));

        return matchesName || matchesCourse || matchesPreviousCourse;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NameOrCourseContainsKeywordsPredicate)) {
            return false;
        }

        NameOrCourseContainsKeywordsPredicate otherPredicate = (NameOrCourseContainsKeywordsPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords) && courseKeywords.equals(otherPredicate.courseKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("courseKeywords", courseKeywords)
                .toString();
    }

}
