import java.util.ArrayList;
import java.util.List;

public class UserCatalog {
    private List<User> users; 

    public UserCatalog() {
        this.users = new ArrayList<>(); 
    }

    // Method to add a user
    public void addUser(User user) {
        if (user != null) {
            users.add(user);
        }
    }

    // Method to remove a user by name
    public boolean removeUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                users.remove(user);
                return true; // User found and removed
            }
        }
        return false; // User not found
    }

    // Method to retrieve a user by name
    public User getUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user; 
            }
        }
        return null; 
    }

    // Method to get all users
    public List<User> getAllUsers() {
        return new ArrayList<>(users); 
    }

    // Method to get the number of users
    public int getUserCount() {
        return users.size(); 
    }
}
