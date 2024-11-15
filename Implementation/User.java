
abstract class User {
    private String name;
    private String password;
    private String userId;
    private static int idCounter = 1; 
    
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.userId = generateUniqueId();
    }

    // Generating different userIds of the space
    private static String generateUniqueId() {
        return String.valueOf(idCounter++); // Convert the counter to a String for userId
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId=id;
    }

    // Getting the user type whether the user is admin, instructor or Client
    public String getUserType() {
        if (this instanceof Client) {
            return "Client";
        } else if (this instanceof Admin) {
            return "Admin";
        } else if (this instanceof UnderageClient) {
            return "UnderageClient";
        } else if(this instanceof Instructor){
            return "Instructor";
        }
        else{
            return "not signed in";
        }
    }

}

