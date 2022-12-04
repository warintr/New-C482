package controller;

import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
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
 * This class controls the Modify Part page
 */

public class ModifyPartController implements Initializable {
    @FXML
    public RadioButton modInHouse;

    @FXML
    public RadioButton modOutsourced;

    @FXML
    public TextField modPartId;

    @FXML
    public TextField modPartName;

    @FXML
    public TextField modPartInventory;

    @FXML
    public TextField modPartPrice;

    @FXML
    public TextField modPartMax;

    @FXML
    public TextField modPartMin;

    @FXML
    public TextField modMachineId;

    @FXML
    public Button modPartSave;

    @FXML
    public Button modPartCancel;

    @FXML
    public Label labelMachineId;

    Part i;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        //Populates fields based on selected part from Inventory Management System page.
        i = Inventory.getSelectedPart();
        if(i.getClass().getTypeName() == "model.InHouse"){
            InHouse part = (InHouse) Inventory.getSelectedPart();
            modInHouse.setSelected(true);
            modPartId.setText(String.valueOf(part.getId()));
            modPartName.setText(part.getName());
            modPartPrice.setText(String.valueOf(part.getPrice()));
            modPartInventory.setText(String.valueOf(part.getStock()));
            modPartMax.setText(String.valueOf(part.getMax()));
            modPartMin.setText(String.valueOf(part.getMin()));
            modMachineId.setText(String.valueOf(part.getMachineId()));
            labelMachineId.setText("Machine ID");
        }   else if (i.getClass().getTypeName() == "model.Outsourced"){
            Outsourced part = (Outsourced) Inventory.getSelectedPart();
            modOutsourced.setSelected(true);
            modPartId.setText(String.valueOf(part.getId()));
            modPartName.setText(part.getName());
            modPartPrice.setText(String.valueOf(part.getPrice()));
            modPartInventory.setText(String.valueOf(part.getStock()));
            modPartMax.setText(String.valueOf(part.getMax()));
            modPartMin.setText(String.valueOf(part.getMin()));
            modMachineId.setText(String.valueOf(part.getCompanyName()));
            labelMachineId.setText("Company Name");
        }
    }

    /**
     * Sets the Machine ID label as Machine ID if In-House radio button is selected.
     * @param actionEvent - Button click event
     */
    public void selectInHouse(javafx.event.ActionEvent actionEvent){
        labelMachineId.setText("Machine ID");
    }

    /**
     * Sets Machine ID label as Company Name if Outsourced radio button is selected
     * @param actionEvent - Button click event
     */
    public void selectOutsourced(ActionEvent actionEvent){
        labelMachineId.setText("Company Name");
    }

    /**
     * Method returns user to the Inventory Management System page.
     * @param actionEvent Button click event.
     * @throws IOException Incorrect/invalid input.
     */
    public void returnToMain(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/InventoryManagementSystem.fxml"));

        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1020, 400);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    /**
     * Method validates information in fields and modifies the indexed part.
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
        int partId = Integer.parseInt(modPartId.getText());
        String partName = modPartName.getText();
        double partPrice;
        int partInventory;
        int partMax;
        int partMin;
        int partMachineId;
        String partCompanyName;

        //Checks if name is valid - Displays alert if not valid
        if (partName.equals("") || partName == null){
            Alert modPartNameAlert = new Alert(Alert.AlertType.ERROR);
            modPartNameAlert.setHeaderText("Part Name is a required field.");
            modPartNameAlert.showAndWait();
            return;
        }

        //Checks if inventory level, min, and max has valid integers - displays alert if no valid integers
        try{
            partInventory = Integer.parseInt(modPartInventory.getText());
            partMax = Integer.parseInt(modPartMax.getText());
            partMin = Integer.parseInt(modPartMin.getText());
        } catch(NumberFormatException e){
            Alert inventoryAlert = new Alert(Alert.AlertType.ERROR);
            inventoryAlert.setHeaderText("Inventory fields should be whole numbers.");
            inventoryAlert.showAndWait();
            return;
        }

        //Checks if price field has valid double - displays alert if not a valid number
        try{
            partPrice = Double.parseDouble(modPartPrice.getText());
        }   catch(NumberFormatException e){
            Alert priceAlert = new Alert(Alert.AlertType.ERROR);
            priceAlert.setHeaderText("Price field should be a number." );
            priceAlert.showAndWait();
            return;
        }

        //Error if negative number is input.
        if(partInventory < 0 || partMax < 0 || partMin < 0 || partPrice < 0){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Only non-negative numbers are allowed.");
            errorAlert.showAndWait();
            return;
        }

        //Checks if inventory min is less than inventory max - displays error if Min is larger than Max
        if(partMin > partMax){
            Alert minMaxAlert = new Alert(Alert.AlertType.ERROR);
            minMaxAlert.setTitle("Error");
            minMaxAlert.setHeaderText("Min field cannot be greater than Max field.");
            minMaxAlert.showAndWait();
            return;
        }

        //Radio button options for creating in-house or outsourced part - checks for a valid integer in Machine ID field if In-House selected and displays alert if no valid integer.
        //Checks for a valid input for Company Name when Outsourced selected.  - Alerts if no valid input.
        if(modInHouse.isSelected()){
            try{
                partMachineId = Integer.parseInt(modMachineId.getText());
            }   catch(NumberFormatException e){
                Alert machineIdAlert = new Alert(Alert.AlertType.ERROR);
                machineIdAlert.setHeaderText("Machine ID field should be a number.");
                machineIdAlert.showAndWait();
                return;
            }
            Inventory.updatePart(Inventory.getAllParts().indexOf(i), new InHouse(partId, partName, partPrice, partInventory, partMin, partMax, partMachineId));
        }   else if (modOutsourced.isSelected()){
            partCompanyName = modMachineId.getText();
            if(partCompanyName.equals("") || partCompanyName == null){
                Alert companyNameAlert = new Alert(Alert.AlertType.ERROR);
                companyNameAlert.setHeaderText("Company Name is a required field.");
                companyNameAlert.showAndWait();
                return;
            }
            Inventory.updatePart(Inventory.getAllParts().indexOf(i), new Outsourced(partId, partName, partPrice, partInventory, partMin, partMax, partCompanyName));
        }
        //Return to main page
        returnToMain(actionEvent);


    }



}
