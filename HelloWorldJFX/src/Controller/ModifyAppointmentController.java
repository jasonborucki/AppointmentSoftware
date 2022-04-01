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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/**
 * The ModifyAppointmentController class allows for the functionality of the ModifyAppointment feature in the
 * application.
 */
public class ModifyAppointmentController implements Initializable {
    public TextField apptIDTextfield1;
    public TextField userIDTextfield;
    public ComboBox EndMinuteCombo;
    public ComboBox EndHourCombo;
    public DatePicker EndDatePicker;
    public ComboBox StartMinuteCombo;
    public ComboBox StartHourCombo;
    public DatePicker StartDatePicker;
    public ComboBox TypeCombo;
    public ComboBox ContactCombo;
    public ComboBox RegionCombo;
    public ComboBox CountryCombo;
    public TextField DescriptionTextfield;
    public TextField titleTextfield;
    public TextField custIDTextfield;
    public TypeModel getSelectedTypeModel;
    public TypeModel getSelectedTypeModel(){return getSelectedTypeModel;}
    public DivisionsModel getSelectedRegion;
    public DivisionsModel getSelectedRegion(){return getSelectedRegion;}
    public ContactsModel getSelectedContact;
    public ContactsModel getSelectedContact(){return getSelectedContact;}
    public ApptModel potentialAppointment = new ApptModel(8,"","", 1,"", LocalDateTime.now(), LocalDateTime.now(), 3,3, 1);


    /**
     * The onClickCancel action takes the user back to the FirstScreen without modifying anything in the database
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
     * The onClickSave method will update the appointment in the database. In addition, it checks for numerous
     * exceptions and conditions to ensure proper data is being input and that certain conditions such as
     * appointment overlapping or appointments during non-business hours do not occur.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onClickSave(ActionEvent actionEvent) throws SQLException, IOException {
        try{
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?  WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            String title = titleTextfield.getText();
            String description = DescriptionTextfield.getText();
            int customerID = Integer.parseInt(custIDTextfield.getText());
            int userID = Integer.parseInt(userIDTextfield.getText());
            LocalDate startdate = StartDatePicker.getValue();
            LocalTime starttime = (LocalTime) StartHourCombo.getValue();
            LocalDate enddate = EndDatePicker.getValue();
            LocalTime endtime = (LocalTime) EndHourCombo.getValue();
            LocalDateTime startlocalDateTime = LocalDateTime.from(LocalDateTime.of(startdate, starttime).atZone(ZoneId.systemDefault()));
            LocalDateTime endlocalDateTime = LocalDateTime.from(LocalDateTime.of(enddate, endtime).atZone(ZoneId.systemDefault()));
            int divisionid = FirstScreen.getApptModel().getDivisionID();
            int contactid = FirstScreen.getApptModel().getContactID();
            String type = FirstScreen.getApptModel().getType();
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
                if (customerID == appt.getCustomerID() && startdate.equals(appt.getStart().toLocalDate()) && FirstScreen.getApptModel().getAppointmentID() != appt.getAppointmentID()){
                    if(starttime.equals(appt.getStart().toLocalTime()) || endtime.equals(appt.getEnd().toLocalTime()) || (starttime.isBefore(appt.getStart().toLocalTime()))&& endtime.isAfter(appt.getEnd().toLocalTime())){
                        isOverlapping = true;
                    }
                }
            }
            for (ApptModel appt: ApptDatabase.getAllAppointments()){
                if (customerID == appt.getCustomerID() && startdate.equals(appt.getStart().toLocalDate())&& FirstScreen.getApptModel().getAppointmentID() != appt.getAppointmentID()) {
                    if ((starttime.isAfter(appt.getStart().toLocalTime()) && starttime.isBefore(appt.getEnd().toLocalTime())) || (endtime.isAfter(appt.getStart().toLocalTime()) && endtime.isBefore(appt.getEnd().toLocalTime()))){
                        isOverlapping = true;
                    }
                }
            }

            int apptID = Integer.parseInt(apptIDTextfield1.getText());
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
            }else if (beforeToday == true){
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
                ps.setInt(10, apptID);
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
            alert.setHeaderText("Please make sure the Customer and User IDs are accurate.");
            alert.showAndWait();

            }



    }

    /**
     * ensures functionality for selecting end time
     * @param actionEvent
     */
    public void onSelectEndHour(ActionEvent actionEvent) {
    }
    /**
     * ensures functionality for selecting end date
     * @param actionEvent
     */
    public void onSelectEndDate(ActionEvent actionEvent) {
    }
    /**
     * ensures functionality for selecting start time
     * @param actionEvent
     */
    public void onSelectStartHour(ActionEvent actionEvent) {
    }
    /**
     * ensures functionality for selecting start ddate
     * @param actionEvent
     */
    public void onSelectStartDate(ActionEvent actionEvent) {
    }

