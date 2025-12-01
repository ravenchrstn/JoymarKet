package view;

import controller.ProductHandler;

import auth.SessionManager;
import model.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminProductPage {

    private TableView<Product> table = new TableView<>();
    private TextField tfStock = new TextField();
    private Button btnUpdate = new Button("Update Stock");

    private ProductHandler handler = new ProductHandler();

    public AdminProductPage(Stage stage) {
        stage.setTitle("Admin - Manage Product Stock");

        // Table columns
        TableColumn<Product, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdProduct()));

        TableColumn<Product, String> colName = new TableColumn<>("Product Name");
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

        TableColumn<Product, String> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(c -> new SimpleStringProperty("" + c.getValue().getStock()));

        table.getColumns().addAll(colId, colName, colStock);

        loadProducts();

        tfStock.setPromptText("New Stock Value");

        btnUpdate.setOnAction(e -> updateStock());

        VBox root = new VBox(15, table, tfStock, btnUpdate);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 650, 500));
        stage.show();
    }

    private void loadProducts() {
        try {
            table.getItems().setAll(Product.getAllProducts());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStock() {
        Product selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a product first.");
            return;
        }

        String result = handler.updateProductStock(
            selected.getIdProduct(),
            tfStock.getText()
        );

        showAlert(result);
        loadProducts(); // refresh table
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(msg);
        a.show();
    }
}