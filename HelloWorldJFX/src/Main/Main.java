package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The Main class will handle how the program operates when it gets compiled
 */
public class Main extends Application {
    /**
     * The program will introduce the stage that will appear when it is compiled. This will be the login.fxml page
     * which handles the log in screen.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
    }

    /**
     * JBDC makes the connection to the database as it is launched.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        JDBC.makeConnection();
        launch(args);
    }
}
