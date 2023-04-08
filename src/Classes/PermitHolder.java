package Classes;

public class PermitHolder {
    //region Properties
    private String id;

    private String first_name;

    private String last_name;

    private Address address;

    private Car car;

    private Permit permit;
    //endregion

    //region Constructor
    public PermitHolder(String id, String firstName, String lastName, Address address, Car car, Permit permit) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.address = address;
        this.car = car;
        this.permit = permit;
    }
    //endregion

    //region Getters
    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
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
    //endregion
}