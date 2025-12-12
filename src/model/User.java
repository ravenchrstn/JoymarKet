package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import da.UserDA;
import exception.InvalidInputException;
import exception.NoRowsAffectedException;
import exception.NotFoundException;

public abstract class User {
	protected String idUser, fullName, email, password, phone, address, role;

	protected static UserDA userDA;

	public static UserDA getUserDA() {
		if (userDA == null) {
			userDA = UserDA.getUserDA();
		}
		return userDA;
	}

	public User(String idUser, String fullName, String email, String password, String phone, String address,
			String role) {
		this.idUser = idUser;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.role = role;
	}

	public String getIdUser() {
		return idUser;
	}

	public String getFullName() {
		return fullName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public String getRole() {
		return role;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public static void updateUserById(String idUser, String fullName, String phone, String address)
			throws NoRowsAffectedException, SQLException {
		userDA.updateUserById(idUser, fullName, phone, address);
	}

	public static HashMap<String, String> getCredentialsByEmail(String email, String password)
			throws InvalidInputException, NotFoundException, SQLException {
		// login
		UserDA userDA = getUserDA();
		HashMap<String, String> hm = userDA.findCredentialsByEmail(email);

		if (hm == null || hm.isEmpty()) {
			throw new NotFoundException("Email is not found. Please try another email or register.");
		}

		return hm;
	}

	public static void reduceBalance(String idUser, double amount) throws SQLException, NoRowsAffectedException {
		UserDA userDA = getUserDA();
		double currentBalance = userDA.getBalanceByIdUser(idUser);

		double newBalance = currentBalance - amount;
		if (newBalance < 0) {
			throw new NoRowsAffectedException("Insufficient balance.");
		}

		userDA.updateBalanceByIdUser(idUser, newBalance);
	}
	
	public static User getUserByEmail(String email) throws NotFoundException {
		UserDA userDA = getUserDA();
		try {
			return userDA.getUserByEmail(email);
		} catch (SQLException e) {
			throw new NotFoundException("No user found!");
		}
	}
}