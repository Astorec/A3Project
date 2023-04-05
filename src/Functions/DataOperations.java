package Functions;

import Classes.PermitHolder;

import java.util.Map;
import java.util.Set;

public class DataOperations {
    Set<Map.Entry<PermitHolder, PermitHolder>> entries;

    private DataOperations() {

    }

    private static DataOperations instance;

    public static DataOperations getInstance() {
        if (instance == null) {
            instance = new DataOperations();
        }
        return instance;
    }

    public Set<Map.Entry<PermitHolder, PermitHolder>> updateEntries(Set<Map.Entry<PermitHolder, PermitHolder>> entryData) {
        entries = entryData;
        return returnEntries();
    }

    private Set<Map.Entry<PermitHolder, PermitHolder>> returnEntries() {
        return entries;
    }


}
