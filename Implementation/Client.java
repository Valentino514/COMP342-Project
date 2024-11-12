import java.util.ArrayList;
import java.util.List;

class Client extends User{
    private String id;
    private String name;
    private String contactInfo;
    private List<Offering> bookings; 

    // Constructor
    public Client(String id, String name, String contactInfo, String password) {
        super(name, password);
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.bookings = new ArrayList<>(); // Initialize empty bookings list
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public List<Offering> getBookings() {
        return bookings;
    }

    // Add a booking
    public void addBooking(Offering booking) {
        bookings.add(booking);
        booking.addClient(this);
    }
}
