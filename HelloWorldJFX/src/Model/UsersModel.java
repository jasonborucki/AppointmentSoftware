package Model;

/**
 * UsersModel class provides the constructors that will be used for the users
 * logging into the program. This will interact with the UsersDatabase which will provide valid
 * usernames and passwords to enter the program.
 */
public class UsersModel {
    private int id;
    private String username;
    private String password;

    /**
     * UsersModel Constructor
     * @param id
     * @param username
     * @param password
     */
    public UsersModel(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
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
     * get Username
     * @return
     */
    public String getUsername() {
        return username;
    }
    /**
     * set username
     * @return
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * get password
     * @return
     */
    public String getPassword() {
        return password;
    }
    /**
     * set password
     * @return
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
