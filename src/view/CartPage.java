package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import components.Navbar;
import controller.PromoHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CartItem;
import model.OrderDetail;
import model.OrderHeader;
import model.Promo;
import model.User;

public class CartPage {

	Scene scene;

	private String idUser;
	private TableView<HashMap<String, Object>> table = new TableView<>();
	private ObservableList<HashMap<String, Object>> cartList = FXCollections.observableArrayList();
	private Label totalLabel = new Label("0.0");
	Stage stage;

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
		Navbar navbar = new Navbar(stage);
		BorderPane root = new BorderPane();
		root.setTop(navbar);

		// COLUMNS ==============================================

		TableColumn<HashMap<String, Object>, String> colName = new TableColumn<>("Product");
		colName.setCellValueFactory(
				c -> new javafx.beans.property.SimpleStringProperty((String) c.getValue().get("name")));
		colName.setPrefWidth(200);

		TableColumn<HashMap<String, Object>, Integer> colQty = new TableColumn<>("Qty");
		colQty.setCellValueFactory(
				c -> new javafx.beans.property.SimpleIntegerProperty((Integer) c.getValue().get("count")).asObject());
		colQty.setPrefWidth(70);

		TableColumn<HashMap<String, Object>, Double> colPrice = new TableColumn<>("Price");
		colPrice.setCellValueFactory(
				c -> new javafx.beans.property.SimpleDoubleProperty((Integer) c.getValue().get("price")).asObject());
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
				if (empty)
					setGraphic(null);
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
		checkoutBtn.setOnAction(e -> checkout(idUser, promoField.getText()));

		VBox content = new VBox(15, table, promoBox, totalBox, checkoutBtn);
		content.setPadding(new Insets(20));
		root.setCenter(content);

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
	private PromoHandler promoHandler = new PromoHandler(); // add import

//	private void applyPromo(String code) {
//		Double discountPercent = promoHandler.getDiscountByCode(code);
//		if (discountPercent == null) {
//			new Alert(Alert.AlertType.ERROR, "Promo code not valid").show();
//			return;
//		}
//		double total = CartItem.getTotalAmountByUserId(idUser);
//		double totalAfter = total * (1 - discountPercent / 100.0);
//		totalLabel.setText(String.format("%.2f", totalAfter));
//	}

	private boolean isPromoApplied = false;

	private void applyPromo(String code) {

		if (isPromoApplied) {
			new Alert(Alert.AlertType.INFORMATION, "Promo sudah digunakan").show();
			return;
		}

		Double discountPercent = promoHandler.getDiscountByCode(code);

		// Jika kode tidak ditemukan
		if (discountPercent == null) {
			new Alert(Alert.AlertType.ERROR, "Promo code not valid").show();
			return;
		}

		// Hitung total sekarang
		double total = CartItem.getTotalAmountByUserId(idUser);

		// Hitung diskon
		double totalAfter = total * (1 - discountPercent / 100.0);

		// Update label
		totalLabel.setText(String.format("%.2f", totalAfter));

		isPromoApplied = true;
	}

	// ================= CHECKOUT ======================

	private void checkout(String idUser, String promoCode) {
		try {
			ArrayList<HashMap<String, Object>> items = CartItem.getCartItemsDataByIdCustomer(idUser);
			if (items.isEmpty()) {
				new Alert(Alert.AlertType.WARNING, "Cart is empty.").show();
				return;
			}

			// 1. Cek stok
			for (HashMap<String, Object> row : items) {
				int count = ((Number) row.get("count")).intValue();
				int stock = ((Number) row.get("stock")).intValue();
				if (count > stock) {
					new Alert(Alert.AlertType.ERROR, "Not enough stock: " + row.get("name")).show();
					return;
				}
			}

			// 2. Hitung total
			double total = CartItem.getTotalAmountByUserId(idUser);

			// 3. Promo
			HashMap<String, Object> promoInfo = Promo.getPromoInfoByCode(promoCode);
			String idPromo = null;

			if (promoInfo != null && !promoInfo.isEmpty()) {
				double discountPercent = (double) promoInfo.get("discountPercentage");
				total = total * (1 - discountPercent / 100.0);
				idPromo = (String) promoInfo.get("idPromo");
			}

			// 4. Kurangi saldo
			User.reduceBalance(idUser, total);

			// 5. Insert header
			String idOrder = OrderHeader.insert(idUser, idPromo, total);

			// 6. Insert detail
			for (HashMap<String, Object> row : items) {
				String idProduct = (String) row.get("idProduct");
				int qty = ((Number) row.get("count")).intValue();
				OrderDetail.insert(idOrder, idProduct, qty);
			}

			// 7. Kurangi stok
			da.ProductDA productDA = da.ProductDA.getProductDA();
			for (HashMap<String, Object> row : items) {
				productDA.reduceStock((String) row.get("idProduct"), ((Number) row.get("count")).intValue());
			}

			// 8. Hapus cart
			CartItem.deleteAllByIdUser(idUser);

			// 9. Show popup dulu!
			new Alert(Alert.AlertType.INFORMATION, "Checkout successful!").show();

			// 10. Baru refresh UI
			loadCart();

		} catch (Exception ex) {
			ex.printStackTrace();
			new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
		}
	}

}
