package view;

import auth.SessionManager;
import components.Navbar;
import controller.UserHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.User;

public class EditProfilePage extends Application {

	Scene scene;
    BorderPane borderPane;

    VBox vbox_main;
    HBox buttonBox;
    HBox successBox;
    
    Label title;
    Label fullNameLabel;
    Label phoneLabel;
    Label addressLabel;
    
    Label errorFullNameLabel;
    Label errorPhoneLabel;
    Label errorAddressLabel;
    Label successMesssage;

    TextField fullNameField;
    TextField phoneField;
    
    TextArea addressArea;

    Button saveBtn;
    
    public EditProfilePage() {
        borderPane = new BorderPane();
        scene = new Scene(borderPane, 800, 500);

        vbox_main = new VBox(10);
        vbox_main.setAlignment(Pos.TOP_LEFT);
        vbox_main.setPadding(new Insets(40));
        vbox_main.setMaxWidth(420);
        vbox_main.setMaxHeight(400);
        vbox_main.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 3);");

        title = new Label("Edit Profile");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        fullNameLabel = new Label("Full Name");
        
        fullNameField = new TextField(SessionManager.getUser().getFullName());
        fullNameField.setPromptText("Enter your new full name");
        fullNameField.setPrefHeight(40);

        errorFullNameLabel = new Label("");
        errorFullNameLabel.setManaged(false);
        errorFullNameLabel.setTextFill(Color.RED);
        errorFullNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        phoneLabel = new Label("Phone");
        phoneField = new TextField(SessionManager.getUser().getPhone());
        phoneField.setPromptText("Enter your new phone number");
        phoneField.setPrefHeight(40);

        errorPhoneLabel = new Label("");
        errorPhoneLabel.setManaged(false);
        errorPhoneLabel.setTextFill(Color.RED);
        errorPhoneLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        addressLabel = new Label("Address");
        addressArea = new TextArea(SessionManager.getUser().getAddress());
        addressArea.setPrefHeight(80);
        addressArea.setWrapText(true);

        errorAddressLabel = new Label("");
        errorAddressLabel.setManaged(false);
        errorAddressLabel.setTextFill(Color.RED);
        errorAddressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        
        saveBtn = new Button("Save Changes");
        saveBtn.setPrefHeight(40);
        saveBtn.setStyle("-fx-background-color: #1A73E8; -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;");
        saveBtn.setOnMouseEntered(e -> saveBtn.setCursor(Cursor.HAND));

        successMesssage = new Label("");
        successMesssage.setManaged(false);
        successMesssage.setTextFill(Color.GREEN);
        successMesssage.setFont(Font.font("Arial",FontWeight.BOLD, 12));
        
        successBox = new HBox(successMesssage);
        successBox.setAlignment(Pos.CENTER);
        
        buttonBox = new HBox(saveBtn);
        buttonBox.setAlignment(Pos.CENTER);

        vbox_main.getChildren().addAll(
                title,
                fullNameLabel, fullNameField, errorFullNameLabel,
                phoneLabel, phoneField, errorPhoneLabel,
                addressLabel, addressArea, errorAddressLabel,
                buttonBox,
                successBox
        );
        
        saveBtn.setOnAction(e -> {

            UserHandler UH = new UserHandler();
            String result = UH.editProfile(
                SessionManager.getUser().getIdUser(),
                fullNameField.getText(),
                phoneField.getText(),
                addressArea.getText()
            );

            // reset
            errorFullNameLabel.setManaged(false);
            errorPhoneLabel.setManaged(false);
            errorAddressLabel.setManaged(false);
            successMesssage.setManaged(false);

            if (!result.equals("Edit Profile is successful!")) {

                if (result.contains("Full")) {
                    errorFullNameLabel.setText(result);
                    errorFullNameLabel.setManaged(true);
                }

                if (result.contains("Phone")) {
                    errorPhoneLabel.setText(result);
                    errorPhoneLabel.setManaged(true);
                }

                if (result.contains("Address")) {
                    errorAddressLabel.setText(result);
                    errorAddressLabel.setManaged(true);
                }

                return;
            }

            // Success
            successMesssage.setText(result);
            successMesssage.setManaged(true);

            try {
                String email = SessionManager.getUser().getEmail();
                User updatedUser = User.getUserDA().getUserByEmail(email);
                SessionManager.saveSession(updatedUser);

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Failed to reload session.");
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Navbar navbar = new Navbar(primaryStage);
        borderPane.setTop(navbar);

        borderPane.setCenter(vbox_main);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}