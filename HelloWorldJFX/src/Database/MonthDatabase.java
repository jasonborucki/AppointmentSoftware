package Database;


import Model.MonthModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * MonthDatabase class is not a part of the SQL database. It is a method of uploading an id and month name
 * to select months in the Type/Month report required in the rubric.
 */

public class MonthDatabase {
    /**
     * getAllMonths returns an observable list of months that the month combo box can use in the reports area
     * of the main screen.
     * @return
     */
    public static ObservableList<MonthModel> getAllMonths() {
        ObservableList<MonthModel> monthsObservableList = FXCollections.observableArrayList();
        MonthModel january = new MonthModel(1, "JANUARY");
        MonthModel february = new MonthModel(2, "FEBRUARY");
        MonthModel march = new MonthModel(3, "MARCH");
        MonthModel april = new MonthModel(4, "APRIL");
        MonthModel may = new MonthModel(5, "MAY");
        MonthModel june = new MonthModel(6, "JUNE");
        MonthModel july = new MonthModel(7, "JULY");
        MonthModel august = new MonthModel(8, "AUGUST");
        MonthModel september = new MonthModel(9, "SEPTEMBER");
        MonthModel october = new MonthModel(10, "OCTOBER");
        MonthModel november = new MonthModel(11, "NOVEMBER");
        MonthModel december = new MonthModel(12, "DECEMBER");

        monthsObservableList.add(january);
        monthsObservableList.add(february);
        monthsObservableList.add(march);
        monthsObservableList.add(april);
        monthsObservableList.add(may);
        monthsObservableList.add(june);
        monthsObservableList.add(july);
        monthsObservableList.add(august);
        monthsObservableList.add(september);
        monthsObservableList.add(october);
        monthsObservableList.add(november);
        monthsObservableList.add(december);

        return monthsObservableList;
    }
}
