import java.util.ArrayList;

public class UserCatalog {
    private static final ArrayList<User> userCatalog = new ArrayList<>(); 

    // Method to add a user
    public static void addUser(User user) {
        if (user != null) {
            userCatalog.add(user);
            System.out.println("user "+user.getName()+ " added to the system");
        }
    }

//remove user
    public static boolean removeUser(String id) {
        for (User user : userCatalog) {
            if (user.getUserId().equals(id)) {
                userCatalog.remove(user);
                System.out.println("user deleted successfully");
                return true; // User found and removed
            }
        }
        System.out.println("unable to delete user");
        return false; // User not found
    }

    // Method to retrieve a user by name
    public static User getUser(String username, String password) {
        for (User user : userCatalog) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                return user; 
            }
        }
        System.out.println("could not find user");
        return null; 
    }

    public static boolean findUser(String username, String password) {
        for (User user : userCatalog) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                return true; 
            }
        }
        return false; 
    }

    // Method to get all userCatalog
    public static ArrayList<User> getUserCatalog() {
        return userCatalog;
    }

    // Method to get the number of userCatalog
    public static int getUserCount() {
        return userCatalog.size(); 
    }

    public static void viewUsers() {
        if (userCatalog.isEmpty()) {
            System.out.println("No users in the catalog.");
        } else {
            for (User user : userCatalog) {
                System.out.println("User ID: " + user.getUserId() + ", Name: " + user.getName());
            }
        }
    }
}
