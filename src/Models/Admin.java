package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User {
    private String emergencyContact;

    public Admin(String idAdmin, String fullName, String email, String password, String phone, String address, String emergencyContact) {
        super(idAdmin, fullName, email, password, phone, address, "Admin");
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public static Admin fromResultSet(ResultSet rs) throws SQLException {
        return new Admin(rs.getString("idUser"), rs.getString("fullName"), rs.getString("email"), rs.getString("password"), rs.getString("phone"), rs.getString("address"), rs.getString("emergencyContact"));

    }
}
