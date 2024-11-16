import java.sql.*;

public class ScheduleCatalog {

    //Add new schedule to the catalog
    public static void addSchedule(Schedule schedule) {
        if (!checkScheduleConflict(schedule)) {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                conn.setAutoCommit(false); // Start transaction
    
                String insertScheduleSQL = "INSERT INTO schedules (start_time, end_time, start_date, end_date, day, space_id) VALUES (?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(insertScheduleSQL, Statement.RETURN_GENERATED_KEYS);
                stmt.setTime(1, Time.valueOf(schedule.getStartTime()));
                stmt.setTime(2, Time.valueOf(schedule.getEndTime()));
                stmt.setDate(3, Date.valueOf(schedule.getStartDate()));
                stmt.setDate(4, Date.valueOf(schedule.getEndDate()));
                stmt.setString(5, schedule.getDay());
                stmt.setInt(6, Integer.parseInt(schedule.getSpace().getSpaceId()));
                stmt.executeUpdate();
    
                // Get generated schedule_id
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int scheduleId = rs.getInt(1);
                    schedule.setScheduleId(String.valueOf(scheduleId));
                }
    
                conn.commit(); // Commit transaction
                System.out.println("Schedule added for space: " + schedule.getSpace().getAddress());
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
        } else {
            System.out.println("Error: schedule conflicts with another schedule for the same space id: " + schedule.getSpace().getSpaceId());
        }
    }

    // Check if the new schedule conflicts with any existing schedules for the same space
    public static boolean checkScheduleConflict(Schedule newSchedule) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM schedules WHERE space_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(newSchedule.getSpace().getSpaceId()));
            rs = stmt.executeQuery();
    
            while (rs.next()) {
                Schedule existingSchedule = new Schedule(
                    rs.getTime("start_time").toString(),
                    rs.getTime("end_time").toString(),
                    rs.getDate("start_date").toString(),
                    rs.getDate("end_date").toString(),
                    newSchedule.getSpace(),
                    rs.getString("day")
                );
    
                // Check for conflict
                if (!existingSchedule.timeslotAvailable(newSchedule)) {
                    return true;
                }
            }
            return false;
    
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
    

    //Print schedules for a specific space
    public static void printSpaceSchedule(String spaceId) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM schedules WHERE space_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(spaceId));
            rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No schedules for space id: " + spaceId);
            } else {
                while (rs.next()) {
                    Schedule schedule = new Schedule(
                            rs.getTime("start_time").toString(),
                            rs.getTime("end_time").toString(),
                            rs.getDate("start_date").toString(),
                            rs.getDate("end_date").toString(),
                            SpaceCatalog.findSpace(spaceId),
                            rs.getString("day")
                    );
                    schedule.printSchedule();
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

    // Find a schedule by ID
    public static Schedule findScheduleById(String scheduleId) {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM schedules WHERE schedule_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(scheduleId));
            rs = stmt.executeQuery();

            if (rs.next()) {
                String startTime = rs.getTime("start_time").toString();
                String endTime = rs.getTime("end_time").toString();
                String startDate = rs.getDate("start_date").toString();
                String endDate = rs.getDate("end_date").toString();
                String day = rs.getString("day");
                String spaceId = rs.getString("space_id");
                Space space = SpaceCatalog.findSpace(spaceId);

                Schedule schedule = new Schedule(startTime, endTime, startDate, endDate, space, day);
                schedule.setScheduleId(scheduleId);
                return schedule;
            } else {
                System.out.println("Schedule not found with id: " + scheduleId);
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
