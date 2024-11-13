import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Offering {
    private String id;
    private Date date;
    private Timeslot timeslot;
    private Lesson lesson; // The Lesson this offering belongs to
    private List<Client> clients;
    private List<Instructor> instructors;
    private String status;

    // Constructor
    public Offering(Lesson lesson, String id, Date date, Timeslot timeslot, String status) {
        this.lesson = lesson;
        this.id = id;
        this.date = date;
        this.timeslot = timeslot;
        this.clients = new ArrayList<>();
        this.instructors = new ArrayList<>();
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
        return lesson; // This offering knows about the lesson it belongs to
    }

    public String getStatus() {
        return status;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    // Setter methods for updating booking status
    public void setStatus(String status) {
        this.status = status;
    }

    public void addClient(Client client) {
        if (!clients.contains(client)) {
            clients.add(client);
            client.addBooking(this); // Add this offering to the client's list
        }
    }

    // Method to assign an instructor to this offering
    public void addInstructor(Instructor instructor) {
        if (!instructors.contains(instructor)) {
            instructors.add(instructor);
            instructor.selectOffering(this); // Register this offering in the instructor's list
        }
    }

    // Method to remove an instructor from this offering
    public void removeInstructor(Instructor instructor) {
        if (instructors.contains(instructor)) {
            instructors.remove(instructor);
            instructor.removeOffering(this); // Remove this offering from the instructor's list
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
                ", instructors=" + instructors +
                ", status='" + status + '\'' +
                '}';
    }
}
