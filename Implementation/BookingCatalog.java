import java.util.ArrayList;
import java.util.List;

class BookingCatalog {
    private List<Booking> bookings;

    // Constructor
    public BookingCatalog() {
        this.bookings = new ArrayList<>();
    }

    // Add a booking to the catalog
    public void addBooking(Booking booking) {
        if (booking != null && !bookings.contains(booking)) {
            bookings.add(booking);
        }
    }

    // Remove a booking from the catalog
    public void removeBooking(String bookingId) {
        bookings.removeIf(booking -> booking.getId().equals(bookingId));
    }

    // Get a booking by its ID
    public Booking getBooking(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId().equals(bookingId)) {
                return booking;
            }
        }
        return null; // Return null if booking not found
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings); // Return a copy of the list
    }

    // Get bookings by status
    public List<Booking> getBookingsByStatus(String status) {
        List<Booking> filteredBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getStatus() == status) {
                filteredBookings.add(booking);
            }
        }
        return filteredBookings;
    }

    // Get the number of bookings
    public int getBookingCount() {
        return bookings.size();
    }
}
