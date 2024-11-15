import java.sql.*;
import java.util.ArrayList;

public class SpaceCatalog {

    // Add a new space to the database
    public static void addSpace(Space space) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn.setAutoCommit(false); // Start transaction

            String insertSpaceSQL = "INSERT INTO spaces (address, type, is_rented, city, person_limit) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertSpaceSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, space.getAddress());
            stmt.setString(2, space.getType());
            stmt.setBoolean(3, space.isRented());
            stmt.setString(4, space.getCity());
            stmt.setInt(5, space.getPersonLimit());
            stmt.executeUpdate();

            // Get generated space_id
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int spaceId = rs.getInt(1);
                space.setSpaceId(String.valueOf(spaceId));
            }

            conn.commit(); // Commit transaction
            System.out.println("Space added: " + space.getAddress());
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of error
                System.out.println("Transaction rolled back due to error");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                conn.setAutoCommit(true); // Restore default auto-commit behavior
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Check if a space exists by ID
    public static boolean spaceExists(String id) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT space_id FROM spaces WHERE space_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(id));
            rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Print all spaces in the system
    public static void printSpaces() {
        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM spaces";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (!rs.isBeforeFirst()) { // Check if ResultSet is empty
                System.out.println("No spaces available in the catalog.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("space_id"));
                    System.out.println("Address: " + rs.getString("address"));
                    System.out.println("Type: " + rs.getString("type"));
                    System.out.println("City: " + rs.getString("city"));
                    System.out.println("Is Rented: " + rs.getBoolean("is_rented"));
                    System.out.println("Person Limi: " + rs.getInt("person_limit"));
                    System.out.println("---------------------------------");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Find a space by ID
    public static Space findSpace(String spaceId) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM spaces WHERE space_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(spaceId));
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                Space space = new Space(
                    rs.getString("address"),
                    rs.getString("type"),
                    rs.getBoolean("is_rented"),
                    rs.getString("city"),
                    rs.getInt("person_limit")
                );
                space.setSpaceId(spaceId);
                return space;
            } else {
                System.out.println("Space not found with id: " + spaceId);
                return null;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
