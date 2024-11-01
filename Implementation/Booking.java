import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Booking {
    private String id;
    private Date date;
    private Timeslot timeslot;
    private Offering offering;
    private Client client;  
    private List<Client> clients;
    private String status;

    // Constructor
    public Booking(String id, Date date, Timeslot timeslot, Offering offering, Client client, String status) {
        this.id = id;
        this.date = date;
        this.timeslot = timeslot;
        this.offering = offering;
        this.client = client;
        this.clients = new ArrayList<>();
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

    public void addClient(Client client) {
        if (!clients.contains(client)) { // Avoid duplicate clients
            clients.add(client);
            client.addBooking(this); // Add this booking to the client's list
        }
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", timeslot=" + timeslot +
                ", offering=" + offering +
                ", clients=" + clients +
                ", status='" + status + '\'' +
                '}';
    }
}
