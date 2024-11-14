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
    // Constructor
    public Lesson(String activity, Schedule schedule, Space space, String lessonId) {
        this.activity = activity;
        this.schedule = schedule;
        this.space = space;
        this.lessonId = lessonId;
        this.offerings = new ArrayList<>();
        this.isOpen = true;
    }

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

    public boolean getIsOpen(){
        return isOpen;
    }

    public void setIsOpen(boolean isOpen){
        this.isOpen = isOpen;
    }

    public void addInstructor(Instructor instructor, boolean isPublic){
        if(this.isOpen){
            Offering offering = new Offering(this.activity,this.schedule,this.space,this.lessonId, instructor, isPublic);
            offerings.add(offering);
            this.isOpen =false;
        }
        else{
            System.out.println("lesson is not available anymore\n");
        }

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

}