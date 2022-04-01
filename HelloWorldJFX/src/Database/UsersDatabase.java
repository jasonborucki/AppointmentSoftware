package Database;

import Main.JDBC;
import Model.UsersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The UsersDatabase class creates the environment where all of the database and observable list connections
 * will be made in the program.
 */
public class UsersDatabase {
    /**
     * The getAllUsers method establishes an observable list to read in
     * users in the users table of the database. This will allow for the proper usernames and passwords
     * to function.
     *
     * @return
     */
    public static ObservableList<UsersModel> getAllUsers() {
        ObservableList<UsersModel> usersModelObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userid = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                UsersModel user = new UsersModel(userid, username, password);
                usersModelObservableList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersModelObservableList;
    }
}

