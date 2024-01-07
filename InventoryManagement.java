import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.HashMap;
import java.util.Map;

public class project extends Application {
    
    private Map<String, InventoryItem> inventory = new HashMap<>();
    private ListView<String> listView = new ListView<>();
    private TextField nameField = new TextField();
    private TextField priceField = new TextField();
    private TextField quantityField = new TextField();
    private TextField updateNameField = new TextField();
    private TextField updateQuantityField = new TextField();
    private TextField deleteNameField = new TextField();
    private TextField buyNameField = new TextField();
    private TextField buyQuantityField = new TextField();
    private TextField sellNameField = new TextField();
    private TextField sellQuantityField = new TextField();
    private TextField categoryField = new TextField();
    private Label adminLabel = new Label("");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Management System");

        Button addButton = new Button("Add Product");
        Button updateButton = new Button("Update Stock");
        Button deleteButton = new Button("Delete Product");
        Button buyButton = new Button("Buy");
        Button sellButton = new Button("Sell");

        // Abstract class for operations
        InventoryOperation inventoryOperation = new ConcreteInventoryOperation();

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String category = categoryField.getText();
            inventoryOperation.addProduct(inventory, name, price, quantity, category);
            updateListView();
        });

        updateButton.setOnAction(e -> {
            String name = updateNameField.getText();
            int newQuantity = Integer.parseInt(updateQuantityField.getText());
            inventoryOperation.updateStock(inventory, name, newQuantity);
            updateListView();
        });

        deleteButton.setOnAction(e -> {
            String name = deleteNameField.getText();
            inventoryOperation.deleteProduct(inventory, name);
            updateListView();
        });

        buyButton.setOnAction(e -> {
            String name = buyNameField.getText();
            int buyQuantity = Integer.parseInt(buyQuantityField.getText());
            inventoryOperation.buyProduct(inventory, name, buyQuantity);
            updateListView();
        });

        sellButton.setOnAction(e -> {
            String name = sellNameField.getText();
            int sellQuantity = Integer.parseInt(sellQuantityField.getText());
            inventoryOperation.sellProduct(inventory, name, sellQuantity);
            updateListView();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
            new Label("Add Product"),
            new HBox(10, new Label("Name:"), nameField, new Label("Price:"), priceField, new Label("Quantity:"), quantityField, new Label("Category:"), categoryField, addButton),
            new Label("Update Stock"),
            new HBox(10, new Label("Name:"), updateNameField, new Label("New Quantity:"), updateQuantityField, updateButton),
            new Label("Delete Product"),
            new HBox(10, new Label("Name:"), deleteNameField, deleteButton),
            new Label("Buy Products"),
            new HBox(10, new Label("Name:"), buyNameField, new Label("Quantity:"), buyQuantityField, buyButton),
            new Label("Sell Products"),
            new HBox(10, new Label("Name:"), sellNameField, new Label("Quantity:"), sellQuantityField, sellButton),
            new Label("Inventory Items"),
            listView,
            adminLabel
        );

        Scene scene = new Scene(layout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        updateListView();
    }

    private void updateListView() {
        listView.getItems().clear();
        for (InventoryItem item : inventory.values()) {
            listView.getItems().add("Name: " + item.name + ", Price: " + item.price + ", Quantity: " + item.quantity + ", Category: " + item.category);
        }
    }

    public class InventoryItem {
        String name;
        int quantity;
        double price;
        String category;

        InventoryItem(String name, int quantity, double price, String category) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.category = category;
        }
    }

    // Abstract class for inventory operations
    abstract class InventoryOperation {
        abstract void addProduct(Map<String, InventoryItem> inventory, String name, double price, int quantity, String category);
        abstract void updateStock(Map<String, InventoryItem> inventory, String name, int newQuantity);
        abstract void deleteProduct(Map<String, InventoryItem> inventory, String name);
        abstract void buyProduct(Map<String, InventoryItem> inventory, String name, int buyQuantity);
        abstract void sellProduct(Map<String, InventoryItem> inventory, String name, int sellQuantity);
    }

    // Concrete class implementing inventory operations
    class ConcreteInventoryOperation extends InventoryOperation {
        @Override
        void addProduct(Map<String, InventoryItem> inventory, String name, double price, int quantity, String category) {
            InventoryItem newItem = new InventoryItem(name, quantity, price, category);
            inventory.put(name, newItem);
        }

        @Override
        void updateStock(Map<String, InventoryItem> inventory, String name, int newQuantity) {
            if (inventory.containsKey(name)) {
                InventoryItem item = inventory.get(name);
                item.quantity = newQuantity;
            }
        }

        @Override
        void deleteProduct(Map<String, InventoryItem> inventory, String name) {
            inventory.remove(name);
        }

        @Override
        void buyProduct(Map<String, InventoryItem> inventory, String name, int buyQuantity) {
            if (inventory.containsKey(name)) {
                InventoryItem item = inventory.get(name);
                item.quantity += buyQuantity;
            }
        }

        @Override
        void sellProduct(Map<String, InventoryItem> inventory, String name, int sellQuantity) {
            if (inventory.containsKey(name)) {
                InventoryItem item = inventory.get(name);
                item.quantity -= sellQuantity;
            }
}}
}
