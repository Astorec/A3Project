package Functions;

import Classes.PermitHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class HashMapIndexing {

    //region Properties
    private static HashMapIndexing instance;
    //endregion

    //region Singleton
    public static HashMapIndexing getInstance() {
        if (instance == null) {
            instance = new HashMapIndexing();
        }
        return instance;
    }
    //endregion

    //region Methods
    // This method returns an Array List based on the search term that is passed through to it
    // allowing for an Index search to be carried out on the Hash Map
    public ArrayList<PermitHolder> indexSearch(String term) throws IOException {

        // Create a new blank Array List that will return later
        ArrayList<PermitHolder> foundPermits = new ArrayList<>();

        // Loop through the entire HashMap and add the values that match our search term
        for (Map.Entry<Integer, PermitHolder> permit : HashMapOperations.getInstance().getHashMap().entrySet()) {
            if (permit.getValue().toString().contains(term)) {
                foundPermits.add(permit.getValue()); // Add the Result to the Array List
            }
        }

        // Return the Array List
        return foundPermits;
    }
    //endregion
}
