public class Admin extends User {
    
    public Admin(String name, String password) {
        super(name, password);
    }

    public void deleteAccount(String id) {
        UserCatalog.removeUser(id);
    }
}