    /**
     * Assigns the type to the appointment being edited
     * @param actionEvent
     */
    public void onSelectType(ActionEvent actionEvent) {
        getSelectedTypeModel = (TypeModel) TypeCombo.getSelectionModel().getSelectedItem();
        FirstScreen.getApptModel().setType(getSelectedTypeModel.getTypeAppt());

    }
    /**
     * assigns contact to the appointment being modified
     * @param actionEvent
     */
    public void onSelectContact(ActionEvent actionEvent) {
        getSelectedContact = (ContactsModel) ContactCombo.getSelectionModel().getSelectedItem();
        FirstScreen.getApptModel().setContactID(getSelectedContact.getContactID());
    }

    /**
     * Assigns a location to the screen being modified.
     * @param actionEvent
     */
    public void onSelectRegion(ActionEvent actionEvent) {
        try {
            getSelectedRegion = (DivisionsModel) RegionCombo.getSelectionModel().getSelectedItem();
            FirstScreen.getApptModel().setDivisionID(getSelectedRegion.getId());

        } catch (NullPointerException e){

        }

    }

    /**
     * populates the region combo boxes with the correct information.
     * @param actionEvent
     * @throws SQLException
     */
    public void onSelectCountry(ActionEvent actionEvent) throws SQLException {
        try{
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
        } catch (NullPointerException e){

        }
        }


    /**
     * Initializes the screen with the appropriate appointment information and pre-populated combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<CountryModel> countries = FXCollections.observableArrayList();

        try {
            countries = CountryDatabase.getAllCountries();
            CountryCombo.setItems(countries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<ContactsModel> contact = FXCollections.observableArrayList();

        try {
            contact = ContactsDatabase.getAllContacts();
            ContactCombo.setItems(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<TypeModel> type = FXCollections.observableArrayList();
        type = TypeDatabase.getAllTypes();
        TypeCombo.setItems(type);

        ObservableList<LocalTime> localTimes = FXCollections.observableArrayList();
        localTimes = TimeDatabase.getAllTimes();
        StartHourCombo.setItems(localTimes);
        EndHourCombo.setItems(localTimes);

        ApptModel selected = FirstScreen.getApptModel();
        StartDatePicker.setValue(selected.getStart().toLocalDate());
        EndDatePicker.setValue(selected.getEnd().toLocalDate());
        StartHourCombo.setValue(selected.getStart().toLocalTime());
        EndHourCombo.setValue(selected.getEnd().toLocalTime());
        apptIDTextfield1.setText(String.valueOf(selected.getAppointmentID()));
        titleTextfield.setText(selected.getTitle());
        DescriptionTextfield.setText(selected.getDescription());
        userIDTextfield.setText(String.valueOf(selected.getUserID()));
        custIDTextfield.setText(String.valueOf(selected.getCustomerID()));
        try{
            for (ContactsModel contacts: ContactsDatabase.getAllContacts()){
                if (contacts.getContactID() == selected.getContactID()){
                    ContactCombo.setValue(contacts.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (TypeModel types: TypeDatabase.getAllTypes()){
            if (types.getTypeAppt().equals(selected.getType())){
                TypeCombo.setValue(selected.getType());
            }
        }




        if (selected.getDivisionID() >= 1 && selected.getDivisionID() <=54){
            int divisionid = selected.getDivisionID();
            try {
                for (DivisionsModel division: DivisionsDatabase.getAllDivisions()){
                    if (division.getId() == divisionid){
                        RegionCombo.setValue(division.toString());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } if (selected.getDivisionID() >= 1 && selected.getDivisionID() <=54){
            int divisionid = selected.getDivisionID();
            int countryid = 1;
            try {
                for (DivisionsModel division: DivisionsDatabase.getAllDivisions()){
                    if (division.getId() == divisionid){
                        RegionCombo.setValue(division.toString());
                    }
                }
                for (CountryModel country: CountryDatabase.getAllCountries()){
                    if (country.getId() == countryid){
                        CountryCombo.setValue(country.toString());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (selected.getDivisionID() >= 1 && selected.getDivisionID() <=54){
            int divisionid = selected.getDivisionID();
            try {
                for (DivisionsModel division: DivisionsDatabase.getAllDivisions()){
                    if (division.getId() == divisionid){
                        RegionCombo.setValue(division.toString());
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } if (selected.getDivisionID() >= 54 && selected.getDivisionID() <=99){
            int divisionid = selected.getDivisionID();
            int countryid = 3;
            try {
                for (DivisionsModel division: DivisionsDatabase.getAllDivisions()){
                    if (division.getId() == divisionid){
                        RegionCombo.setValue(division.toString());
                    }
                }
                for (CountryModel country: CountryDatabase.getAllCountries()){
                    if (country.getId() == countryid){
                        CountryCombo.setValue(country.toString());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (selected.getDivisionID() >= 100){
            int divisionid = selected.getDivisionID();
            int countryid = 2;
            try {
                for (DivisionsModel division: DivisionsDatabase.getAllDivisions()){
                    if (division.getId() == divisionid){
                        RegionCombo.setValue(division.toString());
                    }
                }
                for (CountryModel country: CountryDatabase.getAllCountries()){
                    if (country.getId() == countryid){
                        CountryCombo.setValue(country.toString());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
