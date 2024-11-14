import java.util.ArrayList;
import java.util.List;

class Offering extends Lesson{
    Instructor instructor;
    int bookingAmmount;
    boolean isPublic;
    private List<Client> clients;

    // Constructor
    public Offering(String activity, Schedule schedule, Space space, String lessonId, Instructor instructor, boolean isPublic){
        super(activity,schedule,space,lessonId);
        this.bookingAmmount = 0;
        this.instructor = instructor;
        this.isPublic = isPublic;
        this.clients = new ArrayList<>();
    }

    public Instructor getInstructor(){
        return instructor;
    }

    public void setInstructor(Instructor instructor){
        this.instructor = instructor;
    }

    public int getBookingAmmount(){
        return bookingAmmount;
    }

    public void setBookingAmmount(int ammount){
        this.bookingAmmount = ammount;
    }

    public boolean getIsPublic(){
        return isPublic;
    }

    public void setIsPublic(boolean isPublic){
        this.isPublic = isPublic;
    }


    public boolean addClient(Client client){
        if(this.space.getPersonLimit() > bookingAmmount){
            clients.add(client);
            bookingAmmount++;
            return true;
        }
        else{
            System.out.println("error: Offer is full");
            return false;
        }
    }


}