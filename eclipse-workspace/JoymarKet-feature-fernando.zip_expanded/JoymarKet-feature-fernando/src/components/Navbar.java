package components;

import auth.SessionManager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import view.Register;

public class Navbar extends HBox {

	Button homeBtn;
	Button EditProfileBtn;
	Button loginBtn;
	Button registerBtn;
	
	public Navbar(Stage stage) {		
	    homeBtn = new Button("Home");
	    homeBtn.setStyle("-fx-background-color: rgba(0,0,0,1); -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;");
	    homeBtn.setOnAction(e -> {
	    	try {
	    		view.HomePage home = new view.HomePage();
	    		home.start(stage);
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    	}
	    });
	    
	    EditProfileBtn = new Button("Edit Profile");
	    EditProfileBtn.setStyle("-fx-background-color: rgba(0,0,0,1); -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;");
	    EditProfileBtn.setOnAction(e->{
    		try {
    			view.EditProfilePage ep = new view.EditProfilePage();
				ep.start(stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	    });
	    
	    loginBtn = new Button("Login");
	    loginBtn.setStyle("-fx-background-color: rgba(0,0,0,1); -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;");
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

	    HBox.setHgrow(spacer1, Priority.ALWAYS);
	    spacer2.setPrefWidth(20);

	    if(SessionManager.getUser() != null) {
	    	this.getChildren().addAll(homeBtn, spacer1, EditProfileBtn);
	    }
	    else {
	    	this.getChildren().addAll(homeBtn, spacer1, loginBtn, spacer2, registerBtn);	    		    	
	    }
	    
	    this.setPadding(new Insets(20, 20, 20, 20)); 
    }
}
