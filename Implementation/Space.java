public class Space {
    private String address;
    private String type;
    private boolean isRented = false; // Default value set to false
    private String city;

    // Constructor to initialize address, type, city, and optionally isRented
    public Space(String address, String type, String city, boolean isRented) {
        this.address = address;
        this.type = type;
        this.city = city;
        this.isRented = isRented;
    }

    // Getter for address
    public String getAddress() {
        return address;
    }

    // Getter for type
    public String getType() {
        return type;
    }

    // Getter for isRented
    public boolean isRented() {
        return isRented;
    }

    // Getter for city
    public String getCity() {
        return city;
    }

    // Setter for isRented
    public void setRented(boolean isRented) {
        this.isRented = isRented;
    }

    // Setter for city
    public void setCity(String city) {
        this.city = city;
    }
}
