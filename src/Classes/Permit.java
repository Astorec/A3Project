package Classes;

import java.util.Date;

public class Permit {
    private int id;
    private String areaCodes;
    private Date startDate;
    private Date expiryDate;


    public void setAreaCodes(String newAreaCodes){
        areaCodes = newAreaCodes;
    }

    public String getAreaCodes(){
        return areaCodes;
    }
}
