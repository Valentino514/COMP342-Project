import java.util.ArrayList;

public class UserCatalog {
    private static final ArrayList<User> userCatalog = new ArrayList<>(); 

    // Method to add a user
    public static void addUser(User user) {
        if (user != null) {
            userCatalog.add(user);
        }
    }

//remove user
    public static boolean removeUser(String id) {
        for (User user : userCatalog) {
            if (user.getUserId().equals(id)) {
                userCatalog.remove(user);
                return true; // User found and removed
            }
        }
        return false; // User not found
    }

    // Method to retrieve a user by name
    public static User getUser(String id) {
        for (User user : userCatalog) {
            if (user.getUserId().equals(id)) {
                return user; 
            }
        }
        return null; 
    }

    // Method to get all userCatalog
    public static ArrayList<User> getUserCatalog() {
        return userCatalog;
    }

    // Method to get the number of userCatalog
    public static int getUserCount() {
        return userCatalog.size(); 
    }
}
