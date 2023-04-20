package Functions;

import Classes.PermitHolder;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.LinkedList;

public class PermitData {

    // Get the file path of the JSON file
    private static File jsonData = new File("src/Data/PermitData.json");

    public static LinkedList<PermitHolder> loadPermitData() throws IOException {

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

        // Apply each read in line to a List of Permit Holders
        Type permitHolderListType = new TypeToken<LinkedList<PermitHolder>>(){}.getType();
        LinkedList<PermitHolder> permitHolders = gson.fromJson(reader, permitHolderListType);

        // Close the Buffer Reader once we are done with it
        reader.close();

        return permitHolders;
    }

    public static void saveNewPermit(PermitHolder permitHolder) throws IOException {

        Gson gson = gson();

        // Read the existing JSON file into a String variable
        BufferedReader reader = new BufferedReader(new FileReader("src/Data/PermitData.json"));

        // Apply each read in line to a List of Permit Holders
        Type permitHolderListType = new TypeToken<LinkedList<PermitHolder>>(){}.getType();
        LinkedList<PermitHolder> permitHolders = gson.fromJson(reader, permitHolderListType);

        // Add the new PermitHolder object to the list of Permit Holders
        permitHolders.add(permitHolder);

        // Write the updated JSON object to the same JSON file
        try (Writer writer = new FileWriter(jsonData)) {
            gson.toJson(permitHolders, writer);
        }

        reader.close();

        LinkedListOperations.getInstance().updateList(permitHolders);
    }

    public static void removePermit(int permitId) {
        Gson gson = gson();
        Type type = new TypeToken<LinkedList<PermitHolder>>(){}.getType();
        try (Reader reader = new FileReader(jsonData)) {
            LinkedList<PermitHolder> permits = gson.fromJson(reader, type);
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
                        JsonObject jsonObject =  JsonParser.parseReader(in).getAsJsonObject();
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
