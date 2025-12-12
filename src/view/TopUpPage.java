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
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TopUpPage extends Application {

	Scene scene;
	VBox vbox_main;
	VBox vbox_formCard;
	BorderPane borderPane;
	
	Label formTitle;
	Label topupLabel;
	TextField topupField;
	Label errorMessage;
	Button topupBtn;
	
	public TopUpPage(){
		
	    borderPane = new BorderPane();
        scene = new Scene(borderPane, 800, 500);
	    vbox_main = new VBox(20);
	    vbox_main.setAlignment(Pos.CENTER);
	    vbox_main.setPadding(new Insets(30));
	    
        vbox_formCard = new VBox(20);
        vbox_formCard.setPadding(new Insets(25));
        vbox_formCard.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 3);");

        formTitle = new Label("Add Funds to Your Account");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        topupLabel = new Label("Top-up Amount:");
        topupLabel.setFont(Font.font("Arial", 15));

        topupField = new TextField();
        topupField.setPromptText("$ e.g. 25,000");
        topupField.setPrefHeight(30);
        topupField.setStyle("-fx-background-radius: 6; -fx-border-radius: 6;");

        errorMessage = new Label("");
        errorMessage.setManaged(false);
        errorMessage.setTextFill(Color.RED);
        errorMessage.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        topupBtn = new Button("Top-up Account");
        topupBtn.setPrefHeight(35);
        topupBtn.setMaxWidth(Double.MAX_VALUE);
        topupBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 15; -fx-font-weight: bold; -fx-background-radius: 6;");
        topupBtn.setOnMouseEntered(e -> topupBtn.setCursor(Cursor.HAND));

       
        vbox_formCard.getChildren().addAll(formTitle, topupLabel, topupField, errorMessage, topupBtn);
        vbox_main.getChildren().add(vbox_formCard);
        
        topupBtn.setOnAction(e->{
        	UserHandler UH = new UserHandler() ;
        	String idUser = SessionManager.getUser().getIdUser().toString();
        	
        	String result = UH.TopUpBalance(idUser, topupField.getText());
        	
        	if(!result.equals("Your Top Up is successful!")) {
                errorMessage.setTextFill(Color.RED);
        		if(result.contains("empty")) {
        			errorMessage.setText(result);
        			errorMessage.setManaged(true);
        		}
        		else {
        			errorMessage.setText("");
        			errorMessage.setManaged(false);
        		}
        		if(result.contains("valid")) {
        			errorMessage.setText(result);
        			errorMessage.setManaged(true);
        		}
        		else {
        			errorMessage.setText(result);
        			errorMessage.setManaged(false);
        		}
        		if(result.contains("10000")) {
        			errorMessage.setText(result);
        			errorMessage.setManaged(true);
        		}
        		else {
        			errorMessage.setText("");
        			errorMessage.setManaged(false);
        		}
        	}
        	else {
    			errorMessage.setText(result);
    			errorMessage.setManaged(true);
    	        errorMessage.setTextFill(Color.GREEN);
        	}
        });
	}
	
    @Override
    public void start(Stage primaryStage) {
        borderPane.setCenter(vbox_main);

        Navbar navbar = new Navbar(primaryStage);
        borderPane.setTop(navbar);
    	
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
