import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Offering {
    private String id;
    private Date date;
    private Timeslot timeslot;
    private Lesson lesson;  
    private List<Client> clients;
    private String status;
    private LessonCatalog lessonCatalog;

    // Constructor
    public Offering(Lesson lesson, String id, Date date, Timeslot timeslot, String status) {
        this.lesson = lesson;
        this.id = id;
        this.date = date;
        this.timeslot = timeslot;
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

    public Lesson getLesson() {
        return lesson;
    }

    public String getStatus() {
        return status;
    }

    public LessonCatalog getLessonCatalog() {
        return lessonCatalog;  
    }

    public List<Client> getClients() {
        return clients;
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
        return "Offering{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", timeslot=" + timeslot +
                ", lesson=" + lesson.getActivity() + " (" + lesson.getId() + ")" +
                ", clients=" + clients +
                ", status='" + status + '\'' +
                '}';
    }
}
