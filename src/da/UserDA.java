package da;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import app.Connect;
import exception.NoRowsAffectedException;
import model.Courier;
import model.Customer;

public class UserDA {
    private Connect connect = Connect.getInstance();
    public static UserDA userDA;

    public static UserDA getUserDA() {
        if (userDA == null) userDA = new UserDA();
        return userDA;
    }

    public void updateBalanceByIdUser(String idUser, Double newBalance) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        String query = "UPDATE users SET balance = " + newBalance + " WHERE idUser = " + idUser + ";";
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        ResultSet rs = (ResultSet) hm.get("resultSet");
        rs.next();
        if ((Integer) hm.get("rowsAffected") <= 0) throw new NoRowsAffectedException("Balance is not updated. Please try again.");
    }

    public Customer getCustomerByIdUser(String idUser) throws SQLException {
        // diagram 7 - checkout and place order
        String query = "SELECT idUser, fullName, email, password, phone, address, role FROM users WHERE role = 'customer' and idUser = " + idUser + ";";
        ResultSet rs = this.connect.execQuery(query);
        rs.next();
        return Customer.fromResultSet(rs);
    }

    public void insertCustomer(String fullName, String email, String password, String phone, String address) throws NoRowsAffectedException, SQLException { 
        // diagram 1 - register account
        String query = "INSERT INTO users (fullName, email, password, phone, address, role, balance) VALUES (" + fullName + ", " + email + ", " + password + ", " + phone + ", " + address + ", Customer, 0)";
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        ResultSet rs = (ResultSet) hm.get("resultSet");
        rs.next();
        if ((Integer) hm.get("rowsAffected") <= 0) throw new NoRowsAffectedException("Failed to create your account. Please try again.");
    }

    public HashMap<String, String> findCredentialsByEmail(String email) throws SQLException {
        // login
        String query = "SELECT email, password FROM users WHERE email = " + email + ";";
        ResultSet rs = this.connect.execQuery(query);
        rs.next();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("email", rs.getString("email"));
        hm.put("password", rs.getString("password"));
        return hm;
    }

    public ArrayList<Courier> findAllCouriers() throws SQLException {
        // diagram 10 - view all couriers
        String query = "SELECT idUser, fullName, phone, address, vehicleType, vehiclePlate FROM users WHERE role = 'courier'";
        ResultSet rs = this.connect.execQuery(query);
        ArrayList<Courier> couriers = new ArrayList<Courier>();
        while (rs.next()) {
            Courier courier = Courier.fromResultSet(rs);
            couriers.add(courier);
        }
        return couriers;
    }
}
