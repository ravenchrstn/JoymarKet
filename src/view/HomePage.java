package view;

import java.sql.SQLException;
import java.util.ArrayList;

import auth.SessionManager;
import components.Navbar;
import controller.CartItemHandler;
import controller.ProductHandler;
import exception.InvalidInputException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Customer;
import model.OrderHeader;
import model.Product;
import response.MultipleObjectsResponse;

public class HomePage extends Application {

    Scene scene;
    BorderPane borderPane;
    ScrollPane scrollPane;
    GridPane header_table;

    ProductHandler ph;
    Product p;
    Product rowProduct;
    MultipleObjectsResponse<Product> resp;

    Label topUpBalance;
    Label desc;
    Label balanceLabel;
    Label balanceAmount;
    Label catalogTitle;
    Label lblHeader;
    Label errorMsg;

    Button topUpBtn;
    TextField qtyField;

    TableView<Product> productTable;
    TableColumn<Product, String> nameCol;
    TableColumn<Product, String> priceCol;
    TableColumn<Product, String> stockCol;
    TableColumn<Product, String> categoryCol;
    TableColumn<Product, Void> quantityCol;
    TableColumn<Product, Void> actionCol;
    TableCell<Product, Void> qtyCell;

    VBox vbox_homepage_no_logged;
    VBox balanceCard;
    VBox productCatalogContainer;
    VBox box_action;
    VBox wrapper;
    VBox finalContainer;
    HBox hBox_balance_btn;
    HBox header;

    public HomePage() {

        borderPane = new BorderPane();
        scrollPane = new ScrollPane();

        if (SessionManager.getUser() == null) {
            scene = new Scene(borderPane, 800, 500);
            vbox_homepage_no_logged = new VBox(15);
            vbox_homepage_no_logged.setAlignment(Pos.CENTER);
            Label title = new Label("JoymarKet");
            title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
            title.setTextFill(Color.BLACK);
            desc = new Label("Welcome to our digital marketplace for fresh food, meats, and groceries, delivered instantly to your door. Log in to explore personalized selections and enjoy a faster, fresher shopping experience. Get started today!");
            desc.setWrapText(true);
            desc.setTextAlignment(TextAlignment.CENTER);
            desc.setMaxWidth(450);
            vbox_homepage_no_logged.getChildren().addAll(title, desc);
            borderPane.setCenter(vbox_homepage_no_logged);
            return;
        }

        String role = SessionManager.getUser().getRole();
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setContent(borderPane);
        scene = new Scene(scrollPane, 500, 500);

        if (role.compareTo("customer") == 0) {

            // view account balance
            hBox_balance_btn = new HBox();
            hBox_balance_btn.setAlignment(Pos.CENTER);
            balanceCard = new VBox(10);
            balanceCard.setPadding(new Insets(20));
            balanceCard.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.08), 8, 0, 0, 3);");
            balanceCard.setMinWidth(400);
            balanceCard.setMaxHeight(100);
            balanceLabel = new Label("Your Current Balance");
            balanceLabel.setFont(Font.font("Arial", 16));
            balanceLabel.setTextFill(Color.GRAY);

            String userId = SessionManager.getUser().getIdUser();

            try {
                balanceAmount = new Label("$" + Customer.getBalanceByIdUser(userId));
            } catch (SQLException e) {
                balanceAmount = new Label("$0.0");
                e.printStackTrace();
            }
            balanceAmount.setFont(Font.font("Arial", FontWeight.BOLD, 32));
            topUpBtn = new Button("+");
            topUpBtn.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            topUpBtn.setStyle("-fx-background-radius: 50; -fx-background-color: #4CAF50; -fx-text-fill: white;");

            topUpBtn.setOnMouseEntered(e -> topUpBtn.setCursor(Cursor.HAND));
            Region spacer1 = new Region();
            HBox.setHgrow(spacer1, Priority.ALWAYS);
            hBox_balance_btn.getChildren().addAll(balanceAmount, spacer1, topUpBtn);
            balanceCard.getChildren().addAll(balanceLabel, hBox_balance_btn);
            borderPane.setPadding(new Insets(0, 20, 0, 20));

