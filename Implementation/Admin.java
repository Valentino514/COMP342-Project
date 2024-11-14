public class Admin extends User {
    
    public Admin(String name, String password, String userId) {
        super(name, password, userId);
    }

    public void deleteAccount(String id) {
        UserCatalog.removeUser(id);
    }
}
