package App;

import java.sql.*;
import Helpers.Result;

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

    public Result execQuery(String query) {
        Result result = new Result();
        try {
            result.setRs(st.executeQuery(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String execUpdate(String query) {
        String id = "-2";
        try {
            int rowsAffected = this.st.executeUpdate(query);
            if (rowsAffected <= 0) return "-1";

            ResultSet rs = this.st.getGeneratedKeys();
            id = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
