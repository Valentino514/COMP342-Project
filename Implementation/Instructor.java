import java.util.ArrayList;

public class Instructor extends User {
    private String specialization;
    private String name;
    private String phoneNumber;
    private ArrayList<Lesson> assignedOfferings;
    private ArrayList<Offering> selectedOfferings;
    private Organization organization;

    // Constructor
    public Instructor(String s, String n, String p, String password, Organization organization) {
        super(n, password);
        this.specialization = s;
        this.name = n;
        this.phoneNumber = p;
        this.organization = organization;
        this.assignedOfferings = new ArrayList<>();
        this.selectedOfferings = new ArrayList<>();
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Organization getOrganization() {
        return organization;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    // Method to view open offerings based on specialization
    public void viewOpenOfferings(OfferingsCatalog offeringsCatalog) {
        System.out.println("Open Offerings for " + name + ":");
        for (Offering offering : offeringsCatalog.getOfferings()) {  // Corrected to Offering type
            if (offering.getLesson().getIsPublic() && offering.getLesson().getActivity().equals(specialization)) {
                System.out.println("ID: " + offering.getId() + ", Activity: " + offering.getLesson().getActivity() +
                                   ", Space: " + offering.getLesson().getSpace().getAddress() +
                                   ", Time: " + offering.getTimeslot().getStartTime() + " - " +
                                   offering.getTimeslot().getEndTime());
            }
        }
    }
    

    public void selectOffering(Offering offering) {
        if (offering.getLesson().getActivity().equals(specialization)) {
            selectedOfferings.add(offering);
            offering.addInstructor(this); // Add this instructor to the offering
            System.out.println(name + " has selected the offering: " + offering.getId());
        } else {
            System.out.println("Offering does not match instructor specialization.");
        }
    }

    public void removeOffering(Offering offering) {
        if (selectedOfferings.contains(offering)) {
            selectedOfferings.remove(offering);
            offering.removeInstructor(this); // Remove instructor from offering
            System.out.println(name + " has been removed from the offering: " + offering.getId());
        }
    }

    public void viewAssignedOfferings() {
        System.out.println(name + "'s Assigned Offerings:");
        for (Offering offering : selectedOfferings) {
            System.out.println("ID: " + offering.getId() + ", Activity: " + offering.getLesson().getActivity() +
                               ", Space: " + offering.getLesson().getSpace().getAddress() +
                               ", Time: " + offering.getTimeslot().getStartTime() + " - " +
                               offering.getTimeslot().getEndTime());
        }
    }

    public void registerAvailability(String city, String availabilityDetails) {
        System.out.println(name + " has registered availability in " + city + ": " + availabilityDetails);
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", organization=" + (organization != null ? organization.getName() : "None") +
                ", assignedOfferings=" + assignedOfferings +
                ", selectedOfferings=" + selectedOfferings +
                '}';
    }
}
