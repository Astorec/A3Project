package UnitTests;

import Classes.PermitHolder;
import Functions.LoadPermitData;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


class LoadPermitDataTest {

    // Test to ensure that all the data is being read in correctly from the JSON File
    @org.junit.jupiter.api.Test
    public void testDataRead() {
        HashMap<String, PermitHolder> testData = new HashMap<>();
        LoadPermitData data = new LoadPermitData();

        try {
            testData = data.loadPermitData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assert.fail();
        }

        for (Map.Entry<String, PermitHolder> permit : testData.entrySet()) {
            String id = permit.getKey();
            String firstName = permit.getValue().getFirst_name();
            String secondName = permit.getValue().getLast_name();
            String address = permit.getValue().getAddress().toString();
            String car = permit.getValue().getCar().toString();
            String permitDetails = permit.getValue().getPermit().toString();


            System.out.println("ID: " + id + "\nName: " + firstName + " " + secondName + "\nAddress: " + address + "\nCar Details: " + car + "\nPermit Details: " + permitDetails);
        }
    }

}