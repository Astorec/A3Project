package Functions;

import Classes.PermitHolder;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterData {
    //region Properties
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String areaCode;
    private LocalDate startDate;
    private LocalDate expiryDate;
    //endregion

    //region Setters

    // Setters for getting the current data from the permit
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    //endregion

    //region Methods

    // Filters the current Permit Holder list based on provided filter data
    public Stream<PermitHolder> filter(LinkedList<PermitHolder> permitHolders) {

        // Create a stream object from the passed in linked list
        return permitHolders.stream().filter(permit -> {
            boolean match = id < 1 || permit.getId() == id; // Check that the id matches the object ID

            // Checking if the first name instance variable is set, and if so, if it matches the first name in the PermitHolder object
            if (firstName != null && !firstName.isEmpty() && !permit.getFirst_name().toLowerCase().contains(firstName.toLowerCase())) {
                match = false;
            }
            // Checking if the last name instance variable is set, and if so, if it matches the last name in the PermitHolder object
            if (lastName != null && !lastName.isEmpty() && !permit.getLast_name().toLowerCase().contains(lastName.toLowerCase())) {
                match = false;
            }
            // Checking if the address instance variable is set, and if so, if it matches the address in the PermitHolder object
            if (address != null && !address.isEmpty() && !permit.getAddress().toString().toLowerCase().contains(address.toLowerCase())) {
                match = false;
            }
            // Checking if the Area Code instance variable is set, and if so, if it matches the Area Code in the PermitHolder object
            if (areaCode != null && !areaCode.isEmpty() && !permit.getPermit().sendAreaCodes().toLowerCase().contains(areaCode.toLowerCase())) {
                match = false;
            }
            return match; // Return true or false if the data is matched
        });
    }

    // Filters the current Permit Holder list based on provided filter data and returns an observable list
    public LinkedList<PermitHolder> filterToLinkedList(LinkedList<PermitHolder> permitHolders) {
        return filter(permitHolders).collect(Collectors.toCollection(LinkedList::new));
    }

    //endregion
}
