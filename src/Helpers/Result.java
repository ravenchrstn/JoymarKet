package Helpers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Result {
    private ResultSet rs;
    private ResultSetMetaData rsm;

    public void closeRs() {
        try {
            this.rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;

        try {
            this.rsm = this.rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSetMetaData getRsm() {
        return rsm;
    }

}