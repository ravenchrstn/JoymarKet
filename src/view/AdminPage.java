package view;

import components.Navbar;
import controller.AdminHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Courier;
import model.OrderHeader;
import model.Product;
import response.MultipleObjectsResponse;

public class AdminPage extends Application {

    private Scene scene;
    private final AdminHandler handler = new AdminHandler();
    private BorderPane root;

    @Override
    public void start(Stage stage) {

        root = new BorderPane();

        Navbar navbar = new Navbar(stage);
        root.setTop(navbar);

        ScrollPane sc = new ScrollPane();
        sc.setFitToWidth(true);

        VBox container = new VBox(30);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.TOP_CENTER);

        // title
        VBox titleCard = createTitle("Admin Dashboard");
        container.getChildren().add(titleCard);

        // unassigned orders
        VBox unassignedCard = createSectionCard("Unassigned Orders");
        loadUnassignedOrders(unassignedCard);
        container.getChildren().add(unassignedCard);

        // assigned orders
        VBox assignedCard = createSectionCard("Assigned Deliveries");
        loadAssignedOrders(assignedCard);
        container.getChildren().add(assignedCard);

        // completed orders
        VBox completedCard = createSectionCard("Completed Orders");
        loadCompletedOrders(completedCard);
        container.getChildren().add(completedCard);

        // manage stock
        VBox stockCard = createSectionCard("Manage Product Stock");
        loadStockSection(stockCard);
        container.getChildren().add(stockCard);

        sc.setContent(container);
        root.setCenter(sc);

        scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();
    }

    // UI HELPERS
    private VBox createTitle(String text) {
        Label title = new Label(text);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.BLACK);

        VBox box = new VBox(title);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle(
            "-fx-background-color:white; -fx-background-radius:12;"
          + "-fx-effect:dropshadow(two-pass-box, rgba(0,0,0,0.1), 10,0,0,4);"
        );
        box.setMaxWidth(600);
        return box;
    }

    private VBox createSectionCard(String titleText) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.TOP_LEFT);
        card.setMaxWidth(800);
        card.setStyle(
            "-fx-background-color:white; -fx-background-radius:12;"
          + "-fx-effect:dropshadow(two-pass-box, rgba(0,0,0,0.1), 10,0,0,4);"
        );

        Label title = new Label(titleText);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        card.getChildren().add(title);

        return card;
    }

    // fetching unassigned orders
    private void loadUnassignedOrders(VBox card) {
        MultipleObjectsResponse<OrderHeader> ordersRes = handler.getUnassignedOrders();
        MultipleObjectsResponse<Courier> couriersRes = handler.getAllCouriers();

        if (!ordersRes.getErrorUserMessage().isEmpty()) {
            Label msg = new Label(ordersRes.getErrorUserMessage());
            msg.setTextFill(Color.RED);
            card.getChildren().add(msg);
        }

        for (OrderHeader oh : ordersRes.getHashMap()) {

            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);

            Label info = new Label(
                "Order " + oh.getIdOrder()
              + " | User: " + oh.getCustomer()
              + " | Total: " + oh.getTotalAmount()
            );
            info.setFont(Font.font(15));

            ComboBox<Courier> cbCourier = new ComboBox<>();
            cbCourier.getItems().addAll(couriersRes.getHashMap());
            cbCourier.setPromptText("Select courier");

            Button assignBtn = new Button("Assign");
            assignBtn.setStyle("-fx-background-color:#1e88e5; -fx-text-fill:white; -fx-background-radius:6;");

            Button cancelBtn = new Button("Cancel Order");
            cancelBtn.setStyle("-fx-background-color:#e53935; -fx-text-fill:white; -fx-background-radius:6;");

            Label status = new Label();

            // assign to courier handler
            assignBtn.setOnAction(e -> {
                Courier selected = cbCourier.getValue();
                String result = handler.assignOrderToCourier(
                    oh.getIdOrder(),
                    selected == null ? null : selected.getIdUser()
                );

                status.setText(result);
                status.setTextFill(result.contains("success") ? Color.GREEN : Color.RED);
            });

            // cancel order handler
            cancelBtn.setOnAction(e -> {
                String result = handler.cancelOrder(oh.getIdOrder());
                status.setText(result);
                status.setTextFill(result.contains("success") ? Color.GREEN : Color.RED);

                if (result.contains("success")) {
                    info.setText(info.getText() + " (CANCELLED)");
                    info.setTextFill(Color.RED);
                    cbCourier.setDisable(true);
                    assignBtn.setDisable(true);
                    cancelBtn.setDisable(true);
                }
            });

            row.getChildren().addAll(info, cbCourier, assignBtn, cancelBtn, status);
            card.getChildren().add(row);
        }
    }

    // fetching assigned to courier orders
    private void loadAssignedOrders(VBox card) {
        MultipleObjectsResponse<OrderHeader> res = handler.getAssignedOrders();
        for (OrderHeader oh : res.getHashMap()) {

            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);

            Label info = new Label(
                "Order " + oh.getIdOrder()
              + " | Courier: " + oh.getAssignedCourier()
              + " | Status: " + oh.getStatus()
              + " | Total: " + oh.getTotalAmount()
            );
            info.setFont(Font.font(15));

            Button cancelBtn = new Button("Cancel Order");
            cancelBtn.setStyle("-fx-background-color:#e53935; -fx-text-fill:white; -fx-background-radius:6;");

            Label status = new Label();

            cancelBtn.setOnAction(e -> {
                String result = handler.cancelOrder(oh.getIdOrder());
                status.setText(result);
                status.setTextFill(result.contains("success") ? Color.GREEN : Color.RED);
            });

            // if order cancelled, then disable
            if (oh.getStatus().equalsIgnoreCase("cancelled")) {
                info.setTextFill(Color.RED);
                info.setText(info.getText() + " (CANCELLED)");
                cancelBtn.setDisable(true);
            }

            row.getChildren().addAll(info, cancelBtn, status);
            card.getChildren().add(row);
        }
    }

    // fetch completed orders
    private void loadCompletedOrders(VBox card) {
        MultipleObjectsResponse<OrderHeader> res = handler.getCompletedOrders();
        if (!res.getErrorUserMessage().isEmpty()) {
            Label msg = new Label(res.getErrorUserMessage());
            msg.setTextFill(Color.RED);
            card.getChildren().add(msg);
            return;
        }

        if (res.getHashMap().isEmpty()) {
            Label empty = new Label("No completed orders yet.");
            empty.setTextFill(Color.GRAY);
            empty.setFont(Font.font(14));
            card.getChildren().add(empty);
            return;
        }

        for (OrderHeader oh : res.getHashMap()) {

            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);

            String courierName = oh.getAssignedCourier() == null
                    ? "(No courier assigned?)"
                    : oh.getAssignedCourier();

            Label info = new Label(
                "Order " + oh.getIdOrder()
              + " | Courier: " + courierName
              + " | Total: " + oh.getTotalAmount()
            );
            info.setFont(Font.font(15));
            info.setTextFill(Color.GREEN);

            Label tag = new Label("(COMPLETED)");
            tag.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 14));
            tag.setTextFill(Color.GREEN);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            row.getChildren().addAll(info, spacer, tag);
            card.getChildren().add(row);
        }
    }

    // fetch product stock
    private void loadStockSection(VBox card) {
        MultipleObjectsResponse<Product> productsRes = handler.getAllProducts();
        for (Product p : productsRes.getHashMap()) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);

            Label info = new Label(
                p.getIdProduct() + " - " + p.getName()
              + " | Current stock: " + p.getStock()
            );
            info.setFont(Font.font(15));

            TextField input = new TextField();
            input.setPromptText("New stock");
            input.setMaxWidth(80);

            Button updateBtn = new Button("Update");
            updateBtn.setStyle("-fx-background-color:#1e88e5; -fx-text-fill:white; -fx-background-radius:6;");

            Label status = new Label();
            
            updateBtn.setOnAction(e -> {
            	int inputTextToInt = Integer.parseInt(input.getText());
                String result = handler.updateProductStock(p.getIdProduct(), inputTextToInt);
                status.setText(result);
                status.setTextFill(result.contains("success") ? Color.GREEN : Color.RED);
            });

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            row.getChildren().addAll(info, spacer, input, updateBtn, status);
            card.getChildren().add(row);
        }
    }
}