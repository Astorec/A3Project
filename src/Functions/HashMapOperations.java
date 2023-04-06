package Functions;

import Classes.PermitHolder;

import java.util.HashMap;
import java.util.List;

public class HashMapOperations {

    // Method to convert a List of PermitHolder objects to a HashMap with ID as the key
    public static HashMap<String, PermitHolder> createHashMap(List<PermitHolder> permitHolders) {

        // Create an empty HashMap to store the data
        HashMap<String, PermitHolder> permitHolderMap = new HashMap<>();

        // Iterate through the List of PermitHolder objects
        for (PermitHolder permitHolder : permitHolders) {
            // Add the PermitHolder object to the HashMap using its ID as the key
            permitHolderMap.put(permitHolder.getId(), permitHolder);
        }

        // Return the populated HashMap
        return permitHolderMap;
    }
}