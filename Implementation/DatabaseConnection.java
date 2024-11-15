import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            String url = "jdbc:mysql://localhost:3306/organization";
            String username = "root"; 
            String password = "valentin21"; 

            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to the database");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}
