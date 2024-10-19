import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timeslot {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private Space space;

    //Format times and dates
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Constructor for Timeslot
    public Timeslot(String startTime, String endTime, String startDate, String endDate,Space space) {
        this.startTime = LocalTime.parse(startTime, timeFormatter);
        this.endTime = LocalTime.parse(endTime, timeFormatter);
        this.startDate = LocalDate.parse(startDate, dateFormatter);
        this.endDate = LocalDate.parse(endDate, dateFormatter);
        this.space = space;
    }

    // Getter methods for the timeslot
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

    public Space getSpace(){
        return space;
    }

    //conflicts with time and date
    public boolean timeslotAvailable(Timeslot other) {
        //Check if the date/time dont overlap
        boolean noDateOverlap = this.endDate.isBefore(other.startDate) || this.startDate.isAfter(other.endDate);
        boolean noTimeOverlap = this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime);
        return noDateOverlap || noTimeOverlap;
    }

}
