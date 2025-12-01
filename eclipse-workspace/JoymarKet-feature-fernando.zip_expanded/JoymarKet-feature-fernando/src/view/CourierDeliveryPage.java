package view;

import controller.DeliveryHandler;

import auth.SessionManager;
import model.Delivery;
import model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CourierDeliveryPage {

    private TableView<Delivery> table = new TableView<>();
    private ComboBox<String> statusBox = new ComboBox<>();
    private DeliveryHandler handler = new DeliveryHandler();

    public CourierDeliveryPage(Stage stage) {
        stage.setTitle("Courier Deliveries");

        // Status Options
        statusBox.getItems().addAll("Pending", "In Progress", "Delivered");
        statusBox.setPromptText("Select Status");

        // Table columns
        TableColumn<Delivery, String> colIdOrder = new TableColumn<>("Order ID");
        colIdOrder.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdOrder()));

        TableColumn<Delivery, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

        table.getColumns().addAll(colIdOrder, colStatus);

        loadDeliveries();

        // Update Status Button
        Button btnUpdate = new Button("Update Status");
        btnUpdate.setOnAction(e -> updateStatus());

        VBox root = new VBox(20, table, statusBox, btnUpdate);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 600, 500));
        stage.show();
    }

    private void loadDeliveries() {
        try {
            User courier = SessionManager.getUser();
            table.getItems().setAll(Delivery.getAssignedDeliveries(courier.getIdUser()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStatus() {
        Delivery selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a delivery.");
            return;
        }

        String newStatus = statusBox.getValue();
        if (newStatus == null) {
            showAlert("Please choose a status.");
            return;
        }

        String courierId = SessionManager.getUser().getIdUser();
        String result = handler.updateDeliveryStatus(selected.getIdOrder(), courierId, newStatus);

        showAlert(result);
        loadDeliveries();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}