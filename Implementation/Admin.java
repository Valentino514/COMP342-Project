import java.util.Scanner;

public class Admin extends User {

    public Admin(String name, String password) {
        super(name, password);
    }

    // Method to create a new offering
    public void createOffering(OfferingsCatalog offeringsCatalog) {
        Scanner scanner = new Scanner(System.in);

        // Gather information for the new offering
        System.out.print("Enter start time (HH:mm): ");
        String startTime = scanner.nextLine();

        System.out.print("Enter end time (HH:mm): ");
        String endTime = scanner.nextLine();

        System.out.print("Enter date (yyyy-mm-dd): ");
        String startDate = scanner.nextLine();

        System.out.print("Enter date (yyyy-mm-dd): ");
        String endDate = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter type of space (court, pool, etc.) ");
        String type = scanner.nextLine();

        System.out.print("Enter activity: ");
        String activity = scanner.nextLine();

        System.out.print("Enter offering ID: ");
        String id = scanner.nextLine();

        System.out.print("Is this offering public? (yes/no): ");
        boolean isPublic = scanner.nextLine().trim().equalsIgnoreCase("yes");

        Space space = new Space(address,type);

        Timeslot timeslot = new Timeslot(startTime,endTime, startDate, endDate, space); // Adjust as needed

        // Create the offering
        offeringsCatalog.makeOffering(activity, timeslot, space, id,isPublic);

        scanner.close();

    }

    public void deleteAccount() {
        
    }
}