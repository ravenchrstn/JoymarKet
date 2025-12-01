package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import exception.NoRowsAffectedException;

public class Customer extends User {
    private Double balance;

    public Customer(String idUser, String fullName, String email, String password, String phone, String address, Double balance) {
        super(idUser, fullName, email, password, phone, address, "customer");

        if (balance != 0.0) this.balance = balance;
        else this.balance = 0.0;
    }

    public static Customer fromResultSet(ResultSet rs) throws SQLException {
        return new Customer(rs.getString("idUser"), rs.getString("fullName"), rs.getString("email"), rs.getString("password"), rs.getString("phone"), rs.getString("address"), rs.getDouble("balance"));
    }

    public static Customer getCustomerByIdUser(String idUser) throws SQLException {
        // diagram 7 - checkout and place order
        return userDA.getCustomerByIdUser(idUser);
    }

    public static Double getBalanceByIdUser(String idUser) throws SQLException {
    	return userDA.getBalanceByIdUser(idUser);
    }

    public static void updateBalanceByIdUser(String idUser, Double newBalance) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        userDA.updateBalanceByIdUser(idUser, newBalance);
    }
    
    public static int insert(String fullName, String email, String password, String confirm_password, String phone, String address) throws NoRowsAffectedException, SQLException { 
        // diagram 1 - register account
        return getUserDA().insertUser(fullName, email, password, phone, address);
    }
    
    public static void insertBalance(int userId, Double balance) throws NoRowsAffectedException, SQLException { 
        getUserDA().insertCustomer(userId, balance);
    }
    
    public boolean topUpBalance(Double amount) {
        return false;
    }

    public Double getBalance() {
        return balance;
    }

}