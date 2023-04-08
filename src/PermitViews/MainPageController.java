package PermitViews;

import Classes.PermitHolder;
import Functions.LoadPermitData;
import com.google.gson.Gson;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MainPageController {


    //region FXML Properties
    @FXML
    private AnchorPane filtterAnchorPane;
    @FXML
    TableView<Map.Entry<String, PermitHolder>> permitDataTable;
    @FXML
    TableColumn<Map.Entry<String, PermitHolder>, String> idColumn, fNameColumn, lNameColumn, addressColumn,
            regColumn, areasColumn, startColumn, expiryColumn;
    @FXML
    Tab filterTab, searchTab, addTab, removeTab;
    @FXML
    TextField idTextField, firstNameTextField, lastNameTextField, addressTextField, areaCodeTextField, removeTextField;
    @FXML
    DatePicker startDate, endDate;
    @FXML
    CheckBox idCheckBox, firstNameCheckBox, lastNameCheckBox, addressCheckBox, areaCodeCheckBox, startCheckBox,
            expiryCheckBox;
    @FXML
    Button filterResetBtn, removeButton;
    //endregion

    //region Properties
    private HashMap<String, PermitHolder> permitHolderMap;
    private ObservableList<Map.Entry<String, PermitHolder>> permits;
    //endregion

    //region FXML Related Methods

    //region Initialize method

    // Initialize is called when the MainView is loaded on the system and executes
    // the start app Method which contains our calls for setting up the Hash Map
    // and the Table
    @FXML
    public void initialize() {
        try {
            startApp();
            setUpAdditionalHandlers();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //endregion

    // region FXML Handlers

    // This contains the Handlers used with the FXML to tell the ui what to do when
    // specific elements are clicked. For example, we have  handlers to see if the
    // checkboxes are ticked so that if they are they can enable or disable the
    // text fields based on that

    //region Filter Handlers
    @FXML
    private void handelIDCheckBoxAction() {
        idTextField.setDisable(!idCheckBox.isSelected());
    }

    @FXML
    private void handelFirstNameCheckBoxAction() {
        firstNameTextField.setDisable(!firstNameCheckBox.isSelected());
    }

    @FXML
    private void handelLastNameCheckBoxAction() {
        lastNameTextField.setDisable(!lastNameCheckBox.isSelected());
    }

    @FXML
    private void handelAddressCheckBoxAction() {
        addressTextField.setDisable(!addressCheckBox.isSelected());
    }

    @FXML
    private void handelAreaCodeCheckBoxAction() {
        areaCodeTextField.setDisable(!areaCodeCheckBox.isSelected());
    }

    @FXML
    private void handelStartCheckBoxAction() {
        startDate.setDisable(!startCheckBox.isSelected());
    }

    @FXML
    private void handelEndCheckBoxAction() {
        endDate.setDisable(!expiryCheckBox.isSelected());
    }

    // Reset all the filters  if the reset button is pressed
    @FXML
    private void handelResetButton() {
        idCheckBox.setSelected(false);
        idTextField.setDisable(true);
        firstNameCheckBox.setSelected(false);
        firstNameTextField.setDisable(true);
        lastNameCheckBox.setSelected(false);
        lastNameTextField.setDisable(true);
        addressCheckBox.setSelected(false);
        addressTextField.setDisable(true);
        startCheckBox.setSelected(false);
        startDate.setDisable(true);
        expiryCheckBox.setSelected(false);
        endDate.setDisable(true);
    }

    @FXML
    private void handelApplyButton() {

    }
    //endregion

    //region TextField Handlers
    @FXML
    private void setRemoveTextBoxWhite() {
        if (removeTextField.getStyle() != "-fx-text-box-border:  #ffffff;")
            removeTextField.setStyle("-fx-text-box-border:  #ffffff;");
    }
    //endregion


    //region Tab Handlers

    @FXML
    private void enableRemoveTextField() {
        removeTextField.setDisable(!removeTab.isSelected());
    }

    //endregion


    //region Dialog Boxes
    @FXML
    private void removeConfirmation() {
        if (removeTextField.getText().equals("")) {
            removeTextField.setStyle("-fx-text-box-border: #ff0000;");
        } else {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to remove this entry?", yes, no);

            alert.setTitle("Remove Permit?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no) == yes) {
                removeSelectedRow();
            }
        }

    }
    //endregion

    //endregion

    //region Private Methods
    private void startApp() throws IOException {
        permitHolderMap = new HashMap<>();
        // get the Permit Data from the JSON Fill
        loadPermitData();

        // Populate the Columns on the GUI
        setupColumns();
    }

    private void setupColumns() {
        // Make a list of Items that is used for the JavaFX implementation and populate it with the items
        // from our Hash Map
        permits = FXCollections.observableArrayList(permitHolderMap.entrySet());

        // Set the data table to read in the permit list
        permitDataTable.setItems(permits);

        // Apply the values from our Hash Map pass-through to all the columns on the table
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey()));
        fNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getFirst_name()));
        lNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getLast_name()));
        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getAddress().toString()));
        regColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getCar().sendRegNum()));
        areasColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendAreaCodes()));
        startColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendStartDate()));
        expiryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendExpiryDate()));

        // Set default sort order based on the ID
        permitDataTable.getSortOrder().add(idColumn);
    }

    // Method for Aditional Handlers that need to be setup at the start of the program
    private void setUpAdditionalHandlers() {
        permitDataTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null) {
                removeTextField.setText(newVal.getKey());
            }
        });
    }

    // Method for reading in our JSON file

    // Retreive data from the JSON file
    private void loadPermitData(){
        try{
            LoadPermitData load = new LoadPermitData();

            permitHolderMap = load.loadPermitData();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    private void removeSelectedRow() {
        String idToRemove = removeTextField.getText();
        permits.removeIf(permit -> permit.getKey().equals(idToRemove));

        if(permitHolderMap.get(idToRemove) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "ID not Found in Hash Map");
            alert.setTitle("Error: Permit Not Found");
            alert.show();
        }
        else{
            permitHolderMap.remove(idToRemove);

            for (Map.Entry<String, PermitHolder> permit : permitHolderMap.entrySet()) {
                System.out.println(permit.getKey());
            }
        }
    }
    //endregion
}
