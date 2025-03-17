package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} or {@code Course} matches any of the keywords given.
 */
public class NameOrCourseContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> courseKeywords;

    public NameOrCourseContainsKeywordsPredicate(List<String> nameKeywords, List<String> courseKeywords) {
        this.nameKeywords = nameKeywords;
        this.courseKeywords = courseKeywords;
    }

    @Override
    public boolean test(Person person) {
        boolean matchesName = nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        boolean matchesCourse = courseKeywords.stream()
                .anyMatch(keyword -> person.getCourses().stream()
                        .anyMatch(course -> StringUtil.containsWordIgnoreCase(course.toString(), keyword)));

        return matchesName || matchesCourse;
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
}
