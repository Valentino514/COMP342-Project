import java.util.ArrayList;

public class TimeslotCatalog {
    private ArrayList<Timeslot> timeslots = new ArrayList<>();

    // Method to add a timeslot if no conflict exists
    public void addTimeslot(Timeslot newTimeslot) {
        if (!timeslotExists(newTimeslot)) {
            timeslots.add(newTimeslot);
            System.out.println("Timeslot added for space: " + newTimeslot.getSpace().getAddress() + " from " 
                    + newTimeslot.getStartDate() + " " + newTimeslot.getStartTime() + " to " 
                    + newTimeslot.getEndDate() + " " + newTimeslot.getEndTime());
        } else {
            System.out.println("Error: Conflicting timeslot for the space: " + newTimeslot.getSpace().getAddress());
        }
    }

    // Check if a timeslot already exists or conflicts with any existing timeslot
    public boolean timeslotExists(Timeslot newTimeslot) {
        for (Timeslot existingTimeslot : timeslots) {
            // Check for the same space
            if (existingTimeslot.getSpace().getAddress().equals(newTimeslot.getSpace().getAddress())) {
                // If there is a time conflict, return true
                if (!existingTimeslot.timeslotAvailable(newTimeslot)) {
                    return true;
                }
            }
        }
        return false;
    }
}
