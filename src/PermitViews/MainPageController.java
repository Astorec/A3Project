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
import javafx.fxml.JavaFXBuilderFactory;
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
    TableView<PermitHolder> permitDataTable;
    @FXML
    TableColumn<PermitHolder, String> idColumn, fNameColumn, lNameColumn, addressColumn,
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
    private LinkedListOperations linkedListOPs;
    private LinkedListQuickSort quickSort;
    private LinkedList<PermitHolder> permitHolders;
    private ObservableList<PermitHolder> permits;
    private Boolean isFiltered = false;
    LinkedListQuickSort.sortBy sortBy;
    //endregion


    //region Initialize method

    // Initialize is called when the MainView is loaded on the system and executes
    // the start app Method which contains our calls for setting up the Linked List
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

            // Update the Results via the Search Table method
            searchTable(linkedListOPs.getList(), searchString);
        }

    }
    //endregion

    //region Sort Handlers

    // Sort the order of the Quick Sort to Ascend or Descend
    @FXML
    public void setOrder() {
        // Toggle the Bool depnding on the current state of the button
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

        // Make sure the Text Fields are populated before creating a new permit
        if (!addHouseNum.getText().equals("") && !addStreet.getText().equals("") && !addTown.getText().equals("")
                && !addCounty.getText().equals("") && !addPostCode.getText().equals("")
                && !addCarReg.getText().equals("") && !addCarMake.getText().equals("")
                && !addCarModel.getText().equals("") && !addCarColour.getText().equals("")
                && !addFirstName.getText().equals("") && !addLastName.getText().equals("")) {

            // Create the new Address
            Address address = new Address(addHouseNum.getText(), addStreet.getText(), addTown.getText(), addCounty.getText(),
                    addPostCode.getText());

            // Create the new car
            Car car = new Car(addCarReg.getText(), addCarMake.getText(), addCarModel.getText(), addCarColour.getText());

            // Create new Permit
            Permit permit = new Permit("A1", LocalDate.now(), LocalDate.now().plusYears(1));

            // Crate the new Permit Holder
            PermitHolder newPermit = new PermitHolder(linkedListOPs.getNextID(), addFirstName.getText(), addLastName.getText(), address, car, permit);

            // Save the Permit Holder to the JSON
            PermitData.saveNewPermit(newPermit);

            // Update the Permit Holders
            permitHolders = linkedListOPs.getList();

            // Reset the Add Fields
            resetAddFields();

            // Update the Table with the new Permit
            updateTable(FXCollections.observableArrayList(permitHolders));

            // Display message to show permit was added and show its id
            CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.WARNING, "New Permit Added with ID: " + newPermit.getId(), false);

        } else {
            CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.WARNING, "Please fill out all the fields", false);
        }


    }
    //endregion

    //region TextField Handlers
    @FXML
    private void setRemoveTextBoxWhite() {
        // If the box border isn't white, set it to white
        if (!removeTextField.getStyle().equals("-fx-text-box-border:  #ffffff;"))
            removeTextField.setStyle("-fx-text-box-border:  #ffffff;");
    }

    @FXML
    private void setSearchTextBoxWhite() {
        // If the box border isn't white, set it to white
        if (!searchTextField.equals("-fx-text-box-border:  #ffffff;"))
            searchTextField.setStyle("-fx-text-box-border:  #ffffff;");
    }
    //endregion

    //region Dialog Boxes
    @FXML
    private void removeConfirmation() {

        // If the Text Field is empty make the border red
        if (removeTextField.getText().equals("")) {
            removeTextField.setStyle("-fx-text-box-border: #ff0000;");
        } else {

            // Create a new Alert to confirm if the user wants to remove the Entry
            Optional<ButtonType> result = CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.WARNING,
                    "Do you want to remove this entry?", true).showAndWait();

            // If yes, remove the Entry
            if (result.get().getText().equals("Yes")) {
                removeSelectedRow();
                // Reset the Text Field
                removeTextField.clear();
            }
        }
    }
    //endregion

    //region Private Methods
    private void startApp() throws IOException {

        // Get the instance of the Linked List Operations class. Sets this up for the rest of this class
        linkedListOPs = LinkedListOperations.getInstance();

        // Set the Linked List to the Linked List from the json file which is done via the LinkedListOperations
        permitHolders = linkedListOPs.getList();

        quickSort = LinkedListQuickSort.getInstance();

        setupChoiceBox();


        // Populate the Columns on the GUI
        setupColumns();
    }

    private void setupColumns() {
        isFiltered = false;

        // Make a list of Items that is used for the JavaFX implementation and populate it with the items
        // from our Linked List
        permits = FXCollections.observableArrayList(permitHolders);

        // Set the data table to read in the permit list
        permitDataTable.setItems(permits);

        // Apply the values from our Linked List pass-through to all the columns on the table
        idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId()).asString());
        fNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirst_name()));
        lNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLast_name()));
        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress().toString()));
        regColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCar().sendRegNum()));
        areasColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendAreaCodes()));
        startColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendStartDate()));
        expiryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendExpiryDate()));
    }

    // Filter Data
    private void filterData() {
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
            filterData.setFirstName(firstNameTextField.getText());
            filterData.setLastName(lastNameTextField.getText());
            filterData.setAddress(addressTextField.getText());
            filterData.setAreaCode(areaCodeTextField.getText());

            // Filter the data
            List<PermitHolder> filteredPermitHolders = filterData.filter(permitHolders)
                    .collect(Collectors.toCollection(LinkedList::new));
            ObservableList<PermitHolder> filteredPermits = FXCollections.observableArrayList(filteredPermitHolders);

            // Set up the table columns
            permitDataTable.setItems(filteredPermits);
            idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId()).asString());
            fNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirst_name()));
            lNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLast_name()));
            addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress().toString()));
            regColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCar().sendRegNum()));
            areasColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendAreaCodes()));
            startColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendStartDate()));
            expiryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendExpiryDate()));

            // Set the isFiltered flag
            isFiltered = true;
        }
    }


    // Update Table based on Filter or Search Results
    private void updateTable(ObservableList<PermitHolder> updatedData) {
        // Make a list of Items that is used for the JavaFX implementation and populate it with the items
        // from our Linked List
        permits = updatedData;

        // Set the data table to read in the permit list
        permitDataTable.setItems(permits);

        // Apply the values from our Linked List pass-through to all the columns on the table
        idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId()).asString());
        fNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirst_name()));
        lNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLast_name()));
        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress().toString()));
        regColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCar().sendRegNum()));
        areasColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendAreaCodes()));
        startColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendStartDate()));
        expiryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendExpiryDate()));
    }


    private void searchTable(List<PermitHolder> results, String searchString) {
        // Filter the results based on the search string
        List<PermitHolder> filteredResults = results.stream()
                .filter(ph -> ph.toString().contains(searchString))
                .toList();

        // Convert the Filtered PermitHolder objects to a new Linked List entries and add them to the results Observable List
        LinkedList<PermitHolder> returnResults = new LinkedList<>();

        for (PermitHolder permitHolder : filteredResults) {
            int id = permitHolder.getId();
            returnResults.add(permitHolder);
        }

        ObservableList<PermitHolder> searchResults = FXCollections.observableArrayList();
        searchResults.addAll(returnResults);

        // Set the data table to read in the permit list
        permitDataTable.setItems(searchResults);

        // Apply the values from our Linked List pass-through to all the columns on the table
        idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId()).asString());
        fNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirst_name()));
        lNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLast_name()));
        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress().toString()));
        regColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCar().sendRegNum()));
        areasColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendAreaCodes()));
        startColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendStartDate()));
        expiryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPermit().sendExpiryDate()));
    }


    private void sortData() {
        // Sort the Table's data based on the current sortBy enum value
        LinkedList<PermitHolder> sortedList = quickSort.quickSort(permitHolders, 0, permitHolders.size() - 1, sortBy);

        // store the Linked List as an Observable list so that Java FX can read it
        ObservableList<PermitHolder> sortResult = FXCollections.observableArrayList();
        sortResult.addAll(sortedList);

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
                removeTextField.setText(String.valueOf(newVal.getId()));
            }
        });

        // When the Choice box value is changed, change the table data accordingly
        sortByChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ChangeListener) (ov, old, newval) -> {
            Number idx = (Number) newval;

            switch (idx.intValue()) {
                case 1 -> {
                    sortBy = LinkedListQuickSort.sortBy.FIRST_NAME;
                    sortData();
                }

                case 2 -> {
                    sortBy = LinkedListQuickSort.sortBy.LAST_NAME;
                    sortData();
                }

                case 3 -> {
                    sortBy = LinkedListQuickSort.sortBy.ADDRESS;
                    sortData();
                }
                case 4 -> {
                    sortBy = LinkedListQuickSort.sortBy.CAR;
                    sortData();
                }
                case 5 -> {
                    sortBy = LinkedListQuickSort.sortBy.START_DATE;
                    sortData();
                }
                case 6 -> {
                    sortBy = LinkedListQuickSort.sortBy.END_DATE;
                    sortData();
                }
                default -> {
                    sortBy = LinkedListQuickSort.sortBy.ID;
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
        sortBy = LinkedListQuickSort.sortBy.ID;

        addArea.getItems().add("A1");
        addArea.getItems().add("A2");
        addArea.getItems().add("A3");
        addArea.getSelectionModel().select(0);
    }

    // method that resets all the Text Fields under the Add Tab
    private void resetAddFields() {
        addHouseNum.clear();
        addStreet.clear();
        addTown.clear();
        addCounty.clear();
        addPostCode.clear();
        addCarReg.clear();
        addCarMake.clear();
        addCarModel.clear();
        addCarColour.clear();
        addFirstName.clear();
        addLastName.clear();
    }


    // Remove the Data from the Selected Row
    private void removeSelectedRow() {
        // Call the table operations class
        TableOperations tableOperations = new TableOperations();

        String idToRemove = removeTextField.getText();
        Boolean notFound = true;
        try {
            Iterator<PermitHolder> iterator = permitHolders.iterator();

            while (iterator.hasNext()) {
                if ((iterator.next().getId() == Integer.parseInt(idToRemove))) {
                    // Remove the Item from the Observable Table and the Linked List from the Table Operations class
                    iterator.remove();
                    PermitData.removePermit(Integer.parseInt(idToRemove));

                    // Get our newly updated Linked List
                    permitHolders = linkedListOPs.getList();
                    // Reset our text field
                    removeTextField.clear();

                    linkedListOPs.updateList(permitHolders);

                    if (!isFiltered) {
                        ObservableList<PermitHolder> updatedList = FXCollections.observableArrayList(permitHolders);
                        updateTable(updatedList);
                        notFound = false;
                    }
                }
            }
            if (notFound) {
                // Display an error message if the ID doesn't exist in the Linked List
                CreateAlerts.getInstance().alertBuilder(CreateAlerts.AlertType.ERROR,
                        "ID not found in Linked List", false);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //endregion
}
