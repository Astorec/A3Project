package Classes;

public class Address {
    //region Properties
    private final String house_num;
    private final String street_name;
    private final String town;
    private final String county;
    private final String post_code;
    //endregion

    //region Constructor
    public Address(String houseNum, String streetName, String town, String county, String postCode) {
        this.house_num = houseNum;
        this.street_name = streetName;
        this.town = town;
        this.county = county;
        this.post_code = postCode;
    }
    //endregion

    //region Getters
    public String getHouse_num() {
        return house_num;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getTown() {
        return town;
    }

    public String getCounty() {
        return county;
    }

    public String getPost_code() {
        return post_code;
    }
    //endregion

    //region To String
    public String toString() {
        return this.getHouse_num() + " " + this.getStreet_name() + "\n" + this.getTown() + "\n" + this.getCounty() + "\n" + this.getPost_code();
    }
    //endregion
}