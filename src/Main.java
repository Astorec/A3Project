import Classes.PermitHolder;
import Functions.HashMapOperations;
import Functions.ParseJsonData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main {

    //region Main Method
    public static void main(String[] args) {
        // Attempt to create a new Start Method and throw error message to console if there is any issues
        try {
            Start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //endregion

    //region Main Function
    private static void Start() throws IOException {
        // Parse the JSON data into a list of PermitHolder objects
        List<PermitHolder> permitHolders = ParseJsonData.permitData();

        // Create a HashMap from the list of PermitHolder objects, with the ID as the key
        HashMap<String, PermitHolder> permitHolderMap = HashMapOperations.createHashMap(permitHolders);

        // Output all the data to console
        OutputToConsole(permitHolderMap);
    }

    private static void OutputToConsole(HashMap<String, PermitHolder> permitHolderMap) {
        // Access the data through the console
        // Iterate over the PermitHolder objects in the HashMap and print out their data
        for (PermitHolder permitHolder : permitHolderMap.values()) {
            System.out.println("ID: " + permitHolder.getId());
            System.out.println("First name: " + permitHolder.getFirst_name());
            System.out.println("Last name: " + permitHolder.getLast_name());
            System.out.println("Address: " + permitHolder.getAddress().toString());
            System.out.println("Car: " + permitHolder.getCar().toString());
            System.out.println("Permit: " + permitHolder.getPermit().toString());
        }
    }
    //endregion
}
