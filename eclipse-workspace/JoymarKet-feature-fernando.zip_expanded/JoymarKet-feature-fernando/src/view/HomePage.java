package view;

import java.sql.SQLException;
import auth.SessionManager;
import components.Navbar;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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

public class HomePage extends Application {

    Scene scene;
    BorderPane borderPane;

    Label topUpBalance;
    Label desc;
    Label balanceLabel;
    Label balanceAmount;

    VBox vbox_homepage_no_logged;
    VBox balanceCard;
    HBox hBox_balance_btn;

    public HomePage() {

        borderPane = new BorderPane();
        scene = new Scene(borderPane, 500, 500);

        if (SessionManager.getUser() == null) {

            vbox_homepage_no_logged = new VBox(15);
            vbox_homepage_no_logged.setAlignment(Pos.CENTER);

            Label title = new Label("JoymarKet");
            title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
            title.setTextFill(Color.BLACK);

            desc = new Label(
                "Welcome to our digital marketplace for fresh food, meats, and groceries, delivered instantly to your door. Log in to explore personalized selections and enjoy a faster, fresher shopping experience. Get started today!"
            );
            desc.setWrapText(true);
            desc.setTextAlignment(TextAlignment.CENTER);
            desc.setMaxWidth(450);

            vbox_homepage_no_logged.getChildren().addAll(title, desc);
            borderPane.setCenter(vbox_homepage_no_logged);
            return;
        }


        hBox_balance_btn = new HBox();
        hBox_balance_btn.setAlignment(Pos.CENTER);
        
        
        balanceCard = new VBox(10);
        balanceCard.setPadding(new Insets(20));
        balanceCard.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.08), 8, 0, 0, 3);"
        );
        balanceCard.setMinWidth(400);
        balanceCard.setMaxWidth(400);
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

        Button topUpBtn = new Button("+");
        topUpBtn.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        topUpBtn.setStyle(
            "-fx-background-radius: 50; -fx-background-color: #4CAF50; -fx-text-fill: white;"
        );
        
	    Region spacer1 = new Region();
	    HBox.setHgrow(spacer1, Priority.ALWAYS);
        
        
        hBox_balance_btn.getChildren().addAll(balanceAmount, spacer1, topUpBtn);
        balanceCard.getChildren().addAll(balanceLabel, hBox_balance_btn);
        borderPane.setCenter(balanceCard);
        
        VBox menuBox = new VBox(10);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(20));
        
        // admin menu
        if (SessionManager.getUser().getRole().equals("admin")) {
            Button manageProductBtn = new Button("Manage Product Stock");
            manageProductBtn.setOnAction(e -> {
                try {
                    // new AdminProductPage().start((Stage) manageProductBtn.getScene().getWindow());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            menuBox.getChildren().add(manageProductBtn);
        }
        
        // courier menu
        if (SessionManager.getUser().getRole().equals("courier")) {
            Button courierBtn = new Button("My Deliveries");
            courierBtn.setOnAction(e -> {
                try {
                    // new CourierDeliveryPage().start((Stage) courierBtn.getScene().getWindow());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            menuBox.getChildren().add(courierBtn);
        }
        
        // customer menu
        if (SessionManager.getUser().getRole().equals("customer")) {
            Button cartBtn = new Button("View Cart");
            cartBtn.setOnAction(e -> {
                try {
                    // new CartPage().start((Stage) cartBtn.getScene().getWindow());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            menuBox.getChildren().add(cartBtn);
        }
        
        borderPane.setBottom(menuBox);
        
        topUpBtn.setOnAction(e->{
        	try {
				new TopUpPage().start((Stage) topUpBtn.getScene().getWindow());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Navbar navbar = new Navbar(primaryStage);
        borderPane.setTop(navbar);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
