package Models;

import java.sql.SQLException;
import java.util.HashMap;

import DAs.UserDA;
import Exceptions.InvalidInputException;
import Exceptions.NoRowsAffectedException;
import Exceptions.NotFoundException;

public abstract class User {
    protected String idUser, fullName, email, password, phone, address, role;
    protected static final UserDA userDA = UserDA.getUserDA();

    public User(String idUser, String fullName, String email, String password, String phone, String address, String role) {
        this.idUser = idUser;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
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

    public static void login(String email, String password) throws InvalidInputException, NotFoundException, SQLException {
        // login
        if (email.equals("") || email == null) throw new InvalidInputException("email field is empty.", "Email field is empty. Try input something.");

        if (password.equals("") || password == null) throw new InvalidInputException("password field is empty.", "Password field is empty. Try input something..");

        HashMap<String, String> hm = userDA.findCredentialsByEmail(email);
        if (hm.get("email").equals(email) == false) throw new NotFoundException("Email is not found. Please try another email or register.");
        if (hm.get("password").equals(password) == false) throw new NotFoundException("Password is wrong.");
    }

    public static void updateBalanceByIdUser(String idUser, Double newBalance) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        userDA.updateBalanceByIdUser(idUser, newBalance);
    }
}