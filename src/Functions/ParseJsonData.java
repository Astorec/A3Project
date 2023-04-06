package Functions;

import Classes.PermitHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ParseJsonData {
    public static List<PermitHolder> permitData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(getJson()));

        // Convert JSON to Java object using Gson library
        Gson gson = new Gson();

        // Create a Type object for the PermitHolder List using TypeToken and generic parameter
        Type permitHolderListType = new TypeToken<List<PermitHolder>>() {
        }.getType();

        // Read the JSON file and convert it to a list of PermitHolder objects
        return gson.fromJson(reader, permitHolderListType);
    }

    private static String getJson() {
        return "src/Data/PermitData.json";
    }
}
