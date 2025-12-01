package view;

import components.Navbar;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CartPage extends Application{

	BorderPane borderPane;
	Scene scene;
	VBox vbox_main;
	
	public CartPage() {
	    borderPane = new BorderPane();
	    scene = new Scene(borderPane, 500, 500); 
	    vbox_main = new VBox(20);
	    vbox_main.setAlignment(Pos.CENTER);
	    vbox_main.setPadding(new Insets(30));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        borderPane.setCenter(vbox_main);

        Navbar navbar = new Navbar(primaryStage);
        borderPane.setTop(navbar);
    	
        primaryStage.setScene(scene);
        primaryStage.show();
	}

}
