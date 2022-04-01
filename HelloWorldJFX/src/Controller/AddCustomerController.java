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
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * The AddCustomerController class implements the functionlity occuring within the AddCustomer FXML layout
 */
public class AddCustomerController implements Initializable {
    public TextField nameTextfield;
    public TextField addressTextfield;
    public TextField postalTextfield;
    public TextField phoneTextfield;
    public ComboBox countryCombo;
    public ComboBox stateCombo;
    CustModel potentialCustomer = new CustModel(8,"Generic","3423423", "23432", "343243", 3);

    /**
     * Saves the information being entered in the various textfields and comboboxes and inserts them
     * into the database.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onSaveClick(ActionEvent actionEvent) throws  SQLException, IOException {
            String sql = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(NULL,?,?,?,?,?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            String newName = nameTextfield.getText();
            String newAddress = addressTextfield.getText();
            String newPostal = postalTextfield.getText();
            String newPhone = phoneTextfield.getText();
            ps.setString(1, newName);
            ps.setString(2, newAddress);
            ps.setString(3, newPostal);
            ps.setString(4, newPhone);
            ps.setInt(5, potentialCustomer.getDivisionID());
            ps.execute();

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/firstScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.setTitle("Signed in as: " + LoginController.getUser());
            stage.show();
        }


    /**
     * Sends the user back to the main screen without adding a user to the database
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
     * Initializes the country combobox with the appropriate information
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
    }

    /**
     * onClickComboCountry fills the first_level_divisions with the appropriate divisions related
     * to the country chosen in the country combo box
     * @param actionEvent
     * @throws SQLException
     */
    public void onClickComboCountry(ActionEvent actionEvent) throws SQLException {
        try {
            ObservableList<DivisionsModel> divisions = FXCollections.observableArrayList();
            CountryModel selected = (CountryModel) countryCombo.getSelectionModel().getSelectedItem();
            if (selected.getId() == 1) {
                for (DivisionsModel division : DivisionsDatabase.getAllDivisions()) {
                    if (division.getId() >= 1 && division.getId() <= 54) {
                        divisions.add(division);
                        stateCombo.setItems(divisions);
                        if (division.getId() == 1) {
                            stateCombo.setValue(division);
                        }
                    }
                }
            }
            if (selected.getId() == 3) {
                for (DivisionsModel division : DivisionsDatabase.getAllDivisions()) {
                    if (division.getId() > 55 && division.getId() <= 100) {
                        divisions.add(division);
                        stateCombo.setItems(divisions);
                        if (division.getId() == 60) {
                            stateCombo.setValue(division);
                        }
                    }
                }
            }
            if (selected.getId() == 2) {
                for (DivisionsModel division : DivisionsDatabase.getAllDivisions()) {
                    if (division.getId() > 100) {
                        divisions.add(division);
                        stateCombo.setItems(divisions);
                        if (division.getId() == 101) {
                            stateCombo.setValue(division);
                        }
                    }
                }
            }
        } catch (NullPointerException e) {

        }
    }

    /**
     * assigns the divisionID to the customer being created.
     * @param actionEvent
     */
    public void onClickComboState(ActionEvent actionEvent) {
        try {
            DivisionsModel selected = (DivisionsModel) stateCombo.getSelectionModel().getSelectedItem();
            potentialCustomer.setDivisionID(selected.getId());
        }catch (NullPointerException e){

        }
    }
}