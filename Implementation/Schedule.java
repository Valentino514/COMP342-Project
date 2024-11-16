import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private String scheduleId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String day;
    private ArrayList<Lesson> lessons;
    private Space space;

    // Format times and dates
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Constructor
    public Schedule(String startTime, String endTime, String startDate, String endDate, Space space, String day) {
        // Normalize the time input to handle optional seconds
        startTime = startTime.split(":").length == 3 ? startTime.substring(0, 5) : startTime;
        endTime = endTime.split(":").length == 3 ? endTime.substring(0, 5) : endTime;
    
        this.startTime = LocalTime.parse(startTime, timeFormatter);
        this.endTime = LocalTime.parse(endTime, timeFormatter);
        this.startDate = LocalDate.parse(startDate, dateFormatter);
        this.endDate = LocalDate.parse(endDate, dateFormatter);
        this.lessons = new ArrayList<>();
        this.day = day;
        this.space = space;
    }
    
//getters and setters
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    //getters and setters
    public Space getSpace(){
        return space;
    }

    public void setSpace(Space space){
        this.space=space;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDay() {
        return day;
    }

    public void setStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime, timeFormatter);
    }

    public void setEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime, timeFormatter);
    }

    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, dateFormatter);
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, dateFormatter);
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Lesson> getLessons() {
        if (lessons == null) {
            lessons = new ArrayList<>(); 
        }
        return lessons;
    }

    // Check for conflicts with time and date
    public boolean timeslotAvailable(Schedule other) {
        // Check if dates overlap
        boolean dateOverlap = !this.endDate.isBefore(other.startDate) && !this.startDate.isAfter(other.endDate);
        // Check if times overlap
        boolean timeOverlap = !this.endTime.isBefore(other.startTime) && !this.startTime.isAfter(other.endTime);
        // Check if days are the same
        boolean dayConflict = this.getDay().equalsIgnoreCase(other.getDay());
        boolean conflictExists = dateOverlap && timeOverlap && dayConflict;
        return !conflictExists;
    }
    

    // print the schedule
    public void printSchedule() {
        System.out.println("Schedule Details:");
        System.out.println("Day: " + day);
        System.out.println("Start Date: " + startDate.format(dateFormatter));
        System.out.println("End Date: " + endDate.format(dateFormatter));
        System.out.println("Start Time: " + startTime.format(timeFormatter));
        System.out.println("End Time: " + endTime.format(timeFormatter));
        System.out.println("---------------------------------");
    }
    
}
