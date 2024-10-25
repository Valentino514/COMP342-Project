import java.util.Date;

class Booking {
    private String id;
    private Date date;
    private Timeslot timeslot;
    private Offering offering;
    private Client client;  
    private String status;  // e.g., "confirmed", "canceled"

    // Constructor
    public Booking(String id, Date date, Timeslot timeslot, Offering offering, Client client, String status) {
        this.id = id;
        this.date = date;
        this.timeslot = timeslot;
        this.offering = offering;
        this.client = client;
        this.status = status;
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Offering getOffering() {
        return offering;
    }

    public Client getClient() {
        return client;
    }

    public String getStatus() {
        return status;
    }

    // Setter methods for updating booking status
    public void setStatus(String status) {
        this.status = status;
    }
}
