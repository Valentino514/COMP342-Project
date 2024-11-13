import java.util.ArrayList;
import java.util.Date;

public class OfferingsCatalog {
    private ArrayList<Offering> offerings = new ArrayList<>();
    private SpaceCatalog spaceCatalog;
    private TimeslotCatalog timeslotCatalog;

    // Constructor: Accepts SpaceCatalog and TimeslotCatalog to check space and timeslot availability
    public OfferingsCatalog(SpaceCatalog spaceCatalog, TimeslotCatalog timeslotCatalog) {
        this.spaceCatalog = spaceCatalog;
        this.timeslotCatalog = timeslotCatalog;
    }

    // Method to create a new offering
    public void makeOffering(String activity, Timeslot timeslot, Space space, String id, boolean isPublic) {
        // Create a new Offering object
        Offering newOffering = new Offering(new Lesson(activity, timeslot, space, id), id, new Date(), timeslot, "Available");

        // Make the offering public if needed
        if (isPublic) {
            newOffering.getLesson().makeOfferingPublic();
        }

        // Check if space exists in the SpaceCatalog
        if (!spaceCatalog.spaceExists(space.getAddress())) {
            System.out.println("Error: Space does not exist in the catalog.");
            return;
        }

        // Check if the timeslot is already booked
        if (timeslotCatalog.timeslotExists(timeslot)) {
            System.out.println("Error: Timeslot is already booked.");
            return;
        }

        // Add the new offering to the catalog
        offerings.add(newOffering);
        System.out.println("Offering created successfully.");
    }

    // Method to view all public offerings
    public void viewPublicOfferings() {
        System.out.println("Public Offerings:");
        boolean hasPublicOfferings = false;

        // Loop through the offerings and print those that are public
        for (Offering offering : offerings) {
            if (offering.getLesson().getIsPublic()) {
                System.out.println("ID: " + offering.getId() + ", Activity: " + offering.getLesson().getActivity() + 
                                   ", Space: " + offering.getLesson().getSpace().getAddress() +
                                   ", Date: " + offering.getLesson().getTimeslot().getStartDate() + 
                                   " " + offering.getLesson().getTimeslot().getEndDate() + " to " + 
                                   offering.getLesson().getTimeslot().getEndDate() + 
                                   ", Time: " + offering.getLesson().getTimeslot().getStartTime() + 
                                   " until " + offering.getLesson().getTimeslot().getEndTime());
                hasPublicOfferings = true;
            }
        }

        if (!hasPublicOfferings) {
            System.out.println("No public offerings available.");
        }
    }

    // Method to view all offerings
    public void viewAllOfferings() {
        System.out.println("All Offerings:");
        for (Offering offering : offerings) {
            System.out.println("ID: " + offering.getId() + ", Activity: " + offering.getLesson().getActivity() + 
                               ", Space: " + offering.getLesson().getSpace().getAddress() +
                               ", Date: " + offering.getLesson().getTimeslot().getStartDate() + 
                               " " + offering.getLesson().getTimeslot().getEndDate() + " to " + 
                               offering.getLesson().getTimeslot().getEndDate() + 
                               ", Time: " + offering.getLesson().getTimeslot().getStartTime() + 
                               " until " + offering.getLesson().getTimeslot().getEndTime());
        }
    }

    // Getter for all offerings
    public ArrayList<Offering> getOfferings() {
        return offerings;
    }
}
