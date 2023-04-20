package Functions;

import Classes.PermitHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class LinkedListIndexing {

    //region Properties
    private static LinkedListIndexing instance;
    //endregion

    //region Singleton
    public static LinkedListIndexing getInstance() {
        if (instance == null) {
            instance = new LinkedListIndexing();
        }
        return instance;
    }
    //endregion

    //region Methods
    // This method returns an Array List based on the search term that is passed through to it
    // allowing for an Index search to be carried out on the Linked List
    public LinkedList<PermitHolder> indexSearch(String term) throws IOException {

        // Create a new blank Array List that will return later
        LinkedList<PermitHolder> foundPermits = new LinkedList<>();

        // Loop through the entire Linked List and add the values that match our search term
        for (PermitHolder permit : LinkedListOperations.getInstance().getList()) {
            if (permit.toString().contains(term)) {
                foundPermits.add(permit); // Add the Result to the Array List
            }
        }

        // Return the Array List
        return foundPermits;
    }
    //endregion
}
