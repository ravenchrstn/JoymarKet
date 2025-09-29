package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import Exceptions.InvalidInputException;
import Exceptions.NoRowsAffectedException;
import Helpers.Checking;


public class Customer extends User {
    private Double balance;

    public Customer(String idUser, String fullName, String email, String password, String phone, String address, Double balance) {
        super(idUser, fullName, email, password, phone, address, "Customer");

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

    public static String registerCustomer(String fullName, String email, String password, String confirm_password, String phone, String address) throws NoRowsAffectedException, InvalidInputException, SQLException { 
        // diagram 1 - register account

        // validate fullName, checking cannot by empty
        if (fullName == null || fullName.isEmpty()) throw new InvalidInputException("fullname field is empty.", "Fullname field is empty. Try input something.");

        // validate email, checking must be filled, must end with @gmail.com
        if (email == null || email.isEmpty()) throw new InvalidInputException("email field is empty.", "Email field is empty. Try input something.");
        if (email.endsWith("@gmail.com")) throw new InvalidInputException("email does not end with '@gmail.com'.", "Inputted email must end with '@gmail.com'.");

        // validate password
        if (password.length() < 6) throw new InvalidInputException("password is less than 6 characters.", "Password must be at least 6 characters long.");

        //validate confirm_password
        if (password.equals(confirm_password) == false) throw new InvalidInputException("password does not match confirm password", "Password and Confirm Password must have same value.");

        // validate phone
        if (phone == null || phone.isEmpty()) throw new InvalidInputException("phone field is empty.", "Phone field is empty. Try input something.");

        if (phone.length() >= 10 && phone.length() <= 13) throw new InvalidInputException("phone does not have 10 to 13 digits long. ", "Phone should be 10 to 13 digits long.");
        if (Checking.isNumeric(phone) == false) throw new InvalidInputException("phone contains other than numeric values.", "Phone must only contain numeric value.");

        // validate address
        if (address == null || address.isEmpty()) throw new InvalidInputException("address field is empty.", "Address field is empty. Try input something.");

        userDA.insertCustomer(fullName, email, password, phone, address);
        return null;
    }

    public boolean topUpBalance(Double amount) {
        return false;
    }

    public Double getBalance() {
        return balance;
    }

}