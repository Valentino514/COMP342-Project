import java.util.ArrayList;

public class Space {

    private int personLimit;
    private String address;
    private String type;
    private boolean isRented;
    private String city;
    private String spaceId;
    private ArrayList<Lesson> lessons;
    private static int idCounter = 1;


    public Space(String address,String type, boolean isRented, String city, int personLimit) {
        this.address = address;
        this.type =type;
        this.isRented = isRented;
        this.city = city;
        this.personLimit = personLimit;
        this.spaceId = generateUniqueId();
        this.lessons = new ArrayList<>();
    }

    private static String generateUniqueId() {
        return String.valueOf(idCounter++); 
    }

    public String getAddress() {
        return address;
    }

    // Getter for type
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

    public void setPsaceId(String id) {
        this.spaceId = id;
    }
}