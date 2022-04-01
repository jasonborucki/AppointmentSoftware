package Database;

import Main.JDBC;
import Model.CountryModel;
import Model.CustModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The CountryDatabase reads in information from the country table in the database in order to
 * pre-load the combo boxes in the appropriate screens.
 */
public class CountryDatabase {
    /**
     * the getAllCountries method returns an observable list of countries to that the combo boxes can use
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<CountryModel> getAllCountries() throws SQLException {
        ObservableList<CountryModel> countryModelObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Country_ID, Country from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                CountryModel country = new CountryModel(id, name);
                countryModelObservableList.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return countryModelObservableList;
    }

}
