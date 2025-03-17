package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameOrCourseContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstNameKeywordList = Collections.singletonList("Alice");
        List<String> secondNameKeywordList = Arrays.asList("Alice", "Bob");
        List<String> firstCourseKeywordList = Collections.singletonList("CS2101");
        List<String> secondCourseKeywordList = Arrays.asList("CS2101", "CS2103");

        NameOrCourseContainsKeywordsPredicate firstPredicate =
                new NameOrCourseContainsKeywordsPredicate(firstNameKeywordList, firstCourseKeywordList);
        NameOrCourseContainsKeywordsPredicate secondPredicate =
                new NameOrCourseContainsKeywordsPredicate(secondNameKeywordList, secondCourseKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameOrCourseContainsKeywordsPredicate firstPredicateCopy =
                new NameOrCourseContainsKeywordsPredicate(firstNameKeywordList, firstCourseKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameOrCourseContainsKeywords_returnsTrue() {
        // One name keyword
        NameOrCourseContainsKeywordsPredicate predicate =
                new NameOrCourseContainsKeywordsPredicate(Collections.singletonList("Alice"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withCourses("CS2101").build()));

        // One course keyword
        predicate = new NameOrCourseContainsKeywordsPredicate(Collections.emptyList(), Collections.singletonList("CS2101"));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").withCourses("CS2101").build()));

        // Multiple name keywords
        predicate = new NameOrCourseContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withCourses("CS2103").build()));

        // Multiple course keywords
        predicate = new NameOrCourseContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("CS2101", "CS2103"));
        assertTrue(predicate.test(new PersonBuilder().withName("Charlie").withCourses("CS2103").build()));

        // One matching keyword in name and one in course
        predicate = new NameOrCourseContainsKeywordsPredicate(Arrays.asList("Alice", "Charlie"), Arrays.asList("CS2101"));
        assertTrue(predicate.test(new PersonBuilder().withName("Charlie Brown").withCourses("CS2101").build()));

        // Mixed-case name keyword
        predicate = new NameOrCourseContainsKeywordsPredicate(Arrays.asList("aLIce"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withCourses("CS2103").build()));

        // Mixed-case course keyword
        predicate = new NameOrCourseContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("cs2101"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").withCourses("CS2101").build()));
    }

    @Test
    public void test_nameOrCourseDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameOrCourseContainsKeywordsPredicate predicate =
                new NameOrCourseContainsKeywordsPredicate(Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withCourses("CS2101").build()));

        // Non-matching name keyword
        predicate = new NameOrCourseContainsKeywordsPredicate(Arrays.asList("Carol"), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withCourses("CS2101").build()));

        // Non-matching course keyword
        predicate = new NameOrCourseContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("CS9999"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withCourses("CS2101").build()));

        // Keywords match phone, email, and address but not name or course
        predicate = new NameOrCourseContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withCourses("CS2101").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = List.of("Alice", "Bob");
        List<String> courseKeywords = List.of("CS2101");
        NameOrCourseContainsKeywordsPredicate predicate = new NameOrCourseContainsKeywordsPredicate(nameKeywords, courseKeywords);

        String expected = NameOrCourseContainsKeywordsPredicate.class.getCanonicalName()
                + "{nameKeywords=" + nameKeywords + ", courseKeywords=" + courseKeywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
