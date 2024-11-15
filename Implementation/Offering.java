import java.util.ArrayList;

class Offering extends Lesson{
    Instructor instructor;
    int bookingAmmount;
    boolean isPublic;
    private ArrayList<Client> clients = new ArrayList<>();

    // Constructor
    public Offering(String activity, Schedule schedule, Space space, Instructor instructor, boolean isPublic){
        super(activity,schedule,space);
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

    public ArrayList<Client> getClients(){
        return clients;
    }


    public boolean addClient(Client client){
        if((this.space.getPersonLimit() > bookingAmmount) && !(clients.contains(client))){
            clients.add(client);
            bookingAmmount++;
            return true;
        }
        else {
            System.out.println("error booking this offer.");
            return false;
        }
    }


}