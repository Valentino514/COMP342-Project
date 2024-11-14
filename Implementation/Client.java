import java.util.ArrayList;
import java.util.List;

class Client extends User{
    private int age;
    private List<Offering> offerings; 

    // Constructor
    public Client(String name, String password, String userId, int age) {
        super(name, password,userId);
        this.age = age;
        this.offerings = new ArrayList<>(); // Initialize empty offerings list
    }

    public List<Offering> getofferings() {
        return offerings;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    //book a new offering
    public void addOffering(Offering offering) {
        if(offering.addClient(this)){
        offerings.add(offering);
        }
        else{
            System.out.println("error adding booking since it is full");
        }
    }
}
