package Model;

import javafx.scene.control.ComboBox;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * the appointment model will allow the program to interact with the appointment table in the database.
 */
public class ApptModel {
    private int appointmentID;
    private int customerID;
    private int userID;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private int divisionID;
    private int contactID;
    private String type;


    /**
     * Constructor for appointment model
     * @param appointmentID
     * @param title
     * @param description
     * @param divisionID
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     */
    public ApptModel(int appointmentID, String title, String description, int divisionID, String type, LocalDateTime start, LocalDateTime end,  int customerID, int userID,   int contactID){
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.divisionID = divisionID;
        this.contactID = contactID;
        this.type = type;
    }

    /**
     * get appointment ID
     * @return
     */
    public int getAppointmentID() {
        return appointmentID;
    }
    /**
     * set appointment ID
     * @return
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
    /**
     * get customer ID
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }
    /**
     * set customer ID
     * @return
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /**
     * get user ID
     * @return
     */
    public int getUserID() {
        return userID;
    }
    /**
     * set user ID
     * @return
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /**
     * get appointment title
     * @return
     */
    public String getTitle() {
        return title;
    }
    /**
     * get appointment title
     * @return
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * get appointment description
     * @return
     */
    public String getDescription() {
        return description;
    }
    /**
     * set appointment description
     * @return
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * get division ID
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     * set division ID
     * @return
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    /**
     * get contact ID
     * @return
     */
    public int getContactID() {
        return contactID;
    }
    /**
     * set contact ID
     * @return
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    /**
     * get appointment type
     * @return
     */
    public String getType() {
        return type;
    }
    /**
     * set appointment type
     * @return
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * get appointment start time
     * @return
     */
    public LocalDateTime getStart() {
        return start;
    }
    /**
     * set appointment start
     * @return
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    /**
     * get appointment end
     * @return
     */
    public LocalDateTime getEnd() {
        return end;
    }
    /**
     * set appointment end
     * @return
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
