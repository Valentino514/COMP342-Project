import java.util.ArrayList;
import java.util.List;

class Client extends User{
    private String id;
    private String name;
    private String contactInfo;
    private List<Booking> bookings; 

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

    public List<Booking> getBookings() {
        return bookings;
    }

    // Add a booking
    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.addClient(this);
    }
}
