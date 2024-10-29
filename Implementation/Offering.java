import java.util.ArrayList;
import java.util.List;

class Offering {
    private String activity;
    private Timeslot timeslot;
    private Space space;
    private String id;
    private boolean isPublic = false;
    private List<Booking> bookings;


    // Constructor
    public Offering(String activity, Timeslot timeslot, Space space, String id) {
        this.activity = activity;
        this.timeslot = timeslot;
        this.space = space;
        this.id = id;
        this.isPublic = false;
        this.bookings = new ArrayList<>();
    }

    //Getters for space and timeslot
    public Space getSpace() {
        return space;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public String getActivity(){
        return activity;
    }

    public boolean getIsPublic(){
        return isPublic;
    }

    public String getId(){
        return id;
    }

    public void makeOfferingPublic(){
        isPublic =true;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
    
    public List<Booking> getBookings() {
        return bookings;
    }
}