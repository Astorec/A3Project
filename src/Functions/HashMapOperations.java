package Functions;

import Classes.PermitHolder;

import java.io.IOException;
import java.util.*;

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
           permitHolderMap = PermitData.loadPermitData();
        }
        return permitHolderMap;
    }

    public HashMap<Integer, PermitHolder> removeItem(Integer itemToRemove){
        Integer count = 0;
        List<String> permits = Arrays.asList(String.valueOf(permitHolderMap));
        for(int i = 0; i < permits.size(); i ++){
            if(permits.get(i).equals(itemToRemove)){
                permitHolderMap.remove(itemToRemove);
            }
        }
        return permitHolderMap;
    }

    public HashMap<Integer, PermitHolder> newPermit(PermitHolder permitHolder){
        permitHolderMap.put(permitHolder.getId(), permitHolder);
        return permitHolderMap;
    }

    public int getNextID(){
        Optional<Map.Entry<Integer, PermitHolder>> maxEntry = permitHolderMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByKey()
                );

        return maxEntry.get().getKey() + 1;
    }
}
