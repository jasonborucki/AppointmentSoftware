package Database;

import Model.ContactsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalTime;

/**
 * The TimeDatabase is not part of the SQL Database. This is a class I created simply to upload
 * every 30 minutes as an option in the select time combobox in the add and modify appointments screens.
 */
public class TimeDatabase {
    /**
     * get all times returns an observable list of times that the user can select in the appropriate combo
     * boxes.
     * @return
     */
    public static ObservableList<LocalTime> getAllTimes() {
        ObservableList<LocalTime> timeObservableList = FXCollections.observableArrayList();
        timeObservableList.add(LocalTime.of(0,0));
        timeObservableList.add(LocalTime.of(0,30));
        timeObservableList.add(LocalTime.of(1,0));
        timeObservableList.add(LocalTime.of(1,30));
        timeObservableList.add(LocalTime.of(2,0));
        timeObservableList.add(LocalTime.of(2,30));
        timeObservableList.add(LocalTime.of(3,0));
        timeObservableList.add(LocalTime.of(3,30));
        timeObservableList.add(LocalTime.of(4,0));
        timeObservableList.add(LocalTime.of(4,30));
        timeObservableList.add(LocalTime.of(5,0));
        timeObservableList.add(LocalTime.of(5,30));
        timeObservableList.add(LocalTime.of(6,0));
        timeObservableList.add(LocalTime.of(6,30));
        timeObservableList.add(LocalTime.of(7,0));
        timeObservableList.add(LocalTime.of(7,30));
        timeObservableList.add(LocalTime.of(8,0));
        timeObservableList.add(LocalTime.of(8,30));
        timeObservableList.add(LocalTime.of(9,0));
        timeObservableList.add(LocalTime.of(9,30));
        timeObservableList.add(LocalTime.of(10,0));
        timeObservableList.add(LocalTime.of(10,30));
        timeObservableList.add(LocalTime.of(11,0));
        timeObservableList.add(LocalTime.of(11,30));
        timeObservableList.add(LocalTime.of(12,0));
        timeObservableList.add(LocalTime.of(12,30));
        timeObservableList.add(LocalTime.of(13,0));
        timeObservableList.add(LocalTime.of(13,30));
        timeObservableList.add(LocalTime.of(14,0));
        timeObservableList.add(LocalTime.of(14,30));
        timeObservableList.add(LocalTime.of(15,0));
        timeObservableList.add(LocalTime.of(15,30));
        timeObservableList.add(LocalTime.of(16,0));
        timeObservableList.add(LocalTime.of(16,30));
        timeObservableList.add(LocalTime.of(17,0));
        timeObservableList.add(LocalTime.of(17,30));
        timeObservableList.add(LocalTime.of(18,0));
        timeObservableList.add(LocalTime.of(18,30));
        timeObservableList.add(LocalTime.of(19,0));
        timeObservableList.add(LocalTime.of(19,30));
        timeObservableList.add(LocalTime.of(20,0));
        timeObservableList.add(LocalTime.of(20,30));
        timeObservableList.add(LocalTime.of(21,0));
        timeObservableList.add(LocalTime.of(21,30));
        timeObservableList.add(LocalTime.of(22,0));
        timeObservableList.add(LocalTime.of(22,30));
        timeObservableList.add(LocalTime.of(23,0));
        timeObservableList.add(LocalTime.of(23,30));

        return timeObservableList;
    }
}
