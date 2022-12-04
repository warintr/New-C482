package model;

/**
 * Class is for Outsourced parts.
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Method contains the constructor for Outsourced parts.
     * @param id Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Part Stock/Inventory
     * @param min Part Minimum Stock/Inventory
     * @param max Part Maximum Stock/Inventory
     * @param companyName Part source's company name
     */
    public Outsourced (int id, String name, Double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Method sets the part source's company name.
     * @param companyName Part source's company name.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Method called to get company name.
     * @return Returns company name.
     */
    public String getCompanyName(){
        return companyName;
    }
}
