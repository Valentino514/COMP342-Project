public class Admin extends User {
    
    // constructor
    public Admin(String name, String password) {
        super(name, password);
    }

    // Delete any account
    public void deleteAccount(String id) {
        UserCatalog.removeUser(id);
    }
}
