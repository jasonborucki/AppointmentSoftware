package Database;

import Model.ContactsModel;
import Model.TypeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * The Type is not a table in the database. The word database was used as a naming convention for me
 * to keep more organized since the concept is the same. I created an observable list of types based
 * off the TypeModel class, and uploaded a few types to test in the program. The TypeDatabase class
 * reads these TypeModels into the appropriate combo boxes.
 */
public class TypeDatabase {
    /**
     * getAllTypes returns the observable list of types to set items into combo box.
     * @return
     */
    public static ObservableList<TypeModel> getAllTypes() {
        ObservableList<TypeModel> typeModelObservableList = FXCollections.observableArrayList();
        typeModelObservableList.add(new TypeModel(1, "Consultation"));
        typeModelObservableList.add(new TypeModel(2, "Check Up"));
        typeModelObservableList.add(new TypeModel(3, "Surgery"));
        typeModelObservableList.add(new TypeModel(4, "Post Surgery"));

        return typeModelObservableList;
    }
}
