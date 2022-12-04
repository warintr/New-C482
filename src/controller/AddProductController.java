package controller;

import model.Inventory;
import model.Part;
import model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the Add Product page.
 */
public class AddProductController implements Initializable {

    ObservableList<Part> newAssociatedParts = FXCollections.observableArrayList();
    Product product;

    @FXML
    public TextField addProductID;

    @FXML
    public TextField addProductName;

    @FXML
    public TextField addProductInventory;

    @FXML
    public TextField addProductPrice;

    @FXML
    public TextField addProductMax;

    @FXML
    public TextField addProductMin;

    @FXML
    public Label labelPartSearchResult;

    @FXML
    public TextField searchPart;

    @FXML
    public TableView tableParts;

    @FXML
    public TableColumn colPartId;

    @FXML
    public TableColumn colPartName;

    @FXML
    public TableColumn colPartInventory;

    @FXML
    public TableColumn colPartPrice;

    @FXML
    public Button addProductPart;

    @FXML
    public TableView tableProductParts;

    @FXML
    public TableColumn colProductPartId;

    @FXML
    public TableColumn colProductPartName;

    @FXML
    public TableColumn colProductPartInventory;

    @FXML
    public TableColumn colProductPartPrice;

    @FXML
    public Button removeProductPart;

    @FXML
    public Button saveAddProduct;

    @FXML
    public Button cancelAddProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        colPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableParts.setItems(Inventory.getAllParts());

        colProductPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProductPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableProductParts.setItems(newAssociatedParts);
    }

    /**
     * Method returns user to the Inventory Management System page
     *
     * @param actionEvent Button click event
     * @throws IOException Incorrect/Invalid Input
     */
    public void returnToMain(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/InventoryManagementSystem.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1024, 400);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * Method called when user clicks the Save button and validates for missing required fields and invalid values.
     * @param actionEvent Save button click event.
     * @throws IOException Incorrect input
     */
    public void saveNewProduct(ActionEvent actionEvent) throws IOException {
        int newProductId = Inventory.getNextProductId();
        String newProductName = addProductName.getText();
        int newProductInventory;
        double newProductPrice;
        int newProductMax;
        int newProductMin;

        //Checks for valid text in name field
        if (newProductName.equals("") || newProductName == null) {
            Alert productNameAlert = new Alert(Alert.AlertType.ERROR);
            productNameAlert.setHeaderText("Product Name is a required field.");
            productNameAlert.showAndWait();
            return;
        }

        //Checks if inventory fields have valid integers.
        try {
            newProductInventory = Integer.parseInt(addProductInventory.getText());
            newProductMax = Integer.parseInt(addProductMax.getText());
            newProductMin = Integer.parseInt(addProductMin.getText());
        } catch (NumberFormatException e) {
            Alert inventoryAlert = new Alert(Alert.AlertType.ERROR);
            inventoryAlert.setHeaderText("Inventory fields should be whole numbers.");
            inventoryAlert.showAndWait();
            return;
        }

        //Checks price field for valid double.
        try {
            newProductPrice = Double.parseDouble(addProductPrice.getText());
        } catch (NumberFormatException e) {
            Alert priceAlert = new Alert(Alert.AlertType.ERROR);
            priceAlert.setHeaderText("Price field should be a number");
            priceAlert.showAndWait();
            return;
        }

        //Checks for negative numbers in inventory and price fields.
        if (newProductInventory < 0 || newProductPrice < 0 || newProductMax < 0 || newProductMin < 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Only non-negative numbers allowed");
            errorAlert.showAndWait();
            return;
        }

        //Checks if Min is greater than Max
        if (newProductMin > newProductMax) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Minimum cannot be greater than Maximum");
            errorAlert.showAndWait();
            return;
        }

        //Add new product to inventory
        product = new Product(newProductId, newProductName, newProductPrice, newProductInventory, newProductMin, newProductMax);
        Inventory.addProduct(product);
        for (Part i : newAssociatedParts) {
            product.addAssociatedPart(i);
        }

        //Returns to Inventory Management System page.
        returnToMain(actionEvent);
    }

    /**
     * Method called when the Add button is clicked.  Also validates that there are parts to associate and that one is selected.
     * @param actionEvent Add button click event.
     */
    public void addPartToProduct(ActionEvent actionEvent){
        Part selectedPart = (Part) tableParts.getSelectionModel().getSelectedItem();
        if (Inventory.getAllParts().size() == 0){
            Alert selectPartAlert = new Alert(Alert.AlertType.ERROR);
            selectPartAlert.setHeaderText("No parts to associate.");
            selectPartAlert.showAndWait();
            return;
        }   else if (selectedPart == null) {
            Alert selectPartAlert = new Alert(Alert.AlertType.ERROR);
            selectPartAlert.setHeaderText("Part must be selected.");
            selectPartAlert.showAndWait();
            return;
        }   else{
            newAssociatedParts.add(selectedPart);
        }

    }

    /**
     * Method called when Remove Associated Part button is clicked.  Also validates that there are parts to remove and that one is selected.
     * Also prompts for confirmation before removing part.
     * @param actionEvent Remove Associated Part button click event
     */
    public void removePartFromProduct(ActionEvent actionEvent){
        Part selectedPart = (Part) tableProductParts.getSelectionModel().getSelectedItem();
        if(newAssociatedParts.size() == 0){
            Alert selectPartAlert = new Alert(Alert.AlertType.ERROR);
            selectPartAlert.setHeaderText("No parts to remove.");
            selectPartAlert.showAndWait();
            return;
        }   else if (selectedPart == null) {
            Alert selectPartAlert = new Alert(Alert.AlertType.ERROR);
            selectPartAlert.setHeaderText("Part must be selected.");
            selectPartAlert.showAndWait();
            return;
        }   else{
            Alert confirmRemove = new Alert(Alert.AlertType.CONFIRMATION);
            confirmRemove.setHeaderText("Selected part will be removed from the product.");
            confirmRemove.setContentText("Click OK to proceed.");
            confirmRemove.showAndWait();
            if(confirmRemove.getResult() == ButtonType.CANCEL){
                return;
            }
            newAssociatedParts.remove(selectedPart);
        }
    }

    /**
     * Method called when using part search text field.  Displays the parts that match the text and displays the number of results found.
     * @param keyEvent
     */
    public void partSearch(KeyEvent keyEvent){
        ObservableList<Part> parts = FXCollections.observableArrayList();
        String searchText = searchPart.getText();
        int searchID;
        try{
            searchID = Integer.parseInt(searchText);
            parts.add(Inventory.lookupPart(searchID));
        }   catch(Exception e){
            parts = Inventory.lookupPart(searchText);
        }
        if (parts.size() > 1 || parts.size() < 1){
            labelPartSearchResult.setText(parts.size() + " results found.");
        }   else if(parts.size() == 1){
            labelPartSearchResult.setText(parts.size() + " result found.");
        }
        tableParts.setItems(parts);
    }



}
