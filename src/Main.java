import BPlusTree.BPlusTree;
import Classes.PermitHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashSet;
import java.util.List;
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

            // Range search
            System.out.println("Range search:");
            List<PermitHolder> result = tree.rangeSearch(entries.stream().findFirst().get().getKey(), entries.stream().reduce((a, b) -> b).orElse(null).getKey() );
            for (PermitHolder entry : result) {
                System.out.println(entry.getId() + ": " + entry.getFirstName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}




