package Model;

/**
 * The contacts model will interact with the contacts table so it can upload into the combo box
 */
public class ContactsModel {
    private int contactID;
    private String name;

    /**
     * Contacts constructor
     * @param contactID
     * @param name
     */
    public ContactsModel(int contactID, String name){
        this.contactID = contactID;
        this.name = name;
    }

    /**
     * get contact id
     * @return
     */
    public int getContactID() {
        return contactID;
    }
    /**
     * set contact id
     * @return
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    /**
     * get contact name
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * set contact name
     * @return
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Make contacts readable in the combo box.
     * @return
     */
    public String toString(){
        return contactID + " " + name;
    }
}
