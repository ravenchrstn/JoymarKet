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
        String query = String.format(
            "UPDATE customer SET balance = %.2f WHERE idCustomer = '%s';",
            newBalance, idUser
        );

        HashMap<String, Object> hm = this.connect.execUpdate(query);
        if ((Integer) hm.get("rowsAffected") <= 0)
            throw new NoRowsAffectedException("Balance is not updated. Please try again.");
    }

    public Double getBalanceByIdUser(String idUser) throws SQLException {
    	String query = String.format(
    			"SELECT balance " +
    					"FROM customer WHERE idCustomer = '%s';",
    					idUser
    			);
    	
    	ResultSet rs = this.connect.execQuery(query);
    	rs.next();
    	return rs.getDouble("balance");
    }
    
    public Customer getCustomerByIdUser(String idUser) throws SQLException {
        String query = String.format(
            "SELECT idUser, fullName, email, password, phone, address, role, balance " +
            "FROM user WHERE role = 'customer' AND idUser = '%s';",
            idUser
        );

        ResultSet rs = this.connect.execQuery(query);
        rs.next();
        return Customer.fromResultSet(rs);
    }

    public Customer getUserByEmail(String email) throws SQLException {
        String query = String.format(
                "SELECT u.idUser, u.fullName, u.email, u.password, u.phone, u.address, u.role, c.balance FROM user u JOIN customer c ON u.idUser = c.idCustomer WHERE u.email = '%s';", email
            );

        ResultSet rs = this.connect.execQuery(query);
        rs.next();
        return Customer.fromResultSet(rs);
    }
    
    public Customer getUserByIdl(String userId) throws SQLException {
        String query = String.format(
        		"SELECT u.idUser, u.fullName, u.email, u.password, u.phone, u.address, u.role, c.balance FROM user u JOIN customer c ON u.idUser = c.idCustomer WHERE u.idUser = '%s';", userId
            );

            ResultSet rs = this.connect.execQuery(query);
            rs.next();
            return Customer.fromResultSet(rs);
    }
    
    public int insertUser(String fullName, String email, String password, String phone, String address)
            throws NoRowsAffectedException, SQLException {

        String query = String.format(
            "INSERT INTO user (fullName, email, password, phone, address, role) " +
            "VALUES ('%s', '%s', '%s', '%s', '%s', 'customer');",
            fullName, email, password, phone, address
        );

        HashMap<String, Object> hm = this.connect.execUpdate(query);
        if ((Integer) hm.get("rowsAffected") <= 0)
            throw new NoRowsAffectedException("Failed to create your account. Please try again.");
        
        Object keyObj = hm.get("generatedKey");
        if (keyObj == null)
            throw new SQLException("Unable to retrieve user ID.");

        return ((Number) keyObj).intValue();
    }

    public void insertCustomer(int userId, Double balance)
            throws NoRowsAffectedException, SQLException {

        String query = String.format(
            "INSERT INTO customer (idCustomer, balance) " +
            "VALUES ('%d', '%f');",
            userId, balance
        );

        HashMap<String, Object> hm = this.connect.execUpdate(query);
        if ((Integer) hm.get("rowsAffected") <= 0)
            throw new NoRowsAffectedException("Failed to create your account. Please try again.");
    }
    
    
    
    public void updateUserById(String userId, String fullName, String phone, String address) throws NoRowsAffectedException, SQLException{
    	String query = String.format(
                "UPDATE user SET fullName = '%s', phone = '%s', address = '%s' WHERE idUser = '%s';",
                fullName, phone, address, userId
            );

            HashMap<String, Object> hm = this.connect.execUpdate(query);
            if ((Integer) hm.get("rowsAffected") <= 0)
                throw new NoRowsAffectedException("Balance is not updated. Please try again.");
        }
    
    public HashMap<String, String> findCredentialsByEmail(String email) throws SQLException {
        String query = String.format(
            "SELECT email, password FROM user WHERE email = '%s';",
            email
        );

        ResultSet rs = this.connect.execQuery(query);
        
        if (!rs.next()) {
            return null;
        }
        
        HashMap<String, String> hm = new HashMap<>();
        hm.put("email", rs.getString("email"));
        hm.put("password", rs.getString("password"));
        return hm;
    }

    public ArrayList<Courier> findAllCouriers() throws SQLException {
        String query = 
            "SELECT idUser, fullName, phone, address, vehicleType, vehiclePlate " +
            "FROM user WHERE role = 'courier';";

        ResultSet rs = this.connect.execQuery(query);
        ArrayList<Courier> couriers = new ArrayList<>();

        while (rs.next()) {
            couriers.add(Courier.fromResultSet(rs));
        }
        return couriers;
    }
}

