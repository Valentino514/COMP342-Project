import java.util.ArrayList;
import java.util.List;

class Client extends User{
    private int age;
    private List<Offering> offerings; 

    // Constructor
    public Client(String name, String password, int age) {
        super(name, password);
        this.age = age;
        this.offerings = new ArrayList<>(); // Initialize empty offerings list
    }

    public List<Offering> getOfferings() {
        return offerings;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    //book a new offering
    public void addOffering(Offering offering) {
        if(offering.addClient(this)){
        offerings.add(offering);
        System.out.println("offering added to client "+ this.getName() + " successfully");
        }
        else{
            System.out.println("error adding booking since it is full");
        }
    }

    public void viewfferings() {
        if (offerings.isEmpty()) {
            System.out.println("No offerings booked.");
        } else {
            System.out.println("Booked Offerings:");
            for (Offering offering : offerings) {
                System.out.println("Activity: " + offering.getActivity());
                System.out.println("Schedule: " + offering.getSchedule());
                System.out.println("Space: " + offering.getSpace().getAddress());
                System.out.println("Instructor: " + offering.getInstructor().getName());
                System.out.println("Is Public: " + (offering.getIsPublic() ? "Yes" : "No"));
                System.out.println("---------------------------------");
            }
        }
    }
}