            topUpBtn.setOnAction(e -> {
                try {
                    new TopUpPage().start((Stage) topUpBtn.getScene().getWindow());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });

            VBox orderSection = new VBox(25);
            orderSection.setPadding(new Insets(10, 20, 10, 20));
            
         // product catalogue (items)
            productCatalogContainer = new VBox(20);
            productCatalogContainer.setStyle("fx-background-radius: 10; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.08), 8, 0, 0, 3);");
            productCatalogContainer.setAlignment(Pos.TOP_CENTER);
            catalogTitle = new Label("All Products");
            catalogTitle.setFont(Font.font("Arial", FontWeight.BOLD, 26));
            productTable = new TableView<>();
            productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            nameCol = new TableColumn<>("Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceCol = new TableColumn<>("Price");
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            stockCol = new TableColumn<>("Stock");
            stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            categoryCol = new TableColumn<>("Category");
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            quantityCol = new TableColumn<>("Count");
            actionCol = new TableColumn<>("Action");

            quantityCol.setCellFactory(col -> new TableCell<Product, Void>() {
                public TextField qtyField = new TextField();
                public Label errorMsg = new Label();

                private final VBox wrapper = new VBox(2);

                {
                    qtyField.setMaxWidth(60);
                    qtyField.textProperty().addListener((obs, oldVal, newVal) -> {
                        if (!newVal.matches("\\d*")) {
                            qtyField.setText(newVal.replaceAll("[^\\d]", ""));
                        }
                    });
                    errorMsg.setTextFill(Color.RED);
                    errorMsg.setVisible(false);
                    wrapper.setAlignment(Pos.CENTER);
                    wrapper.getChildren().addAll(qtyField, errorMsg);
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        return;
                    }
                    qtyField.setText("");
                    errorMsg.setVisible(false);
                    setGraphic(wrapper);
                }
            });

            ph = new ProductHandler();
            resp = ph.getProducts();
            productTable.getItems().addAll(resp.getHashMap());

            actionCol.setCellFactory(col -> new TableCell<Product, Void>() {
                private final Button addBtn = new Button("Add to Cart");

                {
                    addBtn.setStyle("-fx-background-color:#1e88e5; -fx-text-fill:white; -fx-background-radius:6;");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        return;
                    }
                    rowProduct = getTableView().getItems().get(getIndex());
                    String idProduct = rowProduct.getIdProduct();
                    addBtn.setOnAction(e -> {
                        qtyCell = (TableCell<Product, Void>) getTableRow().getChildrenUnmodifiable().get(4);
                        qtyField = (TextField) ((VBox) qtyCell.getGraphic()).getChildren().get(0);
                        errorMsg = (Label) ((VBox) qtyCell.getGraphic()).getChildren().get(1);
                        errorMsg.setFont(Font.font("Arial", FontWeight.BOLD, 12));

                        String val = qtyField.getText().trim();
                        if (val.isEmpty()) {
                            errorMsg.setText("Count Field cannot be empty.");
                            errorMsg.setTextFill(Color.RED);
                            errorMsg.setVisible(true);
                            return;
                        }

                        int amount = Integer.parseInt(val);
                        CartItemHandler handler = new CartItemHandler();
                        String result = null;

                        try {
                            result = handler.addToCart(SessionManager.getUser().getIdUser(), idProduct, amount);
                            errorMsg.setVisible(true);
                            errorMsg.setText(result);
                            errorMsg.setTextFill(Color.GREEN);

                        } catch (InvalidInputException ex) {
                            errorMsg.setVisible(true);
                            errorMsg.setText(ex.getUserMessage());
                            errorMsg.setTextFill(Color.RED);
                            return;
                        }
                    });
                    setGraphic(addBtn);
                }
            });

