package view;

import controller.UserHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
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

public class Register extends Application{

	Scene scene;
	BorderPane borderPane;
	
	Label registerLabel;
	Label fullNameLabel;
	Label emailLabel;
	Label passwordLabel;
	Label confirmPasswordLabel;
	Label phoneLabel;
	Label addressLabel;

	Label errorRegisterLabel;
	Label errorFullNameLabel;
	Label errorEmailLabel;
	Label errorPasswordLabel;
	Label errorConfirmPasswordLabel;
	Label errorPhoneLabel;
	Label errorAddressLabel;
	
	Hyperlink loginHyperlink;
	
	TextField fullNameTextField;
	TextField emailTextField;
	PasswordField passwordField;
	PasswordField confirmPasswordField;
	TextField phoneTextField;
	TextField addressTextField;
	
	Button registerBtn;
	
	VBox vbox_main;
	VBox vbox_fullName;
	VBox vbox_email;
	VBox vbox_password;
	VBox vbox_confirmPassword;	
	VBox vbox_phone;	
	VBox vbox_address;	

	UserHandler UH = new UserHandler();
	
	public Register() {
		vbox_main = new VBox();
		borderPane = new BorderPane();
        scene = new Scene(borderPane, 800, 500);

		borderPane.setCenter(vbox_main);
		vbox_main.setAlignment(Pos.CENTER);
		vbox_main.setMaxWidth(400);
		vbox_main.setMaxHeight(700);
		vbox_main.setPadding(new Insets(40));
		vbox_main.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 3);");

		borderPane.setPadding(new Insets(40));
		
