package Controller;

import Database.*;
import Main.JDBC;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Interface for lambda function - Lines 499-502
 */
interface CountCheck{
    void displayMessage();
}

/**
 * Interface for lambda function - Lines 272-276
 */
interface ExitProgram{
    void exitProgram();
}

/**
 * The FirstScreen class is responsible for the functionality of the entire screen. In this includes:
 * TableView population
 * ComboBox Populating
 * Viewing appointments by the current month, week, and all.
 * Generating the reports required in the rubric
 * Button Functionality
 * Logging Out/Exiting
 */
public class FirstScreen implements Initializable {
    public TableView customerTable;
    public TableColumn customerID;
    public TableColumn customerName;
    public TableColumn customerAddress;
    public TableColumn customerPostcode;
    public TableColumn customerPhone;
    public TableColumn customerDivision;

    public TableView appointmentTable;
    public TableColumn apptID;
    public TableColumn apptTitle;
    public TableColumn apptDescription;
    public TableColumn apptLocation;
    public TableColumn apptContact;
    public TableColumn apptType;
    public TableColumn apptStart;
    public TableColumn apptEnd;
    public TableColumn apptCustID;
    public TableColumn apptUserID;
    public static CustModel getCustomer;
    public static ApptModel getApptModel;
    public ComboBox contactsCombo;
    public ComboBox typeCombo;
    public ComboBox monthCombo;
    public ComboBox locationCombo;
    public Label countLabel;
    public RadioButton viewAllFX;
    public Label timeLabel;

    /**
 * Method to return the selected appointment model in this screen to the modify appointment page
 */
    public static ApptModel getApptModel() {
        return getApptModel;
    }

    /**
     * Method to return the selected custModel ID in this screen to the modify appointment page
     * @return
     */
    public static CustModel getCustomer() {
        return getCustomer;
    }

    ;

    /**
     * Assigns all the functionality to the add appointment button
     * @param actionEvent
     * @throws IOException
     */
    public void onClickAddAppt(ActionEvent actionEvent) throws IOException {
        getCustomer = (CustModel) customerTable.getSelectionModel().getSelectedItem();
        if (getCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a customer to add an appointment.");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.setTitle("Add Appointment");
            stage.show();
        }

    }

    /**
     * Assigns all the functionality to the update appointment button
     * @param actionEvent
     * @throws IOException
     */
    public void onClickUpdateAppt(ActionEvent actionEvent) throws IOException {
        ApptModel getAppt = (ApptModel) appointmentTable.getSelectionModel().getSelectedItem();
        if (getAppt == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select an appointment.");
            alert.showAndWait();
        } else {
            getApptModel = (ApptModel) appointmentTable.getSelectionModel().getSelectedItem();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/modifyAppointment.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.setTitle("Update Appointment");
            stage.show();
        }
    }

