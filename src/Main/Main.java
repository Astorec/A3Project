package Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;

public class Main extends Application {

    //region FXML Start Method

    // Used to create our Scene from the FXML and load the initial Main.Main Page
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PermitViews/MainPageView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Permit Tracker");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    //endregion

    //region Main.Main Method
    public static void main(String[] args) {
        launch(args);
    }
    //endregion
}
