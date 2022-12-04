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
 * This class controls the Inventory Management System screen
 */
public class InventoryManagerController implements Initializable {

    @FXML
    public Button addPart;

    @FXML
    public Button modifyPart;

    @FXML
    public Button deletePart;

    @FXML
    public TextField searchInventoryPart;

    @FXML
    public Label labelPartSearchResult;

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
    public Button addProduct;

    @FXML
    public Button modifyProduct;

    @FXML
    public Button deleteProduct;

    @FXML
    public TableView tableProducts;

    @FXML
    public TableColumn colProductId;

    @FXML
    public TableColumn colProductName;

    @FXML
    public TableColumn colProductInventory;

    @FXML
    public TableColumn colProductPrice;

    @FXML
    public TextField searchInventoryProduct;

    @FXML
    public Label labelProductSearchResult;

    @FXML
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Populates parts table
        colPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableParts.setItems(Inventory.getAllParts());

        //Populates products table
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableProducts.setItems(Inventory.getAllProducts());
    }

    /**
     * Method is called when Add button under Parts pane is selected. - Navigates user to the Add Part page.
     *
     * @param actionEvent Add part button click event
     * @throws IOException Incorrect/Invalid input
     */
    public void goToAddPart(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 600, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * Method called when Modify button is selected under the Parts pane. Validates a part is selected - Navigates user to Modify Part page.
     * RUNTIME ERROR NullPointerException if no part is selected.
     *
     * @param actionEvent Modify part button click event
     * @throws IOException Incorrect/Invalid input
     */
    public void goToModifyPart(javafx.event.ActionEvent actionEvent) throws IOException {
        Inventory.setSelectedPart((Part) tableParts.getSelectionModel().getSelectedItem());

        if (Inventory.getSelectedPart() == null) {
            Alert unselectedPartAlert = new Alert(Alert.AlertType.ERROR);
            unselectedPartAlert.setHeaderText("Part must be selected.");
            unselectedPartAlert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 600, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * Method called when Delete button is clicked in Parts pane
     *
     * @param actionEvent Delete part button click event
     */
    public void deleteSelectedPart(ActionEvent actionEvent) {
        Part i = (Part) tableParts.getSelectionModel().getSelectedItem();

        //Checks if part is selected
        if (i == null) {
            Alert selectPartAlert = new Alert(Alert.AlertType.ERROR);
            selectPartAlert.setHeaderText("Part must be selected.");
            selectPartAlert.showAndWait();
            return;
        }

        //Confirm delete
        Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDeleteAlert.setHeaderText("This cannot be undone. \nContinue?");
        confirmDeleteAlert.setContentText("Click OK to Proceed.");
        confirmDeleteAlert.showAndWait();
        if (confirmDeleteAlert.getResult() == ButtonType.CANCEL) {
            return;
        }

        if (Inventory.deletePart(i)) {
            Alert deleteSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            deleteSuccessAlert.setTitle("Success");
            deleteSuccessAlert.setHeaderText("Part deleted.");
            deleteSuccessAlert.showAndWait();
        } else if (!Inventory.deletePart(i)) {
            Alert deleteFailureAlert = new Alert(Alert.AlertType.ERROR);
            deleteFailureAlert.setTitle("Error");
            deleteFailureAlert.setHeaderText("Delete part failed.");
            deleteFailureAlert.showAndWait();
        }
    }

    /**
     * Method called when Add is selected under product pane - navigates user to the Add Product page.
     *
     * @param actionEvent Add product button click event
     * @throws IOException Incorrect/invalid input
     */
    public void goToAddProduct(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 900, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * Method called when Modify is elected under Product pane.  Validates that a product is selected. - Navigates user to Modify Product page.
     * RUNTIME ERROR NullPointerException thrown if no product is selected.
     *
     * @param actionEvent Modify product button click event
     * @throws IOException Incorrect/invalid input.
     */
    public void goToModifyProduct(javafx.event.ActionEvent actionEvent) throws IOException {
        Inventory.setSelectedProduct((Product) tableProducts.getSelectionModel().getSelectedItem());

        if (Inventory.getSelectedProduct() == null) {
            Alert unselectedProductAlert = new Alert(Alert.AlertType.ERROR);
            unselectedProductAlert.setHeaderText("Product must be selected.");
            unselectedProductAlert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 900, 600);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * Method is called when Delete button under product pane is selected.
     *
     * @param actionEvent Delete product button click event.
     */
    public void deleteSelectedProduct(ActionEvent actionEvent) {
        Product i = (Product) tableProducts.getSelectionModel().getSelectedItem();

        //Checks if product is selected
        if (i == null) {
            Alert selectProductAlert = new Alert(Alert.AlertType.ERROR);
            selectProductAlert.setHeaderText("Product must be selected.");
            selectProductAlert.showAndWait();
            return;
        }

        //Confirm delete
        Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDeleteAlert.setHeaderText("This cannot be undone. \nContinue?");
        confirmDeleteAlert.setContentText("Click OK to Proceed.");
        confirmDeleteAlert.showAndWait();
        if (confirmDeleteAlert.getResult() == ButtonType.CANCEL) {
            return;
        }

        if (Inventory.deleteProduct(i)) {
            Alert deleteSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            deleteSuccessAlert.setTitle("Success");
            deleteSuccessAlert.setHeaderText("Product deleted.");
            deleteSuccessAlert.showAndWait();
        } else if (!Inventory.deleteProduct(i)) {
            Alert deleteFailureAlert = new Alert(Alert.AlertType.ERROR);
            deleteFailureAlert.setTitle("Error");
            deleteFailureAlert.setHeaderText("Delete product failed.");
            deleteFailureAlert.showAndWait();
        }
    }

    /**
     * Method called when user types in the search parts field.  Shows parts that match search field and displays results.
     * @param keyEvent User typing event
     */
    public void searchPart(KeyEvent keyEvent){
        ObservableList<Part> parts = FXCollections.observableArrayList();
        String searchPartText = searchInventoryPart.getText();
        int searchPartId;
        try{
            searchPartId = Integer.parseInt(searchPartText);
            parts.add(Inventory.lookupPart(searchPartId));
        }   catch (Exception e) {
            parts = Inventory.lookupPart(searchPartText);
        }
        if (parts.size() > 1 || parts.size() < 1) {
            labelPartSearchResult.setText(parts.size() + " results found.");
        }   else if (parts.size() == 1) {
            labelPartSearchResult.setText(parts.size() + " result found.");
        }
        tableParts.setItems(parts);
    }

    /**
     * Method called when user types in the search products field. Shows parts that match search field and displays results.
     * @param keyEvent
     */
    public void searchProduct(KeyEvent keyEvent) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String searchProductText = searchInventoryProduct.getText();
        int searchProductId;
        try {
            searchProductId = Integer.parseInt(searchProductText);
            products.add(Inventory.lookupProduct(searchProductId));
        } catch (Exception e) {
            products = Inventory.lookupProduct(searchProductText);
        }
        if (products.size() > 1 || products.size() < 1) {
            labelProductSearchResult.setText(products.size() + " results found.");
        } else if (products.size() == 1) {
            labelProductSearchResult.setText(products.size() + " result found.");
        }
        tableProducts.setItems(products);
    }

    /**
     * Method called when user selects exit button. - Closes program.
     * @param actionEvent Exit button click event
     */
    public void exitProgram (ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
