package Functions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.assertj.core.error.future.Warning;

import java.util.Optional;

public class CreateAlerts {

    //region Singleton
    private static CreateAlerts instance;

    public static CreateAlerts getInstance() {
        if (instance == null) {
            instance = new CreateAlerts();
        }
        return instance;
    }

    //endregion

    //region Properties
    public enum AlertType {
        WARNING,
        ERROR
    }

    ;

    private String windowString = "";
    private Optional<ButtonType> result;
    //endregion

    public Alert alertBuilder(AlertType alertType, String message, boolean addYesNo) {

        if (addYesNo) {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alertWindow = new Alert(getAlertType(alertType), message, yes, no);
            alertWindow.show();
            result = alertWindow.showAndWait();
            return alertWindow;
        } else {
            Alert alertWindow = new Alert(getAlertType(alertType), message);
            alertWindow.show();
            return alertWindow;
        }

    }


    private Alert.AlertType getAlertType(AlertType type) {
        switch (type) {
            case ERROR:
                windowString = "Error";
                return Alert.AlertType.ERROR;

            case WARNING:
                windowString = "Warning";
                return Alert.AlertType.WARNING;
            default:
                return Alert.AlertType.ERROR;
        }
    }

    public Optional<ButtonType> getResult() {
        return result;
    }
}
