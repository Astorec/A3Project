package Functions;

import Classes.PermitHolder;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;

public class TableOperations {
    LinkedListOperations linkedListOps;

    public TableOperations() {
        linkedListOps = LinkedListOperations.getInstance();
    }

    public ObservableList<PermitHolder> removePermit(int idToRemove, ObservableList<PermitHolder> permits) throws IOException {
        // Remove the item from the Observable list if the ID exists on the list
        permits.removeIf(permit -> permit.getId() == idToRemove);

        // Remove the ID from the linked list if the ID exists
        if (linkedListOps.getList().get(idToRemove) == null) {

            // Display an error message if the ID doesn't exist in the Linked List
            Alert alert = new Alert(Alert.AlertType.ERROR, "ID not Found in Linked List");
            alert.setTitle("Error: Permit Not Found");
            alert.show();
        } else {
            // Remove the Item from the Linked List via the LinkedListOps class and update it
            linkedListOps.getList().remove(idToRemove);
        }
        return permits;
    }
}
