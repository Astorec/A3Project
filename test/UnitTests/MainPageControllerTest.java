package UnitTests;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

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
    @Order(1)
    public void testIDTextField(FxRobot robot){
        TextField textField = robot.lookup("#idTextField").query();
        CheckBox checkBox = robot.lookup("#idCheckBox").query();
        await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(textField.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(textField.isDisabled());
    }

    @org.junit.jupiter.api.Test
    @Order(2)
    public void testFirstNameTextField(FxRobot robot){
        TextField textField = robot.lookup("#firstNameTextField").query();
        CheckBox checkBox = robot.lookup("#firstNameCheckBox").query();
        await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(textField.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(textField.isDisabled());
    }

    @org.junit.jupiter.api.Test
    @Order(3)
    public void testLastNameTextField(FxRobot robot){
        TextField textField = robot.lookup("#lastNameTextField").query();
        CheckBox checkBox = robot.lookup("#lastNameCheckBox").query();
         await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(textField.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(textField.isDisabled());
    }

    @org.junit.jupiter.api.Test
    @Order(4)
    public void testAddressTextField(FxRobot robot){
        TextField textField = robot.lookup("#addressTextField").query();
        CheckBox checkBox = robot.lookup("#addressCheckBox").query();
         await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(textField.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(textField.isDisabled());
    }
    @org.junit.jupiter.api.Test
    @Order(5)
    public void testAreaCodeTextField(FxRobot robot){
        TextField textField = robot.lookup("#areaCodeTextField").query();
        CheckBox checkBox = robot.lookup("#areaCodeCheckBox").query();
         await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(textField.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(textField.isDisabled());
    }

    @org.junit.jupiter.api.Test
    @Order(6)
    public void testStartDateField(FxRobot robot){
        DatePicker datePicker = robot.lookup("#startDate").query();
        CheckBox checkBox = robot.lookup("#startCheckBox").query();
         await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(datePicker.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(datePicker.isDisabled());
    }

    @org.junit.jupiter.api.Test
    @Order(7)
    public void testExpiryDateField(FxRobot robot){
        DatePicker datePicker = robot.lookup("#endDate").query();
        CheckBox checkBox = robot.lookup("#expiryCheckBox").query();
         await().atMost(10, TimeUnit.SECONDS).until(checkBox::isSelected);

        Assertions.assertFalse(datePicker.isDisabled());
        await().atMost(10, TimeUnit.SECONDS).until(() -> !checkBox.isSelected());
        Assertions.assertTrue(datePicker.isDisabled());
    }
    //endregion
}