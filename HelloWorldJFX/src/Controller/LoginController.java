package Controller;
import Database.ApptDatabase;
import Database.UsersDatabase;
import Model.ApptModel;
import Model.UsersModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {


    public PasswordField passwordFX;
    public TextField usernameFX;
    public static String getUser;
    public static int getId;
    public Label timeZoneID;

    public static String getUser(){return getUser;    }
    public static int getUserId(){return getId;    }


    @FXML
    void onExitClick(ActionEvent event) throws IOException {
        System.exit(0);
    }

    public void onLoginClick(ActionEvent actionEvent) throws IOException, SQLException {
        String filename = "Login_activity.txt", item;
        FileWriter loginActivityF = new FileWriter(filename, true);
        PrintWriter loginActivityP = new PrintWriter(loginActivityF);
        String userLogin = usernameFX.getText();
        String userPassword = passwordFX.getText();
        ObservableList<UsersModel> userList = UsersDatabase.getAllUsers();
        Boolean userFound = false;
        for (UsersModel user : userList) {
            if (user.getUsername().equals(userLogin) && user.getPassword().equals(userPassword)) {
                getUser = user.getUsername();
                getId = user.getId();
                userFound = true;
                LocalTime withinFifteen = LocalTime.now();
                Boolean upComingSoon = false;
                for (ApptModel appt : ApptDatabase.getAllAppointments()) {
                    if (appt.getStart().toLocalDate().equals(LocalDate.now())) {
                        if ((appt.getStart().toLocalTime().isAfter(withinFifteen) || appt.getStart().toLocalTime() == withinFifteen) && (appt.getStart().toLocalTime().isBefore(withinFifteen.plusMinutes(16)))) {
                            upComingSoon = true;
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Meeting Soon");
                            alert.setHeaderText("Meeting ID " + appt.getAppointmentID() + " starts at " + appt.getStart().toLocalTime() + " on " + appt.getStart().toLocalDate());
                            alert.showAndWait();
                            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                            Object scene = FXMLLoader.load(getClass().getResource("/view/firstScreen.fxml"));
                            stage.setScene(new Scene((Parent) scene));
                            stage.setTitle("Signed on as: " + user.getUsername());
                            stage.show();
                            ZoneId currZone = ZoneId.systemDefault();
                            LocalDateTime loginTime = LocalDateTime.now();
                            item = loginTime + " "+ currZone +" Login Successful - Username: " + user.getUsername() + " Password: " + user.getPassword();
                            loginActivityP.println((String) item);
                            loginActivityP.close();
                        }
                    }
                }
                if (upComingSoon == false) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("No meetings soon");
                    alert.setHeaderText("There are no upcoming appointments.");
                    alert.showAndWait();
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Object scene = FXMLLoader.load(getClass().getResource("/view/firstScreen.fxml"));
                    stage.setScene(new Scene((Parent) scene));
                    stage.setTitle("Signed on as: " + user.getUsername());
                    stage.show();
                    LocalDateTime loginTime = LocalDateTime.now();
                    ZoneId currZone = ZoneId.systemDefault();
                    item = loginTime + " " + currZone +" Login Successful - Username: " + user.getUsername() + " Password: " + user.getPassword();
                    loginActivityP.println((String) item);
                    loginActivityP.close();

                }
            }
            if (userFound == false) {
                LocalDateTime loginTime = LocalDateTime.now();
                ZoneId currZone = ZoneId.systemDefault();
                item = loginTime + " " + currZone + " Login Unsuccessful - Username: " + usernameFX.getText() + " Password: " + passwordFX.getText();
                loginActivityP.println((String) item);
                loginActivityP.close();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect information");
                alert.setHeaderText("The username or password is incorrect.");
                alert.showAndWait();

            }


        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeZoneID.setText(String.valueOf(ZoneId.systemDefault()));

    }
}




