package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label previousCourses;
    @FXML
    private Label detailedCourses;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox previousCoursesDetails;
    @FXML
    private Button expandButton;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        List<String> previousCoursesList = person.getPreviousCourses()
                .stream().map(Object::toString).collect(Collectors.toList());

        String previousCoursesText = previousCoursesList.isEmpty()
                ? "NIL"
                : previousCoursesList.size() == 1
                ? previousCoursesList.get(0)
                : String.join(", ", previousCoursesList);


        String recentCoursesText;

        if (previousCoursesList.isEmpty()) {
            recentCoursesText = "NIL";
        } else if (previousCoursesList.size() == 1) {
            recentCoursesText = previousCoursesList.get(0);
        } else {
            // Take only the first 5 courses or fewer if there are less than 5
            List<String> topCourses = previousCoursesList.subList(0, Math.min(previousCoursesList.size(), 5));
            recentCoursesText = String.join(", ", topCourses);

            // Append "..." if there are more than 5 courses
            if (previousCoursesList.size() > 5) {
                recentCoursesText += "...";
            }
        }
        previousCourses.setText("Previous Courses: " + recentCoursesText);
        detailedCourses.setText(previousCoursesText);

        // Add general tags (Blue)
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("tag-general"); // Apply blue color
                    tags.getChildren().add(tagLabel);
                });

        // Add friendship tag (Red)
        if (person.getFriendship() != null && !person.getFriendship().toString().isEmpty()) {
            Label friendshipLabel = new Label(person.getFriendship().toString());
            friendshipLabel.getStyleClass().add("tag-friendship"); // Apply red color
            tags.getChildren().add(friendshipLabel);
        }

        // Add courses (Yellow)
        person.getCourses().stream()
                .sorted(Comparator.comparing(course -> course.toString()))
                .forEach(course -> {
                    Label courseLabel = new Label(course.toString());
                    courseLabel.getStyleClass().add("tag-course"); // Apply yellow color
                    tags.getChildren().add(courseLabel);
                });
    }

    @FXML
    private void togglePreviousCourses() {
        boolean isVisible = previousCoursesDetails.isVisible();
        previousCoursesDetails.setVisible(!isVisible);
        previousCoursesDetails.setManaged(!isVisible); // Ensures it doesn't take up space when hidden
        expandButton.setText(isVisible ? "▼" : "▲"); // Change button icon
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
