package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class is model for Products
 */
public class Product {
    private ObservableList<model.Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private Double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Method contains constructor for Products
     * @param id Product ID
     * @param name Product name
     * @param price Product Price
     * @param stock Product stock/inventory
     * @param min product stock/inventory minimum
     * @param max Product stock/inventory maximum
     */
    public Product(int id, String name, Double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Method called to set product ID.
     * @param id Product ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method called to set product name.
     * @param name Product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method called to set product price
     * @param price Product Price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * method called to set product stock/inventory.
     * @param stock Product stock/inventory.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Method called to set product stock/inventory minimum.
     * @param min Product stock/inventory minimum
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Method called to set product stock/inventory maximum.
     * @param max Product stock/inventory maximum.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Method gets product ID.
     * @return Returns product ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Method gets product name.
     * @return Return product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Method gets product price.
     * @return Returns product price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Method gets product stock/inventory.
     * @return Returns product stock/inventory.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Method gets product stock/inventory minimum.
     * @return Returns product stock/inventory minimum.
     */
    public int getMin() {
        return min;
    }

    /**
     * Method gets product stock/inventory maximum.
     * @return Returns product stock/inventory maximum
     */
    public int getMax() {
        return max;
    }

    /**
     * Method called when user associates a part to the product.
     * @param part Part associated
     */
    public void addAssociatedPart(model.Part part){
        associatedParts.add(part);
    }

    /**
     * Method called when user deletes a part from the product.
     * @param selectedAssociatedPart Selected part to delete.
     * @return Returns status of removal.
     */
    public boolean deleteAssociatedPart(model.Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Method called to populate associated parts table - ensures products are not deleted if parts are associated.
     * @return Returns ObservableList of associated parts.
     */
    public ObservableList<model.Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
