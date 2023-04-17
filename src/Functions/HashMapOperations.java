package Functions;

import Classes.Permit;
import Classes.PermitHolder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapOperations {

    //region Singleton
    private static HashMapOperations instance;

    public static HashMapOperations getInstance() {
        if (instance == null) {
            instance = new HashMapOperations();
        }
        return instance;
    }
    //endregion

    //region Properties
    private HashMap<Integer, PermitHolder> permitHolderMap;
    private String permitHolders[];

    public void updateHashMap(HashMap<Integer, PermitHolder> hashMap){
        permitHolderMap = hashMap;
    }

    public HashMap<Integer, PermitHolder> getHashMap() throws IOException {
        if(permitHolderMap == null){
           permitHolderMap = LoadPermitData.loadPermitData();
        }
        return permitHolderMap;
    }

    public HashMap<Integer, PermitHolder> removeItem(Integer itemToRemove){
        Integer count = 0;
        List<String> permits = Arrays.asList(permitHolders);
        for(int i = 0; i < permits.size(); i ++){
            if(permits.get(i).equals(itemToRemove)){
                permitHolderMap.remove(itemToRemove);
            }
        }
        return permitHolderMap;
    }
}
