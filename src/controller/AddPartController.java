package controller;

import model.InHouse;
import model.Inventory;
import model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the Add Part page.
 */

public class AddPartController implements Initializable {

    @FXML
    public RadioButton addInHouse;

    @FXML
    public RadioButton addOutsourced;

    @FXML
    public TextField addPartId;

    @FXML
    public TextField addPartName;

    @FXML
    public TextField addPartInventory;

    @FXML
    public TextField addPartPrice;

    @FXML
    public TextField addPartMax;

    @FXML
    public TextField addPartMin;

    @FXML
    public TextField addPartMachineId;

    @FXML
    public Button addPartSave;

    @FXML
    public Button addPartCancel;

    @FXML

    public Label labelAddMachineId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    /**
     * Method navigates user back to Inventory Management System page without saving.
     *
     * @param actionEvent - Button click event
     * @throws IOException - Invalid/incorrect input
     */
    public void returnToMain(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/InventoryManagementSystem.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1020, 400);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * Sets the Machine ID label as Machine ID if In-House radio button is selected.
     * @param actionEvent - Button click event
     */
    public void selectInHouse(javafx.event.ActionEvent actionEvent){
        labelAddMachineId.setText("Machine ID");
    }

    /**
     * Sets Machine ID label as Company Name if Outsourced radio button is selected
     * @param actionEvent - Button click event
     */
    public void selectOutsourced(ActionEvent actionEvent){
        labelAddMachineId.setText("Company Name");
    }

    /**
     * Method creates a new part and adds it to the inventory.
     *
     * *
     * RUNTIME ERROR NumberFormatException occurs if non-integer values present in inventory, min, and max fields
     * RUNTIME ERROR NumberFormatException occurs if non-double values present in price field.
     * corrected by using try/catch blocks.
     * *
     *
     * RUNTIME ERROR NumberFormat Exception occurs if entering a non-integer value in Machine ID field.
     * corrected by adding if/then field validation
     *
     * @param actionEvent
     * @throws IOException
     */
    public void savePart(ActionEvent actionEvent) throws IOException{
        int newPartId = Inventory.getNextPartId();
        String newPartName = addPartName.getText();
        double newPartPrice;
        int newPartInventory;
        int newPartMax;
        int newPartMin;
        int newPartMachineId;
        String newPartCompanyName;

        //Checks if name is valid
        if (newPartName.equals("") || newPartName == null){
            Alert addPartNameAlert = new Alert(Alert.AlertType.ERROR);
            addPartNameAlert.setHeaderText("Part Name is a required field.");
            addPartNameAlert.showAndWait();
            return;
        }

        //Checks if inventory level, min, and max has valid integers
        try{
            newPartInventory = Integer.parseInt(addPartInventory.getText());
            newPartMax = Integer.parseInt(addPartMax.getText());
            newPartMin = Integer.parseInt(addPartMin.getText());
        } catch(NumberFormatException e){
            Alert inventoryAlert = new Alert(Alert.AlertType.ERROR);
            inventoryAlert.setHeaderText("Inventory fields should be whole numbers.");
            inventoryAlert.showAndWait();
            return;
        }

        //Checks if price field has valid double
        try{
            newPartPrice = Double.parseDouble(addPartPrice.getText());
        }   catch(NumberFormatException e){
            Alert priceAlert = new Alert(Alert.AlertType.ERROR);
            priceAlert.setHeaderText("Price field should be a number." );
            priceAlert.showAndWait();
            return;
        }

        //Error if negative number is input.
        if(newPartInventory < 0 || newPartMax < 0 || newPartMin < 0 || newPartPrice < 0){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Only non-negative numbers are allowed.");
            errorAlert.showAndWait();
            return;
        }

        //Checks if inventory min is less than inventory max
        if(newPartMin > newPartMax){
            Alert minMaxAlert = new Alert(Alert.AlertType.ERROR);
            minMaxAlert.setTitle("Error");
            minMaxAlert.setHeaderText("Min field cannot be greater than Max field.");
            minMaxAlert.showAndWait();
            return;
        }

        //Radio button options for creating in-house or outsourced part
        if(addInHouse.isSelected()){
            try{
                newPartMachineId = Integer.parseInt(addPartMachineId.getText());
            }   catch(NumberFormatException e){
                Alert machineIdAlert = new Alert(Alert.AlertType.ERROR);
                machineIdAlert.setHeaderText("Machine ID field should be a number.");
                machineIdAlert.showAndWait();
                return;
            }
            Inventory.addPart(new InHouse(newPartId, newPartName, newPartPrice, newPartInventory, newPartMin, newPartMax, newPartMachineId));
        }   else if (addOutsourced.isSelected()){
            newPartCompanyName = addPartMachineId.getText();
            if(newPartCompanyName.equals("") || newPartCompanyName == null){
                Alert companyNameAlert = new Alert(Alert.AlertType.ERROR);
                companyNameAlert.setHeaderText("Company Name is a required field.");
                companyNameAlert.showAndWait();
                return;
            }
            Inventory.addPart(new Outsourced(newPartId, newPartName, newPartPrice, newPartInventory, newPartMin, newPartMax, newPartCompanyName));
        }
        //Return to main page
        returnToMain(actionEvent);

    }
}
