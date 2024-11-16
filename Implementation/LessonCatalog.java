import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LessonCatalog {

    //Create a lesson
    public static void createLesson(String activity, Schedule schedule, String spaceId, String lessonId) {
        Space space = SpaceCatalog.findSpace(spaceId);
    
        if (space != null) {
            // Proceed to create the lesson
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                conn.setAutoCommit(false); // Start transaction
    
                // Insert lesson into database
                String insertLessonSQL = "INSERT INTO lessons (activity, schedule_id, space_id, is_open) VALUES (?, ?, ?, ?)";
                stmt = conn.prepareStatement(insertLessonSQL, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, activity);
                stmt.setInt(2, Integer.parseInt(schedule.getScheduleId()));
                stmt.setInt(3, Integer.parseInt(spaceId));
                stmt.setBoolean(4, true);
                stmt.executeUpdate();
    
                //Get generated lesson_id
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int lessonIdGenerated = rs.getInt(1);
                    System.out.println("Lesson created successfully with ID: " + lessonIdGenerated);
                }
    
                conn.commit(); //Commit
            } catch (SQLException e) {
                try {
                    conn.rollback();//in case error occurs
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
        } else {
            System.out.println("Cannot create lesson. Space not found.");
        }
    }
    

    //Register for a lesson by a specific instructor
    public static void takeLesson(Instructor instructor, String lessonId) {
        Lesson lesson = findLessonById(lessonId);
        if (lesson == null) {
            System.out.println("Error, lesson not found in catalog");
            return;
        }
        String cityOfLesson = lesson.getSpace().getCity();
        boolean cityMatch = instructor.getCities().stream()
            .anyMatch(city -> city.equalsIgnoreCase(cityOfLesson.trim()));
        if (cityMatch) {
            System.out.println("Would you like to make the lesson private? (1 client max) (Y/N)");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine().trim();
            boolean isPublic;
            if (userInput.equalsIgnoreCase("Y")) {
                isPublic = false;
            } else {
                isPublic = true;
            }
            // Set lesson isOpen to FALSE (no longer open to instructors)
            lesson.setIsOpen(false);
            updateLessonIsOpen(lesson);
    
            // Generate offering
            OfferingCatalog.generateOffering(lesson.getActivity(), lesson.getSchedule(), lesson.getSpace(), instructor, isPublic);
    
        } else {
            System.out.println("Lesson located in city not in instructor's list of selected cities");
        }
    }
    //changes the status of the lesson (isOpen false means the lesson can no longer be selected by user)
    private static void updateLessonIsOpen(Lesson lesson) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        try {
            String updateLessonSQL = "UPDATE lessons SET is_open = ? WHERE lesson_id = ?";
            stmt = conn.prepareStatement(updateLessonSQL);
            stmt.setBoolean(1, false);
            stmt.setInt(2, Integer.parseInt(lesson.getLessonId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    // private static void updateSpacePersonLimit(Space space) {
    //     Connection conn = DatabaseConnection.getConnection();
    //     PreparedStatement stmt = null;
    //     try {
    //         String updateSpaceSQL = "UPDATE spaces SET person_limit = ? WHERE space_id = ?";
    //         stmt = conn.prepareStatement(updateSpaceSQL);
    //         stmt.setInt(1, space.getPersonLimit());
    //         stmt.setInt(2, Integer.parseInt(space.getSpaceId()));
    //         stmt.executeUpdate();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         try {
    //             if (stmt != null) stmt.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }
    
    
    //find a lesson based on the activity
    public static Lesson findLessonByActivity(String activity) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM lessons WHERE activity = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, activity);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String lessonId = rs.getString("lesson_id");
                boolean isOpen = rs.getBoolean("is_open");
                String scheduleId = rs.getString("schedule_id");
                String spaceId = rs.getString("space_id");

                Schedule schedule = ScheduleCatalog.findScheduleById(scheduleId);
                Space space = SpaceCatalog.findSpace(spaceId);

                Lesson lesson = new Lesson(activity, schedule, space);
                lesson.setLessonId(lessonId);
                lesson.setIsOpen(isOpen);
                return lesson;
            } else {
                System.out.println("Lesson with activity " + activity + " not found");
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


    //Find a lesson by ID
    public static Lesson findLessonById(String lessonId) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM lessons WHERE lesson_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(lessonId));
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                String activity = rs.getString("activity");
                boolean isOpen = rs.getBoolean("is_open");
                String scheduleId = rs.getString("schedule_id");
                String spaceId = rs.getString("space_id");
    
                Schedule schedule = ScheduleCatalog.findScheduleById(scheduleId);
                Space space = SpaceCatalog.findSpace(spaceId);
    
                Lesson lesson = new Lesson(activity, schedule, space);
                lesson.setLessonId(lessonId);
                lesson.setIsOpen(isOpen);
                return lesson;
            } else {
                System.out.println("Lesson with id " + lessonId + " not found");
                return null;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // No lesson found with the given ID
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    //View all the lessons created
    public static boolean viewLessons() {
        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM lessons WHERE is_open = TRUE";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (!rs.isBeforeFirst()) {
                System.out.println("No open lessons available.");
                return false;
            } else {
                while (rs.next()) {
                    System.out.println("Lesson ID: " + rs.getInt("lesson_id"));
                    System.out.println("Activity: " + rs.getString("activity"));
                    Schedule schedule = ScheduleCatalog.findScheduleById(rs.getString("schedule_id"));
                    System.out.println("Date: " + schedule.getStartDate() + " - " + schedule.getEndDate());
                    System.out.println("Time: " + schedule.getStartTime() + " - " + schedule.getEndTime());
                    Space space = SpaceCatalog.findSpace(rs.getString("space_id"));
                    System.out.println("Space: " + space.getAddress());
                    System.out.println("City: " + space.getCity());
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
}
