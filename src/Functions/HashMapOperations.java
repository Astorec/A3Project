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
    private HashMap<String, PermitHolder> permitHolderMap;
    private String permitHolders[];

    public void updateHashMap(HashMap<String, PermitHolder> hashMap){
        permitHolderMap = hashMap;
    }

    public HashMap<String, PermitHolder> getHashMap() throws IOException {
        if(permitHolderMap == null){
           permitHolderMap = LoadPermitData.loadPermitData();
        }
        return permitHolderMap;
    }

    public HashMap<String, PermitHolder> removeItem(String itemToRemove){
        int count = 0;
        permitHolderMap.remove(itemToRemove);

        List<String> permits = Arrays.asList(permitHolders);
        for(String permit : permits){
            if(permit == itemToRemove){
                permits.remove(count);
            }
            else{
                count++;
            }
        }

        return permitHolderMap;
    }

    private void setPermitHolders(){
        int count = 0;
        for(Map.Entry permit : permitHolderMap.entrySet()){
            permitHolders[count] = permit.getKey().toString();
            count++;
        }
    }

    public String[] getPermitHolders(){
        return permitHolders;
    }
}
