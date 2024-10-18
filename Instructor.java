public class Instructor {
    private String specialization;
    private String name;
    private String phoneNumber;

    public Instructor(String s, String n, String p){
        this.name=n;
        this.phoneNumber =p;
        this.specialization=s;
    }

    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

    }public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    public void viewOpenOfferings(){

    }

    public void selectOffering(){

    }

}
