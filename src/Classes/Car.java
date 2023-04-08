package Classes;

public class Car {
    //region Properties
    private final String reg_num;
    private final String make;
    private final String model;
    private final String colour;
    //endregion

    //region Constructor
    public Car(String regNum, String make, String model, String colour) {
        this.reg_num = regNum;
        this.make = make;
        this.model = model;
        this.colour = colour;
    }
    //endregion

    //region Getters
    private String getReg_num() {
        return reg_num;
    }

    private String getMake() {
        return make;
    }

    private String getModel() {
        return model;
    }

    private String getColour() {
        return colour;
    }
    //endregion

    //region To String
    public String toString() {
        return "Reg Number: " + this.getReg_num() + " Make: " + this.getMake() + " Model: " + this.getModel() + " Colour: " + this.getColour();
    }

    public String sendRegNum(){
        return getReg_num();
    }
    //endregion
}