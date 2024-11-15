import java.util.ArrayList;

public class Offering {
    private Lesson lesson;
    private Instructor instructor;
    private int bookingAmount;
    private boolean isPublic;
    private String offeringId;
    private boolean isOpen;
    private ArrayList<Client> clients;

    // Constructor
    public Offering(Lesson lesson, Instructor instructor, boolean isPublic) {
        this.lesson = lesson;
        this.instructor = instructor;
        this.isPublic = isPublic;
        this.bookingAmount = 0;
        this.isOpen = false;
        this.clients = new ArrayList<>();
    }

    // Getter for lesson
    public Lesson getLesson() {
        return lesson;
    }

    // Setter for lesson (if needed)
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    // Getters and Setters for instructor
    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor){
        this.instructor = instructor;
    }

    // Getters and Setters for bookingAmount
    public int getBookingAmount(){
        return bookingAmount;
    }

    public void setBookingAmount(int amount){
        this.bookingAmount = amount;
    }

    // Getters and Setters for isPublic
    public boolean getIsPublic(){
        return isPublic;
    }

    public void setIsPublic(boolean isPublic){
        this.isPublic = isPublic;
    }

    // Getters and Setters for offeringId
    public String getOfferingId(){
        return offeringId;
    }

    public void setOfferingId(String offeringId){
        this.offeringId = offeringId;
    }

    // Getters and Setters for isOpen
    public boolean getIsOpen(){
        return isOpen;
    }

    public void setIsOpen(boolean isOpen){
        this.isOpen = isOpen;
    }

    // Getter for clients
    public ArrayList<Client> getClients(){
        return clients;
    }

    // Adding a client to the offering
    public boolean addClient(Client client) {
        if ((getCapacity() > bookingAmount) && !clients.contains(client)) {
            clients.add(client);
            bookingAmount++;
            return true;
        } else {
            return false;
        }
    }
    
    public int getCapacity() {
        return isPublic ? lesson.getSpace().getPersonLimit() : 1;
    }
}
