import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Lesson {
    private String activity;
    private Schedule schedule;
    protected Space space;
    private String lessonId;
    private List<Offering> offerings;
    private boolean isOpen;
    private static int idCounter = 1;

    // Constructor
    public Lesson(String activity, Schedule schedule, Space space) {
        this.activity = activity;
        this.schedule = schedule;
        this.space = space;
        this.lessonId = generateUniqueId() ;
        this.offerings = new ArrayList<>();
        this.isOpen = true;
    }

    // Giving different ids
    private static String generateUniqueId() {
        return String.valueOf(idCounter++); 
    }

    // get the activity 
    public String getActivity() {
        return activity;
    }

    // setting up an activity
    public void setActivity(String activity){
        this.activity =activity;
    }

    // getting the schedule
    public Schedule getSchedule() {
        return schedule;
    }

    // setting up the schedule
    public void setSchedule(Schedule schedule){
        this.schedule =schedule;
    }

    // getting the space
    public Space getSpace() {
        return space;
    }

    // setting up the space
    public void setSpace(Space space){
        this.space =space;
    }

    // getting the LessonId
    public String getLessonId() {
        return lessonId;
    }

    // setting up the Lessonid
    public void setLessonId(String id){
        this.lessonId =id;
    }

    // Returns the current open status of the lesson.
    public boolean getIsOpen(){
        return isOpen;
    }

    // Sets the open status of the lesson.
    public void setIsOpen(boolean isOpen){
        this.isOpen = isOpen;
    }
    
        @Override
        public boolean equals(Object obj) {
        // Check if the object is the same as this instance
        if (this == obj) {
            return true;
        }

        // Check if the object is null or of a different class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Cast the object to Lesson
        Lesson other = (Lesson) obj;

        // Compare all relevant fields
        return Objects.equals(activity, other.activity) &&
               Objects.equals(schedule, other.schedule) &&
               Objects.equals(space, other.space) &&
               Objects.equals(lessonId, other.lessonId) &&
               isOpen == other.isOpen;
    }

    // Printing all informations
    public void printDetails() {
        System.out.println("Activity: " + activity);
        schedule.printSchedule(); 
        System.out.println("  Address: " + space.getAddress());
        System.out.println("  Type: " + space.getType());
        System.out.println("  City: " + space.getCity());
        System.out.println("Lesson ID: " + lessonId);
    }

}