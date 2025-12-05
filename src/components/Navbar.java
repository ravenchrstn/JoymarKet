package components;

import auth.SessionManager;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import view.Register;

public class Navbar extends HBox {

	Button homeBtn;
	Button cartBtn;
	Button EditProfileBtn;
	Button loginBtn;
	Button registerBtn;
	
	public Navbar(Stage stage) {		
	    homeBtn = new Button("Home");
	    homeBtn.setStyle("-fx-background-color: rgba(0,0,0,1); -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;");
        homeBtn.setOnMouseEntered(e -> homeBtn.setCursor(Cursor.HAND));
	    homeBtn.setOnAction(e -> {
	    	try {
	    		view.HomePage home = new view.HomePage();
	    		home.start(stage);
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    	}
	    });
	    
	    cartBtn = new Button("Cart");
	    cartBtn.setStyle("-fx-background-color: rgba(0,0,0,1); -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;");
	    cartBtn.setOnMouseEntered(e -> cartBtn.setCursor(Cursor.HAND));
	    cartBtn.setOnAction(e -> {
	        new view.CartPage(stage).show();
	    });

	    
	    EditProfileBtn = new Button("Edit Profile");
	    EditProfileBtn.setStyle("-fx-background-color: rgba(0,0,0,1); -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;");
        EditProfileBtn.setOnMouseEntered(e -> EditProfileBtn.setCursor(Cursor.HAND));
	    EditProfileBtn.setOnAction(e->{
    		try {
    			view.EditProfilePage ep = new view.EditProfilePage();
				ep.start(stage);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	    });
	    
	    loginBtn = new Button("Login");
	    loginBtn.setStyle("-fx-background-color: rgba(0,0,0,1); -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;");
        loginBtn.setOnMouseEntered(e -> loginBtn.setCursor(Cursor.HAND));
	    loginBtn.setOnAction(e -> {
	    	try {
	    		view.Login login = new view.Login();
	    		login.start(stage);
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    	}
	    });
	    
	    registerBtn = new Button("Register");
	    registerBtn.setStyle("-fx-background-color: rgba(0,0,0,1); -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;");
        registerBtn.setOnMouseEntered(e -> registerBtn.setCursor(Cursor.HAND));
        registerBtn.setOnAction(e -> {
            try {
                Register registerPage = new Register();
                registerPage.start((Stage) registerBtn.getScene().getWindow());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

	    Region spacer1 = new Region();
	    Region spacer2 = new Region();
	    
	    Region spacer1_1 = new Region();
	    Region spacer1_2 = new Region();

	    spacer1.setPrefWidth(20);
	    HBox.setHgrow(spacer2, Priority.ALWAYS);
	    
	    HBox.setHgrow(spacer1_1, Priority.ALWAYS);
	    spacer1_2.setPrefWidth(20);

	    if(SessionManager.getUser() != null) {
	    	this.getChildren().addAll(homeBtn, spacer1, cartBtn, spacer2, EditProfileBtn);
	    }
	    else {
	    	this.getChildren().addAll(homeBtn, spacer1_1, loginBtn, spacer1_2, registerBtn);	    		    	
	    }
	    
	    this.setPadding(new Insets(20, 20, 20, 20)); 
    }
}
