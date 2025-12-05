package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import components.Navbar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.CartItem;

public class CartPage{

	Scene scene;
	
    private String idUser; 
    private TableView<HashMap<String, Object>> table = new TableView<>();
    private ObservableList<HashMap<String, Object>> cartList = FXCollections.observableArrayList();
    private Label totalLabel = new Label("0.0");
    private Stage stage;
    
    public CartPage(Stage stage) {
        this.stage = stage;
        this.idUser = auth.SessionManager.getUser().getIdUser();
    }
    
    public CartPage(Stage stage, String idUser) {
        this.stage = stage;
        this.idUser = idUser;
    }

    public void show() {
        stage.setTitle("Your Cart");

        // COLUMNS ==============================================
        
        TableColumn<HashMap<String, Object>, String> colName = new TableColumn<>("Product");
        colName.setCellValueFactory(c -> 
            new javafx.beans.property.SimpleStringProperty(
                (String) c.getValue().get("name")
            )
        );
        colName.setPrefWidth(200);

        TableColumn<HashMap<String, Object>, Integer> colQty = new TableColumn<>("Qty");
        colQty.setCellValueFactory(c -> 
            new javafx.beans.property.SimpleIntegerProperty(
                (Integer) c.getValue().get("count")
            ).asObject()
        );
        colQty.setPrefWidth(70);

        TableColumn<HashMap<String, Object>, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(c -> 
            new javafx.beans.property.SimpleDoubleProperty(
                (Integer) c.getValue().get("price")
            ).asObject()
        ); 
        colPrice.setPrefWidth(100);

        TableColumn<HashMap<String, Object>, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(c -> {
        	Integer subTotal = (Integer) c.getValue().get("price") * (Integer) c.getValue().get("count");
            return new javafx.beans.property.SimpleDoubleProperty(subTotal).asObject();
        });
        colSubtotal.setPrefWidth(100);

        TableColumn<HashMap<String, Object>, Void> colAction = new TableColumn<>("Actions");
        colAction.setPrefWidth(200);
        colAction.setCellFactory(col -> new TableCell<>() {

            private final Button btnUpdate = new Button("Update");
            private final Button btnDel = new Button("Delete");

            {
                btnUpdate.setOnAction(e -> {
                    HashMap<String, Object> item = getTableView().getItems().get(getIndex());
                    String idProduct = (String) item.get("idProduct");

                    TextInputDialog dialog = new TextInputDialog(item.get("count").toString());
                    dialog.setHeaderText("Update Quantity");
                    dialog.showAndWait().ifPresent(newQtyStr -> {
                        try {
                            Integer newQty = Integer.parseInt(newQtyStr);
                            CartItem.updateCount(idUser, idProduct, newQty);
                            loadCart();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                });

                btnDel.setOnAction(e -> {
                    HashMap<String, Object> item = getTableView().getItems().get(getIndex());
                    String idProduct = (String) item.get("idProduct");

                    try {
                        CartItem.delete(idUser, idProduct);
                        loadCart();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                if (empty) setGraphic(null);
                else {
                    HBox actions = new HBox(10, btnUpdate, btnDel);
                    actions.setAlignment(Pos.CENTER);
                    setGraphic(actions);
                }
            }
        });

        table.getColumns().addAll(colName, colQty, colPrice, colSubtotal, colAction);
        table.setItems(cartList);

        // PROMO
        TextField promoField = new TextField();
        promoField.setPromptText("Promo Code");

        Button applyPromoBtn = new Button("Apply");
        applyPromoBtn.setOnAction(e -> applyPromo(promoField.getText()));

        HBox promoBox = new HBox(10, promoField, applyPromoBtn);
        promoBox.setPadding(new Insets(10));

        // TOTAL
        HBox totalBox = new HBox(10, new Label("Total: "), totalLabel);
        totalBox.setAlignment(Pos.CENTER_RIGHT);
        totalBox.setPadding(new Insets(10));

        // CHECKOUT
        Button checkoutBtn = new Button("Checkout");
        checkoutBtn.setOnAction(e -> checkout());

        VBox root = new VBox(new Navbar(stage), table, promoBox, totalBox, checkoutBtn);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 750, 600));
        stage.show();

        loadCart();
    }

    // ================= LOAD CART FROM DB ======================
    private void loadCart() {
        try {
            ArrayList<HashMap<String, Object>> list = CartItem.getCartItemsDataByIdCustomer(idUser);
            cartList.setAll(list);
            updateTotal();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================= TOTAL ======================
    private void updateTotal() {
        Double total = CartItem.getTotalAmountByUserId(idUser);
        totalLabel.setText(String.format("%.2f", total));
    }

    // ================= PROMO ======================
    private boolean isPromoApplied = false;
    private void applyPromo(String code) {
        if (code.equalsIgnoreCase("DISKON10") && isPromoApplied == false) {
            double total = Double.parseDouble(totalLabel.getText());
            totalLabel.setText(String.format("%.2f", total * 0.9));
            isPromoApplied = true;
        }
    }

    // ================= CHECKOUT ======================
    private void checkout() {
        try {
            CartItem.deleteAllByIdUser(idUser);
            loadCart();
            new Alert(Alert.AlertType.INFORMATION, "Checkout successful!").show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
