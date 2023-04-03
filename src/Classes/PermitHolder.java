package Classes;

import com.google.gson.annotations.SerializedName;

public class PermitHolder implements Comparable<PermitHolder> {
    @SerializedName("id")
    private String id; // unique identifier

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("address")
    private Address address;

    @SerializedName("car")
    private Car car;

    @SerializedName("permit")
    private Permit permit;


    // Default Constructor
    public PermitHolder(){

    }
    public PermitHolder(String id, String firstName, String lastName, Address address, Car car, Permit permit) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.car = car;
        this.permit = permit;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public Car getCar() {
        return car;
    }

    public Permit getPermit() {
        return permit;
    }

    // Implement compareTo method to allow PermitHolder objects to be compared and sorted by their id
    @Override
    public int compareTo(PermitHolder other) {
        return this.id.compareTo(other.id);
    }
}