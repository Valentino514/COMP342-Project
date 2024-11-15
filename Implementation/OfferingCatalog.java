import java.util.ArrayList;

public class OfferingCatalog {
    private static final ArrayList<Offering> offeringCatalog = new ArrayList<>();



    public static void generateOffering(String activity, Schedule schedule, Space space,Instructor instructor, boolean isPublic) {
        Offering newOffering = new Offering(activity,schedule,space,instructor,isPublic);
        offeringCatalog.add(newOffering);
    }

    public static ArrayList<Offering> getOfferings() {
        return offeringCatalog;
    }

    public static void viewOfferings() {
            if (offeringCatalog.isEmpty()) {
                System.out.println("No offerings for clients available to book in the catalog.");
                return;
            }
    
            // Iterate through the lessons and print their details
            System.out.println("offerings available to book ");
            for (Offering offering : offeringCatalog) {
                if (!offering.getIsOpen()){
                System.out.println("Lesson ID: " + offering.getLessonId());
                System.out.println("Activity: " + offering.getActivity());
                System.out.println("Schedule: " + offering.getSchedule().getStartDate() + " - " + offering.getSchedule().getEndDate());
                System.out.println("Space: " + offering.getSpace().getAddress());
                System.out.println("------------");
                }
            }
    }

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

