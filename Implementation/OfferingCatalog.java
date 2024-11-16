import java.sql.*;
import java.util.ArrayList;

public class OfferingCatalog {

    // generate a new offering
    public static void generateOffering(String activity, Schedule schedule, Space space, Instructor instructor, boolean isPublic) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn.setAutoCommit(false);
    
            // Insert into offerings table
            String insertOfferingSQL = "INSERT INTO offerings (lesson_id, instructor_id, booking_amount, is_public, is_open) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertOfferingSQL, Statement.RETURN_GENERATED_KEYS);
    
            int lessonId = Integer.parseInt(schedule.getScheduleId()); 
            int instructorId = Integer.parseInt(instructor.getInstructorId()); 
            stmt.setInt(1, lessonId);
            stmt.setInt(2, instructorId);
            stmt.setInt(3, 0); // booking_amount 
            stmt.setBoolean(4, isPublic);
            stmt.setBoolean(5, true); 
    
            stmt.executeUpdate();
    
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int offeringId = rs.getInt(1);
                System.out.println("Offering created successfully with ID: " + offeringId);
            }
    
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("Transaction rolled back due to error");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //find all offerings by the id of instructor
    public static ArrayList<Offering> getOfferingsByInstructorId(String instructorId) {
        ArrayList<Offering> offerings = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM offerings WHERE instructor_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(instructorId));
            rs = stmt.executeQuery();
    
            while (rs.next()) {
                String offeringId = rs.getString("offering_id");
                String lessonId = rs.getString("lesson_id");
                int bookingAmount = rs.getInt("booking_amount");
                boolean isPublic = rs.getBoolean("is_public");
                boolean isOpen = rs.getBoolean("is_open");
    
                //Retrieve lesson and instructor object
                Lesson lesson = LessonCatalog.findLessonById(lessonId);
                Instructor instructor = UserCatalog.getInstructorByInstructorId(instructorId);
    
                // Create offering
                Offering offering = new Offering(lesson, instructor, isPublic);
                offering.setOfferingId(offeringId);
                offering.setBookingAmount(bookingAmount);
                offering.setIsOpen(isOpen);
    
                offerings.add(offering);
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
        return offerings;
    }
    
    //view all offerings that are open
    public static boolean viewPublicOfferings() {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM offerings WHERE is_public = TRUE AND is_open = FALSE";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No public offerings available.");
                return false;
            } else {
                while (rs.next()) {
                    int offeringId = rs.getInt("offering_id");
                    System.out.println("Offering ID: " + offeringId);

                    int lessonId = rs.getInt("lesson_id");
                    int instructorId = rs.getInt("instructor_id");
                    int bookingAmount = rs.getInt("booking_amount");
                    boolean isPublic = rs.getBoolean("is_public");

                    Lesson lesson = LessonCatalog.findLessonById(String.valueOf(lessonId));

                    User user = UserCatalog.getUserById(String.valueOf(instructorId));
                    if (!(user instanceof Instructor)) {
                        System.out.println("Instructor not found for offering ID: " + offeringId);
                        continue;
                    }
                    Instructor instructor = (Instructor) user;

                    //show offerings
                    System.out.println("Activity: " + lesson.getActivity());
                    System.out.println("Date: " + lesson.getSchedule().getStartDate() + " - " + lesson.getSchedule().getEndDate());
                    System.out.println("Time: " + lesson.getSchedule().getStartTime() + " - " + lesson.getSchedule().getEndTime());
                    System.out.println("Address: " + lesson.getSpace().getAddress());
                    System.out.println("City: " + lesson.getSpace().getCity());
                    int spaceLeft = lesson.getSpace().getPersonLimit() - bookingAmount;
                    System.out.println("Space Remaining: " + spaceLeft);
                    System.out.println("Instructor Name: " + instructor.getName());
                    if (isPublic) {
                        System.out.println("Availability: Public");
                    } else {
                        System.out.println("Availability: Private");
                    }
                    System.out.println("------------");
                }
                return true;
            }

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

    //View updated Offerings
    public static boolean viewClientOfferings(Client client) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // Select offerings available for clients
            String query = "SELECT o.* FROM offerings o JOIN lessons l ON o.lesson_id = l.lesson_id WHERE l.is_open = FALSE AND o.is_open = TRUE";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
    
            if (!rs.isBeforeFirst()) {
                System.out.println("No offerings for clients available to book in the catalog.");
                return false;
            } else {
                boolean hasOfferings = false;
                while (rs.next()) {
                    hasOfferings = true;
                    int offeringId = rs.getInt("offering_id");
                    boolean clientHasBooked = clientHasBookedOffering(client.getClientId(), offeringId);
    
                    int lessonId = rs.getInt("lesson_id");
                    int instructorId = rs.getInt("instructor_id");
                    int bookingAmount = rs.getInt("booking_amount");
                    boolean isPublic = rs.getBoolean("is_public");
    
                    Lesson lesson = LessonCatalog.findLessonById(String.valueOf(lessonId));
                    Instructor instructor = UserCatalog.getInstructorByInstructorId(String.valueOf(instructorId));
    
                    int capacity = isPublic ? lesson.getSpace().getPersonLimit() : 1;
                    int spaceLeft = capacity - bookingAmount;
    
                    System.out.println("Offering ID: " + offeringId);
                    System.out.println("Activity: " + lesson.getActivity());
                    System.out.println("Date: " + lesson.getSchedule().getStartDate() + " - " + lesson.getSchedule().getEndDate() + " every " + lesson.getSchedule().getDay());
                    System.out.println("Time: " + lesson.getSchedule().getStartTime() + " - " + lesson.getSchedule().getEndTime());
                    System.out.println("Address: " + lesson.getSpace().getAddress() + ", " + lesson.getSpace().getCity() + ", " + lesson.getSpace().getType());
                    System.out.println("Space Remaining: " + spaceLeft);
                    System.out.println("Instructor Name: " + instructor.getName());
                    System.out.println("Availability: " + (isPublic ? "Public" : "Private"));
    
                    if (clientHasBooked) {
                        System.out.println("Status: Already Booked (Not Available)");
                    } else if (spaceLeft <= 0) {
                        System.out.println("Status: Fully Booked (Not Available)");
                    } else {
                        System.out.println("Status: Available");
                    }
                    System.out.println("------------");
                }
    
                if (!hasOfferings) {
                    System.out.println("No offerings for clients available to book in the catalog.");
                    return false;
                }
                return true;
            }
    
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
    
    //check if client has offering
    private static boolean clientHasBookedOffering(String clientId, int offeringId) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM offerings_clients WHERE client_id = ? AND offering_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(clientId));
            stmt.setInt(2, offeringId);
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
    

    // Finding an offering according to offeringId
    public static Offering findOffering(String offeringId) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM offerings WHERE offering_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(offeringId));
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                int lessonId = rs.getInt("lesson_id");
                int instructorId = rs.getInt("instructor_id");
                int bookingAmount = rs.getInt("booking_amount");
                boolean isPublic = rs.getBoolean("is_public");
                boolean isOpen = rs.getBoolean("is_open");
    
                Lesson lesson = LessonCatalog.findLessonById(String.valueOf(lessonId));
                Instructor instructor = UserCatalog.getInstructorByInstructorId(String.valueOf(instructorId));
                if (instructor != null) {
                    // Create the Offering object
                    Offering offering = new Offering(lesson, instructor, isPublic);
                    offering.setOfferingId(offeringId);
                    offering.setBookingAmount(bookingAmount);
                    offering.setIsOpen(isOpen);
    
                    return offering;
                } else {
                    System.out.println("Instructor with ID " + instructorId + " not found.");
                    return null;
                }
            } else {
                System.out.println("Offering not found with ID: " + offeringId);
                return null;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Offering not found
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    //handle client bookings
    public static boolean bookOffering(Client client, Offering offering) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        try {
            // Check if client has already booked this offering
            if (clientHasBookedOffering(client.getClientId(), Integer.parseInt(offering.getOfferingId()))) {
                System.out.println("You have already booked this offering.");
                return false;
            }
    
            int capacity = offering.getIsPublic() ? offering.getLesson().getSpace().getPersonLimit() : 1;
            int bookingAmount = offering.getBookingAmount();
    
            if (bookingAmount >= capacity) {
                System.out.println("Offering is fully booked.");
                return false;
            }
    
            // Add client to offering
            offering.addClient(client);
    
            // Update booking_amount in the database
            String updateBookingSQL = "UPDATE offerings SET booking_amount = booking_amount + 1 WHERE offering_id = ?";
            stmt = conn.prepareStatement(updateBookingSQL);
            stmt.setInt(1, Integer.parseInt(offering.getOfferingId()));
            stmt.executeUpdate();
    
            // Insert into offerings_clients table
            String insertClientOfferingSQL = "INSERT INTO offerings_clients (offering_id, client_id) VALUES (?, ?)";
            stmt = conn.prepareStatement(insertClientOfferingSQL);
            stmt.setInt(1, Integer.parseInt(offering.getOfferingId()));
            stmt.setInt(2, Integer.parseInt(client.getClientId()));
            stmt.executeUpdate();
    
            // Check if offering is now full
            bookingAmount++;
            if (bookingAmount >= capacity) {
                String updateIsOpenSQL = "UPDATE offerings SET is_open = FALSE WHERE offering_id = ?";
                stmt = conn.prepareStatement(updateIsOpenSQL);
                stmt.setInt(1, Integer.parseInt(offering.getOfferingId()));
                stmt.executeUpdate();
                offering.setIsOpen(false);
            }
    
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //find offerings of a client using the id
    public static ArrayList<Offering> getOfferingsByClientId(String clientId) {
        ArrayList<Offering> offerings = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT o.* FROM offerings o JOIN offerings_clients oc ON o.offering_id = oc.offering_id WHERE oc.client_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(clientId));
            rs = stmt.executeQuery();
    
            while (rs.next()) {
                int offeringId = rs.getInt("offering_id");
                int lessonId = rs.getInt("lesson_id");
                int instructorId = rs.getInt("instructor_id");
                int bookingAmount = rs.getInt("booking_amount");
                boolean isPublic = rs.getBoolean("is_public");
                boolean isOpen = rs.getBoolean("is_open");
    
                Lesson lesson = LessonCatalog.findLessonById(String.valueOf(lessonId));
                Instructor instructor = UserCatalog.getInstructorByInstructorId(String.valueOf(instructorId));
    
                Offering offering = new Offering(lesson, instructor, isPublic);
                offering.setOfferingId(String.valueOf(offeringId));
                offering.setBookingAmount(bookingAmount);
                offering.setIsOpen(isOpen);
    
                offerings.add(offering);
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
        return offerings;
    }
    
    //find any user with id
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
                        user = new Client(username, password, age);
                        user.setUserId(userId);
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
                        user = new UnderageClient(username, password, guardianName, guardianPhone, age);
                        user.setUserId(userId);
                    }
                    clientRs.close();
                    clientStmt.close();
                } else if ("Instructor".equals(userType)) {
                    String instrQuery = "SELECT * FROM instructors WHERE user_id = ?";
                    PreparedStatement instrStmt = conn.prepareStatement(instrQuery);
                    instrStmt.setInt(1, Integer.parseInt(userId));
                    ResultSet instrRs = instrStmt.executeQuery();
                    if (instrRs.next()) {
                        String specialization = instrRs.getString("specialization");
                        String phoneNumber = instrRs.getString("phone_number");
    
                        // Load cities
                        ArrayList<String> cities = new ArrayList<>();
                        String citiesQuery = "SELECT city FROM instructor_cities WHERE instructor_id = ?";
                        PreparedStatement citiesStmt = conn.prepareStatement(citiesQuery);
                        citiesStmt.setInt(1, Integer.parseInt(userId));
                        ResultSet citiesRs = citiesStmt.executeQuery();
                        while (citiesRs.next()) {
                            cities.add(citiesRs.getString("city"));
                        }
                        citiesRs.close();
                        citiesStmt.close();
    
                        user = new Instructor(specialization, username, password, phoneNumber, cities);
                        user.setUserId(userId);
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
