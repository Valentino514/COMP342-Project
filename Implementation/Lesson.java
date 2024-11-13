import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Lesson {
    private String activity;
    private Timeslot timeslot;
    private Space space;
    private String id;
    private boolean isPublic = false;
    private List<Offering> offerings; // List of offerings that belong to this lesson
    private List<Instructor> instructors;

    // Constructor
    public Lesson(String activity, Timeslot timeslot, Space space, String id) {
        this.activity = activity;
        this.timeslot = timeslot;
        this.space = space;
        this.id = id;
        this.isPublic = false;
        this.offerings = new ArrayList<>();
        this.instructors = new ArrayList<>();
    }

    public Space getSpace() {
        return space;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public String getActivity() {
        return activity;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public String getId() {
        return id;
    }

    public void makeOfferingPublic() {
        isPublic = true;
    }

    // Method to add an offering to this lesson (composition)
    public void addOffering(Offering offering) {
        offerings.add(offering);
    }

    // Create a new offering for this lesson
    public Offering createOffering(String offeringId, Date date, Timeslot timeslot, String status) {
        Offering newOffering = new Offering(this, offeringId, date, timeslot, status);
        addOffering(newOffering);
        return newOffering;
    }

    public List<Offering> getOfferings() {
        return offerings;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    // Method to add an instructor to the offering
    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    // Method to remove an instructor from the offering
    public void removeInstructor(Instructor instructor) {
        instructors.remove(instructor);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "activity='" + activity + '\'' +
                ", timeslot=" + timeslot +
                ", space=" + space +
                ", id='" + id + '\'' +
                ", isPublic=" + isPublic +
                ", offerings=" + offerings +
                '}';
    }
}
