package view;

import java.util.ArrayList;

import auth.SessionManager;
import components.Navbar;
import controller.CartItemHandler;
import exception.InvalidInputException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.CartItem;
import model.Product;

public class CartPage extends Application {

    BorderPane borderPane;
    Scene scene;

    public CartPage() {
        borderPane = new BorderPane();
        scene = new Scene(borderPane, 500, 500);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Navbar navbar = new Navbar(primaryStage);
        borderPane.setTop(navbar);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
