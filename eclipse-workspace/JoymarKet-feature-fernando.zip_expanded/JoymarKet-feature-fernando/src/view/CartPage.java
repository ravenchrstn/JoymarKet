package view;

import controller.CartItemHandler;

import auth.SessionManager;
import model.CartItem;
import model.Product;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

public class CartPage {

    private TableView<CartItem> table = new TableView<>();
    private TextField tfCount = new TextField();
    private Button btnUpdate = new Button("Update Quantity");

    private CartItemHandler handler = new CartItemHandler();

    public CartPage(Stage stage) {
        stage.setTitle("Your Cart");

        // Columns
        TableColumn<CartItem, String> colProduct = new TableColumn<>("Product");
        colProduct.setCellValueFactory(c -> {
            try {
                Product p = Product.getById(c.getValue().getIdProduct());
                return new SimpleStringProperty(p.getName());
            } catch (Exception e) {
                return new SimpleStringProperty("Unknown");
            }
        });

        TableColumn<CartItem, String> colQuantity = new TableColumn<>("Quantity");
        colQuantity.setCellValueFactory(c -> new SimpleStringProperty("" + c.getValue().getCount()));

        table.getColumns().addAll(colProduct, colQuantity);

        loadCartItems();

        tfCount.setPromptText("New quantity");

        btnUpdate.setOnAction(e -> updateQuantity());

        VBox root = new VBox(15, table, tfCount, btnUpdate);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 650, 500));
        stage.show();
    }

    private void loadCartItems() {
        try {
            String userId = SessionManager.getUser().getIdUser();
            table.getItems().setAll(CartItem.getCartItems(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateQuantity() {
        CartItem selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Select an item first.");
            return;
        }

        String result = handler.updateCartItemCount(
        	SessionManager.getUser().getIdUser(),
            selected.getIdProduct(),
            tfCount.getText()
        );

        showAlert(result);
        loadCartItems();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}