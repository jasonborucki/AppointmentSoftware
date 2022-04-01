package Database;

import Main.JDBC;
import Model.CustModel;
import Model.UsersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The CustDatabase reads in the customers from the customers table to be used by the customers table
 * in the main screen and to add appointments into the appointments table.
 */
public class CustDatabase {
    /**
     * the getAllCustomers method returns an observable list of customers that will be displayed in the
     * tableview on the main screen controller.
     * @return
     * @throws SQLException
     */
    public static ObservableList<CustModel> getAllCustomers() throws SQLException {
        ObservableList<CustModel> custModelObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postcode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionid = rs.getInt("Division_ID");
                CustModel customer = new CustModel(id, name, address, postcode, phone, divisionid);
                custModelObservableList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return custModelObservableList;
    }
}