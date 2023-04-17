package Functions;

import Classes.PermitHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class LoadPermitData {
    public static HashMap<Integer, PermitHolder> loadPermitData() throws IOException {

        File jsonData = new File("src/Data/PermitData.json");
        if(!jsonData.exists()){
            jsonData.getParentFile().mkdirs();
            jsonData.createNewFile();
        }

        // Create a new Gson builder that specifies the Year Month and Day values from the JSON file we pass in.
        // This was done as we would get an error when trying to parse the date out to the console during testing
        // this still allows for it to work on the UI though, so it will ensure that data is correct
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> {
                    JsonObject jsonObject = json.getAsJsonObject();
                    int year = jsonObject.get("year").getAsInt();
                    int month = jsonObject.get("month").getAsInt();
                    int day = jsonObject.get("day").getAsInt();
                    return LocalDate.of(year, month, day);
                })
                .create();

        // Read in the Json File from src/Data
        BufferedReader reader = new BufferedReader(new FileReader("src/Data/PermitData.json"));

        // Apply each read in line to an array of Permit Holders
        PermitHolder[] permitHolders = gson.fromJson(reader, PermitHolder[].class);

        // Create a new Hash Map to store the Permit Data
        HashMap<Integer, PermitHolder> permitHolderMap = new HashMap<Integer, PermitHolder>();

        // Loop through each permit in our Array and add it to our Hash Map
        for (PermitHolder ph : permitHolders) {
            permitHolderMap.put(ph.getId(), ph);
        }

        // Close the Buffer Reader once we are done with it
        reader.close();

        return permitHolderMap;
    }
}
