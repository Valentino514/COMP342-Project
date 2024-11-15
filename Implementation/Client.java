import java.util.ArrayList;

public class Client extends User {
    private int age;
    private String clientId;
    protected ArrayList<Offering> offerings; 

    // Constructor
    public Client(String name, String password, int age) {
        super(name, password);
        
        this.age = age;
        this.offerings = new ArrayList<>(); 
    }

    public ArrayList<Offering> getOfferings() {
        return offerings;
    }


    // Getting client ID
    public String getClientId() {
        return clientId;
    }
    
    //Setting up Client ID
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    // Get the age of the client
    public int getAge() {
        return age;
    }

    // Set the age of the client
    public void setAge(int age) {
        this.age = age;
    }

    // Book a new offering
    public void addOffering(Offering offering) {
        if (offering.addClient(this)) {
            offerings.add(offering);
            System.out.println("Offering added to client " + this.getName() + " successfully");
        } else {
            System.out.println("Error booking this offer");
        }
    }

    // View all the offerings booked
    public void viewOfferings() {
        ArrayList<Offering> offerings = OfferingCatalog.getOfferingsByClientId(this.getClientId());
        if (offerings.isEmpty()) {
            System.out.println("No offerings booked.");
        } else {
            System.out.println("Booked Offerings:");
            for (Offering offering : offerings) {
                Lesson lesson = offering.getLesson(); // Access the Lesson object
                System.out.println("Activity: " + lesson.getActivity());
                Schedule schedule = lesson.getSchedule();
                System.out.println("Schedule:");
                System.out.println("  Start Date: " + schedule.getStartDate());
                System.out.println("  End Date: " + schedule.getEndDate());
                System.out.println("  Day: " + schedule.getDay());
                System.out.println("  Start Time: " + schedule.getStartTime());
                System.out.println("  End Time: " + schedule.getEndTime());
                System.out.println("Space: " + lesson.getSpace().getAddress());
                System.out.println("Instructor: " + offering.getInstructor().getName());
                System.out.println("Is Public: " + (offering.getIsPublic() ? "Yes" : "No"));
                System.out.println("---------------------------------");
            }
        }
    }
    
}
