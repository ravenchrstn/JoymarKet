package app;

import java.sql.*;
import java.util.HashMap;

public class Connect {
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "joymarKet";
    private final String HOST = "localhost:3306";
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s?autoReconnect=true&useSSL=false",HOST, DATABASE);

    private Connection con;   
    private Statement st;     

    private static Connect connect;

    public static Connect getInstance() {
        if (connect == null) {
            connect = new Connect();
        }
        return connect;
    }

    public Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);

            st = con.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet execQuery(String query) {
        try {
            return st.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, Object> execUpdate(String query) {
        HashMap<String, Object> hm = new HashMap<>();

        try (PreparedStatement ps =
                     con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            int rows = ps.executeUpdate();
            hm.put("rowsAffected", rows);

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                hm.put("generatedKey", rs.getObject(1));
            } else {
                hm.put("generatedKey", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            hm.put("rowsAffected", -1);
            hm.put("generatedKey", null);
        }

        return hm;
    }
}
