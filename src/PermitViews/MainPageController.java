package PermitViews;

import Classes.Address;
import Classes.Car;
import Classes.Permit;
import Classes.PermitHolder;
import Functions.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MainPageController {
    //region FXML Properties
    @FXML
    AnchorPane filterAnchorPane;
    @FXML
    TableView<Map.Entry<Integer, PermitHolder>> permitDataTable;
    @FXML
    TableColumn<Map.Entry<Integer, PermitHolder>, String> idColumn, fNameColumn, lNameColumn, addressColumn,
            regColumn, areasColumn, startColumn, expiryColumn;
    @FXML
    Tab filterTab, addTab;
    @FXML
    TextField firstNameTextField, lastNameTextField, addressTextField, areaCodeTextField, removeTextField,
            searchTextField;
    @FXML
    TextField addFirstName, addLastName, addHouseNum, addStreet, addTown, addCounty, addPostCode, addCarReg, addCarMake,
            addCarModel, addCarColour;

    @FXML
    CheckBox firstNameCheckBox, lastNameCheckBox, addressCheckBox, areaCodeCheckBox;
    @FXML
    Button filterResetBtn, removeButton, orderButton, addButton, cancelButton;

    @FXML
    ChoiceBox<String> sortByChoiceBox, addArea;
    //endregion

    //region Properties
    private HashMapOperations hashMapOps;
    private HashMapQuickSort quickSort;
    private HashMap<Integer, PermitHolder> permitHolderMap;
    private ObservableList<Map.Entry<Integer, PermitHolder>> permits;
    private Boolean isFiltered = false;
    HashMapQuickSort.sortBy sortBy;
    //endregion


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

    //region Filter Handlers
    @FXML
    private void handelFirstNameCheckBoxAction() {
        firstNameTextField.setDisable(!firstNameCheckBox.isSelected());

        if (!firstNameCheckBox.isSelected()) {
            firstNameTextField.clear();
        }
    }

    @FXML
    private void handelLastNameCheckBoxAction() {
        lastNameTextField.setDisable(!lastNameCheckBox.isSelected());

        if (!lastNameCheckBox.isSelected()) {
            lastNameTextField.clear();
        }
    }

    @FXML
    private void handelAddressCheckBoxAction() {
        addressTextField.setDisable(!addressCheckBox.isSelected());
        if (!addressCheckBox.isSelected()) {
            addressTextField.clear();
        }
    }

    @FXML
    private void handelAreaCodeCheckBoxAction() {
        areaCodeTextField.setDisable(!areaCodeCheckBox.isSelected());

        if (!areaCodeCheckBox.isSelected()) {
            areaCodeTextField.clear();
        }
    }

    // Reset all the filters  if the reset button is pressed
    @FXML
    private void handelResetButton() {

        // Reset the checkboxes
        firstNameCheckBox.setSelected(false);
        lastNameCheckBox.setSelected(false);
        addressCheckBox.setSelected(false);

        // Disable the Text Fields and Date Fields
        firstNameTextField.setDisable(true);
        lastNameTextField.setDisable(true);
        addressTextField.setDisable(true);

        // Reset the Text Fields and Date Fields
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();

        // Reset the Data View by calling the setup again
        setupColumns();
    }

    @FXML
    private void handelApplyButton() {
        filterData();
    }
    //endregion

    //region Search Handler
    @FXML
    private void searchData() throws IOException {

        if (searchTextField.getText().equals("")) {
            searchTextField.setStyle("-fx-text-box-border: #ff0000;");
        } else {
            String searchString = searchTextField.getText();

            // Search for Data based on our search Text Field
            List<PermitHolder> indexSearch = new ArrayList<>(permitHolderMap.values());

            // Update the Results via the Search Table method
            searchTable(indexSearch, searchString);
        }

    }
    //endregion

    //region Sort Handlers

    @FXML
    public void setOrder() {
        if (quickSort.order.equals(true)) {
            quickSort.order = false;
            orderButton.setText("v");
            sortData();
        } else if (quickSort.order.equals(false)) {
            quickSort.order = true;
            orderButton.setText("^");
            sortData();
        }
    }
    //endregion

    //region Add Handler
    @FXML
    public void addPermit() throws IOException {
        Address address = new Address(addHouseNum.getText(), addStreet.getText(), addTown.getText(), addCounty.getText(),
                addPostCode.getText());
        Car car = new Car(addCarReg.getText(), addCarMake.getText(), addCarModel.getText(), addCarColour.getText());
        Permit permit = new Permit("A1", LocalDate.now(), LocalDate.now().plusYears(1));
        PermitHolder newPermit = new PermitHolder(hashMapOps.getNextID(), addFirstName.getText(), addLastName.getText(), address, car, permit);

        ObservableList<Map.Entry<Integer, PermitHolder>> updatedPermit = FXCollections.observableArrayList();
        updatedPermit.addAll(hashMapOps.newPermit(newPermit).entrySet());
        PermitData.saveNewPermit(newPermit);
        setupColumns();
    }
    //endregion

    //region TextField Handlers
    @FXML
    private void setRemoveTextBoxWhite() {
        if (!removeTextField.getStyle().equals("-fx-text-box-border:  #ffffff;"))
            removeTextField.setStyle("-fx-text-box-border:  #ffffff;");
    }

    @FXML
    private void setSearchTextBoxWhite() {
        if (!searchTextField.equals("-fx-text-box-border:  #ffffff;"))
            searchTextField.setStyle("-fx-text-box-border:  #ffffff;");
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

            Optional<ButtonType> result = CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.WARNING,
                    "Do you want to remove this entry?", true).showAndWait();

            if (result.get().getText().equals("Yes")) {
                removeSelectedRow();
                removeTextField.clear();
                if (!isFiltered)
                    setupColumns();
            }
        }
    }
    //endregion

    //region Private Methods
    private void startApp() throws IOException {

        // Get the instance of the HashMapOperations class. Sets this up for the rest of this class
        hashMapOps = HashMapOperations.getInstance();

        // Set the Has Map to the Has Table from the json file which is done via the HashMapOperations
        permitHolderMap = hashMapOps.getHashMap();

        quickSort = HashMapQuickSort.getInstance();

        setupChoiceBox();


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
        idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getValue().getId()).asString());
        fNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getFirst_name()));
        lNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getLast_name()));
        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getAddress().toString()));
        regColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getCar().sendRegNum()));
        areasColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendAreaCodes()));
        startColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendStartDate()));
        expiryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendExpiryDate()));
    }

    // Filter Data
    private void filterData() {

        permits = FXCollections.observableArrayList(permitHolderMap.entrySet());
        // Create a new FilterData object
        FilterData filterData = new FilterData();


        if (firstNameCheckBox.isSelected() && firstNameTextField.getText().equals("")) {
            CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.WARNING, "Please enter a valid First Name", false);
        } else if (lastNameCheckBox.isSelected() && lastNameTextField.getText().equals("")) {
            CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.WARNING, "Please enter a valid Last Name", false);
        } else if (addressCheckBox.isSelected() && addressTextField.getText().equals("")) {
            CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.WARNING, "Please enter a valid Address", false);
        } else if (areaCodeCheckBox.isSelected() && areaCodeTextField.getText().equals("")) {
            CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.WARNING, "Please enter a valid Area Code", false);
        } else {
            // Set the filterData values
            //   filterData.setID(Integer.parseInt(idTextField.getText()));
            filterData.setFirstName(firstNameTextField.getText());
            filterData.setLastName(lastNameTextField.getText());
            filterData.setAddress(addressTextField.getText());
            filterData.setAreaCode(areaCodeTextField.getText());
            //   filterData.setStartDate(startDate.getValue());
            //       filterData.setExpiryDate(endDate.getValue());

            // Filter the data
            List<Map.Entry<Integer, PermitHolder>> filteredPermitHolders = filterData.filter(permitHolderMap.entrySet())
                    .collect(Collectors.toList());
            ObservableList<Map.Entry<Integer, PermitHolder>> filteredPermits = FXCollections.observableArrayList(filteredPermitHolders);
            // Set up the table columns
            updateTable(filteredPermits);

            // Set the isFiltered flag
            isFiltered = true;

        }
    }

    // Update Table based on Filter or Search Results
    private void updateTable(ObservableList<Map.Entry<Integer, PermitHolder>> updatedData) {
        // Make a list of Items that is used for the JavaFX implementation and populate it with the items
        // from our Hash Map
        permits = updatedData;

        // Set the data table to read in the permit list
        permitDataTable.setItems(permits);

        // Apply the values from our Hash Map pass-through to all the columns on the table
        idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getValue().getId()).asString());
        fNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getFirst_name()));
        lNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getLast_name()));
        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getAddress().toString()));
        regColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getCar().sendRegNum()));
        areasColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendAreaCodes()));
        startColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendStartDate()));
        expiryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendExpiryDate()));
    }


    private void searchTable(List<PermitHolder> results, String searchString) {
        // Filter the results based on the search string
        List<PermitHolder> filteredResults = results.stream()
                .filter(ph -> ph.toString().contains(searchString))
                .toList();

        // Get the filtered results and pass them to a Hash Set
        Set<PermitHolder> uniquePermitHolders = new HashSet<>(filteredResults);

        // Convert the unique PermitHolder objects to Map entries and add them to the results map
        HashMap<Integer, PermitHolder> returnResults = new HashMap<>();

        for (PermitHolder permitHolder : uniquePermitHolders) {
            int id = permitHolder.getId();
            returnResults.put(id, permitHolder);
        }

        ObservableList<Map.Entry<Integer, PermitHolder>> searchResults = FXCollections.observableArrayList();
        searchResults.addAll(returnResults.entrySet());

        // Set the data table to read in the permit list
        permitDataTable.setItems(searchResults);

        // Apply the values from our Hash Map pass-through to all the columns on the table
        idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getValue().getId()).asString());
        fNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getFirst_name()));
        lNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getLast_name()));
        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getAddress().toString()));
        regColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getCar().sendRegNum()));
        areasColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendAreaCodes()));
        startColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendStartDate()));
        expiryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPermit().sendExpiryDate()));
    }


    private void sortData() {
        // Sort the Table's data based on the current sortBy enum value
        Map<Integer, PermitHolder> sortedMap = quickSort.quickSort(permitHolderMap, 1, permitHolderMap.size() - 1, sortBy);

        // store the Map as an Observable list so that Java FX can read it
        ObservableList<Map.Entry<Integer, PermitHolder>> sortResult = FXCollections.observableArrayList();
        sortResult.addAll(sortedMap.entrySet());

        // Update the table
        permitDataTable.getItems().clear();
        permitDataTable.setItems(sortResult);
    }

    // Method for Aditional Handlers that need to be setup at the start of the program
    private void setUpAdditionalHandlers() {
        // Set the remove text field to the specific selected column id
        permitDataTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null) {
                removeTextField.setText(String.valueOf(newVal.getKey()));
            }
        });

        // When the Choice box value is changed, change the table data accordingly
        sortByChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ChangeListener) (ov, old, newval) -> {
            Number idx = (Number) newval;

            switch (idx.intValue()) {
                case 1 -> {
                    sortBy = HashMapQuickSort.sortBy.FIRST_NAME;
                    sortData();
                }

                case 2 -> {
                    sortBy = HashMapQuickSort.sortBy.LAST_NAME;
                    sortData();
                }

                case 3 -> {
                    sortBy = HashMapQuickSort.sortBy.ADDRESS;
                    sortData();
                }
                case 4 -> {
                    sortBy = HashMapQuickSort.sortBy.CAR;
                    sortData();
                }
                case 5 -> {
                    sortBy = HashMapQuickSort.sortBy.START_DATE;
                    sortData();
                }
                case 6 -> {
                    sortBy = HashMapQuickSort.sortBy.END_DATE;
                    sortData();
                }
                default -> {
                    sortBy = HashMapQuickSort.sortBy.ID;
                    sortData();
                }
            }
        });
    }

    // Set up the Choice box at the start of the application and set the default selection to the ID
    private void setupChoiceBox() {
        sortByChoiceBox.getItems().add("ID");
        sortByChoiceBox.getItems().add("First Name");
        sortByChoiceBox.getItems().add("Last Name");
        sortByChoiceBox.getItems().add("Address");
        sortByChoiceBox.getItems().add("Car");
        sortByChoiceBox.getItems().add("Start Date");
        sortByChoiceBox.getItems().add("Expiry Date");
        sortByChoiceBox.getSelectionModel().select(0);

        sortBy = HashMapQuickSort.sortBy.ID;
    }


    // Remove the Data from the Selected Row
    private void removeSelectedRow() {
        // Call the table operations class
        TableOperations tableOperations = new TableOperations();

        String idToRemove = removeTextField.getText();
        try {
            if (hashMapOps.getHashMap().get(Integer.parseInt(removeTextField.getText())) == null) {
                // Display an error message if the ID doesn't exist in the Hash Map
                CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.ERROR,
                        "ID not found in Hash Map", false);
            } else {
                // Remove the Item from the Observable Table and the Hash map from the Table Operations class
                PermitData.removePermit(Integer.parseInt(idToRemove));
                permits = tableOperations.removePermit(Integer.parseInt(idToRemove), permits);

                // Get our newly updated hashmap
                permitHolderMap = hashMapOps.getHashMap();
                // Reset our text field
                removeTextField.clear();

                hashMapOps.updateHashMap(permitHolderMap);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //endregion
}
