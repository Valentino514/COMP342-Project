import java.util.ArrayList;

public class Instructor {
    private String specialization;
    private String name;
    private String phoneNumber;
    private ArrayList<Offering> assignedOfferings;

    public Instructor(String s, String n, String p){
        this.name=n;
        this.phoneNumber =p;
        this.specialization=s;
        this.assignedOfferings =  new ArrayList<>();
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
        for (Offering offering : offeringsCatalog.getOfferings()) {
            if (offering.getIsPublic() && offering.getActivity().equals(specialization)) {
                System.out.println("ID: " + offering.getId() + ", Activity: " + offering.getActivity() +
                                   ", Space: " + offering.getSpace().getAddress() +
                                   ", Time: " + offering.getTimeslot().getStartTime() + " - " +
                                   offering.getTimeslot().getEndTime());
            }
        }
    }

    // Method to select an offering
    public void selectOffering(Offering offering) {
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
        for (Offering offering : assignedOfferings) {
            System.out.println("ID: " + offering.getId() + ", Activity: " + offering.getActivity() +
                               ", Space: " + offering.getSpace().getAddress() +
                               ", Time: " + offering.getTimeslot().getStartTime() + " - " +
                               offering.getTimeslot().getEndTime());
        }
    }

    
    public void registerAvailability(String city, String availabilityDetails) {
        System.out.println(name + " has registered availability in " + city + ": " + availabilityDetails);
    }

}
