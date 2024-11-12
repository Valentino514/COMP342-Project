import java.util.ArrayList;

public class Instructor extends User {
    private String specialization;
    private String name;
    private String phoneNumber;
    private ArrayList<Lesson> assignedOfferings;
    private Organization organization;

    public Instructor(String s, String n, String p, String password, Organization organization){
        super(n,password);
        this.phoneNumber =p;
        this.specialization=s;
        this.assignedOfferings =  new ArrayList<>();
        this.organization = organization;
    }

    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

    }public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    public void viewOpenOfferings(OfferingsCatalog offeringsCatalog) {
        System.out.println("Open Offerings for " + name + ":");
        for (Lesson offering : offeringsCatalog.getOfferings()) {
            if (offering.getIsPublic() && offering.getActivity().equals(specialization)) {
                System.out.println("ID: " + offering.getId() + ", Activity: " + offering.getActivity() +
                                   ", Space: " + offering.getSpace().getAddress() +
                                   ", Time: " + offering.getTimeslot().getStartTime() + " - " +
                                   offering.getTimeslot().getEndTime());
            }
        }
    }

    // Method to select an offering
    public void selectOffering(Lesson offering) {
        if (offering.getIsPublic() && offering.getActivity().equals(specialization)) {
            assignedOfferings.add(offering);
            System.out.println(name + " has selected the offering: " + offering.getId());
            offering.makeOfferingPublic(); // Mark offering as assigned to instructor
        } else {
            System.out.println("Offering is not available or does not match specialization.");
        }
    }

    // Method to view assigned offerings
    public void viewAssignedOfferings() {
        System.out.println(name + "'s Assigned Offerings:");
        for (Lesson offering : assignedOfferings) {
            System.out.println("ID: " + offering.getId() + ", Activity: " + offering.getActivity() +
                               ", Space: " + offering.getSpace().getAddress() +
                               ", Time: " + offering.getTimeslot().getStartTime() + " - " +
                               offering.getTimeslot().getEndTime());
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
