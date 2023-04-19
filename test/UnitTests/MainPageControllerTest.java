package UnitTests;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainPageControllerTest {

    //region Start Section
    @Start
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/PermitViews/MainPageView.fxml")));
        stage.setTitle("Permit Tracker (Testing");
        stage.setScene(new Scene(root));
        stage.show();
    }
    //endregion

    //region Enable/Disable Text Field Tests
    @org.junit.jupiter.api.Test
    @Order(2)
    public void testFirstNameTextField(FxRobot robot) {
        TextField textField = robot.lookup("#firstNameTextField").query();
        CheckBox checkBox = robot.lookup("#firstNameCheckBox").query();
        await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(textField.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(textField.isDisabled());
    }

    @org.junit.jupiter.api.Test
    @Order(3)
    public void testLastNameTextField(FxRobot robot) {
        TextField textField = robot.lookup("#lastNameTextField").query();
        CheckBox checkBox = robot.lookup("#lastNameCheckBox").query();
        await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(textField.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(textField.isDisabled());
    }

    @org.junit.jupiter.api.Test
    @Order(4)
    public void testAddressTextField(FxRobot robot) {
        TextField textField = robot.lookup("#addressTextField").query();
        CheckBox checkBox = robot.lookup("#addressCheckBox").query();
        await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(textField.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(textField.isDisabled());
    }

    @org.junit.jupiter.api.Test
    @Order(5)
    public void testAreaCodeTextField(FxRobot robot) {
        TextField textField = robot.lookup("#areaCodeTextField").query();
        CheckBox checkBox = robot.lookup("#areaCodeCheckBox").query();
        await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(textField.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(textField.isDisabled());
    }

    //endregion

    //region Check That Data can be Filtered

    public void filterItem(FxRobot robot) throws InterruptedException {

        // Properties to look for
        TableView tableView = robot.lookup("#permitDataTable").query();
        TextField textField = robot.lookup("#addressTextField").query();
        CheckBox checkBox = robot.lookup("#addressCheckBox").query();
        Button apply = robot.lookup("#applyFilterBtn").queryButton();

        // Create an empty list to store the current table list in
        List<Object> rows = new ArrayList<>();

        // Store the rows in to that list
        for (Object row : tableView.getItems()) {
            rows.add(row);
        }

        // Wait until the Address Checkbox is ticked
        await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);
        // Add pass if it enables
        Assertions.assertFalse(textField.isDisabled());
        // Wait until the apply button is pressed
        await().atMost(30, TimeUnit.SECONDS).until(() -> apply.isPressed());

        // Wait until the rows are less than the stored rows
        await().atMost(30, TimeUnit.SECONDS).until(() -> tableView.getItems().stream().count() < rows.stream().count());
        // Final assertion to see if the current row size is different from the rows list
        Assertions.assertTrue(tableView.getItems().stream().count() < rows.stream().count());
    }
    //endregion

    //region Remove Items from Table

    public void removeItem(FxRobot robot) throws InterruptedException {
        // Properties to look for
        TabPane tab = robot.lookup("#mainTabPane").query();
        TableView tableView = robot.lookup("#permitDataTable").query();
        Button button = robot.lookup("#removeButton").queryButton();

        // Create a blank list that will store the initial data that is stored within the table
        List<String> initialIds = new ArrayList<>();

        // Create an Observable List that reads in the current Table Columns
        ObservableList<TableColumn> columns = tableView.getColumns();

        // Loop through each of the Rows within the Table
        for (Object row : tableView.getItems()) {
            // On each row, loop through each Column until it gets to the ID Column
            for (TableColumn column : columns) {
                // If we are on the ID column, add the value to the initialIds List
                if (column.getId().equals("idColumn")) {
                    initialIds.add(column.getCellObservableValue(row).getValue().toString());
                }
            }
        }

        // Wait until the Remove Tab is selected
        await().atMost(10, TimeUnit.SECONDS).until(() -> tab.getSelectionModel().getSelectedItem().getText().equals("Remove"));

        // Ensure that the Tab changes to the remove tab
        Assertions.assertTrue(tab.getSelectionModel().getSelectedItem().getText().equals("Remove"));

        // Wait for the Remove Button to be clicked
        await().atMost(60, TimeUnit.SECONDS).until(() -> button.isPressed());

        // Create a new Text Field object that stores the remove Text field
        TextField textField = robot.lookup("#removeTextField").query();

        // Wait until the Text field resets itself after removing the item
        await().atMost(60, TimeUnit.SECONDS).until(() -> textField.getText().equals(""));

        // Create another  blank list to store our new list in
        List<String> updatedIds = new ArrayList<>();

        // Create another Observable List to store the updated Columns
        ObservableList<TableColumn> updatedColumns = tableView.getColumns();

        // Same as before and add the items to the new blank list
        for (Object row : tableView.getItems()) {
            for (TableColumn column : updatedColumns) {
                if (column.getId().equals("idColumn")) {
                    updatedIds.add(column.getCellObservableValue(row).getValue().toString());
                }
            }
        }

        // Ensure that the Updated list is not the same size as the initial list that was created
        Assertions.assertNotSame(updatedIds.size(), initialIds.size());
    }

    //endregion
}