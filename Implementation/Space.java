import java.util.ArrayList;

public class Space {

    private int personLimit;
    private String address;
    private String type;
    private boolean isRented;
    private String city;
    private String spaceId;
    private ArrayList<Lesson> lessons;

    // constructor
    public Space(String address,String type, boolean isRented, String city, int personLimit) {
        this.address = address;
        this.type =type;
        this.isRented = isRented;
        this.city = city;
        this.personLimit = personLimit;
        this.lessons = new ArrayList<>();
    }

    // getters and setters
    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public boolean isRented() {
        return isRented;
    }

    public String getCity() {
        return city;
    }

    public int getPersonLimit() {
        return personLimit;
    }

    public String getSpaceId() {
        return spaceId;
    }

    // Setters
    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRented(boolean isRented) {
        this.isRented = isRented;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPersonLimit(int limit) {
        this.personLimit = limit;
    }

    public void setSpaceId(String id) {
        this.spaceId = id;
    }
    
}