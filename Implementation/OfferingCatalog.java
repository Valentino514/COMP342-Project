import java.util.ArrayList;

public class OfferingCatalog {
    private static final ArrayList<Offering> offeringCatalog = new ArrayList<>();


    // Generating a new offering
    public static void generateOffering(String activity, Schedule schedule, Space space,Instructor instructor, boolean isPublic) {
        Offering newOffering = new Offering(activity,schedule,space,instructor,isPublic);
        newOffering.setIsOpen(false);
        offeringCatalog.add(newOffering);
        instructor.addLesson(newOffering);
        System.out.println("offering for instructor "+ instructor.getName() + " generated");
    }

    // Getting all offerings from the ArrayList
    public static ArrayList<Offering> getOfferings() {
        return offeringCatalog;
    }

    // View updated Offerings
    public static boolean viewClientOfferings(Client client) {
        if (offeringCatalog.isEmpty()) {
            System.out.println("No offerings for clients available to book in the catalog.");
            return false;
        }
    
        boolean hasAvailableOfferings = false;
    
        System.out.println("Offerings available to book:");
        for (Offering offering : offeringCatalog) {
            if (!offering.getIsOpen()) {
                hasAvailableOfferings = true;
                System.out.println("Lesson ID: " + offering.getLessonId());
                System.out.println("Activity: " + offering.getActivity());
                System.out.println("date: " + offering.getSchedule().getStartDate() + " - " + offering.getSchedule().getEndDate());
                System.out.println("time: " + offering.getSchedule().getStartTime() + " - " + offering.getSchedule().getEndTime());
                System.out.println("address: " + offering.getSpace().getAddress());
                System.out.println("city: " + offering.getSpace().getCity());
                int spaceLeft = (offering.getSpace().getPersonLimit()) - (offering.getBookingAmount());
                System.out.println("space remaining: " +  spaceLeft);
                System.out.println("instructor name: "+offering.getInstructor().getName());
                if(offering.getClients().contains(client)){
                    System.out.println("Not available: already booked");
                }
                System.out.println("------------");

            }
        }
    
        if (!hasAvailableOfferings) {
            System.out.println("No available offerings to book.");
        }
        return hasAvailableOfferings;
    }

    public static boolean viewPublictOfferings() {
        if (offeringCatalog.isEmpty()) {
            System.out.println("No offerings for clients available to book in the catalog.");
            return false;
        }
    
        boolean hasAvailableOfferings = false;
    
        System.out.println("Offerings available to book:");
        for (Offering offering : offeringCatalog) {
            if (!offering.getIsOpen()) {
                hasAvailableOfferings = true;
                System.out.println("Lesson ID: " + offering.getLessonId());
                System.out.println("Activity: " + offering.getActivity());
                System.out.println("date: " + offering.getSchedule().getStartDate() + " - " + offering.getSchedule().getEndDate());
                System.out.println("time: " + offering.getSchedule().getStartTime() + " - " + offering.getSchedule().getEndTime());
                System.out.println("address: " + offering.getSpace().getAddress());
                System.out.println("city: " + offering.getSpace().getCity());
                int spaceLeft = (offering.getSpace().getPersonLimit()) - (offering.getBookingAmount());
                System.out.println("space remaining: " +  spaceLeft);
                System.out.println("instructor name: "+offering.getInstructor().getName());
                System.out.println("------------");
            }
        }
    
        if (!hasAvailableOfferings) {
            System.out.println("No available offerings to book.");
        }
        return hasAvailableOfferings;
    }

    // Finding an offering according to offeringId
    public static Offering findOffering(String offeringId) {
        for (Offering offering : offeringCatalog) {
            if (offering.getLessonId().equals(offeringId)) {
                return offering; 
            }
        }
        System.out.println("Offering not found with ID: " + offeringId);
        return null;
    }
}

