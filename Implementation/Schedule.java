import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String day;
    private ArrayList<Lesson> lessons;

    // Format times and dates
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Constructor
    public Schedule(String startTime, String endTime, String startDate, String endDate, Space space, String day) {
        this.startTime = LocalTime.parse(startTime, timeFormatter);
        this.endTime = LocalTime.parse(endTime, timeFormatter);
        this.startDate = LocalDate.parse(startDate, dateFormatter);
        this.endDate = LocalDate.parse(endDate, dateFormatter);
        this.day = day;
    }

    // Getter methods
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

    // Setter methods
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
    return lessons;
    }

    // Check for conflicts with time and date
    public boolean timeslotAvailable(Schedule other) {
        // Check if the date/time don't overlap
        boolean noDateOverlap = this.endDate.isBefore(other.startDate) || this.startDate.isAfter(other.endDate);
        boolean noTimeOverlap = this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime);
        return noDateOverlap || noTimeOverlap;
    }
}