		registerLabel = new Label("Register Page");
		registerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));			
		
		Region gap1 = new Region();
		gap1.setPrefHeight(20);
		Region gap2 = new Region();
		gap2.setPrefHeight(15);			
		Region gap3 = new Region();
		gap3.setPrefHeight(15);	
		Region gap4 = new Region();
		gap4.setPrefHeight(15);		
		Region gap5 = new Region();
		gap5.setPrefHeight(15);	
		Region gap6 = new Region();
		gap6.setPrefHeight(15);	
		Region gap7 = new Region();
		gap7.setPrefHeight(20);	
		Region gap8 = new Region();
		gap8.setPrefHeight(15);	
		
		vbox_fullName = new VBox();
		vbox_fullName.setAlignment(Pos.CENTER);
		vbox_fullName.setMinWidth(200);
		vbox_fullName.setMaxWidth(200);
		
		fullNameLabel = new Label("Full Name");
		fullNameLabel.setStyle("-fx-font-weight: bold;");
		fullNameTextField = new TextField();
		fullNameTextField.setPromptText("input your full name...");
		errorFullNameLabel = new Label("");
		errorFullNameLabel.setManaged(false);
		errorFullNameLabel.setTextFill(Color.RED);
		errorFullNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		
		vbox_fullName.getChildren().addAll(fullNameLabel, fullNameTextField, errorFullNameLabel);
		
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
		
		vbox_password.getChildren().addAll(passwordLabel, passwordField, errorPasswordLabel);
		
		vbox_confirmPassword = new VBox();
		vbox_confirmPassword.setAlignment(Pos.CENTER);
		vbox_confirmPassword.setMinWidth(200);
		vbox_confirmPassword.setMaxWidth(200);
		
		confirmPasswordLabel = new Label("Confirm Password");
		confirmPasswordLabel.setStyle("-fx-font-weight: bold;");
		confirmPasswordField = new PasswordField();
		confirmPasswordField.setPromptText("input the confirm password...");
		errorConfirmPasswordLabel = new Label("");
		errorConfirmPasswordLabel.setManaged(false);
		errorConfirmPasswordLabel.setTextFill(Color.RED);
		errorConfirmPasswordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		
		vbox_confirmPassword.getChildren().addAll(confirmPasswordLabel, confirmPasswordField, errorConfirmPasswordLabel);

		vbox_phone = new VBox();
		vbox_phone.setAlignment(Pos.CENTER);
		vbox_phone.setMinWidth(200);
		vbox_phone.setMaxWidth(200);
		
		phoneLabel = new Label("Phone Number");
		phoneLabel.setStyle("-fx-font-weight: bold;");
		phoneTextField = new TextField();
		phoneTextField.setPromptText("input your phone number...");
		errorPhoneLabel = new Label("");
		errorPhoneLabel.setManaged(false);
		errorPhoneLabel.setTextFill(Color.RED);
		errorPhoneLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		
		vbox_phone.getChildren().addAll(phoneLabel, phoneTextField, errorPhoneLabel);

		vbox_address = new VBox();
		vbox_address.setAlignment(Pos.CENTER);
		vbox_address.setMinWidth(200);
		vbox_address.setMaxWidth(200);
		
		addressLabel = new Label("Address");
		addressLabel.setStyle("-fx-font-weight: bold;");
		addressTextField = new TextField();
		addressTextField.setPromptText("input your Address...");
		errorAddressLabel = new Label("");
		errorAddressLabel.setManaged(false);
		errorAddressLabel.setTextFill(Color.RED);
		errorAddressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		
		vbox_address.getChildren().addAll(addressLabel, addressTextField, errorAddressLabel);

		registerBtn = new Button("Register");
		registerBtn.setMinWidth(200);
		registerBtn.setMaxWidth(200);
		registerBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 15; -fx-font-weight: bold; -fx-background-radius: 6;");
        registerBtn.setOnMouseEntered(e -> registerBtn.setCursor(Cursor.HAND));

		
		loginHyperlink = new Hyperlink("Already Haven an Account");
		loginHyperlink.setOnAction(e->{
		        try {
		            new Login().start((Stage) loginHyperlink.getScene().getWindow());
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
		});
		loginHyperlink.setStyle("-fx-font-weight: bold;");
		
		vbox_main.getChildren().addAll(registerLabel, gap1, vbox_fullName, gap2, vbox_email, gap3, vbox_password, gap4, vbox_confirmPassword, gap5, vbox_phone, gap6,vbox_address, gap7, registerBtn, gap8, loginHyperlink);

		registerBtn.setOnAction(e->{
			String result = UH.registerCustomer(fullNameTextField.getText(), emailTextField.getText(), passwordField.getText(), confirmPasswordField.getText(), phoneTextField.getText(), addressTextField.getText());
			if (!result.equals("Your register is successful!")) {
				if (result.toLowerCase().contains("fullname")) {
			        errorFullNameLabel.setText(result);
			        errorFullNameLabel.setManaged(true);
			    }
				else {
			        errorFullNameLabel.setText("");
			        errorFullNameLabel.setManaged(false);
				}

				if (result.toLowerCase().contains("email")) {
			        errorEmailLabel.setText(result);
			        errorEmailLabel.setManaged(true);
			    }
				else {
			        errorEmailLabel.setText("");
			        errorEmailLabel.setManaged(false);					
				}

				if (result.toLowerCase().contains("password") && !result.contains("Confirm")) {
			        errorPasswordLabel.setText(result);
			        errorPasswordLabel.setManaged(true);
			    }
				else {
					errorPasswordLabel.setText("");
					errorPasswordLabel.setManaged(false);					
				}

				if (result.toLowerCase().contains("confirm")) {
			        errorConfirmPasswordLabel.setText(result);
			        errorConfirmPasswordLabel.setManaged(true);
			    }
				else {
					errorConfirmPasswordLabel.setText("");
					errorConfirmPasswordLabel.setManaged(false);					
				}

				if (result.toLowerCase().contains("phone")) {
			        errorPhoneLabel.setText(result);
			        errorPhoneLabel.setManaged(true);
			    }
				else {
					errorPhoneLabel.setText("");
					errorPhoneLabel.setManaged(false);					
				}

				if (result.toLowerCase().contains("address")) {
			        errorAddressLabel.setText(result);
			        errorAddressLabel.setManaged(true);
			    }
				else {
					errorAddressLabel.setText("");
			        errorAddressLabel.setManaged(false);
				}

			} else {
		        try {
		            new Login().start((Stage) registerBtn.getScene().getWindow());
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
