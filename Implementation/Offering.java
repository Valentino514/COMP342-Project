import java.util.ArrayList;
import java.util.List;

class Offering extends Lesson{
    Instructor instructor;
    int bookingAmount;
    boolean isPublic;
    private List<Client> clients = new ArrayList<>();

    // Constructor
    public Offering(String activity, Schedule schedule, Space space, Instructor instructor, boolean isPublic){
        super(activity,schedule,space);
        this.bookingAmount = 0;
        this.instructor = instructor;
        this.isPublic = isPublic;
        this.clients = new ArrayList<>();
    }

    // getting an instructor
    public Instructor getInstructor(){
        return instructor;
    }

    // setting up the instructor
    public void setInstructor(Instructor instructor){
        this.instructor = instructor;
    }

    // getting the cost of the booking
    public int getBookingAmount(){
        return bookingAmount;
    }

    // Setting the cost of the booking
    public void setBookingAmount(int amount){
        this.bookingAmount = amount;
    }

    // checking if the lesson is public
    public boolean getIsPublic(){
        return isPublic;
    }

    // setting up if the lesson is public
    public void setIsPublic(boolean isPublic){
        this.isPublic = isPublic;
    }

    // Adding a client to the lesson
    public boolean addClient(Client client){
        if(this.space.getPersonLimit() > bookingAmount){
            clients.add(client);
            bookingAmount++;
            return true;
        }
        else{
            System.out.println("error: Offer is full");
            return false;
        }
    }


}