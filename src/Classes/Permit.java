package Classes;

import java.util.Date;

public class Permit {

    //region Properties
    private final String area_codes;

    private final Date start_date;

    private final Date expiry_date;
    //endregion

    //region Constructor
    public Permit(String areaCodes, Date startDate, Date expiryDate) {
        this.area_codes = areaCodes;
        this.start_date = startDate;
        this.expiry_date = expiryDate;
    }
    //endregion

    //region Getters
    private String getArea_codes() {
        return area_codes;
    }

    private Date getStart_date() {
        return start_date;
    }

    private Date getExpiry_date() {
        return expiry_date;
    }
    //endregion

    //region To String
    public String toString(){
        return "Area Codes: " + this.getArea_codes() + " Start Date: " + this.getStart_date() + " Expiry Date: " + this.getExpiry_date();
    }
    //endregion
}