package Controller;

import Database.CountryDatabase;
import Database.DivisionsDatabase;
import Main.JDBC;
import Model.CountryModel;
import Model.CustModel;
import Model.DivisionsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The ModifyCustomerController implements the necessary functionality for the purpose of the
 * Update Customer Screen. It is responsible for the handling of all the textfields, combo boxes,
 exception handling, aletrs, etc.
 */

/**
 * Initializes variables
 */
public class ModifyCustomerController implements Initializable {
    public TextField nameTextfield;
    public TextField addressTextfield;
    public TextField postalTextfield;
    public TextField phoneTextfield;
    public ComboBox countryCombo;
    public ComboBox stateCombo;
    public TextField custIDTxt;

    /**
     * onSaveClick allows the data in the textfields to be saves and updated into the SQL database
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onSaveClick(ActionEvent actionEvent) throws SQLException, IOException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        String newName = nameTextfield.getText();
        String newAddress = addressTextfield.getText();
        String newPostal = postalTextfield.getText();
        String newPhone = phoneTextfield.getText();
        ps.setString(1, newName);
        ps.setString(2, newAddress);
        ps.setString(3, newPostal);
        ps.setString(4, newPhone);
        ps.setInt(5, FirstScreen.getCustomer.getDivisionID());
        ps.setInt(6, FirstScreen.getCustomer().getId());
        ps.execute();

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/firstScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.setTitle("Signed in as: " + LoginController.getUser());
        stage.show();
    }

    /**
     * onCancelClick returns you back to the main screen without saving edits of the customer
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/firstScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.setTitle("Signed in as: " + LoginController.getUser());
        stage.show();
    }

    /**
     * onClickCountryCombo fills the first_level_divisions combo with its appropriate values relevent
     * to the country that is clicked.
     * @param actionEvent
     * @throws SQLException
     */
    public void onClickComboCountry(ActionEvent actionEvent) throws SQLException {
        ObservableList<DivisionsModel> divisions = FXCollections.observableArrayList();
        CountryModel selected = (CountryModel) countryCombo.getSelectionModel().getSelectedItem();
        if (selected.getId()==1){
            for(DivisionsModel division: DivisionsDatabase.getAllDivisions()){
                if (division.getId()>=1 && division.getId() <=54){
                    divisions.add(division);
                    stateCombo.setItems(divisions);
                    if (division.getId()==1){
                        stateCombo.setValue(division);
                    }
                }
            }
        }if (selected.getId()==3) {
            for (DivisionsModel division : DivisionsDatabase.getAllDivisions()) {
                if (division.getId() > 55 && division.getId() <= 100) {
                    divisions.add(division);
                    stateCombo.setItems(divisions);
                    if (division.getId()==60){
                        stateCombo.setValue(division);
                    }
                }
            }
        } if (selected.getId()==2) {
            for (DivisionsModel division : DivisionsDatabase.getAllDivisions()) {
                if (division.getId() > 100) {
                    divisions.add(division);
                    stateCombo.setItems(divisions);
                    if (division.getId()==101){
                        stateCombo.setValue(division);
                    }
                }
            }
        }
    }

    /**
     * onClickComboState assigns the divisionID to the customer being modified
     * @param actionEvent
     */
    public void onClickComboState(ActionEvent actionEvent) {
        try{
            DivisionsModel selected = (DivisionsModel) stateCombo.getSelectionModel().getSelectedItem();
            FirstScreen.getCustomer().setDivisionID(selected.getId());
        } catch (NullPointerException e){

        }



    }

    /**
     * initialize sets up the modifyCustomerController with the appropriate customer information,
     * prompts, and pre-populated combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<CountryModel> countries = FXCollections.observableArrayList();

        try {
            countries = CountryDatabase.getAllCountries();
            countryCombo.setItems(countries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CustModel selected = FirstScreen.getCustomer();
        nameTextfield.setText(selected.getName());
        addressTextfield.setText(selected.getAddress());
        postalTextfield.setText(selected.getPostcode());
        phoneTextfield.setText(selected.getPhone());

        custIDTxt.setText(String.valueOf(FirstScreen.getCustomer().getId()));

        if (selected.getDivisionID() >= 1 && selected.getDivisionID() <=54){
            int divisionid = selected.getDivisionID();
            int countryid = 1;
            try {
                for (DivisionsModel division: DivisionsDatabase.getAllDivisions()){
                    if (division.getId() == divisionid){
                        stateCombo.setValue(division.toString());
                    }
                }
                for (CountryModel country: CountryDatabase.getAllCountries()){
                    if (country.getId() == countryid){
                        countryCombo.setValue(country.toString());
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
                        stateCombo.setValue(division.toString());
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
                        stateCombo.setValue(division.toString());
                    }
                }
                for (CountryModel country: CountryDatabase.getAllCountries()){
                    if (country.getId() == countryid){
                        countryCombo.setValue(country.toString());
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
                        stateCombo.setValue(division.toString());
                    }
                }
                for (CountryModel country: CountryDatabase.getAllCountries()){
                    if (country.getId() == countryid){
                        countryCombo.setValue(country.toString());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
