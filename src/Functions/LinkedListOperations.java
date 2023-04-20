package Functions;

import Classes.PermitHolder;

import java.io.IOException;
import java.util.*;

public class LinkedListOperations {

    //region Singleton
    private static LinkedListOperations instance;

    public static LinkedListOperations getInstance() {
        if (instance == null) {
            instance = new LinkedListOperations();
        }
        return instance;
    }
    //endregion

    //region Properties
    private LinkedList<PermitHolder> permitHolderList;

    public void updateList(LinkedList<PermitHolder> list){
        permitHolderList = list;
    }

    public LinkedList<PermitHolder> getList() throws IOException {
        if(permitHolderList == null){
            permitHolderList = PermitData.loadPermitData();
        }
        return permitHolderList;
    }

    public int getNextID(){
        Optional<PermitHolder> maxEntry = permitHolderList.stream()
                .max(Comparator.comparing(PermitHolder::getId));

        return maxEntry.map(PermitHolder::getId).orElse(0) + 1;
    }
}
