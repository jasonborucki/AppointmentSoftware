package Model;

/**
 * The CustModel class provides the constructor for the customer so the customers can be read in and
 * out of the database as they are added, updated, and deleted.
 */
public class CustModel {
    private int id;
    private String name;
    private String address;
    private String postcode;
    private String phone;
    private int divisionID;

    /**
     * Constructor for customer
     * @param id
     * @param name
     * @param address
     * @param postcode
     * @param phone
     * @param divisionID
     */
    public CustModel(int id, String name, String address, String postcode, String phone, int divisionID){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postcode = postcode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * get ID
     * @return
     */
    public int getId() {
        return id;
    }
    /**
     * set ID
     * @return
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * get name
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * set name
     * @return
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * get address
     * @return
     */
    public String getAddress() {
        return address;
    }
    /**
     * set address
     * @return
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * get postcode
     * @return
     */
    public String getPostcode() {
        return postcode;
    }
    /**
     * set postcode
     * @return
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    /**
     * get phone number
     * @return
     */
    public String getPhone() {
        return phone;
    }
    /**
     * set phone number
     * @return
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * get divisionID
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     * set divisionID
     * @return
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
