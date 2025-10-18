package model;

import java.sql.SQLException;
import java.util.HashMap;

import da.UserDA;
import exception.InvalidInputException;
import exception.NotFoundException;

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

    public static HashMap<String, String> getCredentialsByEmail(String email, String password) throws InvalidInputException, NotFoundException, SQLException {
        // login
        return userDA.findCredentialsByEmail(email);
    }
}