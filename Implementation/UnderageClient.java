public class UnderageClient extends Client {
    
    private String guardianName;
    private String guardianPhone;

    public UnderageClient(String id, String name, String contactInfo,String password, String guardianName, String guardianPhone) {
        super(id, name, contactInfo,password); 
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }
    
    @Override
    public String getName() {
        return super.getName() + " (Underage)";
    }

    @Override
    public void addBooking(Booking booking) {
        super.addBooking(booking);
    }

    public void displayGuardianInfo() {
        System.out.println("Guardian Name: " + guardianName);
        System.out.println("Guardian Phone: " + guardianPhone);
    }
}