            productTable.getColumns().addAll(nameCol, priceCol, stockCol, categoryCol, quantityCol, actionCol);
            productTable.setFixedCellSize(60);
            productTable.prefHeightProperty().bind(productTable.fixedCellSizeProperty().multiply(productTable.getItems().size()).add(40));
            productTable.minHeightProperty().bind(productTable.prefHeightProperty());
            productTable.maxHeightProperty().bind(productTable.prefHeightProperty());
            nameCol.setStyle("-fx-alignment: CENTER;");
            priceCol.setStyle("-fx-alignment: CENTER;");
            stockCol.setStyle("-fx-alignment: CENTER;");
            categoryCol.setStyle("-fx-alignment: CENTER;");
            actionCol.setStyle("-fx-alignment: CENTER;");
            productCatalogContainer.getChildren().addAll(catalogTitle, productTable);

            // query orders
            ArrayList<OrderHeader> ongoing = new ArrayList<>();
            ArrayList<OrderHeader> completed = new ArrayList<>();
            ArrayList<OrderHeader> cancelled = new ArrayList<>();

            try {
                ongoing = OrderHeader.getOngoingOrders(userId);
                completed = OrderHeader.getCompletedOrdersByCustomer(userId);
                cancelled = OrderHeader.getCancelledOrders(userId);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // view ongoing orders
            VBox ongoingBox = new VBox(10);
            ongoingBox.setStyle("-fx-background-color:white; -fx-background-radius:10; -fx-padding:15;");
            Label ongoingTitle = new Label("Ongoing Orders");
            ongoingTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            ongoingBox.getChildren().add(ongoingTitle);

            if (ongoing.isEmpty()) {
                ongoingBox.getChildren().add(new Label("No ongoing orders."));
            } else {
                for (OrderHeader oh : ongoing) {
                    Label row = new Label(
                        "Order " + oh.getIdOrder() +
                        " | $" + oh.getTotalAmount() +
                        " | Status: " + oh.getDeliveryStatus()
                    );
                    row.setFont(Font.font(15));
                    ongoingBox.getChildren().add(row);
                }
            }

            // view completed orders
            VBox completedBox = new VBox(10);
            completedBox.setStyle("-fx-background-color:white; -fx-background-radius:10; -fx-padding:15;");
            Label completedTitle = new Label("Completed Orders");
            completedTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            completedBox.getChildren().add(completedTitle);

            if (completed.isEmpty()) {
                completedBox.getChildren().add(new Label("No completed orders."));
            } else {
                for (OrderHeader oh : completed) {
                    String courierName = oh.getAssignedCourier();
                    Label row = new Label(
                        "Order " + oh.getIdOrder() +
                        " | $" + oh.getTotalAmount() +
                        " | Courier: " + (courierName != null ? courierName : "-")
                    );
                    row.setFont(Font.font(15));
                    row.setTextFill(Color.GREEN);
                    completedBox.getChildren().add(row);
                }
            }

            // view cancelled orders
            VBox cancelledBox = new VBox(10);
            cancelledBox.setStyle("-fx-background-color:white; -fx-background-radius:10; -fx-padding:15;");
            Label cancelledTitle = new Label("Cancelled Orders");
            cancelledTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            cancelledBox.getChildren().add(cancelledTitle);

            if (cancelled.isEmpty()) {
                cancelledBox.getChildren().add(new Label("No cancelled orders."));
            } else {
                for (OrderHeader oh : cancelled) {
                    Label row = new Label(
                        "Order " + oh.getIdOrder() +
                        " | $" + oh.getTotalAmount()
                    );
                    row.setFont(Font.font(15));
                    row.setTextFill(Color.RED);
                    cancelledBox.getChildren().add(row);
                }
            }

            orderSection.getChildren().addAll(ongoingBox, completedBox, cancelledBox);

            // section compositioning
            finalContainer = new VBox(25, balanceCard, productCatalogContainer, orderSection);
            finalContainer.setAlignment(Pos.TOP_CENTER);
            finalContainer.setPadding(new Insets(10, 20, 10, 20));
            borderPane.setCenter(finalContainer);
        }

        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);

        scrollPane.setContent(borderPane);
        scene = new Scene(scrollPane, 800, 500);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Navbar navbar = new Navbar(primaryStage);
        borderPane.setTop(navbar);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}     