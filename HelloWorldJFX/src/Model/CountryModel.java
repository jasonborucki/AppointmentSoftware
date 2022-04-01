package Model;

import Database.DivisionsDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * The Country Model provides the basis for the first_level_regions to display in the combo boxes in the
 * various views. This works with the country table in the SQL database.
 */

public class CountryModel {
    private ObservableList<DivisionsModel> countryDivisions = FXCollections.observableArrayList();
    private int id;
    private String country;

    /**
     * Constructor for the Country Model
     * @param id
     * @param country
     */
    public CountryModel(int id, String country){
        this.id = id;
        this.country = country;

    }

    /**
     * get Country ID
     * @return
     */
    public int getId() {
        return id;
    }
    /**
     * set Country ID
     * @return
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * get Country name
     * @return
     */
    public String getCountry() {
        return country;
    }
    /**
     * set Country name
     * @return
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * This will provide the proper divisions to add into the combo box once a country is selected
     * in the country combo box.
     */
    public void addDivision(DivisionsModel division) throws SQLException {
        if (this.getId() == 1){
            for(DivisionsModel countryDivision: DivisionsDatabase.getAllDivisions()){
                if (countryDivision.getId()>=1 && countryDivision.getId() <= 54){
                    countryDivisions.add(countryDivision);
                }
            }
        } else if (this.getId() == 2){
            for(DivisionsModel countryDivision: DivisionsDatabase.getAllDivisions()){
                if (countryDivision.getId()>54 && countryDivision.getId() <= 72){
                    countryDivisions.add(countryDivision);
                }
            }
        } else {
            for(DivisionsModel countryDivision: DivisionsDatabase.getAllDivisions()){
                if (countryDivision.getId()>73){
                    countryDivisions.add(countryDivision);
                }
            }
    }
    }

    /**
     * The toString method makes the countries readable in the combo boxes
     * @return
     */
    @Override
    public String toString(){
        return id + " " + country;
    }

    /**
     * returns the country's first level divisions
     * @return
     */
    public ObservableList<DivisionsModel> getCountryDivisions(){ return countryDivisions; }
}
