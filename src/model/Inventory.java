package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class is for Inventory
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int nextPartId = 1;

    private static int nextProductId = 1;

    private static Part selectedPart = null;

    private static Product selectedProduct = null;

    /**
     * Method called when user saves a new part - adds part to inventory.
     * @param newPart Part added to inventory
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Method called when user saves a new product - adds product to inventory.
     * @param newProduct Product added to inventory.
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Method called when user types in the search part field. - Looks for part by part ID.
     * @param partId Part ID to search
     * @return Returns part(s) found.
     */
    public static Part lookupPart(int partId){
        Part tempPart = null;
        ObservableList<Part> tempAllParts = Inventory.getAllParts();
        for (Part part : tempAllParts){
            if (part.getId() == partId){
                tempPart = part;
            }
        }
        return tempPart;
    }

    /**
     * Method called when user types in the search product field. - Looks for product by product ID.
     * @param productId Product ID to search.
     * @return Returns product(s) found.
     */
    public static Product lookupProduct(int productId){
        Product tempProduct = null;
        ObservableList<Product> tempAllProducts = Inventory.getAllProducts();
        for (Product product : tempAllProducts){
            if (product.getId() == productId){
                tempProduct = product;
            }
        }
        return tempProduct;
    }

    /**
     * Method called when user types in the search part field. - Looks for part by part name.
     * @param partName Part name to search.
     * @return Returns part(s) found.
     */

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> tempList = FXCollections.observableArrayList();
        ObservableList<Part> tempAllParts = Inventory.getAllParts();
        for (Part part : tempAllParts){
            if (part.getName().contains(partName)){
                tempList.add(part);
            }
        }
        return tempList;
    }

    /**
     * Method called when user types in the search product field. - Looks for product by product name.
     * @param productName Product name to search.
     * @return Returns product(s) found.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> tempList = FXCollections.observableArrayList();
        ObservableList<Product> tempAllProducts = Inventory.getAllProducts();
        for (Product product : tempAllProducts){
            if (product.getName().contains(productName)){
                tempList.add(product);
            }
        }
        return tempList;
    }

    /**
     * Method called when user saves a modified part.
     * @param index Index of part to be modified.
     * @param selectedPart Part selected to be modified.
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * Method called when user saves a modified product.
     * @param index Index of product to be modified.
     * @param newProduct Part selected to be modified.
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * Method called when user deletes a part.
     * @param selectedPart Part to be deleted.
     * @return Returns status of delete.
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * Method called when user deletes a product.
     * @param selectedProduct Product to be deleted.
     * @return Returns status of delete.
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**
     * Method called to populate parts table.
     * @return Returns ObservableList of parts.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Method called to populate products table.
     * @return Returns ObservableList of products.
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * Method called by the modify part page. - Returns the selected part in parts table to be modified.
     * @return Returns selected part to be modified
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /**
     * Method called to set static variable, selectedPart, to the selected part in the parts table.
     * Part will be passed to the Modify Part page.
     * @param selectedPart Part selected in parts table.
     */
    public static void setSelectedPart(Part selectedPart) {
        Inventory.selectedPart = selectedPart;
    }

    /**
     * Method called by the modify product page. - Returns the selected product in products table to be modified.
     * @return Returns selected product to be modified
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    /**
     * Method called to set static variable, selectedProduct, to the selected product in the products table.
     * Part will be passed to the Modify Product page.
     * @param selectedProduct Product selected in products table
     */
    public static void setSelectedProduct(Product selectedProduct){
        Inventory.selectedProduct = selectedProduct;
    }

    /**
     * Method called when user saves a new part. - nextPartID tracks the next sequential part ID.
     * @return Returns next part ID
     */
    public static int getNextPartId(){
        for (Part i: allParts){
            if (i.getId() == nextPartId){
                nextPartId++;
            }
        }
        return nextPartId++;
    }

    /**
     * Method called when user saves a new product. - nextProductID tracks the next sequential product ID.
     * @return Returns next product ID
     */
    public static int getNextProductId(){
        for (Product i: allProducts){
            if (i.getId() == nextProductId){
                nextProductId++;
            }
        }
        return nextProductId++;
    }

}
