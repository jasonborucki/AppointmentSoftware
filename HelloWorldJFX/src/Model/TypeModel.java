package Model;

/**
 * The TypeModel class provides the constructors for the Types that will be input into the Combo Box
 * in the Add/Update Appointments screens as well as the Type/Month report that is required.
 */
public class TypeModel {
    private int typeID;
    private String typeAppt;

    /**
     * TypeModel Constructor
     * @param typeID
     * @param typeAppt
     */
    public TypeModel(int typeID, String typeAppt){
        this.typeID = typeID;
        this.typeAppt = typeAppt;
    }

    /**
     * get TypeID
     * @return
     */
    public int getTypeID() {
        return typeID;
    }
    /**
     * set TypeID
     * @return
     */
    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }
    /**
     * get Type by Name
     * @return
     */
    public String getTypeAppt() {
        return typeAppt;
    }
    /**
     * set Type by Name
     * @return
     */
    public void setTypeAppt(String typeAppt) {
        this.typeAppt = typeAppt;
    }
    /**
     * makes the types readable in the combo boxes.
     * @return
     */
    public String toString(){
        return typeAppt;
    }

}
