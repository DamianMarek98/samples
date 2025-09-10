package car.repair.shop.springbootlambdaapi.car;

public class Car {
    private Long id;
    private String brandName;
    private String color;
    private String model;
    private Integer year;
    private String vin;

    public Car() {}

    public Car(Long id, String brandName, String color, String model, Integer year, String vin) {
        this.id = id;
        this.brandName = brandName;
        this.color = color;
        this.model = model;
        this.year = year;
        this.vin = vin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
