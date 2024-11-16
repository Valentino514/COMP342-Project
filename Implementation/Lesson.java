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

    // Constructor for lesson
    public Lesson(String activity, Schedule schedule, Space space) {
        this.activity = activity;
        this.schedule = schedule;
        this.space = space;
        this.lessonId = generateUniqueId() ;
        this.offerings = new ArrayList<>();
        this.isOpen = true;
    }

    //Giving different ids
    private static String generateUniqueId() {
        return String.valueOf(idCounter++); 
    }

    //getters and setters
    public String getActivity() {
        return activity;
    }


    public void setActivity(String activity){
        this.activity =activity;
    }


    public Schedule getSchedule() {
        return schedule;
    }


    public void setSchedule(Schedule schedule){
        this.schedule =schedule;
    }


    public Space getSpace() {
        return space;
    }


    public void setSpace(Space space){
        this.space =space;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String id){
        this.lessonId =id;
    }

    //Returns the current open status of the lesson (isOpen means the instructors can select it)
    public boolean getIsOpen(){
        return isOpen;
    }

    //Sets the open status of the lesson.
    public void setIsOpen(boolean isOpen){
        this.isOpen = isOpen;
    }

        //equals method to compare lessons
        @Override
        public boolean equals(Object obj) {
        // Check if the object is the same as this instance
        if (this == obj) {
            return true;
        }

   
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Lesson other = (Lesson) obj;

        return Objects.equals(activity, other.activity) &&
               Objects.equals(schedule, other.schedule) &&
               Objects.equals(space, other.space) &&
               Objects.equals(lessonId, other.lessonId) &&
               isOpen == other.isOpen;
    }

    //print info of lesson
    public void printDetails() {
        System.out.println("Activity: " + activity);
        schedule.printSchedule(); 
        System.out.println("  Address: " + space.getAddress());
        System.out.println("  Type: " + space.getType());
        System.out.println("  City: " + space.getCity());
        System.out.println("Lesson ID: " + lessonId);
    }

}