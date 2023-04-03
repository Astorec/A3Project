package Classes;

import java.util.Date;

public class Permit {
    private String areaCodes;
    private Date startDate;
    private Date expiryDate;

    public Permit(){

    }
    public Permit(String areaCodes, Date startDate, Date expiryDate) {
        this.areaCodes = areaCodes;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    public String getAreaCodes() {
        return areaCodes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}