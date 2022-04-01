package Database;

import Main.JDBC;
import Model.ContactsModel;
import Model.CountryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The contactsDatabase class reads in information from the contacts table in the database. This will be used
 * in the contacts combo boxes that are in the Appointments section of the program.
 */
public class ContactsDatabase {
    /**
     * The getAllContacts method returns an observable list of contacts from the database to prepopulate the
     * combo boxes that it is used in.
     * @return
     * @throws SQLException
     */
    public static ObservableList<ContactsModel> getAllContacts() throws SQLException {
        ObservableList<ContactsModel> contactsModelObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Contact_ID, Contact_Name from contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                ContactsModel contact = new ContactsModel(id, name);
                contactsModelObservableList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return contactsModelObservableList;
    }
}
