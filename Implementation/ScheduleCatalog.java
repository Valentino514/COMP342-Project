import java.util.ArrayList;

public class ScheduleCatalog {
    private static final ArrayList<Schedule> scheduleCatalog = new ArrayList<>();

    // Adds a new schedule to the catalog
    //
    public static void addSchedule(Schedule schedule) {
        if(!checkScheduleConflict(schedule)){
            scheduleCatalog.add(schedule);
        }
        else{
            System.out.println("error: schedule conflicts with another schedule for the same space id: "+ schedule.getSpace().getSpaceId());
        }

    }

    // Get all spaces associated with a given schedule
    public static ArrayList<Space> getSpacesForSchedule(Schedule schedule) {
        ArrayList<Space> spaces = new ArrayList<>();
        
        // Iterate over all lessons in the schedule
        for (Lesson lesson : schedule.getLessons()) {
            Space space = lesson.getSpace();  // Get the space of each lesson
            if (space != null && !spaces.contains(space)) {
                spaces.add(space);  // Add the space to the list (if it's not already added)
            }
        }
        return spaces;
    }

    // Check if the new schedule conflicts with any existing schedules for the same space
    public static boolean checkScheduleConflict(Schedule newSchedule) {
        for (Schedule existingSchedule : scheduleCatalog) {
            ArrayList<Space> existingSpaces = getSpacesForSchedule(existingSchedule);

            // Check if any of the spaces in the existing schedule match the spaceId
            for (Space space : existingSpaces) {
                if (space.getSpaceId().equals(newSchedule.getSpace().getSpaceId())) {
                    // If a schedule is found in the same space, check for time and date conflicts
                    if (!existingSchedule.timeslotAvailable(newSchedule)) {
                        return true;// if we find a conflict
                    }
                }
            }
        }
        return false;//return false otherwise
    }

    public static void printSpaceSchedule(String spaceId){
        for(Schedule schedule: scheduleCatalog){
            if(schedule.getSpace().getSpaceId().equals(spaceId)){
                schedule.printSchedule();
            }

        }

    }
}
