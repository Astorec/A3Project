package Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Permit {

    //region Properties
    private String area_codes;

    private LocalDate start_date;

    private LocalDate expiry_date;
    //endregion

    //region Constructor
    public Permit(String areaCodes, LocalDate startDate, LocalDate expiryDate) {
        this.area_codes = areaCodes;
        this.start_date = startDate;
        this.expiry_date = expiryDate;
    }
    //endregion

    //region Getters
    private String getArea_codes() {
        return area_codes;
    }

    private LocalDate getStart_date() {
        return start_date;
    }

    private LocalDate getExpiry_date() {
        return expiry_date;
    }
    //endregion

    //region To String
    public String toString() {
        return "Area Codes: " + this.getArea_codes() + " Start Date: " + this.sendStartDate() + " Expiry Date: " + this.sendExpiryDate();
    }

    public String sendAreaCodes() {
        return getArea_codes();
    }

    public String sendStartDate() {
        return getStart_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
    }

    public String sendExpiryDate() {
        return getExpiry_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
    }
    //endregion
}