    /**
     * Assigns all the functionality and alerts when trying to delete an appointment.
     * @param actionEvent
     * @throws SQLException
     */
    public void onClickDeleteAppt(ActionEvent actionEvent) throws SQLException {
        ApptModel getAppt = (ApptModel) appointmentTable.getSelectionModel().getSelectedItem();
        if (getAppt == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select an appointment.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to delete appointment ID: " + getAppt.getAppointmentID() + " type: " + getAppt.getType() + "?");
            Optional<ButtonType> clickOk = alert.showAndWait();
            if (clickOk.isPresent() && clickOk.get() == ButtonType.OK) {
                String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.setInt(1, getAppt.getAppointmentID());
                ps.execute();
                Alert alerted = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Deleted.");
                alert.setHeaderText("You have deleted appointment ID: " + getAppt.getAppointmentID() + " type: " + getAppt.getType() + ".");
                alert.showAndWait();
            }
            try {
                appointmentTable.setItems(ApptDatabase.getAllAppointments());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Assigns all the functionality to the add customer button
     * @param actionEvent
     * @throws IOException
     */
    public void onClickAddCust(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.setTitle("Add Customer");
        stage.show();
    }

    /**
     * assigns all the functionality to the update customer button
     * @param actionEvent
     * @throws IOException
     */
    public void onClickUpdateCust(ActionEvent actionEvent) throws IOException {
        getCustomer = (CustModel) customerTable.getSelectionModel().getSelectedItem();
        if (getCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a customer.");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/modifyCustomer.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.setTitle("Update Customer");
            stage.show();
        }
    }

    /**
     * assigns all the functionality and alerts to the delete customer button
     * @param actionEvent
     * @throws SQLException
     */
    public void onClickDeleteCust(ActionEvent actionEvent) throws SQLException {
        CustModel customer = (CustModel) customerTable.getSelectionModel().getSelectedItem();
        if (customer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a customer.");
            alert.showAndWait();
        } else {
            Boolean hasAppointments = false;
            for (ApptModel appts : ApptDatabase.getAllAppointments()) {
                if (appts.getCustomerID() == customer.getId()) {
                    hasAppointments = true;
                }
            }
            if (hasAppointments == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You cannot delete a customer who has appointments. Delete the appointment first.");
                alert.showAndWait();
                hasAppointments = false;
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText("Are you sure you want to delete " + customer.getName() + "?");
                Optional<ButtonType> clickOk = alert.showAndWait();
                if (clickOk.isPresent() && clickOk.get() == ButtonType.OK) {
                    String sql = "DELETE FROM customers WHERE Customer_ID = ?";
                    PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                    ps.setInt(1, customer.getId());
                    ps.execute();
                    Alert alerted = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Customer Deleted.");
                    alert.setHeaderText("You have deleted: " + customer.getName() + ".");
                    alert.showAndWait();
                }
                try {
                    customerTable.setItems(CustDatabase.getAllCustomers());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Lambda Function number 1 - Using the lambda expression to exit the system.
     * @param actionEvent
     */
    public void onClickExit(ActionEvent actionEvent) {
        ExitProgram exitPlease= ()->{
            System.exit(0);
        };
        exitPlease.exitProgram();
    }

    /**
     * the initialize method prepopulates the numerous combo boxes and table views with the appropriate
     * information.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        {

            ObservableList<TypeModel> types = FXCollections.observableArrayList();
            types = TypeDatabase.getAllTypes();
            typeCombo.setItems(types);

            ObservableList<MonthModel> months = FXCollections.observableArrayList();
            months = MonthDatabase.getAllMonths();
            monthCombo.setItems(months);
            try {
                ObservableList<ContactsModel> contacts = FXCollections.observableArrayList();
                contacts = ContactsDatabase.getAllContacts();
                contactsCombo.setItems(contacts);
            }catch (SQLException e){

        }
            try{
                ObservableList<DivisionsModel> divisions = FXCollections.observableArrayList();
                divisions = DivisionsDatabase.getAllDivisions();
                locationCombo.setItems(divisions);
            } catch (SQLException e){

            }


            try {
                appointmentTable.setItems(ApptDatabase.getAllAppointments());

            } catch (SQLException e) {
                e.printStackTrace();
            }

            apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptLocation.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
            apptStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            apptEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            apptContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptCustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            apptUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        }
        try {
            customerTable.setItems(CustDatabase.getAllCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        customerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostcode.setCellValueFactory(new PropertyValueFactory<>("postcode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivision.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

    /**
     * sets the appointment table view to showcase only appointments of the current month
     * @param actionEvent
     */
    public void onClickViewByMonth(ActionEvent actionEvent) {
        appointmentTable.setItems(ApptDatabase.getThisMonth());
        countLabel.setText("This month's appointments");
    }

    /**
     * sets the appointment table to showcase only appointments of the current week.
     * @param actionEvent
     */
    public void onClickViewByWeek(ActionEvent actionEvent) {
        appointmentTable.setItems(ApptDatabase.getThisWeek());
        countLabel.setText("This week's appointments");
    }

    /**
     * Logout button taking the user back to the log in screen
     * @param actionEvent
     * @throws IOException
     */
    public void onClickLogout(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        Optional<ButtonType> clickOk = alert.showAndWait();
        if (clickOk.isPresent() && clickOk.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.setTitle("Log In");
            stage.show();
        }
    }

    /**
     * Showcases all the appointments scheduled in the system
     * @param actionEvent
     * @throws SQLException
     */
    public void onClickViewAll(ActionEvent actionEvent) throws SQLException {
        ObservableList<ApptModel> apptByContact = FXCollections.observableArrayList();
        for (ApptModel appt: ApptDatabase.getAllAppointments()){
            apptByContact.add(appt);
        }
        appointmentTable.setItems(apptByContact);
        countLabel.setText("All Appointments.");
    }

    /**
     * Displays a schedule for the contact being searched for. Also displays a count of appointments.
     * @param actionEvent
     * @throws SQLException
     */
    public void onContactsClick(ActionEvent actionEvent) throws SQLException {
        int count = 0;
        ObservableList<ApptModel> apptByContact = FXCollections.observableArrayList();
        ContactsModel clicked = (ContactsModel) contactsCombo.getSelectionModel().getSelectedItem();
        for (ApptModel appt: ApptDatabase.getAllAppointments()){
            if (clicked.getContactID() == appt.getContactID()){
                apptByContact.add(appt);
                count += 1;
        }
         } appointmentTable.setItems(apptByContact);
            countLabel.setText("Appointment Schedule for: " + clicked.getName() + ". " + count + " appointment(s) total.");
    }

    /**
     * If Month combobox is null, this will show a schedule for the type of appointment selected as well
     * as count. If Month is chosen as well as Type, it will display a schedule for all appointments
     * with the type and month selected as well as a count as per the requirement.
     * @param actionEvent
     * @throws SQLException
     */
    public void onTypeClick(ActionEvent actionEvent) throws SQLException {
        ObservableList<ApptModel> apptByContact = FXCollections.observableArrayList();
        TypeModel clicked = (TypeModel) typeCombo.getSelectionModel().getSelectedItem();
        MonthModel monthClicked = (MonthModel) monthCombo.getSelectionModel().getSelectedItem();
        int count = 0;
        for (ApptModel appt: ApptDatabase.getAllAppointments()){
            if (monthClicked != null){
                if (clicked.getTypeAppt().equals(appt.getType())&& monthClicked.getMonthID() == appt.getStart().getMonthValue()){
                    apptByContact.add(appt);
                    count += 1;
                }
            }else{
                if(clicked.getTypeAppt().equals(appt.getType())){
                    apptByContact.add(appt);
                    count += 1;
                }
            }
        } if(monthClicked == null){
            appointmentTable.setItems(apptByContact);
            countLabel.setText(count + " Appointments with type: " + clicked.getTypeAppt());
        } else {
            appointmentTable.setItems(apptByContact);
            countLabel.setText(count + " Appointments with type: " + clicked.getTypeAppt() + " in the month of " + monthClicked.getMonthName());
        }

    }
    /**
     * If type combobox is null, this will show a schedule for the month selected as well
     * as count. If type is chosen as well as Month, it will display a schedule for all appointments
     * with the type and month selected as well as a count as per the requirement.
     * @param actionEvent
     * @throws SQLException
     */
    public void onMonthClick(ActionEvent actionEvent) throws SQLException {
        ObservableList<ApptModel> apptByContact = FXCollections.observableArrayList();
        TypeModel clicked = (TypeModel) typeCombo.getSelectionModel().getSelectedItem();
        MonthModel monthClicked = (MonthModel) monthCombo.getSelectionModel().getSelectedItem();
        int count = 0;
        for (ApptModel appt: ApptDatabase.getAllAppointments()){
            if (clicked != null){
                if (clicked.getTypeAppt().equals(appt.getType())&& monthClicked.getMonthID() == appt.getStart().getMonthValue()){
                    apptByContact.add(appt);
                    count += 1;
                }
            }else{
                if(monthClicked.getMonthID() == appt.getStart().getMonthValue()){
                    apptByContact.add(appt);
                    count += 1;
                }
            }
        } if(clicked == null){
            appointmentTable.setItems(apptByContact);
            countLabel.setText(count + " Appointments in the month of " + monthClicked.getMonthName());
        } else {
            appointmentTable.setItems(apptByContact);
            countLabel.setText(count + " Appointments with type: " + clicked.getTypeAppt() + " in the month of " + monthClicked.getMonthName());
        }

    }

    /**
     * This will display a schedule based on the location chosen as well as a count.
     *
     * Lambda Expression number two is also here and displays the count textlabel that occurs in the
     * bottom-left of the appointments table when the location report is created.
     * @param actionEvent
     * @throws SQLException
     */
    public void onLocationClick(ActionEvent actionEvent) throws SQLException {
        int count = 0;
        ObservableList<ApptModel> apptByContact = FXCollections.observableArrayList();
        DivisionsModel clicked = (DivisionsModel) locationCombo.getSelectionModel().getSelectedItem();
        for (ApptModel appt: ApptDatabase.getAllAppointments()){
            if (clicked.getId()==appt.getDivisionID()){
                count += 1;
                apptByContact.add(appt);
            }
        } appointmentTable.setItems(apptByContact);
        int finalCount = count;
        CountCheck countCheck = ()->{
            countLabel.setText(finalCount + " appointments scheduled in " + clicked.getName());
        };
        countCheck.displayMessage();

    }
}
