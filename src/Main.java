import BPlusTree.BPlusTree;
import Classes.PermitHolder;
import Functions.DataOperations;
import Functions.DebugFunctions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {
            File filePath = new File("src/Data/PermitData.json");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            Map<String, PermitHolder> data = new Gson().fromJson(bufferedReader, new TypeToken<Map<String, PermitHolder>>() {}.getType());
            bufferedReader.close();

            BPlusTree<String, PermitHolder> tree = new BPlusTree<String, PermitHolder>();

            for (Map.Entry<String, PermitHolder> entry : data.entrySet()) {
                String id = entry.getKey();
                PermitHolder permitHolder = new PermitHolder(id, entry.getValue().getFirstName(), entry.getValue().getLastName(), entry.getValue().getAddress(), entry.getValue().getCar(), entry.getValue().getPermit());
                tree.insert(permitHolder, permitHolder);
            }

            Set<Map.Entry<PermitHolder, PermitHolder>> entries = tree.entrySet();
            System.out.println("Number of entries: " + entries.size());
            for (Map.Entry<PermitHolder, PermitHolder> entry : entries) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }


            // Made a Debug Function to test the refactor of the code as when I tested it before when I originally
            // refactored it, it wouldn't display the output data correctly and would just appear blank. Currently
            // this works when out putting so I suspect changing how we store the entries or potentially when storing
            // the tree could be causing the issue. The issue was that Leaf would return NULL when we would call
            // entries.stream().findFirst().get().getKey() for some reason when the code was split up. So I want to go
            // through one by one and ensure that it works as intended
            DebugFunctions debug = new DebugFunctions();
            debug.outputNames(tree, DataOperations.getInstance().updateEntries(entries));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}




