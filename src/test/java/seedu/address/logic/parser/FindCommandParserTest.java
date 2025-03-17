package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameOrCourseContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameOrCourseContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"),
                        Collections.emptyList()));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);

        // course keywords only
        FindCommand expectedFindCommandWithCourse =
                new FindCommand(new NameOrCourseContainsKeywordsPredicate(Collections.emptyList(),
                        Arrays.asList("CS2101", "CS2103")));
        assertParseSuccess(parser, "c/CS2101 c/CS2103", expectedFindCommandWithCourse);

        // both name and course keywords
        FindCommand expectedFindCommandWithNameAndCourse =
                new FindCommand(new NameOrCourseContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"),
                        Arrays.asList("CS2101")));
        assertParseSuccess(parser, "Alice Bob c/CS2101", expectedFindCommandWithNameAndCourse);
    }

}
