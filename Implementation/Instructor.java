import java.util.ArrayList;

public class Instructor extends User {
    private String specialization;
    private String name;
    private String phoneNumber;
    private ArrayList<String> cities;
    private ArrayList<Offering> offerings;
    private Organization organization;

    public Instructor(String specialization, String name, String password, String phoneNumber, ArrayList<String> cities) {
        super(name, password);
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
        this.offerings = new ArrayList<>();
        this.cities = cities;
        //this.organization = organization; maybe add this later
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    public void addCity(String city) {
        if (!cities.contains(city)) {
            cities.add(city);
        } else {
            System.out.println(city + " is already in the list of cities.");
        }
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

    }public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Method to view assigned offerings
    public void viewAssignedOfferings() {
        if (offerings.isEmpty()) {
            System.out.println("No offerings assigned to this instructor.");
        } else {
            System.out.println("Offerings for Instructor: " + name);
            for (Offering offering : offerings) {
                System.out.println("Activity: " + offering.getActivity());
                System.out.println("Schedule: " + offering.getSchedule());
                System.out.println("Space: " + offering.getSpace());
                System.out.println("Lesson ID: " + offering.getLessonId());
                System.out.println("-------------------------");
            }
        }
    }
    
    public void registerAvailability(String city, String availabilityDetails) {
        System.out.println(name + " has registered availability in " + city + ": " + availabilityDetails);
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
