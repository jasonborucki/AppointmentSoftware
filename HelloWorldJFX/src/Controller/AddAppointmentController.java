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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.util.ResourceBundle;

/**
 * the AddAppointmentController class assigns all the functionality to the addAppointment FXML screen.
 *
 */
public class AddAppointmentController implements Initializable {
    public ComboBox EndHourCombo;
    public DatePicker EndDatePicker;
    public ComboBox StartHourCombo;
    public DatePicker StartDatePicker;
    public ComboBox TypeCombo;
    public ComboBox ContactCombo;
    public ComboBox RegionCombo;
    public ComboBox CountryCombo;
    public TextField DescriptionTextfield;
    public TextField titleTextfield;
    public TextField custIDTextfield;
    public TextField userIDTextfield;

    public ApptModel potentialAppointment = new ApptModel(8,"","", 1,"", LocalDateTime.now(), LocalDateTime.now(), 3,3, 1);

    /**
     * Sends the user back to the main screen without changing the appointment
     * @param actionEvent
     * @throws IOException
     */
    public void onClickCancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/firstScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.setTitle("Signed in as: " + LoginController.getUser());
        stage.show();
    }

    /**
     * Saves the appointment information into the database and handles all the alerts that popup when
     * events like appointment overlapping or scheduling appoints outside of business hours occur
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onClickSave(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            String sql = "INSERT INTO appointments VALUES(NULL,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            String title = titleTextfield.getText();
            String description = DescriptionTextfield.getText();
            int customerID = FirstScreen.getCustomer.getId();
            int userID = LoginController.getUserId();
            LocalDate startdate = StartDatePicker.getValue();
            LocalTime starttime = (LocalTime) StartHourCombo.getValue();
            LocalDate enddate = EndDatePicker.getValue();
            LocalTime endtime = (LocalTime) EndHourCombo.getValue();
            LocalDateTime startlocalDateTime = LocalDateTime.from(LocalDateTime.of(startdate, starttime).atZone(ZoneId.systemDefault()));
            LocalDateTime endlocalDateTime = LocalDateTime.from(LocalDateTime.of(enddate, endtime).atZone(ZoneId.systemDefault()));
            int divisionid = potentialAppointment.getDivisionID();
            int contactid = potentialAppointment.getContactID();
            String type = potentialAppointment.getType();
            ZoneId eastern = ZoneId.of("America/New_York");
            Boolean isOverlapping = false;
            Boolean notInTimeFrame = false;
            Boolean differentDays = false;
            ZonedDateTime inEasternBe = ZonedDateTime.of(startlocalDateTime, ZoneId.systemDefault());
            ZonedDateTime inEasternEn = ZonedDateTime.of(endlocalDateTime,ZoneId.systemDefault());
            ZonedDateTime inEasternB = inEasternBe.withZoneSameInstant(eastern);
            ZonedDateTime inEasternE = inEasternEn.withZoneSameInstant(eastern);
            Boolean beforeToday = false;

            if (inEasternBe.isBefore(ZonedDateTime.now(ZoneId.systemDefault()))){
                beforeToday = true;
            }
            if (((inEasternB.getHour()) < 8||(inEasternB.getHour() >= 22))||((inEasternE.getHour() < 8)||(inEasternE.getHour() >22)) || ((inEasternE.getHour()==22 && inEasternE.getMinute()>0))){
                notInTimeFrame = true;
            }
            if (startlocalDateTime.atZone(eastern).getDayOfMonth() != endlocalDateTime.atZone(eastern).getDayOfMonth()){
                differentDays = true;
            }
            for (ApptModel appt: ApptDatabase.getAllAppointments()){
                if (customerID == appt.getCustomerID() && startdate.equals(appt.getStart().toLocalDate())){
                    if(starttime.equals(appt.getStart().toLocalTime()) || endtime.equals(appt.getEnd().toLocalTime()) || (starttime.isBefore(appt.getStart().toLocalTime()))&& endtime.isAfter(appt.getEnd().toLocalTime())){
                        isOverlapping = true;
                    }
                }
            }
            for (ApptModel appt: ApptDatabase.getAllAppointments()){
                if (customerID == appt.getCustomerID() && startdate.equals(appt.getStart().toLocalDate())) {
                    if ((starttime.isAfter(appt.getStart().toLocalTime()) && starttime.isBefore(appt.getEnd().toLocalTime())) || (endtime.isAfter(appt.getStart().toLocalTime()) && endtime.isBefore(appt.getEnd().toLocalTime()))){
                        isOverlapping = true;
                    }
                }
            }
                if(titleTextfield.getText().length() == 0 || DescriptionTextfield.getText().length() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The Title or Description fields must not be empty");
                alert.showAndWait();
            } else if (TypeCombo.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please select an appointment type.");
                alert.showAndWait();

            } else if (RegionCombo.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please select a region");
                alert.showAndWait();
            } else if (ContactCombo.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please select a contact.");
                alert.showAndWait();
            } else if (((LocalTime) StartHourCombo.getValue()).isAfter((LocalTime) EndHourCombo.getValue())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The start time cannot be after the end time.");
                alert.showAndWait();
            } else if(isOverlapping){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Please Reschedule");
                alert.setHeaderText("Customer "+ customerID + " already has an appointment scheduled during this time.");
                alert.showAndWait();
            }else if (notInTimeFrame){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointments can only be scheduled between 8AM and 10PM EST.");
                alert.showAndWait();
            } else if (differentDays){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointments can only be scheduled in a one day time period.");
                alert.showAndWait();
            } else if (beforeToday){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("You can't schedule an appointment in the past.");
                    alert.showAndWait();

                } else {


                ps.setString(1, title);
                ps.setString(2, description);
                ps.setInt(3, divisionid);
                ps.setString(4, type);
                ps.setTimestamp(5, Timestamp.valueOf(startlocalDateTime));
                ps.setTimestamp(6, Timestamp.valueOf(endlocalDateTime));
                ps.setInt(7, customerID);
                ps.setInt(8, userID);
                ps.setInt(9, contactid);
                ps.execute();

                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/view/firstScreen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.setTitle("Signed in as: " + LoginController.getUser());
                stage.show();
            }

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select the date from the calendar.");
            alert.showAndWait();
        }
    }

/**
 * allows select end hour button to work
 */
    public void onSelectEndHour(ActionEvent actionEvent) {
    }
    /**
     * allows select end date button to work
     */
    public void onSelectEndDate(ActionEvent actionEvent) {
    }

    /**
     * allows select start hour button to work
     */
    public void onSelectStartHour(ActionEvent actionEvent) {
    }
    /**
     * allows select start date button to work
     */
    public void onSelectStartDate(ActionEvent actionEvent) {
        //LocalDate selected = StartDatePicker.getValue();
        //potentialAppointment.setType(selected.getTypeAppt());
    }

    /**
     * Assigns the type of appointment to the appointment
     * @param actionEvent
     */
    public void onSelectType(ActionEvent actionEvent) {
        TypeModel selected = (TypeModel) TypeCombo.getSelectionModel().getSelectedItem();
        potentialAppointment.setType(selected.getTypeAppt());
    }

    /**
     * adds the selected contact to the appointment
     * @param actionEvent
     */
    public void onSelectContact(ActionEvent actionEvent) {
        ContactsModel selected = (ContactsModel) ContactCombo.getSelectionModel().getSelectedItem();
        potentialAppointment.setContactID(selected.getContactID());
    }

    public void onSelectRegion(ActionEvent actionEvent) {
        try {
            DivisionsModel selected = (DivisionsModel) RegionCombo.getSelectionModel().getSelectedItem();
            potentialAppointment.setDivisionID(selected.getId());
        } catch (NullPointerException e) {

        }
    }

    /**
     * Sets the locations available based on the country chosen
     * @param actionEvent
     * @throws SQLException
     */
    public void onSelectCountry(ActionEvent actionEvent) throws SQLException {
        ObservableList<DivisionsModel> divisions = FXCollections.observableArrayList();
        CountryModel selected = (CountryModel) CountryCombo.getSelectionModel().getSelectedItem();
        if (selected.getId()==1){
            for(DivisionsModel division: DivisionsDatabase.getAllDivisions()){
                if (division.getId()>=1 && division.getId() <=54){
                    divisions.add(division);
                    RegionCombo.setItems(divisions);
                    if (division.getId()==1){
                        RegionCombo.setValue(division);
                    }
                }
            }
        }if (selected.getId()==3) {
            for (DivisionsModel division : DivisionsDatabase.getAllDivisions()) {
                if (division.getId() > 55 && division.getId() <= 100) {
                    divisions.add(division);
                    RegionCombo.setItems(divisions);
                    if (division.getId()==60){
                        RegionCombo.setValue(division);
                    }
                }
            }
        } if (selected.getId()==2) {
            for (DivisionsModel division : DivisionsDatabase.getAllDivisions()) {
                if (division.getId() > 100) {
                    divisions.add(division);
                    RegionCombo.setItems(divisions);
                    if (division.getId()==101){
                        RegionCombo.setValue(division);
                    }
                }
            }
        }
    }

    /**
     * Initalizes the page with the appropriate combo box values, prompts etc.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custIDTextfield.setText(String.valueOf(FirstScreen.getCustomer().getId()));
        userIDTextfield.setText(String.valueOf(LoginController.getUserId()));
        CountryCombo.setPromptText("Select a Country");
        RegionCombo.setPromptText("Select a Region");
        ContactCombo.setPromptText("Select a Contact");
        TypeCombo.setPromptText("Select a Type");
        ObservableList<CountryModel> countries = FXCollections.observableArrayList();
        ObservableList<ContactsModel> contacts = FXCollections.observableArrayList();

        try {
            countries = CountryDatabase.getAllCountries();
            CountryCombo.setItems(countries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            contacts = ContactsDatabase.getAllContacts();
            ContactCombo.setItems(contacts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<TypeModel> types = TypeDatabase.getAllTypes();
        TypeCombo.setItems(types);
        ObservableList<LocalTime> times = FXCollections.observableArrayList();

        times = TimeDatabase.getAllTimes();

        StartHourCombo.setItems(times);
        EndHourCombo.setItems(times);


    }
}