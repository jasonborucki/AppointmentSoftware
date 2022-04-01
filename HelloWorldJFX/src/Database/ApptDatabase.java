package Database;

import Main.JDBC;
import Model.ApptModel;
import Model.ContactsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

/**
 * The ApptDatabase class reads in the information from the appointments table in the database. From here,
 * the appointment table view on the main screen can be populated as well as its various views and reports.
 */
public class ApptDatabase {
    /**
     * the getThisMonth method will return an observable list of appointments occuring in the current month.
     * @return
     */
    public static ObservableList<ApptModel> getThisMonth(){
        ObservableList<ApptModel> thisWeekObservableList = FXCollections.observableArrayList();
        LocalDate weekStart = LocalDate.now();
        int month = weekStart.getMonthValue();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Start, End, customer_ID, User_ID, Contact_ID, Type from appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptid = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                int location = Integer.parseInt(rs.getString("Location"));
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerid = rs.getInt("customer_ID");
                int userid = rs.getInt("User_ID");
                int contactid = rs.getInt("Contact_ID");

                if(start.toLocalDate().getMonthValue() == month){
                    ApptModel appt = new ApptModel(apptid, title, description, location, type, start, end, customerid, userid,contactid);
                    thisWeekObservableList.add(appt);
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return thisWeekObservableList;
    }

    /**
     * The getThisWeek method will return an observable list of appointments that are in the current
     * Monday through Sunday week.
     * @return
     */
    public static ObservableList<ApptModel> getThisWeek(){
        ObservableList<ApptModel> thisWeekObservableList = FXCollections.observableArrayList();

       LocalDate today = LocalDate.now();



        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Start, End, customer_ID, User_ID, Contact_ID, Type from appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptid = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                int location = Integer.parseInt(rs.getString("Location"));
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerid = rs.getInt("customer_ID");
                int userid = rs.getInt("User_ID");
                int contactid = rs.getInt("Contact_ID");

                if(today.getDayOfWeek().name().equals("MONDAY")){
                    LocalDate weekStart = LocalDate.now();
                    LocalDate weekEnd = weekStart.plusDays(7);
                    if((start.toLocalDate().equals(weekStart))|| (start.toLocalDate().isAfter(weekStart)&& end.toLocalDate().isBefore(weekEnd))){
                        ApptModel appt = new ApptModel(apptid, title, description, location, type, start, end, customerid, userid,contactid);
                        thisWeekObservableList.add(appt);
                    }

                } else if(today.getDayOfWeek().name().equals("TUESDAY")){
                    LocalDate weekStart = LocalDate.now().minusDays(1);
                    LocalDate weekEnd = weekStart.plusDays(7);
                    if((start.toLocalDate().equals(weekStart))|| (start.toLocalDate().isAfter(weekStart)&& end.toLocalDate().isBefore(weekEnd))){
                        ApptModel appt = new ApptModel(apptid, title, description, location, type, start, end, customerid, userid,contactid);
                        thisWeekObservableList.add(appt);
                    }

                } else if(today.getDayOfWeek().name().equals("WEDNESDAY")){
                    LocalDate weekStart = LocalDate.now().minusDays(2);
                    LocalDate weekEnd = weekStart.plusDays(7);
                    if((start.toLocalDate().equals(weekStart))|| (start.toLocalDate().isAfter(weekStart)&& end.toLocalDate().isBefore(weekEnd))){
                        ApptModel appt = new ApptModel(apptid, title, description, location, type, start, end, customerid, userid,contactid);
                        thisWeekObservableList.add(appt);
                    }

                }else if(today.getDayOfWeek().name().equals("THURSDAY")){
                    LocalDate weekStart = LocalDate.now().minusDays(3);
                    LocalDate weekEnd = weekStart.plusDays(7);
                    if((start.toLocalDate().equals(weekStart))|| (start.toLocalDate().isAfter(weekStart)&& end.toLocalDate().isBefore(weekEnd))){
                        ApptModel appt = new ApptModel(apptid, title, description, location, type, start, end, customerid, userid,contactid);
                        thisWeekObservableList.add(appt);
                    }

                } else if(today.getDayOfWeek().name().equals("FRIDAY")){
                    LocalDate weekStart = LocalDate.now().minusDays(4);
                    LocalDate weekEnd = weekStart.plusDays(7);
                    if((start.toLocalDate().equals(weekStart))|| (start.toLocalDate().isAfter(weekStart)&& end.toLocalDate().isBefore(weekEnd))){
                        ApptModel appt = new ApptModel(apptid, title, description, location, type, start, end, customerid, userid,contactid);
                        thisWeekObservableList.add(appt);
                    }

                }else if(today.getDayOfWeek().name().equals("SATURDAY")){
                    LocalDate weekStart = LocalDate.now().minusDays(5);
                    LocalDate weekEnd = weekStart.plusDays(7);
                    if((start.toLocalDate().equals(weekStart))|| (start.toLocalDate().isAfter(weekStart)&& end.toLocalDate().isBefore(weekEnd))){
                        ApptModel appt = new ApptModel(apptid, title, description, location, type, start, end, customerid, userid,contactid);
                        thisWeekObservableList.add(appt);
                    }

                }else {
                    LocalDate weekStart = LocalDate.now().minusDays(6);
                    LocalDate weekEnd = weekStart.plusDays(7);
                    if((start.toLocalDate().equals(weekStart))|| (start.toLocalDate().isAfter(weekStart)&& end.toLocalDate().isBefore(weekEnd))){
                        ApptModel appt = new ApptModel(apptid, title, description, location, type, start, end, customerid, userid,contactid);
                        thisWeekObservableList.add(appt);
                    }

                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return thisWeekObservableList;
    }

    /**
     * the getAllAppointments method will return an observable list of all scheduled appointments.
     * @return
     * @throws SQLException
     */
    public static ObservableList<ApptModel> getAllAppointments() throws SQLException {
        ObservableList<ApptModel> apptModelObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Start, End, customer_ID, User_ID, Contact_ID, Type from appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptid = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                int location = Integer.parseInt(rs.getString("Location"));
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerid = rs.getInt("customer_ID");
                int userid = rs.getInt("User_ID");
                int contactid = rs.getInt("Contact_ID");

                ApptModel appt = new ApptModel(apptid, title, description, location, type, start, end, customerid, userid,contactid);
                apptModelObservableList.add(appt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return apptModelObservableList;
    }
}