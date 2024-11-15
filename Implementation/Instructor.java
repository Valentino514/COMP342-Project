import java.util.ArrayList;

public class Instructor extends User {
    private String specialization;
    private String phoneNumber;
    private ArrayList<String> cities;
    private ArrayList<Offering> offerings;
    private Organization organization;
    private String instructorId;

    // Constructor
    public Instructor(String specialization, String name, String password, String phoneNumber, ArrayList<String> cities) {
        super(name, password);
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
        this.offerings = new ArrayList<>();
        this.cities = cities;

    }

    public Instructor(String name, String password) {
        super(name, password);
        this.offerings = new ArrayList<>();
        this.cities = new ArrayList<>();
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    // Get the list of all cities
    public ArrayList<String> getCities() {
        return cities;
    }

    // Set cities
    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    // Add a city
    public void addCity(String city) {
        if (!cities.contains(city)) {
            cities.add(city);
        } else {
            System.out.println(city + " is already in the list of cities.");
        }
    }

    // Add an offering
    public void addOffering(Offering offering) {
        offerings.add(offering);
    }

    // Get phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Get specialization
    public String getSpecialization() {
        return specialization;
    }

    // Set phone number
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Set specialization
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Method to view assigned offerings
    public void viewAssignedOfferings() {
        ArrayList<Offering> offerings = OfferingCatalog.getOfferingsByInstructorId(this.getInstructorId());
        if (offerings.isEmpty()) {
            System.out.println("No offerings assigned to this instructor.");
        } else {
            System.out.println("Offerings for Instructor: " + this.getName());
            for (Offering offering : offerings) {
                Lesson lesson = offering.getLesson();
    
                System.out.println("Activity: " + lesson.getActivity());
                System.out.println("Date: " + lesson.getSchedule().getStartDate() + " - " + lesson.getSchedule().getEndDate() + " every " + lesson.getSchedule().getDay());
                System.out.println("Time: " + lesson.getSchedule().getStartTime() + " - " + lesson.getSchedule().getEndTime());
                System.out.println("Address: " + lesson.getSpace().getAddress() + ", " + lesson.getSpace().getCity() + ", " + lesson.getSpace().getType());
                System.out.println("Lesson ID: " + lesson.getLessonId());
                if (offering.getIsPublic()) {
                    System.out.println("Lesson availability: public");
                } else {
                    System.out.println("Lesson availability: private");
                }
                int capacity = offering.getIsPublic() ? lesson.getSpace().getPersonLimit() : 1;
                int remainingSpace = capacity - offering.getBookingAmount();
                System.out.println("Available spaces: " + remainingSpace);
                System.out.println("-------------------------");
            }
        }
    }
    
    
    // Register availability
    public void registerAvailability(String city, String availabilityDetails) {
        System.out.println(this.getName() + " has registered availability in " + city + ": " + availabilityDetails);
    }

    // Get organization
    public Organization getOrganization() {
        return organization;
    }

    // Set organization
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
