package app;

import java.sql.*;
import java.util.HashMap;

public class Connect {
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "JoymarKet";
    private final String HOST = "localhost:3306";
    private final String CONNECTION = String.format("jbdc:mysql://%/%s", HOST, DATABASE);

    private Connection conn;
    private Statement st;
    private static Connect connect;

    public static Connect getInstance() {
        if (connect == null) connect = new Connect();
        return connect;
    }

    private Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            this.st = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet execQuery(String query) {
        try {
            return st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, Object> execUpdate(String query) throws SQLException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("rowsAffected", this.st.executeUpdate(query));
        hm.put("resultSet", this.st.getGeneratedKeys());

        return hm;
    }
}
