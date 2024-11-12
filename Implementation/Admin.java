import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {

    private List<Lesson> createdOfferings;
    private static List<User> users = new ArrayList<>();

    public Admin(String name, String password) {
        super(name, password);
        this.createdOfferings = new ArrayList<>();
    }

    // Method to create a new lesson
    public void createLesson(OfferingsCatalog offeringsCatalog) {
        Scanner scanner = new Scanner(System.in);

        // Gather information for the new lesson
        System.out.print("Enter start time (HH:mm): ");
        String startTime = scanner.nextLine();

        System.out.print("Enter end time (HH:mm): ");
        String endTime = scanner.nextLine();

        System.out.print("Enter date (yyyy-mm-dd): ");
        String startDate = scanner.nextLine();

        System.out.print("Enter end date (yyyy-mm-dd): ");
        String endDate = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter type of space (court, pool, etc.): ");
        String type = scanner.nextLine();

        System.out.print("Enter activity: ");
        String activity = scanner.nextLine();

        System.out.print("Enter offering ID: ");
        String id = scanner.nextLine();

        System.out.print("Is this offering public? (yes/no): ");
        boolean isPublic = scanner.nextLine().trim().equalsIgnoreCase("yes");

        Space space = new Space(address, type);
        Timeslot timeslot = new Timeslot(startTime, endTime, startDate, endDate, space);

        Lesson newLesson = new Lesson(activity, timeslot, space, id);
        createdOfferings.add(newLesson);

        offeringsCatalog.makeOffering(activity, timeslot, space, id, isPublic);

        System.out.println("Lesson created successfully!");
    }

    public List<Lesson> getCreatedOfferings() {
        return createdOfferings;
    }

    public void deleteAccount(List<User> users) {
        // Remove the admin from the global user list
        boolean removed = users.removeIf(user -> user.getName().equals(this.getName()));

        if (removed) {
            System.out.println(this.getName() + "'s account has been deleted.");
        } else {
            System.out.println("Account not found.");
        }
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getUsers() {
        return users;
    }
}
