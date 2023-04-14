package PermitViews;

import Classes.PermitHolder;
import Functions.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainPageController {


    //region FXML Properties
    @FXML
    AnchorPane filterAnchorPane;
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
    private HashMapOperations hashMapOps;
    private CreateAlerts createAlerts;
    private HashMap<String, PermitHolder> permitHolderMap;
    private ObservableList<Map.Entry<String, PermitHolder>> permits;
    private Boolean isFiltered = false;
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

    /*
     This contains the Handlers used with the FXML to tell the ui what to do when
     specific elements are clicked. For example, we have  handlers to see if the
     checkboxes are ticked so that if they are they can enable or disable the
     text fields based on that

     */
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

        // Reset the checkboxes
        idCheckBox.setSelected(false);
        firstNameCheckBox.setSelected(false);
        lastNameCheckBox.setSelected(false);
        addressCheckBox.setSelected(false);
        startCheckBox.setSelected(false);
        expiryCheckBox.setSelected(false);

        // Disable the Text Fields and Date Fields
        idTextField.setDisable(true);
        firstNameTextField.setDisable(true);
        lastNameTextField.setDisable(true);
        addressTextField.setDisable(true);
        startDate.setDisable(true);

        // Reset the Text Fields and Date Fields
        idTextField.clear();
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();
        startDate.getEditor().clear();
        endDate.setDisable(true);
        endDate.getEditor().clear();

        // Reset the Data View by calling the setup again
        setupColumns();
    }

    @FXML
    private void handelApplyButton() {
        filterData();
    }
    //endregion

    //region TextField Handlers
    @FXML
    private void setRemoveTextBoxWhite() {
        if (!removeTextField.getStyle().equals("-fx-text-box-border:  #ffffff;"))
            removeTextField.setStyle("-fx-text-box-border:  #ffffff;");
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

            Alert alert = CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.WARNING,
                    "Do you want to remove this entry?", true);

            if (CreateAlerts.getInstance().getResult().orElse(no) == yes) {
                removeSelectedRow();
                removeTextField.clear();
                if (!isFiltered)
                    setupColumns();
            }
        }
    }

    @FXML
    private void searchData() {
        // TODO Search the data using a searching algorithim

        // Create new empty list to store the text field data
        List<String> textFieldList = new ArrayList<>();

        /*
         loop through the Children in the anchor to see
         if the child object is a text field and is enabled
         and then add it to the list
        */
        for (Node node : filterAnchorPane.getChildren()) {
            if (!node.isDisabled() && node instanceof TextField) {
                textFieldList.add(((TextField) node).getText());
            }
        }
    }
    //endregion

    //endregion

    //region Private Methods
    private void startApp() throws IOException {

        // Get the instance of the HashMapOperations class. Sets this up for the rest of this class
        hashMapOps = HashMapOperations.getInstance();

        // Set the Has Map to the Has Table from the json file which is done via the HashMapOperations
        permitHolderMap = hashMapOps.getHashMap();

        createAlerts = createAlerts.getInstance();

        // Populate the Columns on the GUI
        setupColumns();
    }

    private void setupColumns() {
        isFiltered = false;

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

    // Filter Data
    private void filterData() {

        permits = FXCollections.observableArrayList(permitHolderMap.entrySet());
        // Create a new FilterData object
        FilterData filterData = new FilterData();

        // Set the filterData values
        filterData.setID(idTextField.getText());
        filterData.setFirstName(firstNameTextField.getText());
        filterData.setLastName(lastNameTextField.getText());
        filterData.setAddress(addressTextField.getText());
        filterData.setAreaCode(areaCodeTextField.getText());
        filterData.setStartDate(startDate.getValue());
        filterData.setExpiryDate(endDate.getValue());

        // Filter the data
        List<Map.Entry<String, PermitHolder>> filteredPermitHolders = filterData.filter(permitHolderMap.entrySet())
                .collect(Collectors.toList());
        ObservableList<Map.Entry<String, PermitHolder>> filteredPermits = FXCollections.observableArrayList(filteredPermitHolders);
        // Set up the table columns
        updateTable(filteredPermits);

        // Set the isFiltered flag
        isFiltered = true;

    }

    // Update Table based on Filter or Search Results
    private void updateTable(ObservableList<Map.Entry<String, PermitHolder>> filteredData) {
        // Make a list of Items that is used for the JavaFX implementation and populate it with the items
        // from our Hash Map
        permits = filteredData;

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


    // Remove the Data from the Selected Row
    private void removeSelectedRow() {
        // Call the table operations class
        TableOperations tableOperations = new TableOperations();

        String idToRemove = removeTextField.getText();
        try {
            if (hashMapOps.getHashMap().get(removeTextField.getText()) == null) {
                // Display an error message if the ID doesn't exist in the Hash Map
                createAlerts.alertBuilder(CreateAlerts.AlertType.ERROR, "ID not found in Hash Map", false);
            } else {
                // Remove the Item from the Observable Table and the Hash map from the Table Operations class
                permits = tableOperations.removePermit(idToRemove, permits);

                // Get our newly updated hashmap
                permitHolderMap = hashMapOps.getHashMap();

                // Reset our text field
                removeTextField.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion
}
