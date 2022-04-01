package Model;

/**
 * The DivisionsModel handles the first_level_divisions in the SQL Database. This extracts the name
 * and id from each division in order to read it into its various uses in the program.
 */
public class DivisionsModel {
    private int id;
    private String name;

    /**
     * DivisionsModel constructor
     * @param id
     * @param name
     */
    public DivisionsModel(int id, String name){
        this.id = id;
        this.name = name;
    }
    /**
     * get Id
     * @return
     */
    public int getId() {
        return id;
    }
    /**
     * set Id
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
     * Makes divisions readable in its various combo boxes.
     * @return
     */
    @Override
    public String toString(){
        return id + " " + name;
    }
}
