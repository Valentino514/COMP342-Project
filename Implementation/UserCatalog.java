import java.sql.*;
import java.util.ArrayList;

public class UserCatalog {

    // Method to add a user
    public static void addUser(User user) {
        if (user != null) {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement userStmt = null;
            ResultSet rs = null;
            try {
                conn.setAutoCommit(false); // Start transaction
    
                // Insert into 'users' table
                String insertUserSQL = "INSERT INTO users (name, password, user_type) VALUES (?, ?, ?)";
                userStmt = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
                userStmt.setString(1, user.getName());
                userStmt.setString(2, user.getPassword());
                userStmt.setString(3, user.getUserType());
                userStmt.executeUpdate();
    
                // Get generated user_id
                rs = userStmt.getGeneratedKeys();
                int userId = -1;
                if (rs.next()) {
                    userId = rs.getInt(1);
                    user.setUserId(String.valueOf(userId)); // Set the user ID in the user object
    
                    // Insert into the specific user type table
                    if (user instanceof Instructor) {
                        // Insert into 'instructors' table
                        String insertInstructorSQL = "INSERT INTO instructors (user_id, specialization, phone_number) VALUES (?, ?, ?)";
                        PreparedStatement instrStmt = conn.prepareStatement(insertInstructorSQL, Statement.RETURN_GENERATED_KEYS);
                        instrStmt.setInt(1, userId);
                        instrStmt.setString(2, ((Instructor) user).getSpecialization());
                        instrStmt.setString(3, ((Instructor) user).getPhoneNumber());
                        instrStmt.executeUpdate();
    
                        // Get generated instructor_id
                        ResultSet instrRs = instrStmt.getGeneratedKeys();
                        int instructorId = -1;
                        if (instrRs.next()) {
                            instructorId = instrRs.getInt(1);
                            ((Instructor) user).setInstructorId(String.valueOf(instructorId)); // Set instructorId
                        }
                        instrRs.close();
                        instrStmt.close();
    
                        // Insert instructor's cities
                        ArrayList<String> cities = ((Instructor) user).getCities();
                        if (cities != null && !cities.isEmpty()) {
                            String insertCitySQL = "INSERT INTO instructor_cities (instructor_id, city) VALUES (?, ?)";
                            PreparedStatement cityStmt = conn.prepareStatement(insertCitySQL);
                            for (String city : cities) {
                                cityStmt.setInt(1, instructorId);
                                cityStmt.setString(2, city);
                                cityStmt.executeUpdate();
                            }
                            cityStmt.close();
                        }
                    } else if (user instanceof UnderageClient) {
                        // Insert into 'clients' table for UnderageClient
                        String insertClientSQL = "INSERT INTO clients (user_id, age, guardian_name, guardian_phone) VALUES (?, ?, ?, ?)";
                        PreparedStatement clientStmt = conn.prepareStatement(insertClientSQL);
                        clientStmt.setInt(1, userId);
                        clientStmt.setInt(2, ((UnderageClient) user).getAge());
                        clientStmt.setString(3, ((UnderageClient) user).getGuardianName());
                        clientStmt.setString(4, ((UnderageClient) user).getGuardianPhone());
                        clientStmt.executeUpdate();
                        clientStmt.close();
                    } else if (user instanceof Client) {
                        // Insert into 'clients' table for regular Client
                        String insertClientSQL = "INSERT INTO clients (user_id, age) VALUES (?, ?)";
                        PreparedStatement clientStmt = conn.prepareStatement(insertClientSQL, Statement.RETURN_GENERATED_KEYS);
                        clientStmt.setInt(1, userId);
                        clientStmt.setInt(2, ((Client) user).getAge());
                        clientStmt.executeUpdate();
                    
                        // Get generated client_id
                        ResultSet clientRs = clientStmt.getGeneratedKeys();
                        int clientId = -1;
                        if (clientRs.next()) {
                            clientId = clientRs.getInt(1);
                            ((Client) user).setClientId(String.valueOf(clientId)); 
                        }
                        clientRs.close();
                        clientStmt.close();
                    } else if (user instanceof Admin) {
                        // Insert into 'admins' table
                        String insertAdminSQL = "INSERT INTO admins (user_id) VALUES (?)";
                        PreparedStatement adminStmt = conn.prepareStatement(insertAdminSQL);
                        adminStmt.setInt(1, userId);
                        adminStmt.executeUpdate();
                        adminStmt.close();
                    }
                }
    
                conn.commit(); // Commit transaction
                System.out.println("User " + user.getName() + " added to the system");
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
                    if (userStmt != null) userStmt.close();
                    conn.setAutoCommit(true); // Restore default auto-commit behavior
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

    // Method to remove a user
    public static boolean removeUser(String id) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement delStmt = null;
        try {
            conn.setAutoCommit(false); // Start transaction

            int userId = Integer.parseInt(id);

            // First, determine user type
            String userTypeQuery = "SELECT user_type FROM users WHERE user_id = ?";
            PreparedStatement userTypeStmt = conn.prepareStatement(userTypeQuery);
            userTypeStmt.setInt(1, userId);
            ResultSet rs = userTypeStmt.executeQuery();
            String userType = null;
            if (rs.next()) {
                userType = rs.getString("user_type");
            } else {
                System.out.println("Unable to delete user: User not found");
                return false;
            }
            rs.close();
            userTypeStmt.close();

            // Delete from specialized tables based on user type
            if ("Client".equals(userType) || "UnderageClient".equals(userType)) {
                String deleteClientSQL = "DELETE FROM clients WHERE user_id = ?";
                PreparedStatement delClientStmt = conn.prepareStatement(deleteClientSQL);
                delClientStmt.setInt(1, userId);
                delClientStmt.executeUpdate();
                delClientStmt.close();

                // Also, delete any bookings the client has
                String deleteBookingsSQL = "DELETE FROM offerings_clients WHERE client_id = ?";
                PreparedStatement delBookingStmt = conn.prepareStatement(deleteBookingsSQL);
                delBookingStmt.setInt(1, userId);
                delBookingStmt.executeUpdate();
                delBookingStmt.close();
            } else if ("Instructor".equals(userType)) {
                String deleteInstructorSQL = "DELETE FROM instructors WHERE user_id = ?";
                PreparedStatement delInstructorStmt = conn.prepareStatement(deleteInstructorSQL);
                delInstructorStmt.setInt(1, userId);
                delInstructorStmt.executeUpdate();
                delInstructorStmt.close();

                // Delete instructor cities
                String deleteCitiesSQL = "DELETE FROM instructor_cities WHERE instructor_id = ?";
                PreparedStatement delCitiesStmt = conn.prepareStatement(deleteCitiesSQL);
                delCitiesStmt.setInt(1, userId);
                delCitiesStmt.executeUpdate();
                delCitiesStmt.close();

                // Handle offerings associated with the instructor
                String updateOfferingsSQL = "UPDATE offerings SET instructor_id = NULL WHERE instructor_id = ?";
                PreparedStatement updateOfferingsStmt = conn.prepareStatement(updateOfferingsSQL);
                updateOfferingsStmt.setInt(1, userId);
                updateOfferingsStmt.executeUpdate();
                updateOfferingsStmt.close();
            } else if ("Admin".equals(userType)) {
                String deleteAdminSQL = "DELETE FROM admins WHERE user_id = ?";
                PreparedStatement delAdminStmt = conn.prepareStatement(deleteAdminSQL);
                delAdminStmt.setInt(1, userId);
                delAdminStmt.executeUpdate();
                delAdminStmt.close();
            }

            // Then delete from 'users' table
            String deleteUserSQL = "DELETE FROM users WHERE user_id = ?";
            delStmt = conn.prepareStatement(deleteUserSQL);
            delStmt.setInt(1, userId);
            int rowsAffected = delStmt.executeUpdate();

            conn.commit(); // Commit transaction

            if (rowsAffected > 0) {
                System.out.println("User deleted successfully");
                return true;
            } else {
                System.out.println("Unable to delete user");
                return false;
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback transaction in case of error
                System.out.println("Transaction rolled back due to error");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (delStmt != null) delStmt.close();
                conn.setAutoCommit(true); // Restore default auto-commit behavior
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to retrieve a user by name and password
    public static User getUser(String name, String password) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM users WHERE name = ? AND password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                String userType = rs.getString("user_type");
                String userId = rs.getString("user_id");
    
                if (userType.equals("Admin")) {
                    // Create Admin object
                    Admin admin = new Admin(name, password);
                    admin.setUserId(userId);
                    return admin;
    
                } else if (userType.equals("Instructor")) {
                    // Create Instructor object
                    Instructor instructor = new Instructor(name, password);
                    instructor.setUserId(userId);
    
                    // Fetch instructor details
                    String instrQuery = "SELECT * FROM instructors WHERE user_id = ?";
                    PreparedStatement instrStmt = conn.prepareStatement(instrQuery);
                    instrStmt.setInt(1, Integer.parseInt(userId));
                    ResultSet instrRs = instrStmt.executeQuery();
                    int instructorId = -1;
                    if (instrRs.next()) {
                        String specialization = instrRs.getString("specialization");
                        String phoneNumber = instrRs.getString("phone_number");
                        instructor.setSpecialization(specialization);
                        instructor.setPhoneNumber(phoneNumber);
                        instructorId = instrRs.getInt("instructor_id");
                        instructor.setInstructorId(String.valueOf(instructorId));
                    }
                    instrRs.close();
                    instrStmt.close();
    
                    // Load instructor's cities
                    if (instructorId != -1) {
                        ArrayList<String> cities = getInstructorCitiesByInstructorId(instructorId);
                        instructor.setCities(cities);
                    }
                    return instructor;
    
                } else if (userType.equals("Client")) {
                    // Fetch client details from 'clients' table
                    String clientQuery = "SELECT * FROM clients WHERE user_id = ?";
                    PreparedStatement clientStmt = conn.prepareStatement(clientQuery);
                    clientStmt.setInt(1, Integer.parseInt(userId));
                    ResultSet clientRs = clientStmt.executeQuery();
                    if (clientRs.next()) {
                        int age = clientRs.getInt("age");
                        String clientId = clientRs.getString("client_id"); // Retrieve client_id
    
                        // Create Client object
                        Client client = new Client(name, password, age);
                        client.setUserId(userId);
                        client.setClientId(clientId); // Set the clientId
    
                        // Close resources
                        clientRs.close();
                        clientStmt.close();
    
                        return client;
                    } else {
                        System.out.println("Client details not found for user_id: " + userId);
                        clientRs.close();
                        clientStmt.close();
                    }
    
                } else if (userType.equals("UnderageClient")) {
                    // Fetch underage client details from 'clients' table
                    String clientQuery = "SELECT * FROM clients WHERE user_id = ?";
                    PreparedStatement clientStmt = conn.prepareStatement(clientQuery);
                    clientStmt.setInt(1, Integer.parseInt(userId));
                    ResultSet clientRs = clientStmt.executeQuery();
                    if (clientRs.next()) {
                        int age = clientRs.getInt("age");
                        String guardianName = clientRs.getString("guardian_name");
                        String guardianPhone = clientRs.getString("guardian_phone");
                        String clientId = clientRs.getString("client_id"); // Retrieve client_id
    
                        // Create UnderageClient object
                        UnderageClient underageClient = new UnderageClient(name, password, guardianName, guardianPhone, age);
                        underageClient.setUserId(userId);
                        underageClient.setClientId(clientId); // Set the clientId
    
                        // Close resources
                        clientRs.close();
                        clientStmt.close();
    
                        return underageClient;
                    } else {
                        System.out.println("Underage client details not found for user_id: " + userId);
                        clientRs.close();
                        clientStmt.close();
                    }
    
                } else {
                    System.out.println("Unknown user type: " + userType);
                }
            } else {
                System.out.println("User not found with the provided credentials.");
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
        return null;
    }
    

    
public static Instructor getInstructorByInstructorId(String instructorId) {
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
        // Fetch instructor details
        String instrQuery = "SELECT * FROM instructors WHERE instructor_id = ?";
        stmt = conn.prepareStatement(instrQuery);
        stmt.setInt(1, Integer.parseInt(instructorId));
        rs = stmt.executeQuery();
        if (rs.next()) {
            String userId = rs.getString("user_id");
            String specialization = rs.getString("specialization");
            String phoneNumber = rs.getString("phone_number");

            // Fetch user details
            String userQuery = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement userStmt = conn.prepareStatement(userQuery);
            userStmt.setInt(1, Integer.parseInt(userId));
            ResultSet userRs = userStmt.executeQuery();
            if (userRs.next()) {
                String username = userRs.getString("name");
                String password = userRs.getString("password");

                // Load cities
                ArrayList<String> cities = new ArrayList<>();
                String citiesQuery = "SELECT city FROM instructor_cities WHERE instructor_id = ?";
                PreparedStatement citiesStmt = conn.prepareStatement(citiesQuery);
                citiesStmt.setInt(1, Integer.parseInt(instructorId));
                ResultSet citiesRs = citiesStmt.executeQuery();
                while (citiesRs.next()) {
                    cities.add(citiesRs.getString("city"));
                }
                citiesRs.close();
                citiesStmt.close();

                Instructor instructor = new Instructor(specialization, username, password, phoneNumber, cities);
                instructor.setUserId(userId);
                instructor.setInstructorId(instructorId);

                userRs.close();
                userStmt.close();

                return instructor;
            }
            userRs.close();
            userStmt.close();
        } else {
            System.out.println("Instructor with ID " + instructorId + " not found.");
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
    return null;
}

    

    public static ArrayList<String> getInstructorCitiesByInstructorId(int instructorId) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> cities = new ArrayList<>();
        try {
            String getCitiesSQL = "SELECT city FROM instructor_cities WHERE instructor_id = ?";
            stmt = conn.prepareStatement(getCitiesSQL);
            stmt.setInt(1, instructorId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cities.add(rs.getString("city"));
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
        return cities;
    }
    
    

    // Finding a specific user according to username and password
    public static boolean findUser(String username, String password) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT user_id FROM users WHERE name = ? AND password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            return rs.next(); // Returns true if user is found

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

    // Viewing all users already created
    public static void viewUsers() {
        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT user_id, name, user_type FROM users";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (!rs.isBeforeFirst()) { // Check if ResultSet is empty
                System.out.println("No users in the catalog.");
            } else {
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String name = rs.getString("name");
                    String userType = rs.getString("user_type");
                    System.out.println("User ID: " + userId + ", Name: " + name + ", Type: " + userType);
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
    public static User getUserById(String userId) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM users WHERE user_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(userId));
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                String userType = rs.getString("user_type");
                String username = rs.getString("name");
                String password = rs.getString("password");
                User user = null;
    
                if ("Client".equals(userType)) {
                    String clientQuery = "SELECT * FROM clients WHERE user_id = ?";
                    PreparedStatement clientStmt = conn.prepareStatement(clientQuery);
                    clientStmt.setInt(1, Integer.parseInt(userId));
                    ResultSet clientRs = clientStmt.executeQuery();
                    if (clientRs.next()) {
                        int age = clientRs.getInt("age");
                        String clientId = clientRs.getString("client_id");
                        user = new Client(username, password, age);
                        user.setUserId(userId);
                        ((Client) user).setClientId(clientId); // Set clientId here
                    }
                    clientRs.close();
                    clientStmt.close();
                } else if ("UnderageClient".equals(userType)) {
                    String clientQuery = "SELECT * FROM clients WHERE user_id = ?";
                    PreparedStatement clientStmt = conn.prepareStatement(clientQuery);
                    clientStmt.setInt(1, Integer.parseInt(userId));
                    ResultSet clientRs = clientStmt.executeQuery();
                    if (clientRs.next()) {
                        int age = clientRs.getInt("age");
                        String guardianName = clientRs.getString("guardian_name");
                        String guardianPhone = clientRs.getString("guardian_phone");
                        String clientId = clientRs.getString("client_id");
                        user = new UnderageClient(username, password, guardianName, guardianPhone, age);
                        user.setUserId(userId);
                        ((UnderageClient) user).setClientId(clientId); // Set clientId here
                    }
                    clientRs.close();
                    clientStmt.close();
                } else if ("Instructor".equals(userType)) {
                    // Fetch instructor details
                    String instrQuery = "SELECT * FROM instructors WHERE user_id = ?";
                    PreparedStatement instrStmt = conn.prepareStatement(instrQuery);
                    instrStmt.setInt(1, Integer.parseInt(userId));
                    ResultSet instrRs = instrStmt.executeQuery();
                    if (instrRs.next()) {
                        String instructorId = instrRs.getString("instructor_id");
                        String specialization = instrRs.getString("specialization");
                        String phoneNumber = instrRs.getString("phone_number");
    
                        // Load cities
                        ArrayList<String> cities = new ArrayList<>();
                        String citiesQuery = "SELECT city FROM instructor_cities WHERE instructor_id = ?";
                        PreparedStatement citiesStmt = conn.prepareStatement(citiesQuery);
                        citiesStmt.setInt(1, Integer.parseInt(instructorId));
                        ResultSet citiesRs = citiesStmt.executeQuery();
                        while (citiesRs.next()) {
                            cities.add(citiesRs.getString("city"));
                        }
                        citiesRs.close();
                        citiesStmt.close();
    
                        Instructor instructor = new Instructor(specialization, username, password, phoneNumber, cities);
                        instructor.setUserId(userId);
                        instructor.setInstructorId(instructorId);
    
                        user = instructor;
                    }
                    instrRs.close();
                    instrStmt.close();
                } else if ("Admin".equals(userType)) {
                    user = new Admin(username, password);
                    user.setUserId(userId);
                }
                return user;
            } else {
                System.out.println("Could not find user with ID: " + userId);
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
