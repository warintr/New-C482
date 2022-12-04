package main;

import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class.
 * JAVADOCS is in Javadocs folder.
 * FUTURE ENHANCEMENT This application would only be usable for a small company where their inventory would
 * only be managed by one individual at a time on a single device.
 * This would not be suitable for large organizations where it is not realistic for one person to manage inventory.
 * Moving the data to a database as well as turning the application into a cloud based app would allow for multiple users
 * to manage inventory from any device.  This would also make the data non-volatile.
 * Adding reports to track product sales and inventory movement would benefit the client by providing insightful data to analyze.
 * Integrations with 3rd parties such as shipping partners or part suppliers could simplify the process of adjusting stock by
 * automatically adjusting stock when a product is shipped or parts are received.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/InventoryManagementSystem.fxml"));


        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1024, 400));
        stage.setResizable(false);
        stage.show();


        Part part1 = new InHouse(1, "Wheel", 7.99, 8, 1, 11, 12);
        Inventory.addPart(part1);

        Product product1 = new Product(1, "Bicycle", 67.99, 4, 1, 20);
        Inventory.addProduct(product1);
    }

    /**
     * Main method.  - Launches application
     * @param args Argument of Strings.
     */
    public static void main(String[] args){launch(args);}

}