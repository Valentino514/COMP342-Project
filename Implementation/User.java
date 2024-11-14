
abstract class User {
    private String name;
    private String password;
    private String userId;

    public User(String name, String password, String id) {
        this.name = name;
        this.password = password;
        this.userId = id;
    }

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

}

