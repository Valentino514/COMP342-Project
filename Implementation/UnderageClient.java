public class UnderageClient extends Client {
    
    private String guardianName;
    private String guardianPhone;

    // constructor
    public UnderageClient(String name, String password, String guardianName, String guardianPhone, int age) {
        super(name, password, age); 
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
    }

    // getters
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

    // printing Guardian informations
    public void displayGuardianInfo() {
        System.out.println("Guardian Name: " + guardianName);
        System.out.println("Guardian Phone: " + guardianPhone);
    }
}
