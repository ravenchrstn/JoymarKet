package components;

import auth.SessionManager;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Navbar extends HBox {

    private Stage stage;

    Button homeBtn;
    Button cartBtn;
    Button editProfileBtn;
    Button loginBtn;
    Button registerBtn;
    Button logoutBtn;

    public Navbar(Stage stage) {
        this.stage = stage;

        setPadding(new Insets(20));
        setSpacing(15);

        createButtons();
        updateButtonActions();
        rebuildUI();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        updateButtonActions();
    }

    private void createButtons() {

        homeBtn = new Button("Home");
        cartBtn = new Button("Cart");
        editProfileBtn = new Button("Edit Profile");
        loginBtn = new Button("Login");
        registerBtn = new Button("Register");
        logoutBtn = new Button("Logout");

        String primary =
            "-fx-background-color: black; -fx-text-fill: white; " +
            "-fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;";

        homeBtn.setStyle(primary);
        cartBtn.setStyle(primary);
        editProfileBtn.setStyle(primary);
        loginBtn.setStyle(primary);
        registerBtn.setStyle(primary);

        logoutBtn.setStyle(
            "-fx-background-color: red; -fx-text-fill: white; " +
            "-fx-background-radius: 6; -fx-font-size: 15px; -fx-font-weight: bold;"
        );
    }

    private void updateButtonActions() {

    	homeBtn.setOnAction(e -> {
    	    try {
    	        if (SessionManager.getUser() == null) {
    	            new view.HomePage().start(stage);
    	            return;
    	        }

    	        String role = SessionManager.getUser().getRole().trim().toLowerCase();

    	        switch (role) {
    	            case "admin":
    	                new view.AdminPage().start(stage);
    	                break;
    	            case "courier":
    	                new view.CourierPage().start(stage);
    	                break;
    	            case "customer":
    	            default:
    	                new view.HomePage().start(stage);
    	                break;
    	        }
    	    } catch (Exception ex) {
    	        ex.printStackTrace();
    	    }
    	});

    	cartBtn.setOnAction(e -> {
    	    try {
    	        new view.CartPage(stage).show();
    	    } catch (Exception ex) {
    	        ex.printStackTrace();
    	    }
    	});

        editProfileBtn.setOnAction(e -> {
            try { new view.EditProfilePage().start(stage); }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        loginBtn.setOnAction(e -> {
            try { new view.Login().start(stage); }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        registerBtn.setOnAction(e -> {
            try { new view.Register().start(stage); }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        logoutBtn.setOnAction(e -> {
            SessionManager.clearSession();
            try { new view.HomePage().start(stage); }
            catch (Exception ex) { ex.printStackTrace(); }
        });
    }

    private void rebuildUI() {
        this.getChildren().clear();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        if (SessionManager.getUser() == null) {
            // guest
            this.getChildren().addAll(homeBtn, spacer, loginBtn, registerBtn);
            return;
        }

        String role = SessionManager.getUser().getRole().trim().toLowerCase();

        switch (role) {
            case "admin":
                this.getChildren().addAll(homeBtn, editProfileBtn, spacer, logoutBtn);
                break;

            case "courier":
                this.getChildren().addAll(homeBtn, editProfileBtn, spacer, logoutBtn);
                break;

            case "customer":
                this.getChildren().addAll(homeBtn, cartBtn, editProfileBtn, spacer, logoutBtn);
                break;
        }
    }
}
