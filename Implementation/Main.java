import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SpaceCatalog spaceCatalog = new SpaceCatalog();
        ScheduleCatalog timeslotCatalog = new ScheduleCatalog();
        OfferingsCatalog offeringsCatalog = new OfferingsCatalog(spaceCatalog, timeslotCatalog);
        UserCatalog userCatalog= new UserCatalog();
        OrganizationCatalog organizationCatalog= new OrganizationCatalog();

        //temp values for testing
        Space space1 = new OwnedSpace("36 Concordia", "pool");
        spaceCatalog.addSpace(space1);
        Schedule timeslot1 = new Schedule("12:00", "13:30", "2024-12-01", "2024-12-24", space1);
        offeringsCatalog.makeOffering("swimming", timeslot1, space1, "SWIM-201",true);

        Space space2 = new OwnedSpace("25 Guy st", "pool");
        spaceCatalog.addSpace(space2);
        Schedule timeslot2 = new Schedule("11:00", "12:30", "2024-05-05", "2024-05-25", space2);
        offeringsCatalog.makeOffering("Running", timeslot2, space2, "RUN-201",false);

        System.out.print("Are you an administrator? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {

            System.out.print("Username:");
            String name= scanner.nextLine();

            System.out.print("password:");
            String password= scanner.nextLine();

            Admin admin = new Admin(name,password);
            boolean running = true;

            while (running) {
                System.out.println("ADMIN / writer mode");
                System.out.println("1. Create a new offering");
                System.out.println("2. view all offerings");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); //check next input
                switch (choice) {
                    case 1:
                        admin.createLesson(offeringsCatalog);
                        break;
                    case 2:
                        offeringsCatalog.viewAllOfferings();
                        break;
                    case 3:
                        running = false;
                        System.out.println("Exiting.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else if (response.equals("no")) {
            boolean running = true;
            while (running) {
                System.out.println("Reader Mode");
                System.out.println("1. view all offerings");
                System.out.println("2. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();//check next input

                switch (choice) {
                    case 1:
                    offeringsCatalog.viewAllOfferings();
                        break;
                    case 2:
                        running = false;
                        System.out.println("Exiting.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }

        scanner.close();
    }
}