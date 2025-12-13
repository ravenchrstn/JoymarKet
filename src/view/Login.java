package view;
import java.util.ArrayList;

import auth.SessionManager;
import controller.UserHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.OrderHeader;
import model.User;

public class Login extends Application{

		Scene scene;
		BorderPane borderPane;
		
		Label loginLabel;
		Label emailLabel;
		Label passwordLabel;
		TextField emailTextField;
		PasswordField passwordField;
		Button loginBtn;
		
		Hyperlink registerHyperlink;
		
		Label errorEmailLabel;
		Label errorPasswordLabel;
		
		VBox vbox_main;
		VBox vbox_email;
		VBox vbox_password;
		
		UserHandler UH = new UserHandler();
		
		public Login() { 
			
			vbox_main = new VBox();
			borderPane = new BorderPane();
	        scene = new Scene(borderPane, 800, 500);

			borderPane.setCenter(vbox_main);
			vbox_main.setAlignment(Pos.CENTER);
			vbox_main.setMaxWidth(350);
			vbox_main.setMaxHeight(350);
			vbox_main.setPadding(new Insets(40));
			vbox_main.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 3);");
			
			borderPane.setPadding(new Insets(40));

			loginLabel = new Label("Login Page");
			loginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));			

			Region gap1 = new Region();
			gap1.setPrefHeight(20);
			Region gap2 = new Region();
			gap2.setPrefHeight(10);			
			Region gap3 = new Region();
			gap3.setPrefHeight(15);	
			Region gap4 = new Region();
			gap4.setPrefHeight(15);

			vbox_email = new VBox();
			vbox_email.setAlignment(Pos.CENTER);
			vbox_email.setMinWidth(200);
			vbox_email.setMaxWidth(200);
			
			emailLabel = new Label("Email");
			emailLabel.setStyle("-fx-font-weight: bold;");
			emailTextField = new TextField();
			emailTextField.setPromptText("input your email...");
			
			errorEmailLabel = new Label("");
			errorEmailLabel.setManaged(false);
			errorEmailLabel.setTextFill(Color.RED);
			errorEmailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
			
			vbox_email.getChildren().addAll(emailLabel, emailTextField, errorEmailLabel);

			vbox_password = new VBox();
			vbox_password.setAlignment(Pos.CENTER);
			vbox_password.setMinWidth(200);
			vbox_password.setMaxWidth(200);
			
			passwordLabel = new Label("Password");
			passwordLabel.setStyle("-fx-font-weight: bold;");
			passwordField = new PasswordField();
			passwordField.setPromptText("input your password...");
			
			errorPasswordLabel = new Label("");
			errorPasswordLabel.setManaged(false);
			errorPasswordLabel.setTextFill(Color.RED);
			errorPasswordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
			
			vbox_password.getChildren().addAll(passwordLabel, passwordField, errorPasswordLabel );

			loginBtn = new Button("Login");
			loginBtn.setMinWidth(200);
			loginBtn.setMaxWidth(200);
			loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 15; -fx-font-weight: bold; -fx-background-radius: 6;");
            loginBtn.setOnMouseEntered(e -> loginBtn.setCursor(Cursor.HAND));

			registerHyperlink = new Hyperlink("Don't have an account yet");
			registerHyperlink.setOnAction(e->{
		        try {
		            new Register().start((Stage) registerHyperlink.getScene().getWindow());
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
			});
			registerHyperlink.setStyle("-fx-font-weight: bold;");
			
			vbox_main.getChildren().addAll(loginLabel, gap1, vbox_email, gap2, vbox_password, gap3, loginBtn, gap4, registerHyperlink);

			loginBtn.setOnAction(e -> {
			    String result = UH.login(emailTextField.getText(), passwordField.getText());

			    if (!result.equals("Your login is successful!")) {

			        if (result.toLowerCase().contains("email")) {
			            errorEmailLabel.setText(result);
			            errorEmailLabel.setManaged(true);
			        } else {
			            errorEmailLabel.setText("");
			            errorEmailLabel.setManaged(false);
			        }

			        if (result.toLowerCase().contains("password")) {
			            errorPasswordLabel.setText(result);
			            errorPasswordLabel.setManaged(true);
			        } else {
			            errorPasswordLabel.setText("");
			            errorPasswordLabel.setManaged(false);
			        }

			    } else {
			        try {
			            User loggedUser = SessionManager.getUser();
			            Stage stage = (Stage) loginBtn.getScene().getWindow();

			            if (loggedUser.getRole().equalsIgnoreCase("Customer")) {

			                String customerId = loggedUser.getIdUser();

			                ArrayList<OrderHeader> cancelledOrders =
			                        OrderHeader.getUnnotifiedCancelledOrders(customerId);

			                if (!cancelledOrders.isEmpty()) {

			                    OrderHeader oh = cancelledOrders.get(0);

			                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
			                    alert.setTitle("Order Cancelled");
			                    alert.setHeaderText("Your order has been cancelled");
			                    alert.setContentText("Order " + oh.getIdOrder() + " was cancelled by the admin.");
			                    alert.showAndWait();

			                    for (OrderHeader o : cancelledOrders) {
			                        OrderHeader.markCustomerNotified(o.getIdOrder());
			                    }
			                }
			                
			                ArrayList<OrderHeader> completedOrders =
			                	    OrderHeader.getUnnotifiedCompletedOrders(customerId);

			                	if (!completedOrders.isEmpty()) {
			                	    OrderHeader oh = completedOrders.get(0);

			                	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
			                	    alert.setTitle("Order Completed");
			                	    alert.setHeaderText("Your order has been completed");
			                	    alert.setContentText("Order " + oh.getIdOrder() + " was successfully delivered!");
			                	    alert.showAndWait();

			                	    for (OrderHeader o : completedOrders) {
			                	        OrderHeader.markCustomerNotifiedCompleted(o.getIdOrder());
			                	    }
			                	}


			                new HomePage().start(stage);

			            } else if (loggedUser.getRole().equalsIgnoreCase("Admin")) {
			                new AdminPage().start(stage);

			            } else if (loggedUser.getRole().equalsIgnoreCase("Courier")) {
			                new CourierPage().start(stage);
			            }

			        } catch (Exception ex) {
			            ex.printStackTrace();
			        }
			    }
			});
		}
		
		@Override
		public void start(Stage primaryStage) throws Exception {
			primaryStage.setScene(scene);
			primaryStage.show();
		}
    
}
