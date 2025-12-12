package da;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import app.Connect;
import exception.NoRowsAffectedException;
import model.Admin;
import model.Courier;
import model.Customer;
import model.User;

public class UserDA {
	private Connect connect = Connect.getInstance();
	public static UserDA userDA;

	public static UserDA getUserDA() {
		if (userDA == null)
			userDA = new UserDA();
		return userDA;
	}

	// updates customer's balance
	public void updateBalanceByIdUser(String idUser, Double newBalance) throws NoRowsAffectedException, SQLException {
		String query = String.format("UPDATE customer SET balance = %.2f WHERE idCustomer = '%s';", newBalance, idUser);

		HashMap<String, Object> hm = this.connect.execUpdate(query);
		if ((Integer) hm.get("rowsAffected") <= 0)
			throw new NoRowsAffectedException("Balance is not updated. Please try again.");
	}

	// fetch customer's current balance
	public Double getBalanceByIdUser(String idUser) throws SQLException {
		String query = String.format("SELECT balance " + "FROM customer WHERE idCustomer = '%s';", idUser);

		ResultSet rs = this.connect.execQuery(query);
		rs.next();
		return rs.getDouble("balance");
	}
	
	// deduct money from checkout (customer)
	public void reduceBalance(String idUser, double amount) throws SQLException, NoRowsAffectedException {
		double current = getBalanceByIdUser(idUser);
		double updated = current - amount;

		if (updated < 0) {
			throw new NoRowsAffectedException("Insufficient balance.");
		}

		updateBalanceByIdUser(idUser, updated);
	}

	// load customer's full profile info by ID
	public Customer getCustomerByIdUser(String idUser) throws SQLException {
		String query = String
				.format("SELECT u.idUser, u.fullName, u.email, u.password, u.phone, u.address, u.role, c.balance "
						+ "FROM user u JOIN customer c ON u.idUser = c.idCustomer WHERE u.idUser = '%s';", idUser);

		ResultSet rs = this.connect.execQuery(query);
		rs.next();
		return Customer.fromResultSet(rs);
	}

	// authenticate email
	public User getUserByEmail(String email) throws SQLException {
		String query = "SELECT * FROM user WHERE email = '" + email + "'";
		ResultSet rs = connect.execQuery(query);

		if (!rs.next()) {
		    return null; // user not found
		}

		String role = rs.getString("role");
		int id = rs.getInt("idUser");
		
		if (role.equalsIgnoreCase("customer")) {
			String customerQuery =
				"SELECT u.*, c.balance " +
				"FROM user u " +
				"JOIN customer c ON u.idUser = c.idCustomer " +
				"WHERE u.idUser = " + id;
			 
			 ResultSet customerRS = connect.execQuery(customerQuery);
		     if (customerRS.next()) return Customer.fromResultSet(customerRS);
		} else if (role.equalsIgnoreCase("courier")) {
			String courierQuery =
				"SELECT u.*, c.vehicleType, c.vehiclePlate " +
				"FROM user u " +
			    "JOIN courier c ON u.idUser = c.idCourier " +
			    "WHERE u.idUser = " + id;

			ResultSet courierRS = connect.execQuery(courierQuery);
			
			if (courierRS.next()) {
	            return Courier.fromResultSet(courierRS);
	        }	        
		} else if (role.equalsIgnoreCase("admin")) {
		    String adminQuery = 
		        "SELECT u.*, a.emergencyContact " +
		        "FROM user u " +
		        "JOIN admin a ON u.idUser = a.idAdmin " +
		        "WHERE u.idUser = " + rs.getInt("idUser");

		    ResultSet adminRS = connect.execQuery(adminQuery);

		    if (adminRS.next()) {
		        return Admin.fromResultSet(adminRS);
		    }
		}
		return null;
	}

	// loads users (ONLY CUSTOMERS) by ID
	public Customer getUserByIdl(String userId) throws SQLException {
		String query = String.format(
				"SELECT u.idUser, u.fullName, u.email, u.password, u.phone, u.address, u.role, c.balance FROM user u JOIN customer c ON u.idUser = c.idCustomer WHERE u.idUser = '%s';",
				userId);

		ResultSet rs = this.connect.execQuery(query);
		rs.next();
		return Customer.fromResultSet(rs);
	}

	// create new user (role = customer)
	public int insertUser(String fullName, String email, String password, String phone, String address)
			throws NoRowsAffectedException, SQLException {
		String query = String.format(
				"INSERT INTO user (fullName, email, password, phone, address, role) "
						+ "VALUES ('%s', '%s', '%s', '%s', '%s', 'customer');",
				fullName, email, password, phone, address);

		HashMap<String, Object> hm = this.connect.execUpdate(query);
		if ((Integer) hm.get("rowsAffected") <= 0)
			throw new NoRowsAffectedException("Failed to create your account. Please try again.");

		Object keyObj = hm.get("generatedKey");
		if (keyObj == null)
			throw new SQLException("Unable to retrieve user ID.");

		return ((Number) keyObj).intValue();
	}

	// insert customer to table customer
	public void insertCustomer(int userId, Double balance) throws NoRowsAffectedException, SQLException {
		String query = String.format("INSERT INTO customer (idCustomer, balance) " + "VALUES ('%d', '%f');", userId,
				balance);

		HashMap<String, Object> hm = this.connect.execUpdate(query);
		if ((Integer) hm.get("rowsAffected") <= 0)
			throw new NoRowsAffectedException("Failed to create your account. Please try again.");
	}

	// edit profile
	public void updateUserById(String userId, String fullName, String phone, String address)
			throws NoRowsAffectedException, SQLException {
		String query = String.format(
				"UPDATE user SET fullName = '%s', phone = '%s', address = '%s' WHERE idUser = '%s';", fullName, phone,
				address, userId);

		HashMap<String, Object> hm = this.connect.execUpdate(query);
		if ((Integer) hm.get("rowsAffected") <= 0)
			throw new NoRowsAffectedException("Balance is not updated. Please try again.");
	}

	// fetch email and password for auth
	public HashMap<String, String> findCredentialsByEmail(String email) throws SQLException {
		String query = String.format("SELECT email, password FROM user WHERE email = '%s';", email);

		ResultSet rs = this.connect.execQuery(query);

		if (!rs.next()) {
			return null;
		}

		HashMap<String, String> hm = new HashMap<>();
		hm.put("email", rs.getString("email"));
		hm.put("password", rs.getString("password"));
		return hm;
	}

	// get all courier
	public ArrayList<Courier> findAllCouriers() throws SQLException {
		String query =
			    "SELECT u.idUser, u.fullName, u.email, u.password, u.phone, u.address, " +
			    "c.vehicleType, c.vehiclePlate " +
			    "FROM user u " +
			    "JOIN courier c ON u.idUser = c.idCourier";

		ResultSet rs = this.connect.execQuery(query);
		ArrayList<Courier> couriers = new ArrayList<>();

		while (rs.next()) {
			couriers.add(Courier.fromResultSet(rs));
		}
		return couriers;
	}
	
	// loads any user's (cust/admin/courier) id
	public User getUserById(String idUser) throws SQLException {
	    String query = "SELECT * FROM user WHERE idUser = '" + idUser + "'";
	    ResultSet rs = connect.execQuery(query);

	    if (!rs.next()) return null;

	    String role = rs.getString("role");

	    return getUserByEmail(rs.getString("email")); 
	}
}