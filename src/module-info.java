module JoymarKet {
    requires java.sql;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    
    opens view;
    opens app to javafx.fxml;
    opens controller to javafx.fxml;
    exports app;
}
