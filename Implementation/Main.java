import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Create admin and add to catalog
        Admin admin = new Admin("admin", "qwert");
        UserCatalog.addUser(admin);

        //_________________________________________________________

        boolean logout = false; // Logout flag
        Scanner scanner = new Scanner(System.in);
        while (true) { // Main application loop
            logout = false; 

            System.out.println("Do you have an account? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                User user = null;
                boolean signedIn = false;

                while (!signedIn) {
                    System.out.print("Username: ");
                    String name = scanner.nextLine();

                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    if (UserCatalog.findUser(name, password)) {
                        System.out.println("Login successful.");
                        signedIn = true;
                        user = UserCatalog.getUser(name, password);
                    } else {
                        System.out.println("Error finding user. Try again.");
                    }
                }

                while (!logout) {// Run until user logs out
                    if (user.getUserType().equals("Admin")) {
                        System.out.println("ADMIN / writer mode");
                        System.out.println("1. Create a new lesson");
                        System.out.println("2. View all lessons");
                        System.out.println("3. Delete user");
                        System.out.println("4. Log out");
                        System.out.print("Choose an option: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                case 1:
                    System.out.println("Create new lesson option chosen\nSelect space ID:");
                    SpaceCatalog.printSpaces();
                    String id = scanner.nextLine();
                    if (SpaceCatalog.spaceExists(id)) {
                        System.out.println("Selected space ID: " + id);
                        System.out.println("Enter the activity:");
                        String activity = scanner.nextLine();
                        ScheduleCatalog.printSpaceSchedule(id);
                        System.out.println("Enter a schedule that does not overlap with the ones mentioned.");
                        System.out.println("Enter start time in (HH:mm) format:");
                        String startTime = scanner.nextLine();
                        System.out.println("Enter end time in (HH:mm) format:");
                        String endTime = scanner.nextLine();
                        System.out.println("Enter start date in (YYYY-MM-DD) format:");
                        String startDate = scanner.nextLine();
                        System.out.println("Enter end date in (YYYY-MM-DD) format:");
                        String endDate = scanner.nextLine();
                        System.out.println("Enter day of the week:");
                        String day = scanner.nextLine();

                        Schedule schedule = new Schedule(startTime, endTime, startDate, endDate, SpaceCatalog.findSpace(id), day);

                        // Check for schedule conflicts before adding
                        if (!ScheduleCatalog.checkScheduleConflict(schedule)) {
                            // No conflict, add the schedule
                            ScheduleCatalog.addSchedule(schedule);

                            // Proceed to create the lesson
                            System.out.println("Creating lesson...");
                            LessonCatalog.createLesson(activity, schedule, id,null);
                        } else {
                            System.out.println("Error: schedule conflicts with another schedule for the same space id: " + schedule.getSpace().getSpaceId());
                        }
                    } else {
                        System.out.println("Space ID does not exist.");
                    }

                                break;
                            case 2:
                                LessonCatalog.viewLessons();
                                break;
                            case 3:
                                UserCatalog.viewUsers();
                                System.out.println("Type the ID of the user to delete: ");
                                String userId = scanner.nextLine();
                                admin.deleteAccount(userId);
                                System.out.println("Updated list:");
                                UserCatalog.viewUsers();
                                break;
                            case 4:
                                logout = true;
                                System.out.println("Logging out.");
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    } else if (user.getUserType().equals("Client") || user.getUserType().equals("UnderageClient")) {
                        Client client = (Client) user;
                        System.out.println("Client / reader mode");
                        System.out.println("1. Book an offering");
                        System.out.println("2. View offerings");
                        System.out.println("3. Log out");
                        System.out.print("Choose an option: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1:
                                // View offerings and check if there are any available to book
                                if (!OfferingCatalog.viewClientOfferings(client)) {
                                    break; // exit case 1 if no offerings available
                                }
                                System.out.println("Type the offering ID to select it: ");
                                String offeringId = scanner.nextLine();
                                Offering offering = OfferingCatalog.findOffering(offeringId);

                                // Check if the offering is null (not found)
                                if (offering == null) {
                                    System.out.println("Offering with ID " + offeringId + " not found. Please check the ID and try again.");
                                    break; // Exit case 1 if the offering is not found
                                }

                                // Proceed with booking the offering if it's found
                                if (OfferingCatalog.bookOffering(client, offering)) {
                                    System.out.println("Successfully booked the offering.");
                                } else {
                                    System.out.println("Failed to book the offering.");
                                }
                                break;

                            case 2:
                                client.viewOfferings();
                                break;
                            case 3:
                                logout = true;
                                System.out.println("Logging out.");
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    } else if (user.getUserType().equals("Instructor")) {
                        Instructor instructor = (Instructor) user;
                        System.out.println("Instructor / writer mode");
                        System.out.println("1. Select a lesson to instruct");
                        System.out.println("2. View my offerings");
                        System.out.println("3. Log out");
                        System.out.print("Choose an option: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1:
                                // View lessons and check if there are any open lessons
                                if (!LessonCatalog.viewLessons()) {
                                    break; // Exit case 1 if no open lessons are available
                                }
                                System.out.println("Select lesson by typing the ID: ");
                                String lessonChoice = scanner.nextLine();
                                LessonCatalog.takeLesson(instructor, lessonChoice);
                                break;
                            case 2:
                                instructor.viewAssignedOfferings();
                                break;
                            case 3:
                                logout = true;
                                System.out.println("Logging out.");
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    }
                }
            } else if (response.equals("no")) {
                boolean running = true;

                while (running) {
                    System.out.println("Reader Mode");
                    System.out.println("1. View all public offerings");
                    System.out.println("2. Create account");
                    System.out.println("3. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            // Implementing viewPublicOfferings method in OfferingCatalog
                            OfferingCatalog.viewPublicOfferings();
                            break;
                        case 2:
                            System.out.println("Enter your name:");
                            String username = scanner.nextLine();
                            System.out.println("Enter password:");
                            String password = scanner.nextLine();
                            System.out.println("Register as client or instructor?");
                            String userType = scanner.nextLine();
                            if (userType.equalsIgnoreCase("client")) {
                                System.out.println("Enter your age:");
                                int age = scanner.nextInt();
                                scanner.nextLine();
                                if (age < 18) {
                                    System.out.println("Underage client detected, need supervisor to create account");
                                    System.out.println("Enter supervisor name:");
                                    String supervisorName = scanner.nextLine();
                                    System.out.println("Enter supervisor phone number:");
                                    String supervisorPhone = scanner.nextLine();
                                    Client underageClient = new UnderageClient(username, password, supervisorName, supervisorPhone, age);
                                    UserCatalog.addUser(underageClient);
                                } else {
                                    Client client = new Client(username, password, age);
                                    UserCatalog.addUser(client);
                                }
                            } else if (userType.equalsIgnoreCase("instructor")) {
                                System.out.println("Enter your specialization:");
                                String specialty = scanner.nextLine();
                                System.out.println("Enter your phone number:");
                                String phoneNumber = scanner.nextLine();
                                System.out.println("Enter the cities you can instruct in (type 'done' when finished):");
                                ArrayList<String> cities = new ArrayList<>();
                                while (true) {
                                    String city = scanner.nextLine();
                                    if (city.equalsIgnoreCase("done")) {
                                        break; // Exit the loop
                                    }
                                    // Add the city to the list if it's not "done"
                                    cities.add(city);
                                }
                                Instructor instructor = new Instructor(specialty, username, password, phoneNumber, cities);
                                UserCatalog.addUser(instructor);
                                System.out.println("Instructor created with cities: " + cities);
                            }
                            break;
                        case 3:
                            running = false;
                            System.out.println("Exiting.");
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }
            }
        }
    }
}
