package Classes;

public class Address {
    private String houseNum;
    private String streetName;
    private String town;
    private String county;
    private String postCode;

    public Address(){

    }
    public Address(String houseNum, String streetName, String town, String county, String postCode) {
        this.houseNum = houseNum;
        this.streetName = streetName;
        this.town = town;
        this.county = county;
        this.postCode = postCode;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getTown() {
        return town;
    }

    public String getCounty() {
        return county;
    }

    public String getPostCode() {
        return postCode;
    }
}