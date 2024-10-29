import java.util.ArrayList;
import java.util.List;

class Client {
    private String id;
    private String name;
    private String contactInfo;
    private boolean isUnderage;
    private Client legalGuardian;  // if the client is underage
    private List<Booking> bookings;  // a client can have multiple bookings

    // Constructor
    public Client(String id, String name, String contactInfo, boolean isUnderage, Client legalGuardian) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.isUnderage = isUnderage;
        this.legalGuardian = legalGuardian;
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

    public boolean isUnderage() {
        return isUnderage;
    }

    public Client getLegalGuardian() {
        return legalGuardian;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    // Add a booking
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}
