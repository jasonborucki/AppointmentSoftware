package Database;

import Main.JDBC;
import Model.CountryModel;
import Model.CustModel;
import Model.DivisionsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * the DivisionsDatabase reads in information from the first_level_divisions table in the SQL database.
 * This will facilitate the combo boxes loading the divisions where necessary in the program.
 */
public class DivisionsDatabase {
    /**
     * The getAllDivisions method will read in the SQL table into an observable list that will be read
     * into the combo boxes in various controllers.
     * @return
     * @throws SQLException
     */
    public static ObservableList<DivisionsModel> getAllDivisions() throws SQLException {
        ObservableList<DivisionsModel> divisionsModelObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Division_ID, Division from first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                DivisionsModel division = new DivisionsModel(id, name);
                divisionsModelObservableList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return divisionsModelObservableList;
    }

}