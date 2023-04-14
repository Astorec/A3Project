package Functions;

import Classes.Permit;
import Classes.PermitHolder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HashBasedSearch {
    public static HashBasedSearch instance;

    private static HashBasedSearch getInstance() {
        if (instance == null) {
            instance = new HashBasedSearch();
        }
        return instance;
    }

    public PermitHolder searchData(PermitHolder permit) throws IOException {

        String key = permit.getId();

        HashMapOperations hashMapOps = HashMapOperations.getInstance();
        // Get the key that is passed in

        // Get the instance of the current Array of Hash Keys
        var permitHolders = hashMapOps.getPermitHolders();
        //  List<String> permitHolders = Arrays.asList(hashMapOps.getPermitHolders());

        // Get the key where our search term is located

        for (int i = 0; i < Arrays.stream(permitHolders).count(); i++) {
            if (permitHolders[i] == key) {
                return HashMapOperations.getInstance().getHashMap().get(key);
            }
        }
        return null;
    }



}
