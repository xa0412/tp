package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.backend.SetSimilarityComparator;
import seedu.address.model.Model;
import seedu.address.model.course.Course;
import seedu.address.model.person.NameOrCourseContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PreviousCourse;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords. Keyword matching is
 * case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names/courses contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Format: find [n/KEYWORD [MORE_KEYWORDS]] [c/KEYWORD [MORE_KEYWORDS]]\n"
            + "Example: " + COMMAND_WORD + " n/alice c/CS2103T";

    private final NameOrCourseContainsKeywordsPredicate predicate;

    public FindCommand(NameOrCourseContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        Set<Course> courses = predicate.getCourseKeywords().stream()
                .filter(keyword -> Course.isValidCourseName(keyword))
                .map(keyword -> new Course(keyword))
                .collect(Collectors.toCollection(HashSet::new));

        Set<PreviousCourse> previousCourses = courses.stream()
                .map(course -> new PreviousCourse(course.toString()))
                .collect(Collectors.toCollection(HashSet::new));

        model.sortFilteredPersonList(Comparator
                .comparing(Person::getCourses, new SetSimilarityComparator<>(courses))
                .thenComparing(Person::getPreviousCourses, new SetSimilarityComparator<>(previousCourses))
                .thenComparing(Person::getFriendship));

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredThenSortedPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
