package Functions;

import BPlusTree.BPlusTree;
import Classes.PermitHolder;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DebugFunctions {
    public void outputNames(BPlusTree<String, PermitHolder> tree, Set<Map.Entry<PermitHolder, PermitHolder>> entries){
        List<PermitHolder> result = tree.rangeSearch(entries.stream().findFirst().get().getKey(), entries.stream().reduce((a, b) -> b).orElse(null).getKey() );
        for (PermitHolder entry : result) {
            System.out.println(entry.getId() + ": " + entry.getFirstName());
        }

    }
}
