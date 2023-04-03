package Classes;

public class Car {
    private String regNum;
    private String make;
    private String model;
    private String colour;

    public Car(){

    }
    public Car(String regNum, String make, String model, String colour) {
        this.regNum = regNum;
        this.make = make;
        this.model = model;
        this.colour = colour;
    }

    public String getRegNum() {
        return regNum;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColour() {
        return colour;
    }
}