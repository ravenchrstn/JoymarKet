package view;

import java.util.ArrayList;

import auth.SessionManager;
import components.Navbar;
import controller.CourierHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.OrderHeader;
import response.MultipleObjectsResponse;

public class CourierPage extends Application {

    private Scene scene;
    private final CourierHandler handler = new CourierHandler();

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();
        Navbar navbar = new Navbar(stage);
        root.setTop(navbar);

        ScrollPane sc = new ScrollPane();
        sc.setFitToWidth(true);

        VBox container = new VBox(25);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.TOP_CENTER);

        // title
        Label title = new Label("Courier Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        VBox titleCard = new VBox(title);
        titleCard.setAlignment(Pos.CENTER);
        titleCard.setPadding(new Insets(20));
        titleCard.setStyle(
            "-fx-background-color:white; -fx-background-radius:12;"
            + "-fx-effect:dropshadow(two-pass-box, rgba(0,0,0,0.1), 10,0,0,4);"
        );
        titleCard.setMaxWidth(600);
        container.getChildren().add(titleCard);

        // fetch active and completed orders/deliveries
        String courierId = SessionManager.getUser().getIdUser();

        MultipleObjectsResponse<OrderHeader> activeRes =
            handler.getAssignedDeliveries(courierId);

        MultipleObjectsResponse<OrderHeader> completedRes =
            handler.getCompletedDeliveries(courierId);

        VBox activeCard = createCard("Active Deliveries");
        VBox completedCard = createCard("Completed Deliveries");

        // FILTER ACTIVE + COMPLETED + CANCELLED
        ArrayList<OrderHeader> activeDeliveries = new ArrayList<>();
        ArrayList<OrderHeader> completedDeliveries = new ArrayList<>();

        if (activeRes.getHashMap() != null) {

            for (OrderHeader oh : activeRes.getHashMap()) {

                if (oh.getStatus().equalsIgnoreCase("completed")) {
                    completedDeliveries.add(oh);
                    continue;
                }

                if (oh.getStatus().equalsIgnoreCase("cancelled")) {
                    continue; // if not showing cancelled
                }

                // only pending / on-delivery
                activeDeliveries.add(oh);
            }
        }

        // displaying active deliveries
        if (activeDeliveries.isEmpty()) {
            Label none = new Label("No deliveries assigned.");
            none.setFont(Font.font(15));
            none.setTextFill(Color.GRAY);
            activeCard.getChildren().add(none);
        } else {
            for (OrderHeader oh : activeDeliveries) {
                activeCard.getChildren().add(renderActiveRow(oh, courierId));
            }
        }

        // displaying completed deliveries
        if (completedRes.getHashMap() != null && !completedRes.getHashMap().isEmpty()) {
            for (OrderHeader oh : completedRes.getHashMap()) {
                completedCard.getChildren().add(renderCompletedRow(oh));
            }
        } else {
            Label none = new Label("No completed deliveries yet.");
            none.setFont(Font.font(14));
            none.setTextFill(Color.GRAY);
            completedCard.getChildren().add(none);
        }

        // add cards
        container.getChildren().addAll(activeCard, completedCard);

        sc.setContent(container);
        root.setCenter(sc);

        scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();
    }

    // UI HELPERS
    private VBox createCard(String titleText) {
        VBox card = new VBox(20);
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

    private HBox renderCompletedRow(OrderHeader oh) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label info = new Label(
            "Order " + oh.getIdOrder()
            + " | Customer: " + oh.getCustomer()
            + " | Total: " + oh.getTotalAmount()
        );
        info.setFont(Font.font(15));
        info.setTextFill(Color.GREEN);

        Label tag = new Label("(COMPLETED)");
        tag.setFont(Font.font(14));
        tag.setTextFill(Color.GREEN);

        row.getChildren().addAll(info, tag);
        return row;
    }

    private HBox renderActiveRow(OrderHeader oh, String courierId) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label info = new Label(
            "Order " + oh.getIdOrder()
            + " | Customer: " + oh.getCustomer()
            + " | Total: " + oh.getTotalAmount()
        );
        info.setFont(Font.font(15));

        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.getItems().addAll("pending", "in progress", "completed");
        statusBox.setPromptText("Update status");

        Button updateBtn = new Button("Update");
        updateBtn.setStyle("-fx-background-color:#1e88e5; -fx-text-fill:white; -fx-background-radius:6;");

        Label statusMsg = new Label();
        statusMsg.setFont(Font.font(14));

        // cancelled? then disable (dont show)
        if ("cancelled".equalsIgnoreCase(oh.getStatus())) {
            info.setTextFill(Color.RED);
            info.setText(info.getText() + " (CANCELLED)");
            statusBox.setDisable(true);
            updateBtn.setDisable(true);
        }

        updateBtn.setOnAction(e -> {
            String selected = statusBox.getValue();
            if (selected == null) {
                statusMsg.setText("Select a status.");
                statusMsg.setTextFill(Color.RED);
                return;
            }

            String result = handler.updateDeliveryStatus(oh.getIdOrder(), courierId, selected);
            statusMsg.setText(result);
            statusMsg.setTextFill(result.contains("success") ? Color.GREEN : Color.RED);
        });

        row.getChildren().addAll(info, statusBox, updateBtn, statusMsg);
        return row;
    }
}