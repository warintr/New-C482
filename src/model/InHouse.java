package model;

/**
 * Class is for In House parts
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * Constructor for In-House parts
     * @param id ID of part
     * @param name Name of part
     * @param price Price of part
     * @param stock Part stock count
     * @param min Minimum stock count
     * @param max Maximum stock count
     * @param machineId Machine ID of part
     */
    public InHouse(int id, String name, Double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Method called to set Machine ID
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Method called to get Machine ID
     * @return Returns Machine ID
     */
    public int getMachineId() {
        return machineId;
    }
}
