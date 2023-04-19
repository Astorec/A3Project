package Functions;

import Classes.PermitHolder;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class PermitData {

    // Get the file path of the JSON file
    private static File jsonData = new File("src/Data/PermitData.json");

    public static HashMap<Integer, PermitHolder> loadPermitData() throws IOException {


        // If the File and path doesn't exist, create it
        if (!jsonData.exists()) {
            jsonData.getParentFile().mkdirs();
            jsonData.createNewFile();
        }

        // Create a new Gson builder that specifies the Year Month and Day values from the JSON file we pass in.
        // This was done as we would get an error when trying to parse the date out to the console during testing
        // this still allows for it to work on the UI though, so it will ensure that data is correct
        Gson gson = gson();

        // Read in the Json File from src/Data
        BufferedReader reader = new BufferedReader(new FileReader("src/Data/PermitData.json"));

        // Apply each read in line to an array of Permit Holders
        PermitHolder[] permitHolders = gson.fromJson(reader, PermitHolder[].class);

        // Create a new Hash Map to store the Permit Data
        HashMap<Integer, PermitHolder> permitHolderMap = new HashMap<>();

        // Loop through each permit in our Array and add it to our Hash Map
        for (PermitHolder ph : permitHolders) {
            permitHolderMap.put(ph.getId(), ph);
        }

        // Close the Buffer Reader once we are done with it
        reader.close();

        return permitHolderMap;
    }

    public static void saveNewPermit(PermitHolder permitHolder) throws IOException {

        Gson gson = gson();

        // Read the existing JSON file into a String variable
        BufferedReader reader = new BufferedReader(new FileReader("src/Data/PermitData.json"));
        // Apply each read in line to an array of Permit Holders
        PermitHolder[] permitHolders = gson.fromJson(reader, PermitHolder[].class);

        // Create a new Hash Map to store the Permit Data
        HashMap<Integer, PermitHolder> permitHolderMap = new HashMap<>();

        // Loop through each permit in our Array and add it to our Hash Map
        for (PermitHolder ph : permitHolders) {
            permitHolderMap.put(ph.getId(), ph);
        }

        // Add the new PermitHolder object to the JSON object
        permitHolderMap.put(permitHolder.getId(), permitHolder);

        // Write the updated JSON object to the same JSON file
        try (Writer writer = new FileWriter(jsonData)) {
            JsonArray jsonArray = new JsonArray();
            permitHolderMap.values().forEach(ph -> jsonArray.add(gson.toJsonTree(ph)));
            gson.toJson(jsonArray, writer);
        }

        reader.close();
    }

    public static void removePermit(int permitId) {
        Gson gson = gson();
        Type type = new TypeToken<List<PermitHolder>>(){}.getType();
        try (Reader reader = new FileReader(jsonData)) {
            List<PermitHolder> permits = gson.fromJson(reader, type);
            permits.removeIf(permit -> permit.getId() == permitId);
            String json = gson.toJson(permits);
            try (Writer writer = new FileWriter(jsonData)) {
                writer.write(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
                    @Override
                    public void write(JsonWriter out, LocalDate value) throws IOException {
                        out.beginObject();
                        out.name("year").value(value.getYear());
                        out.name("month").value(value.getMonthValue());
                        out.name("day").value(value.getDayOfMonth());
                        out.endObject();
                    }

                    @Override
                    public LocalDate read(JsonReader in) throws IOException {
                        JsonObject jsonObject = new JsonParser().parse(in).getAsJsonObject();
                        int year = jsonObject.get("year").getAsInt();
                        int month = jsonObject.get("month").getAsInt();
                        int day = jsonObject.get("day").getAsInt();
                        return LocalDate.of(year, month, day);
                    }
                })
                .setPrettyPrinting()
                .create();
    }


}
