package Model;

/**
 * The MonthModel was created to interact with the Type Model in the Type/Month report that is required.
 */
public class MonthModel {
    private int monthID;
    private String monthName;

    /**
     * MonthModel constructor
     * @param monthID
     * @param monthName
     */
    public MonthModel(int monthID, String monthName){
        this.monthID = monthID;
        this.monthName = monthName;
    }

    /**
     * get monthID
     * @return
     */
    public int getMonthID() {
        return monthID;
    }
    /**
     * set monthID
     * @return
     */
    public void setMonthID(int monthID) {
        this.monthID = monthID;
    }
    /**
     * get month by name
     * @return
     */
    public String getMonthName() {
        return monthName;
    }
    /**
     * set month by name
     * @return
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
    /**
     * toString method to make the months readable in the combo boxes
     * @return
     */
    @Override
    public String toString(){
        return monthName;
    }
}
