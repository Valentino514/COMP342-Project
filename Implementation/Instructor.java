import java.util.ArrayList;

public class Instructor extends User {
    private String specialization;
    private String phoneNumber;
    private ArrayList<String> cities;
    private ArrayList<Offering> offerings;
    private Organization organization;

    // constructor
    public Instructor(String specialization, String name, String password, String phoneNumber, ArrayList<String> cities) {
        super(name, password);
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
        this.offerings = new ArrayList<>();
        this.cities = cities;
        //this.organization = organization; maybe add this later
    }

    // get the List of all cities from the arrayList
    public ArrayList<String> getCities() {
        return cities;
    }

    // set cities in the arrayList
    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    // Adding a city
    public void addCity(String city) {
        if (!cities.contains(city)) {
            cities.add(city);
        } else {
            System.out.println(city + " is already in the list of cities.");
        }
    }

    // Adding a lesson
    public void addLesson(Offering offering){
        offerings.add(offering);
    }

    // get the Phone number of the client
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // get the specialization of the client
    public String getSpecialization() {
        return specialization;
    }

    // set the phone number of the client
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // set the specialization of the client
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Method to view assigned offerings
    public void viewAssignedOfferings() {
        if (offerings.isEmpty()) {
            System.out.println("No offerings assigned to this instructor.");
        } else {
            System.out.println("Offerings for Instructor: " + this.getName());
            for (Offering offering : offerings) {
                System.out.println("Activity: " + offering.getActivity());
                System.out.println("date: " + offering.getSchedule().getStartDate()+ " - " +offering.getSchedule().getEndDate()+ " every "+ offering.getSchedule().getDay());
                System.out.println("time: " + offering.getSchedule().getStartTime()+ " - " +offering.getSchedule().getEndTime());
                System.out.println("address: " + offering.getSpace().getAddress() + ", " + offering.getSpace().getCity() + ", "+offering.getSpace().getType());
                System.out.println("Lesson ID: " + offering.getLessonId());
                if(offering.getIsPublic()){
                    System.out.println("lesson availability: public");
                }else{
                    System.out.println("lesson availability: private");
                }
                int remainingSpace = (offering.getSpace().getPersonLimit()) - offering.getBookingAmount();
                System.out.println("available spaces: "+ remainingSpace );

                System.out.println("-------------------------");
            }
        }
    }
    
    // Showing the availability of the city
    public void registerAvailability(String city, String availabilityDetails) {
        System.out.println(this.getName() + " has registered availability in " + city + ": " + availabilityDetails);
    }

    // get the organization
    public Organization getOrganization() {
        return organization;
    }

    // sets an organization
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
