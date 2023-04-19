package Functions;

import Classes.PermitHolder;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.Map;

public class TableOperations {
    HashMapOperations hashMapOps;

    public TableOperations() {
        hashMapOps = HashMapOperations.getInstance();
    }

    public ObservableList<Map.Entry<Integer, PermitHolder>> removePermit(int idToRemove, ObservableList<Map.Entry<Integer, PermitHolder>> permits) throws IOException {
        // Remove the item from the Observable list if the ID exists on the list
        permits.removeIf(permit -> permit.getKey() == idToRemove);

        // Remove the ID from the Hashmap if the ID exists
        if (hashMapOps.getHashMap().get(idToRemove) == null) {

            // Display an error message if the ID doesn't exist in the Hash Map
            Alert alert = new Alert(Alert.AlertType.ERROR, "ID not Found in Hash Map");
            alert.setTitle("Error: Permit Not Found");
            alert.show();
        } else {
            // Remove the Item from the Hash Map via the HashMapOps class and update it
            hashMapOps.removeItem(idToRemove);
        }
        return permits;
    }
}
